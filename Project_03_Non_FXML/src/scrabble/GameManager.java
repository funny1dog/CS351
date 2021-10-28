package scrabble;
/**
 *  Jiajun Guo
 *
 *  This class is the logic
 *
 */
import java.util.*;

import static scrabble.Board.*;

public class GameManager {

    static Map<LetterContainer, Boolean> containersWithCommittedLetters = new HashMap<>();
    private final Board board;
    private static final Set<Character> ALPHABET = new HashSet<>();
    static WordChecker wordChecker = new WordChecker();
    private static Map<LetterContainer, Set<Character>> acrossPlaysForEmptySquares = new HashMap<>();
    private static Map<LetterContainer, Set<Character>> downPlaysForEmptySquares = new HashMap<>();
    private static Map<Integer, LetterContainer> columnFifteenSentinels = new HashMap<>();
    private static Map<Integer, LetterContainer> rowFifteenSentinels = new HashMap<>();
    private static Set<LetterContainer> sentinels = new HashSet<>();
    private static Map<LetterContainer, Map<Character, Integer>> acrossAnchorCharacterScores = new HashMap<>();
    private static Map<LetterContainer, Map<Character, Integer>> downAnchorCharacterScores = new HashMap<>();
    /**
     * Constructor GameManger
     * @param board
     */
    GameManager(Board board) {
        this.board = board;
        buildAlphabetSet(ALPHABET);
        placeRowColumnFifteenSentinels();
    }

    private static void buildAlphabetSet(final Set<Character> characters) {
        characters.add('a');
        characters.add('b');
        characters.add('c');
        characters.add('d');
        characters.add('e');
        characters.add('f');
        characters.add('g');
        characters.add('h');
        characters.add('i');
        characters.add('j');
        characters.add('k');
        characters.add('l');
        characters.add('m');
        characters.add('n');
        characters.add('o');
        characters.add('p');
        characters.add('q');
        characters.add('r');
        characters.add('s');
        characters.add('t');
        characters.add('u');
        characters.add('v');
        characters.add('w');
        characters.add('x');
        characters.add('y');
        characters.add('z');
    }

    private static void removeNewlyPopulatedFromFringeSet(LetterContainer letterContainer) {
        acrossPlaysForEmptySquares.remove(letterContainer);
        downPlaysForEmptySquares.remove(letterContainer);
    }

    private static int getMoveScore(Move move) {
        int wordScore = 0;
        int numTripleWordBonuses = 0;
        int numDoubleWordBonuses = 0;
        for (TileMove tileMove : move.getTileMoves()) {
            int letterScore = LetterBag.letterScoreMappings.get(tileMove.getCharacter());
            // check move mapping to see if this letter was placed from rack, if so apply bonus
            if (tileMove.isFromRack()) {
                switch (tileMove.getDestination().getBonusText()) {
                    case DOUBLE_WORD_SCORE:
                    case STAR:
                        numDoubleWordBonuses++;
                        break;
                    case TRIPLE_LETTER_SCORE:
                        letterScore = letterScore * 3;
                        break;
                    case DOUBLE_LETTER_SCORE:
                        letterScore = letterScore * 2;
                        break;
                    case TRIPLE_WORD_SCORE:
                        numTripleWordBonuses++;
                        break;
                }
            }
            wordScore += letterScore;
        }
        wordScore = wordScore * ((numDoubleWordBonuses != 0) ? 2 * numDoubleWordBonuses : 1) *
                ((numTripleWordBonuses != 0) ? 3 * numTripleWordBonuses : 1);
        return wordScore;
    }

    private static Set<Character> restrictBySurroundingWords(String wordBefore, String wordAfter, Set<Character> restrictedSet) {
        if (wordBefore == null) {
            wordBefore = "";
        }

        if (wordAfter == null) {
            wordAfter = "";
        }

        if (wordAfter.equals("") && wordBefore.equals("")) {
            return restrictedSet;
        }

        for (Character c : ALPHABET) {
            WordChecker.TrieNode result = wordChecker.search(wordBefore + c.toString() + wordAfter);
            if (null == result || !result.endOfWord ) {
                restrictedSet.remove(c);
            }
        }

        return restrictedSet;
    }

