package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<ListNode>();
        starter = "";
        rnGenerator = generator;
    }


    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        String[] source = sourceText.split("\\s+");

        //loop all the words from position 0 to position length - 2
        for (int i = 0; i < source.length; i++) {
            String current = source[i];

            //if the word is last word in sourceText, set next to the first word
            String next;
            if (i == source.length - 1) {
                next = source[0];
            } else {
                next = source[i + 1];
            }

            boolean findWord = false;

            //if find the word in wordList
            for (ListNode ln : wordList) {
                if (ln.getWord().equals(current)) {
                    ln.addNextWord(next);
                    findWord = true;
                    break;
                }
            }

            //if didn't find the word
            if (!findWord) {
                ListNode ln = new ListNode(current);
                ln.addNextWord(next);
                wordList.add(ln);
            }
        }
    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        String currWord = wordList.get(0).getWord();
        StringBuilder output = new StringBuilder(currWord);
        output.append(" ");
        int countSize = 1;
        while (countSize < numWords) {
            String next;
            //find the random next word in wordList
            for (ListNode ln : wordList) {
                if (ln.getWord().equals(currWord)) {
                    next = ln.getRandomNextWord(rnGenerator);
                    output.append(next);
                    output.append(" ");
                    countSize++;
                    currWord = next;
                }
            }
        }
        return output.toString().trim();
    }


    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        wordList.clear();
        train(sourceText);
    }

    // TODO: Add any private helper methods you need here.


    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {
        // The random number generator should be passed from
        // the MarkovTextGeneratorLoL class
        int idx = generator.nextInt(nextWords.size());
        return nextWords.get(idx);
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


