import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Spiderman {
    static Spiderman.Kattio io = new Spiderman.Kattio(System.in, System.out);
    static int[] climbs;
    static LinkedList<Integer>[] memoized;
    static LinkedList<Queue<Integer>> pathsToZero = new LinkedList();
    static LinkedList<Queue<Integer>> shortcuts = new LinkedList();
    static LinkedList<Integer>[] shortcutInfo;

    //fixme finn alle nullpaths, sett sammen paths med memoized, såå regn ut høyden per path og velg laveste.
    public static void main(String[] args) {
        int tasks = io.getInt();
        for (int task = 0; task < tasks; task++) {
            System.out.println("task " + task);
            climbs = new int[io.getInt()];
            memoized = new LinkedList[climbs.length];
            shortcutInfo = new LinkedList[climbs.length];
            for (int climb = 0; climb < climbs.length; climb++) {
                memoized[climb] = new LinkedList();
                shortcutInfo[climb] = new LinkedList();
                climbs[climb] = io.getInt();
            }
            Queue<Integer> path = new LinkedList();
            path.add(climbs[0]);
            climb(1, climbs[0], new LinkedList());
        }

        Queue<Integer> currentPath;
        System.out.println("shortcuts");
        LinkedList<String> bestShortcuts;
        while(!shortcuts.isEmpty()){
            //todo sammenlign shortcuts og finn de med lavest topphøyde (for lignende shortcuts. bruk SET?)
            currentPath = shortcuts.pop();
            //System.out.println(shortcutInfo[currentPath.size()].pop());
            System.out.println(currentPath);
        }
        /*
        pathtoZero er nå eks: 3-1-6-3-2-0
        shortcut eks: 3-5-0-3
        shortcut eks2: 3-6-0-3
        shortcut eks3: 3-2-3-1-4
        todo
        for hver pathtozero
                for pathtozero[i]
                    if shortcutinfo[i].contains == pathtozero[i];   //todo bytte til en mer effektiv runtime for .contains()?
                            for tilsvarende shortcuts, finn den med lavest maxtall (husk å først sammenlign med pathtozero)




        */

        System.out.println("paths to zero");
        while(!pathsToZero.isEmpty()){
            System.out.println(pathsToZero.pop());
            //todo gå gjennom pathen og hvis shortcuts matcher høyden og i, sammenlign topphøyden på disse.
        }

        io.close();

    }
    //todo ---------------------
    static public void climb(int i, int height, Queue<Integer> path) {
        if (i == climbs.length) {
            System.out.println("end. height = " + height + " path: " + path);
            if (height == 0) {
                pathsToZero.push(path);
                return;
            } else {
                //todo do something?
                return;
            }
        }

        if (memoized[i].contains(height)) {
            //todo lagre topheight og path sammen? hvis paths som er lagret har lik start, ta den med lavest topheight
            shortcuts.push(path);
            shortcutInfo[i].push(height);
            return;
        }
        memoized[i].push(height);//todo

       // System.out.println("im at: " + i + " with height: " + height + "  and topheight: " + topHeight[i]);

        //todo bytt ut string concatenation med en stack el lignende.
        if (height - climbs[i] >= 0) {
                path.add(height - climbs[i]);
                climb(i + 1, height - climbs[i], path);
                path.remove();
        }
        path.add(height + climbs[i]);
        climb(i + 1, height + climbs[i], path);


    }



    static public void climbOld(int i, int height, String path) {
        if (i == climbs.length) {
            System.out.println("end. height = " + height + " path: " + path);
            if (height == 0) {
                //pathsToZero.push(path);
                return;
            } else {
                //todo do something?
                return;
            }
        }

        if (memoized[i].contains(height)) {
            //todo lagre topheight og path sammen? hvis paths som er lagret har lik start, ta den med lavest topheight
            //shortcuts.push(path);
            shortcutInfo[i].push(height);
            return;
        }
        memoized[i].push(height);//todo

        // System.out.println("im at: " + i + " with height: " + height + "  and topheight: " + topHeight[i]);

        //todo bytt ut string concatenation med en stack el lignende.
        if (height - climbs[i] >= 0) {
            int res = height - climbs[i];
            climbOld(i + 1, height - climbs[i], path + "D");
        }
        climbOld(i + 1, height + climbs[i], path + "U");

    }





    class shortcut{
        String path;
        int maxHeight;
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