    private void placeRowColumnFifteenSentinels() {
        for (int j = 0; j < 15; j++) {
            LetterContainer colSentinel = new LetterContainer(null, null, j, 15, board);
            Set<Character> emptyPlayableSet = new HashSet<>();
            acrossPlaysForEmptySquares.put(colSentinel, emptyPlayableSet);
            columnFifteenSentinels.put(j, colSentinel);
            LetterContainer rowSentinel = new LetterContainer(null, null, 15, j, board);
            downPlaysForEmptySquares.put(rowSentinel, emptyPlayableSet);
            rowFifteenSentinels.put(j, rowSentinel);
            sentinels.add(colSentinel);
            sentinels.add(rowSentinel);
        }
    }

    // Naively search all four squares adjacent to each newly committed tile for its neighbors, ignores diagonals
    private Set<LetterContainer> getEmptyAdjacentSquares(LetterContainer emptySquare) {
        Set<LetterContainer> adjacentSquares = new HashSet<>();
        int row = emptySquare.getLocation().getRow();
        int col = emptySquare.getLocation().getCol();
        int oneRowDown = row < 14 ? row + 1 : -1;
        int oneRowUp = row > 0 ? row - 1 : -1;
        int oneColRight = col < 14 ? col + 1 : -1;
        int oneColLeft = col > 0 ? col - 1 : -1;
        // clockwise bounds check:
        // ^
        if (oneRowUp != -1 && !board.getRefToSquareByRowColumn(oneRowUp, col).containsLetter) {
            adjacentSquares.add(board.getRefToSquareByRowColumn(oneRowUp, col));
        }
        // ->
        if (oneColRight != -1 && !board.getRefToSquareByRowColumn(row, oneColRight).containsLetter) {
            adjacentSquares.add(board.getRefToSquareByRowColumn(row, oneColRight));
        }
        // down
        if (oneRowDown != -1 && !board.getRefToSquareByRowColumn(oneRowDown, col).containsLetter) {
            adjacentSquares.add(board.getRefToSquareByRowColumn(oneRowDown, col));
        }
        // <-
        if (oneColLeft != -1 && !board.getRefToSquareByRowColumn(row, oneColLeft).containsLetter) {
            adjacentSquares.add(board.getRefToSquareByRowColumn(row, oneColLeft));
        }

        return adjacentSquares;
    }

    /** Compute the playable chars for both across and down moves for each empty (anchor) square.
    Calls saveAcrossAnchorCrossScores and saveDownAnchorCrossScores for each anchor*/
    private void restrictEmptySquareAndSaveCrossCheckScore(LetterContainer anchorSquare) {
        LetterContainer.Location currentLocation = anchorSquare.getLocation();
        Set<Character> restrictedAcrossSet = new HashSet<>();
        buildAlphabetSet(restrictedAcrossSet);
        Set<Character> restrictedDownSet = new HashSet<>();
        buildAlphabetSet(restrictedDownSet);
        // get surrounding words
        String wordAbove = getWordAbove(currentLocation);
        String wordBelow = getWordBelow(currentLocation);
        String wordRight = getWordRight(currentLocation);
        String wordLeft = getWordLeft(currentLocation);
        // generate restricted sets for cross words
        restrictedAcrossSet = restrictBySurroundingWords(wordAbove, wordBelow, restrictedAcrossSet);
        restrictedDownSet = restrictBySurroundingWords(wordLeft, wordRight, restrictedDownSet);
        // save crossScores for the anchor
        acrossPlaysForEmptySquares.put(anchorSquare, restrictedAcrossSet);
        saveAcrossAnchorCrossScores(anchorSquare, wordAbove, wordBelow);
        downPlaysForEmptySquares.put(anchorSquare, restrictedDownSet);
        saveDownAnchorCrossScore(anchorSquare, wordRight, wordLeft);
    }

    /** Stores the value that would be scored for each playable char in anchor, when making a across move through anchor */
    private void saveDownAnchorCrossScore(LetterContainer emptySquare, String wordRight, String wordLeft) {
        Map<Character, Integer> charScoresForDownAnchor = new HashMap<>();
        for (Character c : downPlaysForEmptySquares.get(emptySquare)) {
            charScoresForDownAnchor.put(c, getWordScore(wordLeft + c + wordRight));
        }
        downAnchorCharacterScores.put(emptySquare, charScoresForDownAnchor);
    }

