import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * Created by bigwood928 on 2/26/14.
 */
public class EmvowlerTreeSolution {

    Tree[] wordTrees = new Tree[26];
    public final Tree EMPTY_TREE = new Tree(null, false);

    public static void main(String[] args) throws IOException {
        EmvowlerTreeSolution emvowler = new EmvowlerTreeSolution();
        File input = new File("./res/emvowlerinput");

        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(input));
            String line = "";

            while((line = inputStream.readLine()) != null) {
                String nonVowels = line;
                String vowels = inputStream.readLine();
                emvowler.emvowel(nonVowels, vowels);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public EmvowlerTreeSolution() {
        Character root = 'a';
        for(int i=0;i<26;i++) {
            boolean isWord = false;
            if(root == 'a' || root == 'i') {
                isWord = true;
            }
            wordTrees[i] = new Tree(root, isWord);
            root++;
        }
        File wordBankFile = new File("./res/wordBank");
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(wordBankFile));
            String newWord = "";
            while((newWord = inputStream.readLine()) != null){
                newWord = newWord.toLowerCase();
                Tree letterTree = wordTrees[newWord.charAt(0)-'a'];
                createBranchesForWord(letterTree.root, newWord, 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBranchesForWord(Tree.Node currentNode, String newWord, int depth) {
        if(depth >= newWord.length()) return;
        Character currentLetter = newWord.charAt(depth);
        Tree.Node next = null;
        for(Tree.Node node : currentNode.children) {
            if(node.data.equals(currentLetter)) {
                next = node;
                break;
            }
        }
        if(next == null) {
            next = currentNode.addChild(currentLetter, depth == newWord.length()-1);
        }
        depth++;
        createBranchesForWord(next, newWord, depth);
    }

    public void emvowel(String startNonVowels, String startVowel) {
        Tree nonVowelTree = EMPTY_TREE;
        Tree vowelTree = EMPTY_TREE;
        Set<String> words = new HashSet<String>();
        words.add(encode("", "",startNonVowels, startVowel));
        do{
            HashSet<String> toBeRemoved = new HashSet<String>();
            HashSet<String> encodedWords = new HashSet<String>();
            for(String word : words) {
                List<String> decodedString = Arrays.asList(word.split("@"));
                String phrase = "";
                String nonVowels = "";
                String vowel = "";
                for(int i=0;i<decodedString.size();i++) {
                    if(i==0) phrase = decodedString.get(i);
                    if(i==1) nonVowels = decodedString.get(i);
                    if(i==2) vowel = decodedString.get(i);
                }
                if(!vowel.isEmpty()) {
                    vowelTree = wordTrees[vowel.charAt(0)-'a'];
                }
                if(!nonVowels.isEmpty()) {
                    nonVowelTree = wordTrees[nonVowels.charAt(0)-'a'];
                }
                Set<String> potentialWords = new HashSet<String>();
                potentialWords.addAll(findWords(nonVowelTree, nonVowels, vowel));
                potentialWords.addAll(findWords(vowelTree, vowel, nonVowels));
                if((potentialWords.isEmpty() && vowel.length() == 0 && nonVowels.length() ==0) ) {
                    System.out.println(phrase);
                    toBeRemoved.add(word);
                } else if ((potentialWords.isEmpty() && vowel.length() != 0 && nonVowels.length() != 0)) {
                    toBeRemoved.add(word);
                } else {
                    for(String potentialWord : potentialWords) {
                        if(!phrase.isEmpty()) encodedWords.add(encode(phrase+" "+potentialWord, potentialWord, nonVowels, vowel));
                        else encodedWords.add(encode("",potentialWord, nonVowels, vowel));
                    }
                }

            }
            words.removeAll(toBeRemoved);
            words.addAll(encodedWords);
        } while (!words.isEmpty());
    }

    private String encode(String phrase, String word, String nonVowels, String vowel) {
        String reducedNonVowels = nonVowels;
        String reducedVowels = vowel;

        for(int i=0; i<word.length();i++) {
            Character character = word.charAt(i);
            if(!isVowel(character) && !reducedNonVowels.isEmpty()) {
                reducedNonVowels = reducedNonVowels.substring(1);
            } else if(!reducedVowels.isEmpty()){
                reducedVowels = reducedVowels.substring(1);
            }
        }
        StringBuilder builder = new StringBuilder(phrase).append("@").append(reducedNonVowels).append("@")
                .append(reducedVowels);
        return builder.toString();
    }

    private boolean isVowel(Character character){
        switch (character) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }


    private Set<String> findWords(Tree wordTree, String rootString, String other) {
        LinkedList<MakeWordTracker> wordStack = new LinkedList<MakeWordTracker>();
        Set<String> words = new HashSet<String>();
        wordStack.push(new MakeWordTracker(wordTree.getRoot(), 1, 0));
        while(!wordStack.isEmpty()) {
            MakeWordTracker tracker = wordStack.pop();
            Tree.Node node = tracker.node;
            if(node.isWord()) {
                words.add(getWordForNode(node));
            }
            Character next;
            if(tracker.index1 < rootString.length()) {
                next = rootString.charAt(tracker.index1);
                for(Tree.Node child : node.getChildren()) {
                    if(child.getData().equals(next)) {
                        Integer newIndex = tracker.index1+1;
                        if(newIndex < rootString.length()) wordStack.push(new MakeWordTracker(child, newIndex, tracker.index2));
                    }
                }
            }
            if(tracker.index2 < other.length()) {
                next = other.charAt(tracker.index2);
                for(Tree.Node child : node.getChildren()) {
                    if(child.getData().equals(next)) {
                        Integer newIndex = tracker.index2+1;
                        if(newIndex < other.length()) wordStack.push(new MakeWordTracker(child, tracker.index1, newIndex));
                    }
                }
            }
        }
        return words;
    }

    private String getWordForNode(Tree.Node node) {
        if(node == null) throw new NullPointerException();
        String word = "";
        do{
            word = node.getData() + word;
            node = node.getParent();
        } while(node != null);
        return word;
    }


    public class Tree {
        private Node root;
        private int depth;

        public Tree(Character rootData, boolean isWord) {
            root = new Node(rootData, null, 0, isWord);
        }

        public Node getRoot() {
            return root;
        }



        public class Node {
            private Character data;
            private Node parent;
            private List<Node> children;
            private int depth;
            private boolean isWord;

            public Node(Character data, Node parent, int depth, boolean isWord){
                this.data = data;
                this.parent = parent;
                this.children = new ArrayList<Node>();
                this.depth = depth;
                this.isWord = isWord;
            }

            public Node getParent() {
                return parent;
            }

            public Character getData() {
                return data;
            }

            public int getDepth(){
                return depth;
            }

            public Node addChild(Character data, boolean isWord) {
                Node child = new Node(data, this, depth+1, isWord);
                children.add(child);
                return child;
            }

            public List<Node> getChildren() {
                return children;
            }

            public boolean isWord() {
                return isWord;
            }
        }

    }

    private class MakeWordTracker{
        public Tree.Node node;
        public Integer index1;
        public Integer index2;

        public MakeWordTracker(Tree.Node node, Integer index1, Integer index2) {
            this.node = node;
            this.index1 = index1;
            this.index2 = index2;
        }
    }


}
