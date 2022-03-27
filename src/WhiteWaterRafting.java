import java.io.*;
import java.util.*;

public class WhiteWaterRafting {
    static WhiteWaterRafting.Kattio io = new WhiteWaterRafting.Kattio(System.in, System.out);

    public static void main(String[] args) {

        int tasks = io.getInt();
        for (int task = 0; task < tasks; task++) {

            int innerSize = io.getInt();
            vector[] innerPoly = new vector[innerSize];
            for (int i = 0; i < innerSize; i++) {
                innerPoly[i] = new vector(io.getInt(), io.getInt());
            }
            int outerSize = io.getInt();
            vector[] outerPoly = new vector[outerSize];
            for (int i = 0; i < outerSize; i++) {
                outerPoly[i] = new vector(io.getInt(), io.getInt());
            }

            double currentDist;
            double pointMinimum = 10000000;
            for (int i = 0; i < innerPoly.length; i++) {
                for (int j = 0; j < outerPoly.length; j++) {
                    currentDist = Dist(outerPoly[j], innerPoly[i]);
                    if (currentDist < pointMinimum) {
                        pointMinimum = currentDist;
                    }
                }
            }
            io.println(Math.min(Math.min(work(innerPoly, outerPoly), work(outerPoly, innerPoly)), pointMinimum) / 2);
        }
        io.close();
    }

    //checks the distances from points in poly1 to the lines between points in poly2
    static double work(vector[] poly1, vector[] poly2) {
        double minimum = 10000000;
        double currentDist;
        for (int i = 0; i < poly1.length; i++) {
            for (int j = 1; j < poly2.length; j++) {
                currentDist = DistToLine(poly2[j - 1], poly2[j], poly1[i]);
                if (currentDist < minimum && currentDist > 0) {
                    minimum = currentDist;
                }
            }
            //check the line from index 0 to -1
            currentDist = DistToLine(poly2[0], poly2[poly2.length-1], poly1[i]);
            if (currentDist < minimum && currentDist > 0) {
                minimum = currentDist;
            }
        }
        return minimum;
    }

    //returns -1 if the projection does not hit the line segment
    static double DistToLine(vector lineStart, vector lineEnd, vector point) {
        vector V = new vector(point.x - lineStart.x, point.y - lineStart.y);
        vector direction = dirVector(lineStart, lineEnd);
        double scalar = dot(direction, V) / dot(direction, direction);
        vector projection = new vector(V.x - scalar * direction.x, V.y - scalar * direction.y);
        double lineDist = Dist(lineStart, lineEnd); //todo use distSquared instead
        vector pointOnLine = subtract(point, projection); //where the projection (might) hit the line segment
        //if projection is out of bounds for the line segment return -1
        if (lineDist < Dist(lineStart, pointOnLine) || lineDist < Dist(lineEnd, pointOnLine)) { //todo use distSquared instead
            return -1;
        } else {
            return Math.sqrt(dot(projection, projection)); //todo remove sqrt
        }
    }

    static vector subtract(vector v1, vector v2) {
        return new vector(v1.x - v2.x, v1.y - v2.y);
    }

    static double dot(vector v1, vector v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    static vector dirVector(vector start, vector end) {
        double scalar = (Dist(start, end));
        return new vector((start.x - end.x) / scalar, (start.y - end.y) / scalar);
    }

    static double Dist(vector vector1, vector vector2) {
        return Math.sqrt((vector1.x - vector2.x) * (vector1.x - vector2.x) + (vector1.y - vector2.y) * (vector1.y - vector2.y));
    }

    static class vector {
        double x;
        double y;

        vector(double x, double y) {
            this.x = x;
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