    /** Stores the value that would be scored for each playable char in anchor, when making a down move through anchor */
    private void saveAcrossAnchorCrossScores(LetterContainer anchorSquare, String wordAbove, String wordBelow) {
        Map<Character, Integer> charScoresForAcrossAnchor = new HashMap<>();
        for (Character c : acrossPlaysForEmptySquares.get(anchorSquare)) {
            charScoresForAcrossAnchor.put(c, getWordScore(wordAbove + c + wordBelow));
        }
        acrossAnchorCharacterScores.put(anchorSquare, charScoresForAcrossAnchor);
    }

    /** Returns the sum of the scores for each letter in a word, no bonuses applied */
    private Integer getWordScore(String word) {
        if (word == null || word.equals("")) {
            return 0;
        } else {
            int score = 0;
            for (char c : word.toCharArray()) {
                score += LetterBag.letterScoreMappings.get(c);
            }
            return score;
        }
    }

    /** Returns a string representing the contiguous tiles left up to the edge of the board or an empty square */
    private String getWordLeft(LetterContainer.Location location) {
        final StringBuilder sb = new StringBuilder();
        if (location.getCol() != 0) {
            int offset = -1;
            char letterLeft = getLetterInRowByOffSet(location, offset);
            if (letterLeft != ' ') {
                sb.append(letterLeft);
                while (offset > (-1 * location.getCol()) && getLetterInRowByOffSet(location, --offset) != ' ') {
                    sb.append(getLetterInRowByOffSet(location, offset));
                }
                return sb.reverse().toString();
            }
        }
        return null;
    }

    /** Returns a string representing the contiguous tiles above up to the edge of the board or an empty square */
    private String getWordBelow(LetterContainer.Location location) {
        final StringBuilder sb = new StringBuilder();
        if (location.getRow() != 14) {
            int offset = 1;
            char letterBelow = getLetterInColByOffSet(location, offset);
            if (letterBelow != ' ') {
                sb.append(letterBelow);
                while (offset < (14 - location.getRow()) && getLetterInColByOffSet(location, ++offset) != ' ') {
                    sb.append(getLetterInColByOffSet(location, offset));
                }
                return sb.toString();
            }
        }
        return null;
    }

    /** Returns a string representing the contiguous tiles right up to the edge of the board or an empty square */
    private String getWordRight(LetterContainer.Location location) {
        final StringBuilder sb = new StringBuilder();
        if (location.getCol() != 14) {
            int offset = 1;
            char letterRight = getLetterInRowByOffSet(location, offset);
            if (letterRight != ' ') {
                sb.append(letterRight);
                while (offset < (14 - location.getCol()) && getLetterInRowByOffSet(location, ++offset) != ' ') {
                    sb.append(getLetterInRowByOffSet(location, offset));
                }
                return sb.toString();
            }
        }
        return null;
    }

    /** Returns a string representing the contiguous tiles left up to the edge of the board or an empty square */
    private String getWordAbove(LetterContainer.Location location) {
        final StringBuilder sb = new StringBuilder();
        if (location.getRow() != 0) {
            int offset = -1;
            char letterAbove = getLetterInColByOffSet(location, offset);
            if (letterAbove != ' ') {
                sb.append(letterAbove);
                while (offset > (-1 * location.getRow())
                        && getLetterInColByOffSet(location, --offset) != ' ') {
                    sb.append(getLetterInColByOffSet(location, offset));
                }
            }
            return sb.reverse().toString();
        }
        return null;
    }

    private List<LetterContainer> getLCsOfWordLeft(LetterContainer.Location location) {
        int row = location.getRow();
        int col = location.getCol();
        List<LetterContainer> lcsOfWordToLeft = new ArrayList<>();
        if (location.getCol() != 0) {
            int offset = -1;
            char letterLeft = getLetterInRowByOffSet(location, offset);
            if (letterLeft != ' ') {
                lcsOfWordToLeft.add(board.getRefToSquareByRowColumn(row, col + offset));
                while (offset > (-1 * col) && getLetterInRowByOffSet(location, --offset) != ' ') {
                    lcsOfWordToLeft.add(board.getRefToSquareByRowColumn(row, col + offset));
                }
            }
            return lcsOfWordToLeft;
        }
        return null; // edge of board
    }

