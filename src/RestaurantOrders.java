import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public class RestaurantOrders {
    static RestaurantOrders.Kattio io = new RestaurantOrders.Kattio(System.in, System.out);
    static int targetIndex;
    static boolean[] ambiguous;

    public static void main(String[] args) {

        int menu[] = new int[io.getInt()];
        HashSet<Integer> menuDuplicates = new HashSet<>();
        HashSet<Integer> dupCheck = new HashSet();

        int menuItem;
        int dupCount = 0;
        for (int i = 0; i < menu.length; i++) {
            menuItem = io.getInt();
            menu[i] = menuItem;
            dupCheck.add(menuItem);
            if(dupCheck.size()-1 + dupCount < i){
                dupCount++;
                menuDuplicates.add(menuItem);
            }
        }


        int[] tasks = new int[io.getInt()];
        int maxTask = 0;
        for (int taskIndex = 0; taskIndex < tasks.length; taskIndex++) {
            int task = io.getInt();
            if (task > maxTask) {
                maxTask = task;
            }
            tasks[taskIndex] = task;
        }

        ambiguous = new boolean[maxTask + 1];
        HashSet<ArrayList<Integer>>[] orders = new HashSet[maxTask + 1];
        orders[0] = new HashSet<>();

        //fill first targetIndexes
        for (int menuIndex = 0; menuIndex < menu.length; menuIndex++) {
            targetIndex = menu[menuIndex];

            if (orders.length - 1 < targetIndex) {
                continue;
            }
            orders[targetIndex] = new HashSet<>();

            ArrayList<Integer> newOrder = new ArrayList<Integer>(menu.length);
            for (int i = 0; i < menu.length; i++) {
                newOrder.add(0);
            }
            newOrder.set(menuIndex, 1);

            orders[targetIndex] = new HashSet<>();
            orders[targetIndex].add(newOrder);
        }

        //orderLoop
        for (int orderIndex = 1; orderIndex < orders.length; orderIndex++) {
            if (orders[orderIndex] == null) {
                continue;
            }
            if (orders[orderIndex].size() > 1) {
                ambiguous[orderIndex] = true;
            }


            //menuloop
            for (int menuIndex = 0; menuIndex < menu.length; menuIndex++) {
                targetIndex = menu[menuIndex] + orderIndex;
                //if target is out of bounds
                if (orders.length - 1 < targetIndex) {
                    continue;
                }

                if (orders[targetIndex] == null) {
                    orders[targetIndex] = new HashSet<>();
                }

                if (ambiguous[orderIndex]) {
                    ambiguous[targetIndex] = true;
                } else {
                    //todo loop through all arrays in orderIndex hashSet, copy and add +1 to current menuitem (set as ambiguous if current is ambiguous)
                    for (ArrayList<Integer> order : orders[orderIndex]) {
                        ArrayList<Integer> newOrder = new ArrayList<>(menu.length);
                        for (int i = 0; i < menu.length; i++) {
                            newOrder.add(order.get(i));
                        }
                        newOrder.set(menuIndex, newOrder.get(menuIndex) + 1);
                        orders[targetIndex].add(newOrder);
                    }
                }
            }
        }
        //print answer
        boolean ambi;
        for (int task : tasks) {
            if (ambiguous[task]) {
                io.println("Ambiguous");
            } else if (orders[task] != null) {
                ArrayList<Integer> answer = orders[ task].iterator().next();
                ambi = false;
                for(int i = 0; i < answer.size(); i++){
                    if(answer.get(i) > 0 && menuDuplicates.contains(menu[i])){
                        ambi = true;
                        break;
                    }
                }
                if(ambi) {
                    io.println("Ambiguous");
                }else{
                boolean first = true;
                for (int i = 0; i < answer.size(); i++) {
                    for (int k = 0; k < answer.get(i); k++) {
                        if (first) {
                            first = false;
                            io.print((i + 1));
                        } else {
                            io.print(" " + (i + 1));
                        }
                    }
                }
                io.println("");
            }
            } else {
                io.println("Impossible");
            }
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

