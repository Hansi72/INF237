import java.io.*;
import java.util.StringTokenizer;

public class earlyWinter {
    public static void main(String[] args) {
        earlyWinter.Kattio io = new earlyWinter.Kattio(System.in, System.out);

        io.getInt();
        int thisYear = io.getInt();
        int count = 0;
        boolean never = true;
        while (io.hasMoreTokens()) {
            if (thisYear >= io.getInt()) {
                io.println("It hadn't snowed this early in " + count + " years!");
                never = false;
                break;
            }
            count++;
        }
        if(never){
            io.println("It had never snowed this early!");
        }
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





