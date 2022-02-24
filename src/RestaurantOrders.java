import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class RestaurantOrders {
    static RestaurantOrders.Kattio io = new RestaurantOrders.Kattio(System.in, System.out);
    static int targetIndex;

    public static void main(String[] args) {


        int menu[] = new int[io.getInt()];

        for (int i = 0; i < menu.length; i++) {
            menu[i] = io.getInt();
        }


        int tasks = io.getInt();

        for (int taskIndex = 0; taskIndex < tasks; taskIndex++) {
            LinkedList<int[]>[] order = new LinkedList[io.getInt() + 1];
            order[0] = new LinkedList();

            //fill first targetIndexes
            for (int menuIndex = 0; menuIndex < menu.length; menuIndex++) {
                targetIndex = menu[menuIndex];
                order[targetIndex] = new LinkedList<int[]>();
                order[targetIndex].push(new int[1]);
                order[targetIndex].peek()[0] = menu[menuIndex];
            }
            for (int orderIndex = 1; orderIndex < order.length; orderIndex++) {
                if (order[orderIndex] != null) {
                    //menuloop
                    for (int menuIndex = 0; menuIndex < menu.length; menuIndex++) {
                        //if target is not out of bounds
                        targetIndex = menu[menuIndex] + orderIndex;
                        if (targetIndex < order.length) {
                            if (order[targetIndex] == null) {
                                order[targetIndex] = new LinkedList<int[]>();
                            }
                            //push orderIndex arrays to targetIndex
                            for (int[] orderList : order[orderIndex]) {
                                order[targetIndex].push(new int[orderList.length + 1]);
                                for (int i = 0; i < orderList.length; i++) {
                                    order[targetIndex].peek()[i] = orderList[i];
                                }
                                order[targetIndex].peek()[order[targetIndex].peek().length - 1] = menu[menuIndex];
                            }
                        }
                    }
                }
/*
//fixme remove me
                System.out.println("-----------  orderIndex::" + orderIndex);
                for (int i = 0; i < order.length; i++) {
                    System.out.print("current order[" + i + "] = [");
                    if (order[i] != null) {
                        for (int k = 0; k < order[i].size(); k++) {
                            System.out.print("[");
                            for (int num : order[i].get(k)) {
                                System.out.print(num + ", ");
                            }
                            System.out.print("]");
                        }
                    }
                    System.out.println("]");
                }
                //fixme remove me
*/

            }

            if (order[order.length - 1] != null) {
                //io.println("task: " + taskIndex + " finished size = " + order[order.length - 1].size());





                LinkedList<int[]> solution = order[order.length - 1];
                HashMap<String, Integer> map = new HashMap();
                for(int[] i : solution){
                    Arrays.sort(i);
                    //todo temp
                    List<Integer> listTest = Arrays.stream(i).boxed().toList();
                    //todo temp
                    //io.println("map put" + listTest.hashCode());
                    map.put(listTest.toString(), 0);
                }

//io.println("mapsize=" + map.size());


                if (map.size() < 2) {
                    //io.println("");
                    for (int i = 0; i < solution.peek().length - 1; i++) {
                        io.print(Arrays.binarySearch(menu, solution.peek()[i]) +1 + " ");
                    }
                    io.print(Arrays.binarySearch(menu, solution.peek()[solution.size() - 1]) +1);
                } else {
                    io.println("Ambiguous");
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
