import java.io.*;
import java.util.StringTokenizer;

public class Firefly {
    public static void main(String[] args) {
        Firefly.Kattio io = new Firefly.Kattio(System.in, System.out);

        int N = io.getInt();
        int H = io.getInt();

        int[] stalagmites = new int[H];
        int[] stalactites = new int[H];
        for (int i = 0; i < N / 2; i++) {
            stalagmites[io.getInt() - 1]++;
            stalactites[io.getInt() - 1]++;
        }

        int botCave[] = new int[H];
        int topCave[] = new int[H];
        int overBot = 0;
        int overTop = 0;
        for (int i = H - 1; i >= 0; i--) {
            botCave[i] = stalagmites[i] + overBot;
            overBot = botCave[i];

            topCave[i] = stalactites[i] + overTop;
            overTop = topCave[i];
        }
        int current;
        int min = N;
        int count = 0;
        for (int i = 0; i < H; i++) {
            current = botCave[i] + topCave[H - 1 - i];
            if (current <= min) {
                if (current == min) {
                    count++;
                } else {
                    min = current;
                    count = 1;
                }
            }
        }
        io.println(min + " " + count);
        io.close();
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