    private List<LetterContainer> getLCsOfWordAbove(LetterContainer.Location location) {
        int row = location.getRow();
        int col = location.getCol();
        List<LetterContainer> lcsOfWordAbove = new ArrayList<>();
        if (location.getRow() != 0) {
            int offset = -1;
            char letterAbove = getLetterInColByOffSet(location, offset);
            if (letterAbove != ' ') {
                lcsOfWordAbove.add(board.getRefToSquareByRowColumn(row + offset, col));
                while (offset > (-1 * row) && getLetterInColByOffSet(location, --offset) != ' ') {
                    lcsOfWordAbove.add(board.getRefToSquareByRowColumn(row + offset, col));
                }
            }
            return lcsOfWordAbove;
        }
        return null;
    }

    private Character getLetterInColByOffSet(LetterContainer.Location location, int offset) {
        return board.getVirtualBoard()[location.getRow() + offset][location.getCol()];
    }

    private Character getLetterInRowByOffSet(LetterContainer.Location location, int offset) {
        return board.getVirtualBoard()[location.getRow()][location.getCol() + offset];
    }

    /** Returns the number of the associated WordChecker.TrieNode index corresponding to the given letter*/
    private int getChildIndexForLetter(String letter) {
        if (letter.equalsIgnoreCase("A")) {
            return 0;
        } else if (letter.equalsIgnoreCase("B")) {
            return 1;
        } else if (letter.equalsIgnoreCase("C")) {
            return 2;
        } else if (letter.equalsIgnoreCase("D")) {
            return 3;
        } else if (letter.equalsIgnoreCase("E")) {
            return 4;
        } else if (letter.equalsIgnoreCase("F")) {
            return 5;
        } else if (letter.equalsIgnoreCase("G")) {
            return 6;
        } else if (letter.equalsIgnoreCase("H")) {
            return 7;
        } else if (letter.equalsIgnoreCase("I")) {
            return 8;
        } else if (letter.equalsIgnoreCase("j")) {
            return 9;
        } else if (letter.equalsIgnoreCase("k")) {
            return 10;
        } else if (letter.equalsIgnoreCase("l")) {
            return 11;
        } else if (letter.equalsIgnoreCase("m")) {
            return 12;
        } else if (letter.equalsIgnoreCase("n")) {
            return 13;
        } else if (letter.equalsIgnoreCase("o")) {
            return 14;
        } else if (letter.equalsIgnoreCase("p")) {
            return 15;
        } else if (letter.equalsIgnoreCase("q")) {
            return 16;
        } else if (letter.equalsIgnoreCase("r")) {
            return 17;
        } else if (letter.equalsIgnoreCase("s")) {
            return 18;
        } else if (letter.equalsIgnoreCase("t")) {
            return 19;
        } else if (letter.equalsIgnoreCase("u")) {
            return 20;
        } else if (letter.equalsIgnoreCase("v")) {
            return 21;
        } else if (letter.equalsIgnoreCase("w")) {
            return 22;
        } else if (letter.equalsIgnoreCase("x")) {
            return 23;
        } else if (letter.equalsIgnoreCase("y")) {
            return 24;
        } else if (letter.equalsIgnoreCase("z")) {
            return 25;
        }

        return -1;
    }

    /** Returns the number of empty squares to the contiguous above that have non-null cross check sets*/
    private int getNumNonAnchorsInColAbove(LetterContainer anchor) {
        LetterContainer.Location location = anchor.getLocation();
        Map<Integer, Set<LetterContainer>> anchorsInEachCol = getAnchorsInEachCol();
        int row = location.getRow();
        int col = location.getCol();
        if (row == 0) {
            return 0;
        }

        Set<LetterContainer> anchorsInSameCol = anchorsInEachCol.get(col);
        if (anchorsInSameCol == null) {
            return 0;
        }
        int max = 0;
        for (LetterContainer a : anchorsInSameCol) {
            if (a.getLocation().getRow() < row && a.getLocation().getRow() > max) {
                max = a.getLocation().getRow();
            }
        }

        if (max == 0) {
            return row;
        }

        return row - max - 1;
    }

