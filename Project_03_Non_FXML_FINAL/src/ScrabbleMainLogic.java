import java.util.*;

public class ScrabbleMainLogic {

    static Map<LetterPiece, Boolean> piecesWithCommittedLetters = new HashMap<>();
    private final GameBoard Board;
    private static final Set<Character> ALPHABET = new HashSet<>();
    static SpellChecker spellChecker = new SpellChecker();
    private static final Map<LetterPiece, Set<Character>> acrossPlaysForEmptySquares = new HashMap<>();
    private static final Map<LetterPiece, Set<Character>> downPlaysForEmptySquares = new HashMap<>();
    private static final Map<Integer, LetterPiece> columnFifteenSentinels = new HashMap<>();
    private static final Map<Integer, LetterPiece> rowFifteenSentinels = new HashMap<>();
    private static final Set<LetterPiece> sentinels = new HashSet<>();
    private static final Map<LetterPiece, Map<Character, Integer>> acrossAnchorCharacterScores = new HashMap<>();
    private static final Map<LetterPiece, Map<Character, Integer>> downAnchorCharacterScores = new HashMap<>();


    ScrabbleMainLogic(GameBoard board) {
        this.Board = board;
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

    private static void removeNewlyPopulatedFromFringeSet(LetterPiece lp) {
        acrossPlaysForEmptySquares.remove(lp);
        downPlaysForEmptySquares.remove(lp);
    }

    private static int getMoveScore(Move move) {
        int wordScore = 0;
        int numTripleWordBonuses = 0;
        int numDoubleWordBonuses = 0;
        for (TileMove tileMove : move.getTileMoves()) {
            int letterScore = LetterBag.letterScoreHashMap.get(tileMove.getCharacter());
            // check move mapping to see if this letter was placed from rack, if so apply bonus
            if (tileMove.isFromRack()) {
                switch (tileMove.getDestination().getBonusText()) {
                    case GameBoard.DoubleWordScore, GameBoard.Star -> numDoubleWordBonuses++;
                    case GameBoard.TripleLetterScore -> letterScore = letterScore * 3;
                    case GameBoard.DoubleLetterScore -> letterScore = letterScore * 2;
                    case GameBoard.TripleWordScore -> numTripleWordBonuses++;
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
            LetterPiece colSentinel = new LetterPiece(null, null, j, 15, Board);
            Set<Character> emptyPlayableSet = new HashSet<>();
            acrossPlaysForEmptySquares.put(colSentinel, emptyPlayableSet);
            columnFifteenSentinels.put(j, colSentinel);
            LetterPiece rowSentinel = new LetterPiece(null, null, 15, j, Board);
            downPlaysForEmptySquares.put(rowSentinel, emptyPlayableSet);
            rowFifteenSentinels.put(j, rowSentinel);
            sentinels.add(colSentinel);
            sentinels.add(rowSentinel);
        }
    }

    // Naively search all four squares adjacent to each newly committed tile for its neighbors, ignores diagonals
    private Set<LetterPiece> getEmptyAdjacentSquares(LetterPiece emptySquare) {
        Set<LetterPiece> adjacentSquares = new HashSet<>();
        int row = emptySquare.getLetterPieceLocation().getRow();
        int col = emptySquare.getLetterPieceLocation().getCol();
        int oneRowDown = row < 14 ? row + 1 : -1;
        int oneRowUp = row > 0 ? row - 1 : -1;
        int oneColRight = col < 14 ? col + 1 : -1;
        int oneColLeft = col > 0 ? col - 1 : -1;
        // clockwise bounds check:
        // ^
        if (oneRowUp != -1 && !Board.getRefToSquareByRowColumn(oneRowUp, col).containsLetter) {
            adjacentSquares.add(Board.getRefToSquareByRowColumn(oneRowUp, col));
        }
        // ->
        if (oneColRight != -1 && !Board.getRefToSquareByRowColumn(row, oneColRight).containsLetter) {
            adjacentSquares.add(Board.getRefToSquareByRowColumn(row, oneColRight));
        }
        // down
        if (oneRowDown != -1 && !Board.getRefToSquareByRowColumn(oneRowDown, col).containsLetter) {
            adjacentSquares.add(Board.getRefToSquareByRowColumn(oneRowDown, col));
        }
        // <-
        if (oneColLeft != -1 && !Board.getRefToSquareByRowColumn(row, oneColLeft).containsLetter) {
            adjacentSquares.add(Board.getRefToSquareByRowColumn(row, oneColLeft));
        }

        return adjacentSquares;
    }

    /** Compute the playable chars for both across and down moves for each empty (anchor) square.
     Calls saveAcrossAnchorCrossScores and saveDownAnchorCrossScores for each anchor*/
    private void restrictEmptySquareAndSaveCrossCheckScore(LetterPiece anchorSquare) {
        LetterPiece.Location currentLocation = anchorSquare.getLetterPieceLocation();
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
        restrictBySurroundingWords(wordAbove, wordBelow, restrictedAcrossSet);
        restrictBySurroundingWords(wordLeft, wordRight, restrictedDownSet);
        // save crossScores for the anchor
        acrossPlaysForEmptySquares.put(anchorSquare, restrictedAcrossSet);
        saveAcrossAnchorCrossScores(anchorSquare, wordAbove, wordBelow);
        downPlaysForEmptySquares.put(anchorSquare, restrictedDownSet);
        saveDownAnchorCrossScore(anchorSquare, wordRight, wordLeft);
    }

    /** Stores the value that would be scored for each playable char in anchor, when making a across move through anchor */
    private void saveDownAnchorCrossScore(LetterPiece emptySquare, String wordRight, String wordLeft) {
        Map<Character, Integer> charScoresForDownAnchor = new HashMap<>();
        for (Character c : downPlaysForEmptySquares.get(emptySquare)) {
            charScoresForDownAnchor.put(c, getWordScore(wordLeft + c + wordRight));
        }
        downAnchorCharacterScores.put(emptySquare, charScoresForDownAnchor);
    }

    /** Stores the value that would be scored for each playable char in anchor, when making a down move through anchor */
    private void saveAcrossAnchorCrossScores(LetterPiece anchorSquare, String wordAbove, String wordBelow) {
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
                score += LetterBag.letterScoreHashMap.get(c);
            }
            return score;
        }
    }

    /** Returns a string representing the contiguous tiles left up to the edge of the board or an empty square */
    private String getWordLeft(LetterPiece.Location location) {
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
    private String getWordBelow(LetterPiece.Location location) {
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
    private String getWordRight(LetterPiece.Location location) {
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
    private String getWordAbove(LetterPiece.Location location) {
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

    private List<LetterPiece> getLCsOfWordLeft(LetterPiece.Location location) {
        int row = location.getRow();
        int col = location.getCol();
        List<LetterPiece> lcsOfWordToLeft = new ArrayList<>();
        if (location.getCol() != 0) {
            int offset = -1;
            char letterLeft = getLetterInRowByOffSet(location, offset);
            if (letterLeft != ' ') {
                lcsOfWordToLeft.add(Board.getRefToSquareByRowColumn(row, col + offset));
                while (offset > (-1 * col) && getLetterInRowByOffSet(location, --offset) != ' ') {
                    lcsOfWordToLeft.add(Board.getRefToSquareByRowColumn(row, col + offset));
                }
            }
            return lcsOfWordToLeft;
        }
        return null; // edge of board
    }

    private List<LetterPiece> getLCsOfWordAbove(LetterPiece.Location location) {
        int row = location.getRow();
        int col = location.getCol();
        List<LetterPiece> lcsOfWordAbove = new ArrayList<>();
        if (location.getRow() != 0) {
            int offset = -1;
            char letterAbove = getLetterInColByOffSet(location, offset);
            if (letterAbove != ' ') {
                lcsOfWordAbove.add(Board.getRefToSquareByRowColumn(row + offset, col));
                while (offset > (-1 * row) && getLetterInColByOffSet(location, --offset) != ' ') {
                    lcsOfWordAbove.add(Board.getRefToSquareByRowColumn(row + offset, col));
                }
            }
            return lcsOfWordAbove;
        }
        return null;
    }

    private Character getLetterInColByOffSet(LetterPiece.Location location, int offset) {
        return Board.getVirtualBoard()[location.getRow() + offset][location.getCol()];
    }

    private Character getLetterInRowByOffSet(LetterPiece.Location location, int offset) {
        return Board.getVirtualBoard()[location.getRow()][location.getCol() + offset];
    }

    /** Returns the number of the associated WordChecker.SpellChecker.TrieNode index corresponding to the given letter*/
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
    private int getNumNonAnchorsInColAbove(LetterPiece anchor) {
        LetterPiece.Location location = anchor.getLetterPieceLocation();
        Map<Integer, Set<LetterPiece>> anchorsInEachCol = getAnchorsInEachCol();
        int row = location.getRow();
        int col = location.getCol();
        if (row == 0) {
            return 0;
        }

        Set<LetterPiece> anchorsInSameCol = anchorsInEachCol.get(col);
        if (anchorsInSameCol == null) {
            return 0;
        }
        int max = 0;
        for (LetterPiece lp : anchorsInSameCol) {
            if (lp.getLetterPieceLocation().getRow() < row && lp.getLetterPieceLocation().getRow() > max) {
                max = lp.getLetterPieceLocation().getRow();
            }
        }

        if (max == 0) {
            return row;
        }

        return row - max - 1;
    }

    /** Returns the number of empty squares to the contiguous left that have non-null cross check sets*/
    private int getNumNonAnchorsInRowToLeft(LetterPiece anchor) {
        LetterPiece.Location location = anchor.getLetterPieceLocation();
        Map<Integer, Set<LetterPiece>> anchorsInEachRow = getAnchorsInEachRow();
        int row = location.getRow();
        int col = location.getCol();
        if (col == 0) {
            return 0;
        }

        Set<LetterPiece> anchorsInSameRow = anchorsInEachRow.get(row);
        if (anchorsInSameRow == null) {
            return 0;
        }
        int max = 0;
        for (LetterPiece lp : anchorsInSameRow) {
            if (lp.getLetterPieceLocation().getCol() < col && lp.getLetterPieceLocation().getCol() > max) {
                max = lp.getLetterPieceLocation().getCol();
            }
        }

        if (max == 0) {
            return col;
        }
        return col - max - 1;
    }

    private Map<Integer, Set<LetterPiece>> getAnchorsInEachRow() {
        Set<LetterPiece> anchors = findAcrossAnchors().keySet();
        Map<Integer, Set<LetterPiece>> anchorsInEachRow = new HashMap<>();
        for (int k = 0; k < 14; k++) {
            HashSet<LetterPiece> anchorsInThisRow = new HashSet<>();
            for (LetterPiece lp : anchors) {
                if (lp.getLetterPieceLocation().getRow() == k) {
                    anchorsInThisRow.add(lp);
                }
            }
            anchorsInEachRow.put(k, anchorsInThisRow);
        }
        return anchorsInEachRow;
    }

    private Map<Integer, Set<LetterPiece>> getAnchorsInEachCol() {
        Set<LetterPiece> anchors = findDownAnchors().keySet();
        Map<Integer, Set<LetterPiece>> anchorsInEachCol = new HashMap<>();
        for (int k = 0; k < 14; k++) {
            HashSet<LetterPiece> anchorsInThisCol = new HashSet<>();
            for (LetterPiece lp : anchors) {
                if (lp.getLetterPieceLocation().getCol() == k) {
                    anchorsInThisCol.add(lp);
                }
            }
            anchorsInEachCol.put(k, anchorsInThisCol);
        }
        return anchorsInEachCol;
    }

    private Map<LetterPiece, Set<Character>> findAcrossAnchors() {
        return acrossPlaysForEmptySquares;
    }

    private Map<LetterPiece, Set<Character>> findDownAnchors() {
        return downPlaysForEmptySquares;
    }

    private void placeHighestScoringWord(Move highestScoringMove, Player player) {
        if (highestScoringMove != null) {
            for (TileMove tileMove : highestScoringMove.getTileMoves()) {
                if (tileMove.isFromRack()) {
                    player.removeLetterFromRack(tileMove.getCharacter().toString());
                    LetterPiece target = tileMove.getDestination();
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
    private void addTileMovesOfExistingWord(Move newMove, List<LetterPiece> lcsOfWordBefore) {
        List<TileMove> wordOnTheBoard = new ArrayList<>();
        int size = lcsOfWordBefore.size();
        for (int k = size - 1; k >= 0; k--) {
            LetterPiece lp = lcsOfWordBefore.get(k);
            TileMove tileThatIsAlreadyOnBoard = new TileMove(false, lp, lp.getText().charAt(0));
            wordOnTheBoard.add(tileThatIsAlreadyOnBoard);
        }
        newMove.setTileMoves(wordOnTheBoard);
    }

    /** Updates the character restrictions around the adjacent squares of a newly placed word*/
    private void updatePlayableCharsForSquaresAroundWord(Set<LetterPiece> piecesOfMove) {
        for (LetterPiece letterOfWord : piecesOfMove) {
            Set<LetterPiece> emptyAdjacentSquares = getEmptyAdjacentSquares(letterOfWord);
            for (LetterPiece emptyAdjacentSquare : emptyAdjacentSquares) {
                restrictEmptySquareAndSaveCrossCheckScore(emptyAdjacentSquare);
            }
        }
    }

    private void findAllPossibleDownMoves(Player player, LetterPiece anchor) {
        Move newMove = new Move();
        String wordAbove = getWordAbove(anchor.getLetterPieceLocation());
        List<LetterPiece> LetterPiecesOfWordAbove = getLCsOfWordAbove(anchor.getLetterPieceLocation());
        if (wordAbove != null && LetterPiecesOfWordAbove != null && !wordAbove.equals("")) {
            addTileMovesOfExistingWord(newMove, LetterPiecesOfWordAbove);
            extendDown(wordAbove, spellChecker.search(wordAbove), anchor, player, false);
        }
        else {
            int limitAbove = getNumNonAnchorsInColAbove(anchor);
            getAbovePart(anchor, "", spellChecker.getRoot(), limitAbove, player);
        }
    }

    private void getAbovePart(LetterPiece anchor, String wordSoFar, SpellChecker.TrieNode currentNode, int limit, Player player) {
        extendDown(wordSoFar, currentNode, anchor, player, true);
        if (limit > 0) {
            for (int j = 0; j < currentNode.children.length; j++) {
                SpellChecker.TrieNode child = currentNode.children[j];
                if (child == null) {
                    continue;
                }
                Character characterAtChildNode = SpellChecker.toChar(j);
                if (player.letterRackContainsLetter(characterAtChildNode.toString())) {
                    LetterPiece removedLettersPieces = player.removeLetterFromRack(characterAtChildNode.toString());
                    getAbovePart(anchor, wordSoFar + characterAtChildNode, child, limit - 1, player);
                    player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersPieces);
                }
            }
        }
    }

    private void extendDown(String wordSoFar, SpellChecker.TrieNode node, LetterPiece currentSquare, Player player, Boolean anchorFilled) {
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

                        LetterPiece removedLettersPieces = player.removeLetterFromRack(characterAtChildNode.toString());
                        LetterPiece nextSquare = Board.getNextDown(currentSquare.getLetterPieceLocation());
                        extendDown(wordSoFar + characterAtChildNode, child, nextSquare, player, true);
                        player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersPieces);
                    }
                }
            }
        } else if (currentSquare != null && node != null){
            extendDownCurrentOccupied(wordSoFar, node, currentSquare, player);
        }
    }

    /** While extending down, we found a square that is occupied, save it and continue extending down*/
    private void extendDownCurrentOccupied(String wordSoFar, SpellChecker.TrieNode node, LetterPiece currentSquare, Player player) {
        String letter = currentSquare.getText();
        if (node.children[getChildIndexForLetter(letter)] != null) {
           SpellChecker.TrieNode child = node.children[getChildIndexForLetter(letter)];
            LetterPiece nextSquare = Board.getNextDown(currentSquare.getLetterPieceLocation());
            extendDown(wordSoFar + letter, child, nextSquare, player,true);
        }
    }

    private void recordLegalDownMove(String word, Player player, LetterPiece lastPiecesOfWord) {
        List<TileMove> tileMoves = new ArrayList<>();
        int row = lastPiecesOfWord.getLetterPieceLocation().getRow() - 1;
        int col = lastPiecesOfWord.getLetterPieceLocation().getCol();
        int crossScoreTotal = 0;
        boolean containsLetterFromRack = false;
        boolean containsLetterOnBoard = false;
        for (int i = 0; i < word.length(); i++) {
            LetterPiece currentLetterInWord = Board.getRefToSquareByRowColumn(row - i,col);
            boolean fromRack = !Board.getRefToSquareByRowColumn(row - i,col).containsLetter;
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

    private void findAllPossibleAcrossMoves(Player player, LetterPiece anchor) {
        if (sentinels.contains(anchor)) {
            return;
        }
        Move newMove = new Move(); //copied later to save each playable moves
        // first check for a word to the left
        String wordLeft = getWordLeft(anchor.getLetterPieceLocation());
        List<LetterPiece> LetterPiecesOfWordToLeft = getLCsOfWordLeft(anchor.getLetterPieceLocation());
        if (wordLeft != null && LetterPiecesOfWordToLeft != null && !wordLeft.equals("")) {
            addTileMovesOfExistingWord(newMove, LetterPiecesOfWordToLeft);
            extendRight(wordLeft, spellChecker.search(wordLeft), anchor, player, false);
        }
        // find all left parts
        else {
            int limitToTheLeft = getNumNonAnchorsInRowToLeft(anchor);
            getLeftPart(anchor, "", spellChecker.getRoot(), limitToTheLeft, player);
        }
    }

    private void getLeftPart(LetterPiece anchor, String wordSoFar, SpellChecker.TrieNode currentNode, int limit, Player player) {
        extendRight(wordSoFar, currentNode, anchor, player, true);
        if (limit > 0) {
            for (int j = 0; j < currentNode.children.length; j++) {
                SpellChecker.TrieNode child = currentNode.children[j];
                if (child == null) {
                    continue;
                }
                Character characterAtChildNode = SpellChecker.toChar(j);
                if (player.letterRackContainsLetter(characterAtChildNode.toString())) {
                    LetterPiece removedLettersPiece = player.removeLetterFromRack(characterAtChildNode.toString());
                    getLeftPart(anchor, wordSoFar + characterAtChildNode, child, limit - 1, player);
                    player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersPiece);
                }
            }
        }
    }

    private void extendRight(String wordSoFar, SpellChecker.TrieNode node, LetterPiece currentSquare, Player player, Boolean anchorFilled) {
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

                        LetterPiece removedLettersPiece = player.removeLetterFromRack(characterAtChildNode.toString());
                        LetterPiece nextSquare = Board.getNextRight(currentSquare.getLetterPieceLocation());
                        extendRight(wordSoFar + characterAtChildNode, child, nextSquare, player, true);
                        player.putLetterInRack(characterAtChildNode.toString().toUpperCase(), removedLettersPiece);
                    }
                }
            }
        } else if (currentSquare != null && node != null){
            extendRightCurrentOccupied(wordSoFar, node, currentSquare, player);
        }
    }

