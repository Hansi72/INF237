import java.io.*;
import java.util.*;

public class BingItOn {
    static BingItOn.Kattio io = new BingItOn.Kattio(System.in, System.out);

    public static void main(String[] args) {
        Node root = new Node('*', null);
        int wordCount = io.getInt();

        //create trie and set amount of words in subtrees. Output solutions while building trie.
        String wordInput;
        Node currentNode;
        for (int i = 0; i < wordCount; i++) {
            wordInput = io.getWord();
            currentNode = root;
            for (int j = 0; j < wordInput.length(); j++) {
                if (!currentNode.children.containsKey(wordInput.charAt(j))) {
                    currentNode.children.put(wordInput.charAt(j), new Node(wordInput.charAt(j), currentNode));
                }
                currentNode = currentNode.children.get(wordInput.charAt(j));
                currentNode.subWords++;
                if (j == wordInput.length() - 1) {
                    io.println(currentNode.subWords-1);
                }
            }
        }
        io.close();
    }

    static class Node {
        char letter;
        HashMap<Character, Node> children;
        Node parent;
        int subWords = 0;

        public Node(char letter, Node parent) {
            this.letter = letter;
            children = new HashMap<>();
            this.parent = parent;
        }
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