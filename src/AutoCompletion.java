import java.io.*;
import java.util.*;

public class AutoCompletion {
    static AutoCompletion.Kattio io = new AutoCompletion.Kattio(System.in, System.out);

    public static void main(String[] args) {
        Node root = new Node('*', null, 0);
        int dictCount = io.getInt();

        /*
        Trie. et tree der bokstavene nedover i treet lager ord. ord ender med en *

        beveger seg nedover treet til man møter en #, teller disse og autocompleter. beveger seg til autocompleted word.
        for å finne ordet man er på, så går man oppover treet mens man henter bokstavene. (derfor depth)


        lagrer også antall subwords sånn at man vet hvor man skal gå når man autocompleter.
         */


        //create trie from dictionary and add amount of words in subtrees
        String wordInput;
        Node currentNode;
        for (int i = 0; i < dictCount; i++) {
            wordInput = io.getWord();
            currentNode = root;
            for (int j = 0; j < wordInput.length(); j++) {
                if (!currentNode.children.containsKey(wordInput.charAt(j))) {
                    currentNode.children.put(wordInput.charAt(j), new Node(wordInput.charAt(j), currentNode, j+1));
                }
                currentNode = currentNode.children.get(wordInput.charAt(j));
                currentNode.subWords++;
                if (j == wordInput.length() - 1) {
                    currentNode.children.put('*', new Node('*', currentNode, j+1));
                }
            }
        }

        //tasks
        StringBuilder suffix = new StringBuilder();
        int tabCount;
        int inputCount = io.getInt();
        for (int i = 0; i < inputCount; i++) {
            suffix.setLength(0);
            wordInput = io.getWord();
            currentNode = root;
            int j = 0;
            while (j < wordInput.length()) {
                tabCount = 0;
                if (wordInput.charAt(j) == '#') {
                    //count the number of tabs
                    while (j < wordInput.length() && wordInput.charAt(j) == '#') {
                        tabCount++;
                        j++;
                    }
                    //ignore direct '*' child. (current word)
                    if (currentNode.children.firstKey() == '*') {
                        tabCount++;
                    }
                    tabCount = ((tabCount - 1) % currentNode.subWords) + 1;
                    currentNode = autoComplete(currentNode, tabCount);
                } else {
                    if (currentNode.children.containsKey(wordInput.charAt(j))) {
                        currentNode = currentNode.children.get(wordInput.charAt(j));
                    } else {
                        //no more autocompletion can be done, add rest of string to suffix(except '#')
                        while(j < wordInput.length()) {
                            if(wordInput.charAt(j) != '#') {
                                suffix.append(wordInput.charAt(j));
                            }
                            j++;
                        }
                        break;
                    }
                    j++;
                }
            }
            io.println(getWord(currentNode) + suffix);
        }
        io.close();
    }

    static Node autoComplete(Node currentNode, int tabCount) {
        if (currentNode.letter == '*') {
            return currentNode.parent;
        }
        Iterator<Node> children = currentNode.children.values().iterator();
        Node currentChild = children.next();
        int totalSubWords = currentChild.subWords;
        while (tabCount > totalSubWords && children.hasNext()) {
            currentChild = children.next();
            totalSubWords = totalSubWords + currentChild.subWords;
        }
        return autoComplete(currentChild, tabCount - (totalSubWords - currentChild.subWords));
    }

    static class Node {
        char letter;
        TreeMap<Character, Node> children;
        Node parent;
        int subWords = 0;
        int depth;

        public Node(char letter, Node parent, int depth) {
            this.letter = letter;
            children = new TreeMap<>();
            this.parent = parent;
            this.depth = depth;
            if (letter == '*') {
                subWords++;
            }
        }
    }

    static String getWord(Node node) {
        char[] solution = new char[node.depth];
        Node currentNode = node;
        for(int i = 0; i < solution.length; i++){
            solution[solution.length-i-1] = currentNode.letter;
            currentNode = currentNode.parent;
        }
        return String.valueOf(solution);
    }

    static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public boolean hasMoreTokens() {
            return peekToken() != null;
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
        }

        public long getLong() {
            return Long.parseLong(nextToken());
        }

        public String getWord() {
            return nextToken();
        }


        private BufferedReader r;
        private String line;
        private StringTokenizer st;
        private String token;

        private String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) {
                }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}