    private void recordLegalAcrossMove(String word, Player player, LetterPiece lastPieceofWord) {
        List<TileMove> tileMoves = new ArrayList<>();
        int row = lastPieceofWord.getLetterPieceLocation().getRow();
        int col = lastPieceofWord.getLetterPieceLocation().getCol() - 1;
        int crossScoreTotal = 0;
        boolean containsLetterFromRack = false;
        boolean containsLetterOnBoard = false;
        for (int i = 0; i < word.length(); i++) {
            LetterPiece currentLetterInWord = Board.getRefToSquareByRowColumn(row,col - i);
            boolean fromRack = !Board.getRefToSquareByRowColumn(row,col - i).containsLetter;
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
    private void extendRightCurrentOccupied(String wordSoFar, SpellChecker.TrieNode node, LetterPiece currentSquare,
                                            Player player) {
        String letter = currentSquare.getText();
        if (node.children[getChildIndexForLetter(letter)] != null) {
            SpellChecker.TrieNode child = node.children[getChildIndexForLetter(letter)];
            LetterPiece nextSquare = Board.getNextRight(currentSquare.getLetterPieceLocation());
            extendRight(wordSoFar + letter, child, nextSquare, player, true);
        }
    }


/////////// PUBLIC METHODS /////////////////////////////////////////////////////////////////////////////////////////////

    /** Adds a letter to the virtual copy of the UI board.
     *
     * @param lp
     */
    static void clearSpaceOnBoard(LetterPiece lp) {
        int col = lp.getLetterPieceLocation().getCol();
        int row = lp.getLetterPieceLocation().getRow();
        GameBoard.virtualBoard[row][col] = ' ';
        GameBoard.newLetterPieceSet.remove(lp);
    }

    /** Removes a letter in the virtual copy of the UI board.*/
    static void addLetterToRowColOnBoard(char c, LetterPiece lp) {
        GameBoard.newLetterPieceSet.add(lp);
        ScrabbleMainLogic.piecesWithCommittedLetters.put(lp, true);
        int row = lp.getLetterPieceLocation().getRow();
        int col = lp.getLetterPieceLocation().getCol();
        if (GameBoard.virtualBoard[row][col] == ' ') {
            GameBoard.virtualBoard[row][col] = c;
        }
    }

    static LetterPiece getColumnFifteenSentinels(int row) {
        return columnFifteenSentinels.get(row);
    }

    static LetterPiece getRowFifteenSentinels(int col) {
        return rowFifteenSentinels.get(col);
    }

    /** Disables drag and drop on letters that have been committed*/
    void commitAllNewlyPopulatedContainers() {
        for (LetterPiece lp : GameBoard.newLetterPieceSet) {
            ScrabbleMainLogic.piecesWithCommittedLetters.put(lp, true);
            lp.setDisable(true);
            removeNewlyPopulatedFromFringeSet(lp);
        }
        updatePlayableCharsForSquaresAroundWord(GameBoard.newLetterPieceSet);
        GameBoard.newLetterPieceSet.clear();
    }

    /** Finds the entire sets of possible across and down moves, places the highest scoring one on the board and
     * updates the players score to reflect the move.*/
    void doBestPossibleMove(Player player) {
        player.clearPlayableMoves();
        Map<LetterPiece, Set<Character>> potentialAcrosses = findAcrossAnchors();
        for (LetterPiece anchor : potentialAcrosses.keySet()) {
            if (!potentialAcrosses.get(anchor).isEmpty()) {
                findAllPossibleAcrossMoves(player, anchor);
            }
        }

        Map<LetterPiece, Set<Character>> potentialDowns = findDownAnchors();
        for (LetterPiece anchor : potentialDowns.keySet()) {
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
class HumanPlayer extends Player {}
class AIPlayer extends Player{}
class HumanTurn extends Turn{
    HumanTurn(Mutex mutex, int turnID, Player player) {
        super (mutex, turnID, player);
    }
}
class AITurn extends Turn{
    AITurn(Mutex mutex, int turnID, Player player) {
        super (mutex, turnID, player);
    }
}
class Move {

    private List<TileMove> tileMoves = new ArrayList<>();
    // moving
    Move(List<TileMove> tileMoves) {
        List<TileMove> newTileMoves = new ArrayList<>();
        for (TileMove tileMove : tileMoves) {
            TileMove newTileMove = new TileMove(tileMove.isFromRack(), tileMove.getDestination(), tileMove.getCharacter());
            newTileMoves.add(newTileMove);
        }
        this.tileMoves = newTileMoves;
    }

    Move() {
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
    private LetterPiece destination;
    private Character character;

    //move the letter from container to destination
    TileMove(boolean source, LetterPiece destination, Character character) {
        this.isFromRack = source;
        this.destination = destination;
        this.character = character;
    }
    //
    boolean isFromRack() {
        return isFromRack;
    }
    //get the destination
    LetterPiece getDestination() {
        return destination;
    }
    // get the letter
    Character getCharacter() {
        return character;
    }
}


