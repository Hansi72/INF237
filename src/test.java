import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

public class test {
    static test.Kattio io = new test.Kattio(System.in, System.out);

    public static void main(String[] args) {
        int turnCount = io.getInt();
        int interval = io.getInt();

        vector[] track = new vector[turnCount];
        int[] scalars = new int[turnCount];
        for (int i = 0; i < turnCount; i++) {
            int x = io.getInt();
            int y = io.getInt();
            int scalar = io.getInt();
            scalars[i] = scalar;
            track[i] = new vector(x, y);
        }

        vector[] GPS = CreateGPSData(track, scalars, interval);
        BigDecimal realDistance = RunDistance(track);
        BigDecimal diff = realDistance.subtract(RunDistance(GPS));
        io.print((diff.divide(realDistance, 25, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)));
        io.close();
    }

    static vector[] CreateGPSData(vector[] track, int[] scalars, int interval) {
        int currentInterval = interval;
        vector[] GPSTrack = new vector[(int) (Math.ceil((double) scalars[scalars.length - 1] / interval) + 1)];
        vector direction = new vector(0,0);
        GPSTrack[0] = new vector(0, 0);
        int trackIndex = 1;
        double dirScale;
        int GPSIndex = 1;
        while(currentInterval < scalars[scalars.length-1]) {
            //increase trackIndex until it is above GPS index (to get it between two points)
            while (scalars[trackIndex] < currentInterval && trackIndex < track.length - 1) {
                trackIndex++;
            }
            //find the vector basis (direction) of the line the GPS point is in between
            dirScale = (scalars[trackIndex] - scalars[trackIndex - 1]);
            direction.x = (track[trackIndex].x - track[trackIndex - 1].x) / dirScale;
            direction.y = (track[trackIndex].y - track[trackIndex - 1].y) / dirScale;

            //add to GPS location
            GPSTrack[GPSIndex] = new vector(track[trackIndex].x - direction.x * (scalars[trackIndex] - currentInterval), track[trackIndex].y - direction.y * (scalars[trackIndex] - currentInterval));
            currentInterval = currentInterval + interval;
            GPSIndex++;
        }
        GPSTrack[GPSTrack.length - 1] = new vector(track[track.length - 1].x, track[track.length - 1].y);
        return GPSTrack;
    }

    static BigDecimal RunDistance(vector[] coords) {
        BigDecimal total = new BigDecimal(0);
        for (int i = 1; i < coords.length; i++) {
            total = total.add(Dist(coords[i - 1], coords[i]));
        }
        return total;
    }

    static BigDecimal Dist(vector vector1, vector vector2) {
        return new BigDecimal(Math.sqrt((vector1.x - vector2.x) * (vector1.x - vector2.x) + (vector1.y - vector2.y) * (vector1.y - vector2.y)));
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
/*
class vector {
    double x;
    double y;

    vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}


 */