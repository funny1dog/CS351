import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class LetterBag {
    private static LetterBag ourInstance;
    private static final LinkedList<Character> letters = new LinkedList<>();
    static Map<Character, Integer> letterScoreHashMap = new HashMap<>();

    // setup the score to each char in hashmap;
    static {
        // lower cases
        letterScoreHashMap.put('a', 1);
        letterScoreHashMap.put('b', 3);
        letterScoreHashMap.put('c', 3);
        letterScoreHashMap.put('d', 2);
        letterScoreHashMap.put('e', 1);
        letterScoreHashMap.put('f', 4);
        letterScoreHashMap.put('g', 2);
        letterScoreHashMap.put('h', 4);
        letterScoreHashMap.put('i', 1);
        letterScoreHashMap.put('j', 8);
        letterScoreHashMap.put('k', 5);
        letterScoreHashMap.put('l', 1);
        letterScoreHashMap.put('m', 3);
        letterScoreHashMap.put('n', 1);
        letterScoreHashMap.put('o', 1);
        letterScoreHashMap.put('p', 3);
        letterScoreHashMap.put('q', 10);
        letterScoreHashMap.put('r', 1);
        letterScoreHashMap.put('s', 1);
        letterScoreHashMap.put('t', 1);
        letterScoreHashMap.put('u', 1);
        letterScoreHashMap.put('v', 4);
        letterScoreHashMap.put('w', 4);
        letterScoreHashMap.put('x', 8);
        letterScoreHashMap.put('y', 4);
        letterScoreHashMap.put('z', 10);

        // Upper cases
        letterScoreHashMap.put('A', 1);
        letterScoreHashMap.put('B', 3);
        letterScoreHashMap.put('C', 3);
        letterScoreHashMap.put('D', 2);
        letterScoreHashMap.put('E', 1);
        letterScoreHashMap.put('F', 4);
        letterScoreHashMap.put('G', 2);
        letterScoreHashMap.put('H', 4);
        letterScoreHashMap.put('I', 1);
        letterScoreHashMap.put('J', 8);
        letterScoreHashMap.put('K', 5);
        letterScoreHashMap.put('L', 1);
        letterScoreHashMap.put('M', 3);
        letterScoreHashMap.put('N', 1);
        letterScoreHashMap.put('O', 1);
        letterScoreHashMap.put('P', 3);
        letterScoreHashMap.put('Q', 10);
        letterScoreHashMap.put('R', 1);
        letterScoreHashMap.put('S', 1);
        letterScoreHashMap.put('T', 1);
        letterScoreHashMap.put('U', 1);
        letterScoreHashMap.put('V', 4);
        letterScoreHashMap.put('W', 4);
        letterScoreHashMap.put('X', 8);
        letterScoreHashMap.put('Y', 4);
        letterScoreHashMap.put('Z', 10);
    }

    // check if the bag is empty
    private static boolean isBagEmpty() {
        return letters.isEmpty();
    }

    // Fill letter bag
    private LetterBag() {
        // 1 point letters
        addInitialLetters('A', 9);
        addInitialLetters('I', 9);
        addInitialLetters('E', 12);
        addInitialLetters('O', 8);
        addInitialLetters('N', 6);
        addInitialLetters('R', 6);
        addInitialLetters('T', 6);
        addInitialLetters('L', 4);
        addInitialLetters('S', 4);
        addInitialLetters('U', 4);

        // 2 point letters
        addInitialLetters('D', 4);
        addInitialLetters('G', 3);

        // 3 point letters
        addInitialLetters('B', 2);
        addInitialLetters('C', 2);
        addInitialLetters('M', 2);
        addInitialLetters('P', 2);

        // 4 point letters
        addInitialLetters('F', 2);
        addInitialLetters('H', 2);
        addInitialLetters('V', 2);
        addInitialLetters('W', 2);
        addInitialLetters('Y', 2);

        // 5 point letter
        addInitialLetters('K', 1);

        // 8 point letters
        addInitialLetters('J', 1);
        addInitialLetters('X', 1);
        addInitialLetters('Q',  1);
        addInitialLetters('Z',  1);
    }

    // create a list of random alphabets and later put on the rack
    public static String getRandomChar () {
        Random random  = new Random();
        if (letters.size()>0) {
            int rand = random.nextInt (letters.size());
            return letters.remove(rand).toString();
        }
        else
            return null;

    }

    // add the initial letter(s)
    private void addInitialLetters(Character character, int numberOfOccurrences) {
        if (isBagEmpty()) {
            letters.addFirst(character);
            for (int i = 0; i < numberOfOccurrences-1; i++){
                letters.add(character);
            }
        }
        else {
            for (int i = 0; i < numberOfOccurrences; i++) {
                letters.add(character);
            }
        }
    }

    // add one letter
    public static void addLetter(Character letterTile) {
        letters.add(letterTile);
    }

    // get the size of a letter
    public static int sizeOfLetter() {
        return letters.size();
    }

    // create instance
    static void createInstance() {
        if (ourInstance == null)
            ourInstance = new LetterBag();
    }
}