    /** Returns the number of empty squares to the contiguous left that have non-null cross check sets*/
    private int getNumNonAnchorsInRowToLeft(LetterContainer anchor) {
        LetterContainer.Location location = anchor.getLocation();
        Map<Integer, Set<LetterContainer>> anchorsInEachRow = getAnchorsInEachRow();
        int row = location.getRow();
        int col = location.getCol();
        if (col == 0) {
            return 0;
        }

        Set<LetterContainer> anchorsInSameRow = anchorsInEachRow.get(row);
        if (anchorsInSameRow == null) {
            return 0;
        }
        int max = 0;
        for (LetterContainer a : anchorsInSameRow) {
            if (a.getLocation().getCol() < col && a.getLocation().getCol() > max) {
                max = a.getLocation().getCol();
            }
        }

        if (max == 0) {
            return col;
        }
        return col - max - 1;
    }

    private Map<Integer, Set<LetterContainer>> getAnchorsInEachRow() {
        Set<LetterContainer> anchors = findAcrossAnchors().keySet();
        Map<Integer, Set<LetterContainer>> anchorsInEachRow = new HashMap<>();
        for (int k = 0; k < 14; k++) {
            HashSet<LetterContainer> anchorsInThisRow = new HashSet<>();
            for (LetterContainer a : anchors) {
                if (a.getLocation().getRow() == k) {
                    anchorsInThisRow.add(a);
                }
            }
            anchorsInEachRow.put(k, anchorsInThisRow);
        }
        return anchorsInEachRow;
    }

    private Map<Integer, Set<LetterContainer>> getAnchorsInEachCol() {
        Set<LetterContainer> anchors = findDownAnchors().keySet();
        Map<Integer, Set<LetterContainer>> anchorsInEachCol = new HashMap<>();
        for (int k = 0; k < 14; k++) {
            HashSet<LetterContainer> anchorsInThisCol = new HashSet<>();
            for (LetterContainer a : anchors) {
                if (a.getLocation().getCol() == k) {
                    anchorsInThisCol.add(a);
                }
            }
            anchorsInEachCol.put(k, anchorsInThisCol);
        }
        return anchorsInEachCol;
    }

    private Map<LetterContainer, Set<Character>> findAcrossAnchors() {
        return acrossPlaysForEmptySquares;
    }

    private Map<LetterContainer, Set<Character>> findDownAnchors() {
        return downPlaysForEmptySquares;
    }

    private void placeHighestScoringWord(Move highestScoringMove, Player player) {
        if (highestScoringMove != null) {
            for (TileMove tileMove : highestScoringMove.getTileMoves()) {
                if (tileMove.isFromRack()) {
                    player.removeLetterFromRack(tileMove.getCharacter().toString());
                    LetterContainer target = tileMove.getDestination();
                    target.addLetter(tileMove.getCharacter().toString());
                }
            }
        }
    }

    private void updatePlayerScore(Player player, int highScore) {
        player.updateScore(highScore);
        System.out.println(player.getScore());
    }

    /* Records the tileMoves of word already on board, need to save these for scoring later, in-case they are extended*/
    private void addTileMovesOfExistingWord(Move newMove, List<LetterContainer> lcsOfWordBefore) {
        List<TileMove> wordOnTheBoard = new ArrayList<>();
        int size = lcsOfWordBefore.size();
        for (int k = size - 1; k >= 0; k--) {
            LetterContainer letterContainer = lcsOfWordBefore.get(k);
            TileMove tileThatIsAlreadyOnBoard = new TileMove(false, letterContainer, letterContainer.getText().charAt(0));
            wordOnTheBoard.add(tileThatIsAlreadyOnBoard);
        }
        newMove.setTileMoves(wordOnTheBoard);
    }

    /** Updates the character restrictions around the adjacent squares of a newly placed word*/
    private void updatePlayableCharsForSquaresAroundWord(Set<LetterContainer> containersOfMove) {
        for (LetterContainer letterOfWord : containersOfMove) {
            Set<LetterContainer> emptyAdjacentSquares = getEmptyAdjacentSquares(letterOfWord);
            for (LetterContainer emptyAdjacentSquare : emptyAdjacentSquares) {
                restrictEmptySquareAndSaveCrossCheckScore(emptyAdjacentSquare);
            }
        }
    }

