import java.io.*;
import java.util.*;

public class MovieCollection {
    static MovieCollection.Kattio io = new MovieCollection.Kattio(System.in, System.out);
    static int[] movieOrdering;
    static int[] movieIndexes;
    static int movieCount;

    public static void main(String[] args) {
        int tasks = io.getInt();

        //task loop
        for (int i = 0; i < tasks; i++) {
            movieCount = io.getInt();
            int queryCount = io.getInt();
            movieIndexes = new int[movieCount + 1];
            movieOrdering = new int[(movieCount+queryCount)*4];

            //init fill
            for (int j = 1; j < movieCount + 1; j++) {
                movieIndexes[j] = movieCount - j + 1;
                Update(j, true);
            }

            int count = movieCount;
            for (int k = 0; k < queryCount; k++) {

                count++;
                int currentMovie = io.getInt();
                int currentIndex = movieIndexes[currentMovie];
                int sum = 0;

                //walk up the three while gathering total
                while (currentIndex > 0) {
                    sum = sum + movieOrdering[currentIndex];
                    currentIndex = GetParent(currentIndex);
                }
                Update(movieIndexes[currentMovie], false);
                movieIndexes[currentMovie] = count;
                Update(movieIndexes[currentMovie], true);
                io.print(movieCount - sum + " ");
            }
            io.println("");
        }
        io.close();
    }

    static void Update(int i, boolean add) {
        if (i > movieOrdering.length-1) {
            return;
        }
        if(add){
            movieOrdering[i] = movieOrdering[i] + 1;
            Update(GetNext(i), true);
        }else{
            movieOrdering[i] = movieOrdering[i] - 1;
            Update(GetNext(i), false);
        }
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


