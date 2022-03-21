import java.io.*;
import java.util.*;

public class test {
    static test.Kattio io = new test.Kattio(System.in, System.out);
    static int[] movieOrdering;

    public static void main(String[] args) {
        int tasks = io.getInt();

        //task loop
        for (int i = 0; i < tasks; i++) {
            int movieCount = io.getInt();
            int[] movies = new int[movieCount + 1];
            movieOrdering = new int[movieCount * 4];

            //init fill
            for (int j = 1; j < movieCount + 1; j++) {
                movies[j] = movieCount - j + 1;
                Update(j, 1);
            }

            int count = movieCount;
            int queryCount = io.getInt();
            for (int k = 0; k < queryCount; k++) {

                count++;
                int currentMovie = io.getInt();
                int index = movies[currentMovie];
                int total = 0;

                //walk up the three while gathering total
                while (index > 0) {
                    total = total + movieOrdering[index];
                    index = GetParent(index);
                }
                Update(movies[currentMovie], -1);
                movies[currentMovie] = count;
                Update(movies[currentMovie], 1);
                io.print(movieCount - total + " ");
            }
            io.println("");
        }
        io.close();
    }

    static void Update(int index, int value) {
        if (index > movieOrdering.length - 1) {
            return;
        }
        movieOrdering[index] = movieOrdering[index] + value;
        Update(GetNext(index), value);
    }

    static int GetNext(int i) {
        return i + (-i & i);
    }

    static int GetParent(int i) {
        return i - (-i & i);
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


