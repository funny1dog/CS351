package Scrabble;
import java.util.*;
import static Scrabble.GameBoard.*;

public class ScrabbleGameLogic {

    static Map<Letters, Boolean> containersWithCommittedLetters = new HashMap<>();
    private final GameBoard gameBoard;
    private static final Set<Character> ALPHABET = new HashSet<>();
    static SpellChecker spellChecker = new SpellChecker();
    private static Map<Letters, Set<Character>> acrossPlaysForEmptySquares = new HashMap<>();
    private static Map<Letters, Set<Character>> downPlaysForEmptySquares = new HashMap<>();
    private static Map<Integer, Letters> columnFifteenSentinels = new HashMap<>();
    private static Map<Integer, Letters> rowFifteenSentinels = new HashMap<>();
    private static Set<Letters> sentinels = new HashSet<>();
    private static Map<Letters, Map<Character, Integer>> acrossAnchorCharacterScores = new HashMap<>();
    private static Map<Letters, Map<Character, Integer>> downAnchorCharacterScores = new HashMap<>();
    /**
     * Constructor GameManger
     * @param gameBoard
     */
    ScrabbleGameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
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

    private static void removeNewlyPopulatedFromFringeSet(Letters letters) {
        acrossPlaysForEmptySquares.remove(letters);
        downPlaysForEmptySquares.remove(letters);
    }