    private void findAllPossibleDownMoves(Player player, LetterContainer anchor) {
        Move newMove = new Move();
        String wordAbove = getWordAbove(anchor.getLocation());
        List<LetterContainer> letterContainersOfWordAbove = getLCsOfWordAbove(anchor.getLocation());
        if (wordAbove != null && letterContainersOfWordAbove != null && !wordAbove.equals("")) {
            addTileMovesOfExistingWord(newMove, letterContainersOfWordAbove);
            extendDown(wordAbove, wordChecker.search(wordAbove), anchor, player, false);
        }
        else {
            int limitAbove = getNumNonAnchorsInColAbove(anchor);
            getAbovePart(anchor, "", wordChecker.getRoot(), limitAbove, player);
        }
    }

    private void getAbovePart(LetterContainer anchor, String wordSoFar, WordChecker.TrieNode currentNode, int limit, Player player) {
        extendDown(wordSoFar, currentNode, anchor, player, true);
        if (limit > 0) {
            for (int j = 0; j < currentNode.children.length; j++) {
                WordChecker.TrieNode child = currentNode.children[j];
                if (child == null) {
                    continue;
                }
                Character characterAtChildNode = WordChecker.toChar(j);
                if (player.letterRackContainsLetter(characterAtChildNode.toString())) {
                    LetterContainer removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                    getAbovePart(anchor, wordSoFar + characterAtChildNode, child, limit - 1, player);
                    player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                }
            }
        }
    }

    private void extendDown(String wordSoFar, WordChecker.TrieNode node, LetterContainer currentSquare, Player player, Boolean anchorFilled) {
        if (currentSquare != null && !currentSquare.containsLetter && node != null) {
            if (node.endOfWord && anchorFilled) {
                recordLegalDownMove(wordSoFar, player, currentSquare);
            }

            for (int j = 0; j < node.children.length; j++) {
                WordChecker.TrieNode child = node.children[j];
                if (child != null) {
                    Character characterAtChildNode = WordChecker.toChar(j);

                    if (player.letterRackContainsLetter(characterAtChildNode.toString()) &&
                            (downPlaysForEmptySquares.get(currentSquare) == null ||
                                    downPlaysForEmptySquares.get(currentSquare).contains(characterAtChildNode))) {

                        LetterContainer removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                        LetterContainer nextSquare = board.getNextDown(currentSquare.getLocation());
                        extendDown(wordSoFar + characterAtChildNode, child, nextSquare, player, true);
                        player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                    }
                }
            }
        } else if (currentSquare != null && node != null){
            extendDownCurrentOccupied(wordSoFar, node, currentSquare, player);
        }
    }

    /** While extending down, we found a square that is occupied, save it and continue extending down*/
    private void extendDownCurrentOccupied(String wordSoFar, WordChecker.TrieNode node, LetterContainer currentSquare, Player player) {
        String letter = currentSquare.getText();
        if (node.children[getChildIndexForLetter(letter)] != null) {
            WordChecker.TrieNode child = node.children[getChildIndexForLetter(letter)];
            LetterContainer nextSquare = board.getNextDown(currentSquare.getLocation());
            extendDown(wordSoFar + letter, child, nextSquare, player,true);
        }
    }

    private void recordLegalDownMove(String word, Player player, LetterContainer lastContainerOfWord) {
        List<TileMove> tileMoves = new ArrayList<>();
        int row = lastContainerOfWord.getLocation().getRow() - 1;
        int col = lastContainerOfWord.getLocation().getCol();
        int crossScoreTotal = 0;
        boolean containsLetterFromRack = false;
        boolean containsLetterOnBoard = false;
        for (int i = 0; i < word.length(); i++) {
            LetterContainer currentLetterInWord = board.getRefToSquareByRowColumn(row - i,col);
            boolean fromRack = !board.getRefToSquareByRowColumn(row - i,col).containsLetter;
            if (fromRack) {
                containsLetterFromRack = true;
            }
            if (!fromRack) {
                containsLetterOnBoard = true;
                Map<Character, Integer> charScores = downAnchorCharacterScores.get(currentLetterInWord);
                if (charScores != null) {
                    if (charScores.get(word.charAt(word.length() - 1 - i)) != null) {
                        crossScoreTotal += charScores.get(word.charAt(word.length() - 1 - i));
                    }
                }
            }
            TileMove tileMove = new TileMove(fromRack, currentLetterInWord, word.charAt(word.length() - 1 - i));
            tileMoves.add(tileMove);
        }

        // a legal play requires at least one letter from the players rack and one on the board
        if (containsLetterFromRack && containsLetterOnBoard) {
            Move move = new Move(tileMoves);
            player.addPlayableMove(move, getMoveScore(move) + crossScoreTotal);
        }
    }

