package trie;

import java.util.HashMap;

public class ImplementTrie {
    class TrieNode {
        HashMap<Character, TrieNode> children;
        boolean isEnd;
        
        TrieNode() {
            children = new HashMap<>();
            isEnd = false;
            
        }
        
    }
    TrieNode root = new TrieNode();
    
    public void insert(String word) {
        if(word == null || word.length() ==0)
            return;
        
        //start from root of the Trie tree
        TrieNode node = root;
        for(int i=0; i<word.length(); i++) {
            //for each letter in the word
            //check the current node 's children contains this letter or not
            //if not, insert new trienode constructed of this letter to the current node
            //move the pointer to point to the new inserted trienode
            char c = word.charAt(i);
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
            //at the end of the word, mark the corresponding node with isEnd true
            if (i == word.length()-1) {
                node.isEnd = true;
            }
        }
        
        
    }
    
    // Returns if the word is in the trie.
    public boolean search(String word) {
         if (word == null || word.length() == 0) {
            return true;
        }

        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node.isEnd;
    }
    
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return true;
        }

        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return true;
    }
}
