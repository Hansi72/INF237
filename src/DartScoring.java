import java.io.*;
import java.util.*;

public class DartScoring {
    static DartScoring.Kattio io = new DartScoring.Kattio(System.in, System.out);

    public static void main(String[] args) {
        double[] inputLine;
        vector[] darts;
        while (io.hasMoreTokens()) {
            inputLine = Arrays.stream(io.line.split(" ")).mapToDouble(Double::parseDouble).toArray();
            darts = new vector[inputLine.length / 2];
            for (int i = 0; i < darts.length; i++) {
                darts[i] = new vector(io.getDouble(), io.getDouble());
            }

            //todo find the lowest Y coordinate (if for any two y y1 == y2, choose lowest x) = P

            //todo sort by increasing order of the angle they and the point P make with the x-axis (lag metode, og custom comparator(dot product) for sorting) (de nevner heapsort?)

            //todo for each point do work (se om den går til høyre eller venstre?) bruk en stack

            io.println("answer");
        }
        io.close();
    }

    static double dot(vector p1, vector p2){
        //todo
        return 0.0;
    }

    static class vector {
        double x;
        double y;

        public vector(double x, double y) {
            this.x = y;
            this.y = y;
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