    private void findAllPossibleAcrossMoves(Player player, LetterContainer anchor) {
        if (sentinels.contains(anchor)) {
            return;
        }
        Move newMove = new Move(); //copied later to save each playable moves
        // first check for a word to the left
        String wordLeft = getWordLeft(anchor.getLocation());
        List<LetterContainer> letterContainersOfWordToLeft = getLCsOfWordLeft(anchor.getLocation());
        if (wordLeft != null && letterContainersOfWordToLeft != null && !wordLeft.equals("")) {
            addTileMovesOfExistingWord(newMove, letterContainersOfWordToLeft);
            extendRight(wordLeft, wordChecker.search(wordLeft), anchor, player, false);
        }
        // find all left parts
        else {
            int limitToTheLeft = getNumNonAnchorsInRowToLeft(anchor);
            getLeftPart(anchor, "", wordChecker.getRoot(), limitToTheLeft, player);
        }
    }

    private void getLeftPart(LetterContainer anchor, String wordSoFar, WordChecker.TrieNode currentNode, int limit, Player player) {
        extendRight(wordSoFar, currentNode, anchor, player, true);
        if (limit > 0) {
            for (int j = 0; j < currentNode.children.length; j++) {
                WordChecker.TrieNode child = currentNode.children[j];
                if (child == null) {
                    continue;
                }
                Character characterAtChildNode = WordChecker.toChar(j);
                if (player.letterRackContainsLetter(characterAtChildNode.toString())) {
                    LetterContainer removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                    getLeftPart(anchor, wordSoFar + characterAtChildNode, child, limit - 1, player);
                    player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                }
            }
        }
    }

    private void extendRight(String wordSoFar, WordChecker.TrieNode node, LetterContainer currentSquare, Player player, Boolean anchorFilled) {
        if (currentSquare != null && !currentSquare.containsLetter && node != null) {
            if (anchorFilled && node.endOfWord) {
                recordLegalAcrossMove(wordSoFar, player, currentSquare);
            }

            for (int j = 0; j < node.children.length; j++) {
                WordChecker.TrieNode child = node.children[j];
                if (child != null) {
                    Character characterAtChildNode = WordChecker.toChar(j);

                    if (player.letterRackContainsLetter(characterAtChildNode.toString()) &&
                            (acrossPlaysForEmptySquares.get(currentSquare) == null ||
                            acrossPlaysForEmptySquares.get(currentSquare).contains(characterAtChildNode))) {

                        LetterContainer removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                        LetterContainer nextSquare = board.getNextRight(currentSquare.getLocation());
                        extendRight(wordSoFar + characterAtChildNode, child, nextSquare, player, true);
                        player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                    }
                }
            }
        } else if (currentSquare != null && node != null){
            extendRightCurrentOccupied(wordSoFar, node, currentSquare, player);
        }
    }

    private void recordLegalAcrossMove(String word, Player player, LetterContainer lastContainerOfWord) {
        List<TileMove> tileMoves = new ArrayList<>();
        int row = lastContainerOfWord.getLocation().getRow();
        int col = lastContainerOfWord.getLocation().getCol() - 1;
        int crossScoreTotal = 0;
        boolean containsLetterFromRack = false;
        boolean containsLetterOnBoard = false;
        for (int i = 0; i < word.length(); i++) {
            LetterContainer currentLetterInWord = board.getRefToSquareByRowColumn(row,col - i);
            boolean fromRack = !board.getRefToSquareByRowColumn(row,col - i).containsLetter;
            if (fromRack) {
                containsLetterFromRack = true;
            } else {
                containsLetterOnBoard = true;
                Map<Character, Integer> charScores = acrossAnchorCharacterScores.get(currentLetterInWord);
                if (charScores != null) {
                    if (charScores.get(word.charAt(word.length() - 1 - i)) != null) {
                        crossScoreTotal += charScores.get(word.charAt(word.length() - 1 - i));
                    }
                }
            }
            TileMove tileMove = new TileMove(fromRack, currentLetterInWord, word.charAt(word.length() - 1 - i));
            tileMoves.add(tileMove);
        }

        // a legal play requires at least one letter from the players rack and one on the board
        if (containsLetterFromRack && containsLetterOnBoard) {
            Move move = new Move(tileMoves);
            player.addPlayableMove(move, getMoveScore(move) + crossScoreTotal);
        }
    }

