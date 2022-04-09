import java.io.*;
import java.util.*;

public class SocialAdvertising {
    static HoleyNQueens.Kattio io = new HoleyNQueens.Kattio(System.in, System.out);

    public static void main(String[] args) {

        int caseCount = io.getInt();
        int groupSize;
        int friendless;

        for (int currCase = 0; currCase < caseCount; currCase++) {
            //setup
            friendless = 0;
            groupSize = io.getInt();
            int[] friends = new int[groupSize];
            int popularity;
            for (int i = 0; i < friends.length; i++) {
                popularity = io.getInt();
                if (popularity == 0) {
                    friendless = FlipBit(friendless, i);
                }
                for (int j = 0; j < popularity; j++) {
                    friends[i] = FlipBit(friends[i], io.getInt() - 1);
                }
            }

            //work
            if (friends.length == 1) {
                io.println("1");
            } else {
                int subset = 1;
                int max = FlipBit(0, friends.length);
                while (true) {
                    if (CanReachAll(friends, subset | friendless, max)) { //People with no friends will always be in the solution.
                        io.println(Integer.bitCount(subset | friendless));
                        break;
                    }
                    subset = NextPermutation(subset, max);
                }
            }
        }
        io.close();
    }

    static boolean CanReachAll(int[] friends, int subset, int max) {
        int friendsReached = subset;

        for (int i = 0; i < friends.length; i++) {
            if (TestBit(subset, i) == 1) {
                friendsReached = friendsReached | friends[i]; //add all friends to friendsreached
            }
            if (friendsReached == max - 1) {
                return true;
            }
        }
        return false;
    }

    static int TestBit(int bits, int bit) {
        return bits >> bit & 1;
    }

    static int FlipBit(int bits, int bit) {
        return bits ^ (1 << bit);
    }

    //'gospers hack'
    static int NextPermutation(int n, int max) {
        int c = n & -n;
        int r = n + c;
        int result = (((r ^ n) >>> 2) / c) | r;
        if (result >= max) {
            int bitCount = Integer.bitCount(n);
            result = 0;
            for (int i = 0; i < bitCount + 1; i++) {
                result = FlipBit(result, i);
            }
        }

        return result;
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


