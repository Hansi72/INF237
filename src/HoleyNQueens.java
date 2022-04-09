import java.io.*;
import java.util.*;

public class HoleyNQueens {
    static HoleyNQueens.Kattio io = new HoleyNQueens.Kattio(System.in, System.out);
    static int boardSize;
    static int count;
    static boolean[][] holes;

    public static void main(String[] args) {

        int holeCount;
        while (true) {
            count = 0;
            boardSize = io.getInt();
            holeCount = io.getInt();
            if (boardSize == 0 & holeCount == 0) {
                break;
            }
            holes = new boolean[boardSize][boardSize];
            for (int i = 0; i < holeCount; i++) {
                holes[io.getInt()][io.getInt()] = true;
            }
            placeQueen(0, 0, 0, 0);
            io.println(count);
        }
        io.close();
    }

    //queensPlaced = current row. columnCheck and diagonalChecks are bitmasks for spots already occupied (columns and diagonals)
    static void placeQueen(int queensPlaced, int columnCheck, int diagonalCheckR, int diagonalCheckL) {
        if (queensPlaced >= boardSize) {
            count++;
            return;
        }
        //loop through columns on current row(queensPlaced) and try to place a queen
        for (int i = 0; i < boardSize; i++) {
            if (CanPlace(queensPlaced, i, columnCheck, diagonalCheckR, diagonalCheckL)) {
                placeQueen(queensPlaced + 1, FlipBit(columnCheck, i), FlipBit(diagonalCheckR, queensPlaced + i), FlipBit(diagonalCheckL, queensPlaced - i));
            }
        }
    }

    static boolean CanPlace(int row, int column, int columnCheck, int diagonalCheckR, int diagonalCheckL) {
        if (holes[row][column]) {
            return false;
        }
        if (TestBit(columnCheck, column)) {
            return false;
        }
        //"The sum of row and column is constant and unique for each right diagonal"
        if (TestBit(diagonalCheckR, row + column)) {
            return false;
        }
        //"The difference of row and column is constant and unique for each left diagonal"
        if (TestBit(diagonalCheckL, row - column)) {
            return false;
        }
        return true;
    }

    static boolean TestBit(int bits, int bit) {
        if ((bits >> bit & 1) == 1) {
            return true;
        }
        return false;
    }

    static int FlipBit(int bits, int bit) {
        return bits ^ (1 << bit);
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