    /* While extending right, we found a square that is occupied, save it and continue extending right*/
    private void extendRightCurrentOccupied(String wordSoFar, WordChecker.TrieNode node, LetterContainer currentSquare,
                                            Player player) {
        String letter = currentSquare.getText();
        if (node.children[getChildIndexForLetter(letter)] != null) {
            WordChecker.TrieNode child = node.children[getChildIndexForLetter(letter)];
            LetterContainer nextSquare = board.getNextRight(currentSquare.getLocation());
            extendRight(wordSoFar + letter, child, nextSquare, player, true);
        }
    }


/////////// PUBLIC METHODS /////////////////////////////////////////////////////////////////////////////////////////////

    /** Adds a letter to the virtual copy of the UI board.*/
    static void clearSpaceOnBoard(LetterContainer letterContainer) {
        int col = letterContainer.getLocation().getCol();
        int row = letterContainer.getLocation().getRow();
        virtualBoard[row][col] = ' ';
        newlyPopulatedContainers.remove(letterContainer);
    }

    /** Removes a letter in the virtual copy of the UI board.*/
    static void addLetterToRowColOnBoard(char c, LetterContainer letterContainer) {
        newlyPopulatedContainers.add(letterContainer);
        GameManager.containersWithCommittedLetters.put(letterContainer, true);
        int row = letterContainer.getLocation().getRow();
        int col = letterContainer.getLocation().getCol();
        if (virtualBoard[row][col] == ' ') {
            virtualBoard[row][col] = c;
        }
    }

    static LetterContainer getColumnFifteenSentinels(int row) {
        return columnFifteenSentinels.get(row);
    }

    static LetterContainer getRowFifteenSentinels(int col) {
        return rowFifteenSentinels.get(col);
    }

    /** Disables drag and drop on letters that have been committed*/
    void commitAllNewlyPopulatedContainers() {
        for (LetterContainer letterContainer : newlyPopulatedContainers) {
            GameManager.containersWithCommittedLetters.put(letterContainer, true);
            letterContainer.setDisable(true);
            removeNewlyPopulatedFromFringeSet(letterContainer);
        }
        updatePlayableCharsForSquaresAroundWord(newlyPopulatedContainers);
        newlyPopulatedContainers.clear();
    }

    /** Finds the entire sets of possible across and down moves, places the highest scoring one on the board and
     * updates the players score to reflect the move.*/
    void doBestPossibleMove(Player player) {
        player.clearPlayableMoves();
        Map<LetterContainer, Set<Character>> potentialAcrosses = findAcrossAnchors();
        for (LetterContainer anchor : potentialAcrosses.keySet()) {
            if (!potentialAcrosses.get(anchor).isEmpty()) {
                findAllPossibleAcrossMoves(player, anchor);
            }
        }

        Map<LetterContainer, Set<Character>> potentialDowns = findDownAnchors();
        for (LetterContainer anchor : potentialDowns.keySet()) {
            if (!potentialDowns.get(anchor).isEmpty()) {
                findAllPossibleDownMoves(player, anchor);
            }
        }

        Move highestScoringMove = null;
        int highScore = 0;
        for (Map.Entry move : player.getPlayableMoves().entrySet()) {
            if ((Integer) move.getValue() > highScore) {
                highScore = (Integer) move.getValue();
                highestScoringMove = (Move) move.getKey();
            }
        }

        placeHighestScoringWord(highestScoringMove, player);
        updatePlayerScore(player, highScore);
    }
}
