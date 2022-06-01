import java.io.*;
import java.util.StringTokenizer;

public class ImperfectGPS {
    static ImperfectGPS.Kattio io = new ImperfectGPS.Kattio(System.in, System.out);


    /*
    finn 2 punkter sånn at GPSen er i mellom disse punktene, finn ut retningen på disse punktene.
    finner ut retningen av løperen og beregner hvor langt hen har kommet når GPSen ticker.
     */


    public static void main(String[] args) {
        int turnCount = io.getInt();
        int interval = io.getInt();

        //get input
        vector[] track = new vector[turnCount];
        int[] scalars = new int[turnCount];
        for (int i = 0; i < turnCount; i++) {
            int x = io.getInt();
            int y = io.getInt();
            int scalar = io.getInt();
            scalars[i] = scalar;
            track[i] = new vector(x, y);
        }

        //output
    if(interval == 1){
        io.print(0.0);
    }else{
        vector[] GPS = CreateGPSData(track, scalars, interval);
        double realDistance = RunDistance(track);
        io.print((double)Math.round((((realDistance - RunDistance(GPS)) / realDistance * 100)) * 100000d) / 100000d);
    }
        io.close();
    }

    static vector[] CreateGPSData(vector[] track, int[] scalars, int interval) {
        int currentInterval = interval;
        vector[] GPSTrack = new vector[(int) (Math.ceil((double) scalars[scalars.length - 1] / interval) + 1)];
        vector direction = new vector(0,0);
        GPSTrack[0] = new vector(track[0].x, track[0].y); //<<<--------------
        int trackIndex = 1;
        double dirScale;

        //for each GPS index to create.
        for (int GPSIndex = 1; GPSIndex < GPSTrack.length; GPSIndex++) {
            //increase trackIndex until it is above GPS index (to get in between two points in the real track)
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
        return Math.sqrt((vector1.x - vector2.x) * (vector1.x - vector2.x) + (vector1.y - vector2.y) * (vector1.y - vector2.y));
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

    static class vector {
        double x;
        double y;

        vector(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}


