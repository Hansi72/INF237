import java.io.*;
import java.util.*;

public class RobertHood {
    static RobertHood.Kattio io = new RobertHood.Kattio(System.in, System.out);
    static vector P;

    public static void main(String[] args) {
        int dartCount = io.getInt();
        vector[] darts = new vector[dartCount];
        for (int i = 0; i < dartCount; i++) {
            darts[i] = new vector(io.getInt(), io.getInt());
        }
        //find the convex hull
        //find the lowest Y coordinate
        int minY = 0;
        for (int i = 1; i < darts.length; i++) {
            if (darts[i].y < darts[minY].y || (darts[i].y == darts[minY].y && darts[i].x < darts[minY].x)) {
                minY = i;
            }
        }
        P = darts[minY];

        //Precompute angles
        for (int i = 0; i < darts.length; i++) {
            darts[i].angle = angle(P, darts[i]);
        }

        //Sort by increasing order of angles with P as a base
        Arrays.sort(darts, new CompareByAngle());

        darts = RemoveDuplicates(darts);

        //remove colinear points
        Set<Double> colinearFind = new HashSet<>();
        for (int i = 0; i < darts.length; i++) {
            colinearFind.add(darts[i].angle);
        }
        while (darts.length != colinearFind.size()) {
            colinearFind.clear();
            darts = removeColinear(darts);
            for (int i = 0; i < darts.length; i++) {
                colinearFind.add(darts[i].angle);
            }
        }

        //pop() all points that create the middle point of a left turn of three points
        Stack<vector> dartStack = new Stack<>();
        for (int i = 0; i < darts.length; i++) {
            while (dartStack.size() > 1 && LeftTurn(nextToTop(dartStack), dartStack.peek(), darts[i]) < 1) {
                dartStack.pop();
            }
            dartStack.push(darts[i]);
        }

        vector[] convexHull = new vector[dartStack.size()];
        for (int i = 0; i < convexHull.length; i++) {
            convexHull[i] = dartStack.pop();
        }
        double max = 0;
        int j;
        double lastDist;
        double nextDist;
        //For every point of the convex hull, travel along the convex hull and check distances.
        //If the next point to check has a lower distance to i, the current point is the current max distance for this i.
        for (int i = 0; i < convexHull.length; i++) {
            j = i;
            lastDist = 0;
            nextDist = 0.000000001;
            while (nextDist > lastDist) {
                j++;
                if (j == convexHull.length) {
                    j = 0;
                }
                lastDist = nextDist;
                nextDist = dist(convexHull[i], convexHull[j]);
            }
            max = Math.max(max, lastDist);
        }
        if (max > 0.00001) {
            System.out.println(max);
        } else {
            System.out.println(0);
        }
        io.close();
    }

    static double angle(vector v1, vector v2) {
        vector v2BaseP = new vector(v2.x - v1.x, v2.y - v1.y);
        vector yAxis = new vector(0, 1);
        double dist2 = Math.sqrt(Math.pow(v2BaseP.x, 2) + Math.pow(v2BaseP.y, 2));
        return Math.asin(cross(yAxis, v2BaseP) / (1 * dist2));
    }

    static double cross(vector v1, vector v2) {
        return v1.x * v2.y - v2.x * v1.y;
    }

    static class CompareByAngle implements Comparator<vector> {
        @Override
        public int compare(vector v1, vector v2) {
            return Double.compare(v1.angle, v2.angle);
        }
    }

    static vector[] removeColinear(vector[] darts) {
        int removed = 0;
        for (int i = 1; i < darts.length; i++) {
            if (darts[i].angle == darts[i - 1].angle) {
                removed++;
                if (dist(P, darts[i]) > dist(P, darts[i - 1])) {
                    darts[i - 1] = null;
                } else {
                    darts[i] = null;
                    i++;
                }
            }
        }
        vector[] result = new vector[darts.length - removed];
        int offset = 0;
        for (int i = 0; i < result.length; i++) {
            if (darts[i + offset] != null) {
                result[i] = darts[i + offset];
            } else {
                offset++;
                i--;
            }
        }
        return result;
    }

    static int LeftTurn(vector v1, vector v2, vector v3) {
        int result = (v2.x - v1.x) * (v3.y - v1.y) - (v2.y - v1.y) * (v3.x - v1.x);
        if (result > 0) {
            return 1;
        }
        if (result < 0) {
            return -1;
        }
        return 0;
    }

    static double dist(vector v1, vector v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    static vector nextToTop(Stack<vector> stack) {
        vector top = stack.pop();
        vector result = stack.peek();
        stack.push(top);
        return result;
    }

    static class vector {
        int x;
        int y;
        double angle;

        public vector(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static vector[] RemoveDuplicates(vector[] darts) { //todo remove?
        LinkedList<vector> collect = new LinkedList<>();
        int index = 0;
        while (index < darts.length) {
            while (index < darts.length - 1 && darts[index].x == darts[index + 1].x && darts[index].y == darts[index + 1].y) {
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


