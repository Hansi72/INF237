import java.io.*;
import java.util.*;

public class DartScoring {
    static DartScoring.Kattio io = new DartScoring.Kattio(System.in, System.out);
    static vector P;

    /*
    finner convex hull og regner ut lengden av denne.

    begynner med å finne laveste left most point.
    sorterer alle punkter i forhold til vinkelen til startpunktet.
    sjekk 3 punkter om gangen, hvis dette skaper en venstresving, pop midtpunktet.
     */



    public static void main(String[] args) {
        double[] inputLine;
        vector[] darts;
        int dartCount;
        while (io.hasMoreTokens()) {
            inputLine = Arrays.stream(io.line.split(" ")).mapToDouble(Double::parseDouble).toArray();
            darts = new vector[inputLine.length / 2];
            for (int i = 0; i < darts.length; i++) {
                darts[i] = new vector(io.getDouble(), io.getDouble());
            }
            dartCount = darts.length;
            //find the lowest Y coordinate
            int minY = 0;
            for (int i = 1; i < darts.length; i++) {
                if (darts[i].y < darts[minY].y || (darts[i].y == darts[minY].y && darts[i].x < darts[minY].x)) {
                    minY = i;
                }
            }
            P = darts[minY];

            //Sort by increasing order of angles with P as a base
            Arrays.sort(darts, new CompareByYCord());

            darts = RemoveDuplicates(darts);

            //pop() all points that create the middle point of a left turn of three points
            Stack<vector> dartStack = new Stack<>();
            for (int i = 0; i < darts.length; i++) {
                while (dartStack.size() > 1 && LeftTurn(nextToTop(dartStack), dartStack.peek(), darts[i])) {
                    dartStack.pop();
                }
                dartStack.push(darts[i]);
            }

            io.println(calculateScore(dartStack, dartCount));
        }
        io.close();
    }

    static class CompareByYCord implements Comparator<vector> {
        @Override
        public int compare(vector v1, vector v2) {
            if (v2 == P) {
                return -1;
            }
            if (LeftTurn(P, v1, v2) || v1 == P) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    static boolean LeftTurn(vector v1, vector v2, vector v3) {
        if ((v2.x - v1.x) * (v3.y - v1.y) - (v2.y - v1.y) * (v3.x - v1.x) < 0.0001) {
            return false;
        } else {
            return true;
        }
    }

    static double dist(vector v1, vector v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    static double calculateScore(Stack<vector> darts, int n) {
        double s = 0;
        while (darts.size() > 1) {
            s = s + dist(darts.pop(), darts.peek());
        }
        s = s + dist(darts.pop(), P);
        return 100 * n / (1 + s);
    }

    static vector nextToTop(Stack<vector> stack) {
        vector top = stack.pop();
        vector result = stack.peek();
        stack.push(top);
        return result;
    }

    static class vector {
        double x;
        double y;

        public vector(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static vector[] RemoveDuplicates(vector[] darts) {
            LinkedList<vector> collect = new LinkedList<>();
            int index = 0;
                while(index < darts.length) {
                    while (index < darts.length-1 && darts[index].x == darts[index + 1].x && darts[index].y == darts[index + 1].y) {
                        index++;
                    }
                    collect.add(darts[index]);
                    index++;
                }
        return collect.toArray(new vector[0]);
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


