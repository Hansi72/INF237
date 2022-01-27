import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class EvenUpSolitaire {
    public static void main(String[] args) {
        EvenUpSolitaire.Kattio io = new EvenUpSolitaire.Kattio(System.in, System.out);

        LinkedList<Boolean> cards = new LinkedList<>();
        io.getInt();
        while (io.hasMoreTokens()) {
            if(io.getInt() % 2 == 0){
                cards.add(true);
            }else{
                cards.add(false);
            }
        }

        boolean currentCard;
        int lastSize = cards.size() + 1;
        ListIterator<Boolean> iter;
        while (cards.size() < lastSize) {
            iter = cards.listIterator();
            lastSize = cards.size();
            while (iter.hasNext()) {
                currentCard = iter.next();
                if (iter.hasNext()) {
                    if (currentCard == iter.next()) {
                        iter.previous();
                        iter.previous();
                        iter.remove();
                        iter.next();
                        iter.remove();
                    } else {
                        iter.previous();
                    }
                }
            }
        }
        io.println(cards.size());
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