    private static int getMoveScore(InGameMove inGameMove) {
        int wordScore = 0;
        int numTripleWordBonuses = 0;
        int numDoubleWordBonuses = 0;
        for (TileMove tileMove : inGameMove.getTileMoves()) {
            int letterScore = LetterBag.letterScoreMappings.get(tileMove.getCharacter());
            // check move mapping to see if this letter was placed from rack, if so apply bonus
            if (tileMove.isFromRack()) {
                switch (tileMove.getDestination().getBonusText()) {
                    case wordDoubleModifier:
                    case STAR:
                        numDoubleWordBonuses++;
                        break;
                    case letterTripleModifier:
                        letterScore = letterScore * 3;
                        break;
                    case letterDoubleModifier:
                        letterScore = letterScore * 2;
                        break;
                    case wordTripleModifier:
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
            SpellChecker.TrieNode result = spellChecker.search(wordBefore + c.toString() + wordAfter);
            if (null == result || !result.endOfWord ) {
                restrictedSet.remove(c);
            }
        }

        return restrictedSet;
    }

    private void placeRowColumnFifteenSentinels() {
        for (int j = 0; j < 15; j++) {
            Letters colSentinel = new Letters(null, null, j, 15, gameBoard);
            Set<Character> emptyPlayableSet = new HashSet<>();
            acrossPlaysForEmptySquares.put(colSentinel, emptyPlayableSet);
            columnFifteenSentinels.put(j, colSentinel);
            Letters rowSentinel = new Letters(null, null, 15, j, gameBoard);
            downPlaysForEmptySquares.put(rowSentinel, emptyPlayableSet);
            rowFifteenSentinels.put(j, rowSentinel);
            sentinels.add(colSentinel);
            sentinels.add(rowSentinel);
        }
    }

    // Naively search all four squares adjacent to each newly committed tile for its neighbors, ignores diagonals
    private Set<Letters> getEmptyAdjacentSquares(Letters emptySquare) {
        Set<Letters> adjacentSquares = new HashSet<>();
        int row = emptySquare.getLocation().getRow();
        int col = emptySquare.getLocation().getCol();
        int oneRowDown = row < 14 ? row + 1 : -1;
        int oneRowUp = row > 0 ? row - 1 : -1;
        int oneColRight = col < 14 ? col + 1 : -1;
        int oneColLeft = col > 0 ? col - 1 : -1;
        // clockwise bounds check:
        // ^
        if (oneRowUp != -1 && !gameBoard.girdCoordinates(oneRowUp, col).containsLetter) {
            adjacentSquares.add(gameBoard.girdCoordinates(oneRowUp, col));
        }
        // ->
        if (oneColRight != -1 && !gameBoard.girdCoordinates(row, oneColRight).containsLetter) {
            adjacentSquares.add(gameBoard.girdCoordinates(row, oneColRight));
        }
        // down
        if (oneRowDown != -1 && !gameBoard.girdCoordinates(oneRowDown, col).containsLetter) {
            adjacentSquares.add(gameBoard.girdCoordinates(oneRowDown, col));
        }
        // <-
        if (oneColLeft != -1 && !gameBoard.girdCoordinates(row, oneColLeft).containsLetter) {
            adjacentSquares.add(gameBoard.girdCoordinates(row, oneColLeft));
        }

        return adjacentSquares;
    }

    private void restrictEmptySquareAndSaveCrossCheckScore(Letters anchorSquare) {
        Letters.Location currentLocation = anchorSquare.getLocation();
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

    private void saveDownAnchorCrossScore(Letters emptySquare, String wordRight, String wordLeft) {
        Map<Character, Integer> charScoresForDownAnchor = new HashMap<>();
        for (Character c : downPlaysForEmptySquares.get(emptySquare)) {
            charScoresForDownAnchor.put(c, getWordScore(wordLeft + c + wordRight));
        }
        downAnchorCharacterScores.put(emptySquare, charScoresForDownAnchor);
    }

    private void saveAcrossAnchorCrossScores(Letters anchorSquare, String wordAbove, String wordBelow) {
        Map<Character, Integer> charScoresForAcrossAnchor = new HashMap<>();
        for (Character c : acrossPlaysForEmptySquares.get(anchorSquare)) {
            charScoresForAcrossAnchor.put(c, getWordScore(wordAbove + c + wordBelow));
        }
        acrossAnchorCharacterScores.put(anchorSquare, charScoresForAcrossAnchor);
    }

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

    private String getWordLeft(Letters.Location location) {
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

    private String getWordBelow(Letters.Location location) {
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

    private String getWordRight(Letters.Location location) {
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

    private String getWordAbove(Letters.Location location) {
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

    private List<Letters> getLCsOfWordLeft(Letters.Location location) {
        int row = location.getRow();
        int col = location.getCol();
        List<Letters> lcsOfWordToLeft = new ArrayList<>();
        if (location.getCol() != 0) {
            int offset = -1;
            char letterLeft = getLetterInRowByOffSet(location, offset);
            if (letterLeft != ' ') {
                lcsOfWordToLeft.add(gameBoard.girdCoordinates(row, col + offset));
                while (offset > (-1 * col) && getLetterInRowByOffSet(location, --offset) != ' ') {
                    lcsOfWordToLeft.add(gameBoard.girdCoordinates(row, col + offset));
                }
            }
            return lcsOfWordToLeft;
        }
        return null; // edge of board
    }

    private List<Letters> getLCsOfWordAbove(Letters.Location location) {
        int row = location.getRow();
        int col = location.getCol();
        List<Letters> lcsOfWordAbove = new ArrayList<>();
        if (location.getRow() != 0) {
            int offset = -1;
            char letterAbove = getLetterInColByOffSet(location, offset);
            if (letterAbove != ' ') {
                lcsOfWordAbove.add(gameBoard.girdCoordinates(row + offset, col));
                while (offset > (-1 * row) && getLetterInColByOffSet(location, --offset) != ' ') {
                    lcsOfWordAbove.add(gameBoard.girdCoordinates(row + offset, col));
                }
            }
            return lcsOfWordAbove;
        }
        return null;
    }

    private Character getLetterInColByOffSet(Letters.Location location, int offset) {
        return gameBoard.getVirtualBoard()[location.getRow() + offset][location.getCol()];
    }

    private Character getLetterInRowByOffSet(Letters.Location location, int offset) {
        return gameBoard.getVirtualBoard()[location.getRow()][location.getCol() + offset];
    }

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

    private int getNumNonAnchorsInColAbove(Letters anchor) {
        Letters.Location location = anchor.getLocation();
        Map<Integer, Set<Letters>> anchorsInEachCol = getAnchorsInEachCol();
        int row = location.getRow();
        int col = location.getCol();
        if (row == 0) {
            return 0;
        }

        Set<Letters> anchorsInSameCol = anchorsInEachCol.get(col);
        if (anchorsInSameCol == null) {
            return 0;
        }
        int max = 0;
        for (Letters a : anchorsInSameCol) {
            if (a.getLocation().getRow() < row && a.getLocation().getRow() > max) {
                max = a.getLocation().getRow();
            }
        }

        if (max == 0) {
            return row;
        }

        return row - max - 1;
    }

    private int getNumNonAnchorsInRowToLeft(Letters anchor) {
        Letters.Location location = anchor.getLocation();
        Map<Integer, Set<Letters>> anchorsInEachRow = getAnchorsInEachRow();
        int row = location.getRow();
        int col = location.getCol();
        if (col == 0) {
            return 0;
        }

        Set<Letters> anchorsInSameRow = anchorsInEachRow.get(row);
        if (anchorsInSameRow == null) {
            return 0;
        }
        int max = 0;
        for (Letters a : anchorsInSameRow) {
            if (a.getLocation().getCol() < col && a.getLocation().getCol() > max) {
                max = a.getLocation().getCol();
            }
        }

        if (max == 0) {
            return col;
        }
        return col - max - 1;
    }

    private Map<Integer, Set<Letters>> getAnchorsInEachRow() {
        Set<Letters> anchors = findAcrossAnchors().keySet();
        Map<Integer, Set<Letters>> anchorsInEachRow = new HashMap<>();
        for (int k = 0; k < 14; k++) {
            HashSet<Letters> anchorsInThisRow = new HashSet<>();
            for (Letters a : anchors) {
                if (a.getLocation().getRow() == k) {
                    anchorsInThisRow.add(a);
                }
            }
            anchorsInEachRow.put(k, anchorsInThisRow);
        }
        return anchorsInEachRow;
    }

    private Map<Integer, Set<Letters>> getAnchorsInEachCol() {
        Set<Letters> anchors = findDownAnchors().keySet();
        Map<Integer, Set<Letters>> anchorsInEachCol = new HashMap<>();
        for (int k = 0; k < 14; k++) {
            HashSet<Letters> anchorsInThisCol = new HashSet<>();
            for (Letters a : anchors) {
                if (a.getLocation().getCol() == k) {
                    anchorsInThisCol.add(a);
                }
            }
            anchorsInEachCol.put(k, anchorsInThisCol);
        }
        return anchorsInEachCol;
    }

    private Map<Letters, Set<Character>> findAcrossAnchors() {
        return acrossPlaysForEmptySquares;
    }

    private Map<Letters, Set<Character>> findDownAnchors() {
        return downPlaysForEmptySquares;
    }

    private void placeHighestScoringWord(InGameMove highestScoringInGameMove, Player player) {
        if (highestScoringInGameMove != null) {
            for (TileMove tileMove : highestScoringInGameMove.getTileMoves()) {
                if (tileMove.isFromRack()) {
                    player.removeLetterFromRack(tileMove.getCharacter().toString());
                    Letters target = tileMove.getDestination();
                    target.addLetter(tileMove.getCharacter().toString());
                }
            }
        }
    }

    private void updatePlayerScore(Player player, int highScore) {
        player.updateScore(highScore);
        System.out.println(player.getScore());
    }

    private void addTileMovesOfExistingWord(InGameMove newInGameMove, List<Letters> lcsOfWordBefore) {
        List<TileMove> wordOnTheBoard = new ArrayList<>();
        int size = lcsOfWordBefore.size();
        for (int k = size - 1; k >= 0; k--) {
            Letters letters = lcsOfWordBefore.get(k);
            TileMove tileThatIsAlreadyOnBoard = new TileMove(false, letters, letters.getText().charAt(0));
            wordOnTheBoard.add(tileThatIsAlreadyOnBoard);
        }
        newInGameMove.setTileMoves(wordOnTheBoard);
    }

    private void updatePlayableCharsForSquaresAroundWord(Set<Letters> containersOfMove) {
        for (Letters lettersOfWord : containersOfMove) {
            Set<Letters> emptyAdjacentSquares = getEmptyAdjacentSquares(lettersOfWord);
            for (Letters emptyAdjacentSquare : emptyAdjacentSquares) {
                restrictEmptySquareAndSaveCrossCheckScore(emptyAdjacentSquare);
            }
        }
    }

    private void findAllPossibleDownMoves(Player player, Letters anchor) {
        InGameMove newInGameMove = new InGameMove();
        String wordAbove = getWordAbove(anchor.getLocation());
        List<Letters> lettersContainersOfWordAbove = getLCsOfWordAbove(anchor.getLocation());
        if (wordAbove != null && lettersContainersOfWordAbove != null && !wordAbove.equals("")) {
            addTileMovesOfExistingWord(newInGameMove, lettersContainersOfWordAbove);
            extendDown(wordAbove, spellChecker.search(wordAbove), anchor, player, false);
        }
        else {
            int limitAbove = getNumNonAnchorsInColAbove(anchor);
            getAbovePart(anchor, "", spellChecker.getRoot(), limitAbove, player);
        }
    }

    private void getAbovePart(Letters anchor, String wordSoFar, SpellChecker.TrieNode currentNode, int limit, Player player) {
        extendDown(wordSoFar, currentNode, anchor, player, true);
        if (limit > 0) {
            for (int j = 0; j < currentNode.children.length; j++) {
                SpellChecker.TrieNode child = currentNode.children[j];
                if (child == null) {
                    continue;
                }
                Character characterAtChildNode = SpellChecker.toChar(j);
                if (player.letterRackContainsLetter(characterAtChildNode.toString())) {
                    Letters removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                    getAbovePart(anchor, wordSoFar + characterAtChildNode, child, limit - 1, player);
                    player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                }
            }
        }
    }

    private void extendDown(String wordSoFar, SpellChecker.TrieNode node, Letters currentSquare, Player player, Boolean anchorFilled) {
        if (currentSquare != null && !currentSquare.containsLetter && node != null) {
            if (node.endOfWord && anchorFilled) {
                recordLegalDownMove(wordSoFar, player, currentSquare);
            }

            for (int j = 0; j < node.children.length; j++) {
                SpellChecker.TrieNode child = node.children[j];
                if (child != null) {
                    Character characterAtChildNode = SpellChecker.toChar(j);

                    if (player.letterRackContainsLetter(characterAtChildNode.toString()) &&
                            (downPlaysForEmptySquares.get(currentSquare) == null ||
                                    downPlaysForEmptySquares.get(currentSquare).contains(characterAtChildNode))) {

                        Letters removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                        Letters nextSquare = gameBoard.getNextDown(currentSquare.getLocation());
                        extendDown(wordSoFar + characterAtChildNode, child, nextSquare, player, true);
                        player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                    }
                }
            }
        } else if (currentSquare != null && node != null){
            extendDownCurrentOccupied(wordSoFar, node, currentSquare, player);
        }
    }

    private void extendDownCurrentOccupied(String wordSoFar, SpellChecker.TrieNode node, Letters currentSquare, Player player) {
        String letter = currentSquare.getText();
        if (node.children[getChildIndexForLetter(letter)] != null) {
            SpellChecker.TrieNode child = node.children[getChildIndexForLetter(letter)];
            Letters nextSquare = gameBoard.getNextDown(currentSquare.getLocation());
            extendDown(wordSoFar + letter, child, nextSquare, player,true);
        }
    }

    private void recordLegalDownMove(String word, Player player, Letters lastContainerOfWord) {
        List<TileMove> tileMoves = new ArrayList<>();
        int row = lastContainerOfWord.getLocation().getRow() - 1;
        int col = lastContainerOfWord.getLocation().getCol();
        int crossScoreTotal = 0;
        boolean containsLetterFromRack = false;
        boolean containsLetterOnBoard = false;
        for (int i = 0; i < word.length(); i++) {
            Letters currentLettersInWord = gameBoard.girdCoordinates(row - i,col);
            boolean fromRack = !gameBoard.girdCoordinates(row - i,col).containsLetter;
            if (fromRack) {
                containsLetterFromRack = true;
            }
            if (!fromRack) {
                containsLetterOnBoard = true;
                Map<Character, Integer> charScores = downAnchorCharacterScores.get(currentLettersInWord);
                if (charScores != null) {
                    if (charScores.get(word.charAt(word.length() - 1 - i)) != null) {
                        crossScoreTotal += charScores.get(word.charAt(word.length() - 1 - i));
                    }
                }
            }
            TileMove tileMove = new TileMove(fromRack, currentLettersInWord, word.charAt(word.length() - 1 - i));
            tileMoves.add(tileMove);
        }

        // a legal play requires at least one letter from the players rack and one on the board
        if (containsLetterFromRack && containsLetterOnBoard) {
            InGameMove inGameMove = new InGameMove(tileMoves);
            player.addPlayableMove(inGameMove, getMoveScore(inGameMove) + crossScoreTotal);
        }
    }

    private void findAllPossibleAcrossMoves(Player player, Letters anchor) {
        if (sentinels.contains(anchor)) {
            return;
        }
        InGameMove newInGameMove = new InGameMove(); //copied later to save each playable moves
        // first check for a word to the left
        String wordLeft = getWordLeft(anchor.getLocation());
        List<Letters> lettersContainersOfWordToLeft = getLCsOfWordLeft(anchor.getLocation());
        if (wordLeft != null && lettersContainersOfWordToLeft != null && !wordLeft.equals("")) {
            addTileMovesOfExistingWord(newInGameMove, lettersContainersOfWordToLeft);
            extendRight(wordLeft, spellChecker.search(wordLeft), anchor, player, false);
        }
        // find all left parts
        else {
            int limitToTheLeft = getNumNonAnchorsInRowToLeft(anchor);
            getLeftPart(anchor, "", spellChecker.getRoot(), limitToTheLeft, player);
        }
    }

    private void getLeftPart(Letters anchor, String wordSoFar, SpellChecker.TrieNode currentNode, int limit, Player player) {
        extendRight(wordSoFar, currentNode, anchor, player, true);
        if (limit > 0) {
            for (int j = 0; j < currentNode.children.length; j++) {
                SpellChecker.TrieNode child = currentNode.children[j];
                if (child == null) {
                    continue;
                }
                Character characterAtChildNode = SpellChecker.toChar(j);
                if (player.letterRackContainsLetter(characterAtChildNode.toString())) {
                    Letters removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                    getLeftPart(anchor, wordSoFar + characterAtChildNode, child, limit - 1, player);
                    player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                }
            }
        }
    }

    private void extendRight(String wordSoFar, SpellChecker.TrieNode node, Letters currentSquare, Player player, Boolean anchorFilled) {
        if (currentSquare != null && !currentSquare.containsLetter && node != null) {
            if (anchorFilled && node.endOfWord) {
                recordLegalAcrossMove(wordSoFar, player, currentSquare);
            }

            for (int j = 0; j < node.children.length; j++) {
                SpellChecker.TrieNode child = node.children[j];
                if (child != null) {
                    Character characterAtChildNode = SpellChecker.toChar(j);

                    if (player.letterRackContainsLetter(characterAtChildNode.toString()) &&
                            (acrossPlaysForEmptySquares.get(currentSquare) == null ||
                            acrossPlaysForEmptySquares.get(currentSquare).contains(characterAtChildNode))) {

                        Letters removedLettersContainer = player.removeLetterFromRack(characterAtChildNode.toString());
                        Letters nextSquare = gameBoard.getNextRight(currentSquare.getLocation());
                        extendRight(wordSoFar + characterAtChildNode, child, nextSquare, player, true);
                        player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersContainer);
                    }
                }
            }
        } else if (currentSquare != null && node != null){
            extendRightCurrentOccupied(wordSoFar, node, currentSquare, player);
        }
    }

    private void recordLegalAcrossMove(String word, Player player, Letters lastContainerOfWord) {
        List<TileMove> tileMoves = new ArrayList<>();
        int row = lastContainerOfWord.getLocation().getRow();
        int col = lastContainerOfWord.getLocation().getCol() - 1;
        int crossScoreTotal = 0;
        boolean containsLetterFromRack = false;
        boolean containsLetterOnBoard = false;
        for (int i = 0; i < word.length(); i++) {
            Letters currentLettersInWord = gameBoard.girdCoordinates(row,col - i);
            boolean fromRack = !gameBoard.girdCoordinates(row,col - i).containsLetter;
            if (fromRack) {
                containsLetterFromRack = true;
            } else {
                containsLetterOnBoard = true;
                Map<Character, Integer> charScores = acrossAnchorCharacterScores.get(currentLettersInWord);
                if (charScores != null) {
                    if (charScores.get(word.charAt(word.length() - 1 - i)) != null) {
                        crossScoreTotal += charScores.get(word.charAt(word.length() - 1 - i));
                    }
                }
            }
            TileMove tileMove = new TileMove(fromRack, currentLettersInWord, word.charAt(word.length() - 1 - i));
            tileMoves.add(tileMove);
        }

        if (containsLetterFromRack && containsLetterOnBoard) {
            InGameMove inGameMove = new InGameMove(tileMoves);
            player.addPlayableMove(inGameMove, getMoveScore(inGameMove) + crossScoreTotal);
        }
    }

    private void extendRightCurrentOccupied(String wordSoFar, SpellChecker.TrieNode node, Letters currentSquare,
                                            Player player) {
        String letter = currentSquare.getText();
        if (node.children[getChildIndexForLetter(letter)] != null) {
            SpellChecker.TrieNode child = node.children[getChildIndexForLetter(letter)];
            Letters nextSquare = gameBoard.getNextRight(currentSquare.getLocation());
            extendRight(wordSoFar + letter, child, nextSquare, player, true);
        }
    }

    static void clearSpaceOnBoard(Letters letters) {
        int col = letters.getLocation().getCol();
        int row = letters.getLocation().getRow();
        virtualBoard[row][col] = ' ';
        newlyPopulatedContainers.remove(letters);
    }

    static void addLetterToRowColOnBoard(char c, Letters letters) {
        newlyPopulatedContainers.add(letters);
        ScrabbleGameLogic.containersWithCommittedLetters.put(letters, true);
        int row = letters.getLocation().getRow();
        int col = letters.getLocation().getCol();
        if (virtualBoard[row][col] == ' ') {
            virtualBoard[row][col] = c;
        }
    }

    static Letters getColumnFifteenSentinels(int row) {
        return columnFifteenSentinels.get(row);
    }

    static Letters getRowFifteenSentinels(int col) {
        return rowFifteenSentinels.get(col);
    }

    void commitAllNewlyPopulatedContainers() {
        for (Letters letters : newlyPopulatedContainers) {
            ScrabbleGameLogic.containersWithCommittedLetters.put(letters, true);
            letters.setDisable(true);
            removeNewlyPopulatedFromFringeSet(letters);
        }
        updatePlayableCharsForSquaresAroundWord(newlyPopulatedContainers);
        newlyPopulatedContainers.clear();
    }

    void doBestPossibleMove(Player player) {
        player.clearPlayableMoves();
        Map<Letters, Set<Character>> potentialAcrosses = findAcrossAnchors();
        for (Letters anchor : potentialAcrosses.keySet()) {
            if (!potentialAcrosses.get(anchor).isEmpty()) {
                findAllPossibleAcrossMoves(player, anchor);
            }
        }

        Map<Letters, Set<Character>> potentialDowns = findDownAnchors();
        for (Letters anchor : potentialDowns.keySet()) {
            if (!potentialDowns.get(anchor).isEmpty()) {
                findAllPossibleDownMoves(player, anchor);
            }
        }

        InGameMove highestScoringInGameMove = null;
        int highScore = 0;
        for (Map.Entry move : player.getPlayableMoves().entrySet()) {
            if ((Integer) move.getValue() > highScore) {
                highScore = (Integer) move.getValue();
                highestScoringInGameMove = (InGameMove) move.getKey();
            }
        }

        placeHighestScoringWord(highestScoringInGameMove, player);
        updatePlayerScore(player, highScore);
    }
}

class AIPlayer extends Player { }

class HumanPlayer extends Player { }

class AISwapTurn extends SwapTurn {
    AISwapTurn(GameSync gameSync, int turnId, Player player) {
        super(gameSync, turnId, player);
    }
}

class HumanSwapTurn extends SwapTurn {
    HumanSwapTurn(GameSync gameSync, int turnId, Player player) {
    super(gameSync, turnId, player);
}}

class InGameMove {

    private List<TileMove> tileMoves = new ArrayList<>();
    // moving
    InGameMove(List<TileMove> tileMoves) {
        List<TileMove> newTileMoves = new ArrayList<>();
        for (TileMove tileMove : tileMoves) {
            TileMove newTileMove = new TileMove(tileMove.isFromRack(), tileMove.getDestination(), tileMove.getCharacter());
            newTileMoves.add(newTileMove);
        }
        this.tileMoves = newTileMoves;
    }

    InGameMove() {
    }

    List<TileMove> getTileMoves() {
        return tileMoves;
    }

    void setTileMoves(List<TileMove> tileMoves) {
        this.tileMoves = tileMoves;
    }
}

class TileMove {

    private boolean isFromRack;
    private Letters destination;
    private Character character;

    //move the letter from container to destination
    TileMove(boolean source, Letters destination, Character character) {
        this.isFromRack = source;
        this.destination = destination;
        this.character = character;
    }
    //
    boolean isFromRack() {
        return isFromRack;
    }
    //get the destination
    Letters getDestination() {
        return destination;
    }
    // get the letter
    Character getCharacter() {
        return character;
    }
}


