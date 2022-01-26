import java.io.*;
import java.util.*;

public class ThroughTheGrapevine {
    static LinkedList<Integer>[] neighbours;
    static int[] skepticism;
    static boolean[] visited;

    public static void main(String[] args) {
        ThroughTheGrapevine.Kattio io = new ThroughTheGrapevine.Kattio(System.in, System.out);

        int n = io.getInt();
        int m = io.getInt();
        int d = io.getInt();
        neighbours = new LinkedList[n];
        skepticism = new int[n];
        HashMap<String, Integer> people = new HashMap();
        visited = new boolean[n];
        int[] oldSkepticism = new int[n];

        int skept;
        for (int i = 0; i < n; i++) {
            people.put(io.getWord(), i);
            skept = io.getInt();
            skepticism[i] = skept;
            oldSkepticism[i] = skept;
            neighbours[i] = new LinkedList();
            visited[i] = false;
        }

        int person1;
        int person2;
        for (int i = 0; i < m; i++) {
            person1 = people.get(io.getWord());
            person2 = people.get(io.getWord());
            neighbours[person1].add(person2);
            neighbours[person2].add(person1);
        }

        int source = people.get(io.getWord());
        spreadRumour(source, source, d);

        int count = 0;
        for(int i = 0; i < skepticism.length; i++){
            if(i == source){continue;}
            if(oldSkepticism[i] > skepticism[i]){
                count++;
            }
        }
        io.println(count);
        io.close();
    }


    public static void spreadRumour(int node, int parent, int daysLeft) {
        if (daysLeft < 1) {
            return;
        }
        if (skepticism[node] < 1) {
            visited[node] = true;
            for (int neighbour : neighbours[node]) {
                if (neighbour != parent) {
                    skepticism[neighbour]--;
                }
                if (!visited[neighbour]) {
                    spreadRumour(neighbour, node, daysLeft - 1);
                }
            }
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
            if (token == null) try {
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
