package spelling;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie() {
        root = new TrieNode();
    }


    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should ignore the word's case.
     * That is, you should convert the string to all lower case as you insert it.
     */
    public boolean addWord(String word) {
        String wordLC = word.toLowerCase();
        if (isWord(wordLC)) return false;
        TrieNode curTrieNode = root;
        for (int i = 0; i < wordLC.length(); i++) {
            char curChar = wordLC.charAt(i);
            curTrieNode.insert(curChar);
            curTrieNode = curTrieNode.getChild(curChar);
            if (i == wordLC.length() - 1) curTrieNode.setEndsWord(true);
        }
        size++;
        return true;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size;
    }


    /**
     * Returns whether the string is a word in the trie
     */
    @Override
    public boolean isWord(String s) {
        String sLC = s.toLowerCase();
        TrieNode curTrieNode = root;
        for (char c : sLC.toCharArray()) {
            if (curTrieNode.getValidNextCharacters().contains(c)) {
                curTrieNode = curTrieNode.getChild(c);
            }
            if (curTrieNode.endsWord() && curTrieNode.getText().equals(sLC)) return true;
        }
        return false;
    }

    /**
     * * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * <p>
     * text The text to use at the word stem
     * n    The maximum number of predictions desired.
     *
     * @return A list containing the up to n best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        String prefixLC = prefix.toLowerCase();
        TrieNode curTrieNode = root;
        if (prefix.length() > 0) {
            for (char c : prefixLC.toCharArray()) {
                if (curTrieNode.getValidNextCharacters().contains(c)) {
                    curTrieNode = curTrieNode.getChild(c);
                } else return new ArrayList<>();
            }
        }

        LinkedList<TrieNode> queue = new LinkedList<>();
        queue.add(curTrieNode);
        List<String> completions = new ArrayList<>();
        while (queue.size() > 0 && completions.size() < numCompletions) {
            TrieNode firstNode = queue.get(0);
            queue.remove(0);
            if (firstNode.endsWord()) completions.add(firstNode.getText());
            queue.addAll(firstNode.getValidNextCharacters().stream().map(firstNode::getChild).collect(Collectors.toList()));
        }
        return completions;
    }

// --Commented out by Inspection START (4/7/2016 12:06 PM):
//    // For debugging
//    public void printTree() {
//        printNode(root);
//    }
// --Commented out by Inspection STOP (4/7/2016 12:06 PM)

// --Commented out by Inspection START (4/7/2016 12:07 PM):
//    /**
//     * Do a pre-order traversal from this node down
//     */
//    public void printNode(TrieNode curr) {
//        if (curr == null)
//            return;
//
//        System.out.println(curr.getText());
//
//        TrieNode next;
//        for (Character c : curr.getValidNextCharacters()) {
//            next = curr.getChild(c);
//            printNode(next);
//        }
//    }
// --Commented out by Inspection STOP (4/7/2016 12:07 PM)


}