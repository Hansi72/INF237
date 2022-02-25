import java.io.*;
import java.util.*;


public class RestaurantOrders {
    static RestaurantOrders.Kattio io = new RestaurantOrders.Kattio(System.in, System.out);
    static int[][] dp;
    static int[] menu;
    static HashMap<Integer, Integer> unSortedMenu;

    public static void main(String[] args) {

        int menuLen = io.getInt();
        menu = new int[menuLen];
        unSortedMenu = new HashMap<>();

        for (int i = 0; i < menu.length; i++) {
            int menuItem = io.getInt();
            menu[i] = menuItem;
            unSortedMenu.put(menuItem, i+1);
        }
        Arrays.sort(menu);

        int[] tasks = new int[io.getInt()];
        int maxTask = 0;
        for (int taskIndex = 0; taskIndex < tasks.length; taskIndex++) {
            int task = io.getInt();
            if (task > maxTask) {
                maxTask = task;
            }
            tasks[taskIndex] = task;
        }

        dp = new int[menu.length + 1][maxTask + 1];

        //initialize array, resten er false by default
        for (int i = 0; i < menu.length + 1; i++) {
            dp[i][0] = 1;
        }

        for (int menuIndex = 1; menuIndex < menu.length + 1; menuIndex++) {
            for (int target = 1; target < maxTask + 1; target++) {
                int itemPrice = menu[menuIndex - 1];

                //hvis den "over" er true, sett til true.
                dp[menuIndex][target] = dp[menuIndex][target] + dp[menuIndex - 1][target];

                //skip om target er out of bounds
                if ((target - itemPrice < 0)) {
                    continue;
                }
                //legg til den som ligger itemPrice til venstre i arrayet
                dp[menuIndex][target] = dp[menuIndex][target] + dp[menuIndex][target - itemPrice];
            }
        }
        //print answer
        for (int task = 0; task < tasks.length; task++) {
            if (dp[menu.length][tasks[task]] == 0) {
                if (task == tasks.length - 1) {
                    System.out.print("Impossible");
                } else {
                    System.out.println("Impossible");
                }
            } else if (dp[menu.length][tasks[task]] > 1) {
                if (task == tasks.length - 1) {
                    System.out.print("Ambiguous");
                } else {
                    System.out.println("Ambiguous");
                }
            } else {
                int[] solution = getAnswer(tasks[task], menu.length, new int[0]);
                boolean first = true;
                int[] solutionIndexes = new int[solution.length];
                for (int i = 0; i < solution.length; i++) {
                   solutionIndexes[i] = unSortedMenu.get(solution[i]);
                }
                Arrays.sort(solutionIndexes);
                String answer = "";
                for (int i : solutionIndexes) {
                    if (first) {
                        first = false;
                        answer = answer + i;
                    } else {
                        answer = answer + " " + i;
                    }
                }
                if (task == tasks.length - 1) {
                    System.out.print(answer);
                } else {
                    System.out.println(answer);
                }
            }
        }
    }

    static int[] getAnswer(int sum, int menuIndex, int[] solution) {
        if (sum < 1) {
            return solution;
        }
        if (dp[menuIndex - 1][sum] == 0) {
            int newSum = sum - menu[menuIndex - 1];
            int[] newSolution = new int[solution.length + 1];
            System.arraycopy(solution, 0, newSolution, 0, solution.length);
            newSolution[newSolution.length - 1] = menu[menuIndex - 1];
            return getAnswer(newSum, menu.length, newSolution);
        }
        return getAnswer(sum, menuIndex - 1, solution);
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
            if (token == null) try {
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
