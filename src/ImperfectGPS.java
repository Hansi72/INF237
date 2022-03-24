import java.io.*;
import java.util.StringTokenizer;

public class ImperfectGPS {
    static ImperfectGPS.Kattio io = new ImperfectGPS.Kattio(System.in, System.out);

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
        double realDistance = RunDistance(track);
        io.print((float)((realDistance - RunDistance(GPS)) / realDistance * 100));
        io.close();
    }

    static vector[] CreateGPSData(vector[] track, int[] scalars, int interval) {
        int currentInterval = interval;
        vector[] GPSTrack = new vector[(int) (Math.ceil((double) scalars[scalars.length - 1] / interval) + 1)];
        vector direction = new vector(track[1].x, track[1].y);
        GPSTrack[0] = new vector(0, 0);
        int trackIndex = 1;
        int dirScale;

        for (int GPSIndex = 1; GPSIndex < GPSTrack.length; GPSIndex++) {
            while (scalars[trackIndex] < currentInterval && trackIndex < track.length - 1) {
                trackIndex++;
            }
            dirScale = (scalars[trackIndex] - scalars[trackIndex - 1]);
            direction.x = (track[trackIndex].x - track[trackIndex - 1].x) / dirScale;
            direction.y = (track[trackIndex].y - track[trackIndex - 1].y) / dirScale;

            GPSTrack[GPSIndex] = new vector(track[trackIndex].x - direction.x * (scalars[trackIndex] - currentInterval), track[trackIndex].y - direction.y * (scalars[trackIndex] - currentInterval));
            currentInterval = currentInterval + interval;
        }
        GPSTrack[GPSTrack.length - 1].x = track[track.length - 1].x;
        GPSTrack[GPSTrack.length - 1].y = track[track.length - 1].y;
        return GPSTrack;
    }

    static double RunDistance(vector[] coords) {
        double total = 0.0;
        for (int i = 1; i < coords.length; i++) {
            total = total + Dist(coords[i - 1], coords[i]);
        }
        return total;
    }
    static double Dist(vector vector1, vector vector2) {
        return Math.sqrt(Math.pow((vector1.x - vector2.x), 2) + (Math.pow((vector1.y - vector2.y), 2)));
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

class vector {
    double x;
    double y;

    vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
