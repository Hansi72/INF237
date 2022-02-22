import java.io.*;
import java.util.*;

public class Spiderman {
    static Spiderman.Kattio io = new Spiderman.Kattio(System.in, System.out);
    static HashMap<LinkedList<Integer>, int[]> memoized = new HashMap();

    public static void main(String[] args) {
/*
        int tasks = io.getInt();
        for(int i = 0; i < tasks; i++){
            int climbs = io.getInt();
            Stack<Integer> heights = new Stack();
            for(int k = 0; k < climbs; k++){
                heights.push(io.getInt());
            }
            printAnswer(climb(heights, heights.peek()));

        }
*/
        LinkedList<Integer> st1 = new LinkedList();
        st1.push(20);
        st1.push(20);
        st1.push(20);
        st1.push(20);

        int[] result = climb(st1);

        //printAnswer(result);

        for(int i:result) {
            System.out.print(i + ", " );
        }System.out.println("");

        System.out.println("memosize "+ memoized.size());
        System.out.println(memoized);

        io.close();

    }

    static void printAnswer(LinkedList<Integer> result) {
        if (result == null) {
            io.println("IMPOSSIBLE");
        } else {
            //io.print("U");
            while(result.size() > 2){
                if (result.pop() > result.peek()) {
                    io.print("D");
                } else {
                    io.print("U");
                }
            }
            //io.print("D");
            io.println("");
        }

    }

    static int[] climb(LinkedList<Integer> heights) {
        if (heights.size() == 1) {
            if (heights.get(0) == 0) {
                int[] returnList = new int[2];
                returnList[0] = 0;
                returnList[0] = 0;
                return returnList;
            } else {
                return null;
            }
        }
        if (heights.peek() < 0) {
            return null;
        }
        if(memoized.containsKey(heights)) {

            if (memoized.get(heights) != null) {
                System.out.println("---------------start-----------------");
                System.out.println("Currently at::::  Heights");
                for (int i = 0; i < heights.size(); i++) {
                    System.out.print(heights.get(i) + ", ");
                }
                System.out.println("");

                System.out.println("and returning MEMO, results");
                for (int i = 0; i < memoized.get(heights).length; i++) {
                    System.out.print(memoized.get(heights)[i] + ", ");
                }
                System.out.println("");
                System.out.println("---------------end-----------------");
                return memoized.get(heights);
            }
            return null;
        }



        //create return array
        int[] result;

        LinkedList<Integer> upStack = new LinkedList((LinkedList) heights.clone());
        LinkedList<Integer> downStack = new LinkedList((LinkedList) heights.clone());

        upStack.push(upStack.pop() + upStack.pop());
        downStack.push(downStack.pop() - downStack.pop());

        result = leastHeighted(climb(downStack), climb(upStack));

        if (result == null) {
            memoized.put(heights, null);
            return null;
        }
        System.out.print("result :");// + heights.peek() + ", ");
        for(int i = 0; i < result.length; i++){
            System.out.print(result[i]+ ", ");
        }System.out.println("");


        int currentHeight = heights.peek();
        if(result[result.length-1] < currentHeight){
            result[result.length-1] = heights.peek(); //todo denne lagrer en referanse istedenfor intValue();
        }


        System.out.println("heigths :" + heights.toString());
        //System.out.println("output :" + currentHeight + ", "+ result.toString());

        memoized.put(heights, new int[result.length+1]);
        memoized.get(heights)[0] = heights.peek();
        for(int i = 0; i < result.length; i++){
            //while(!output.isEmpty()){
            memoized.get(heights)[i+1] = result[i];
        }


        //System.out.println("memoized: "+ memoized.toString());



        for(LinkedList<Integer> key:memoized.keySet()){
            if(memoized.get(key) !=  null){
                System.out.print(key + " = " );
            for(int i: memoized.get(key)) {
                System.out.print(i + ", ");
            }}
            System.out.println("");
        }


        System.out.println("-----------------------");
        return memoized.get(heights);
    }

    static int[] leastHeighted(int[] stack1, int[] stack2) {
        if (stack1 == null) {
            return stack2;
        }
        if (stack2 == null) {
            return stack1;
        }

        if (stack1[stack1.length-1] < stack2[stack2.length-1]) {
            return stack1;
        } else {
            return stack2;
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


