import java.io.*;
import java.util.*;

public class Spiderman {
    static Spiderman.Kattio io = new Spiderman.Kattio(System.in, System.out);
    static HashMap<String, LinkedList<Integer>> memoized = new HashMap();

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

        //LinkedList<Integer> result =
                climb(st1);

        //printAnswer(result);

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

    static LinkedList<Integer> climb(LinkedList<Integer> heights) {
        if (heights.size() == 1) {
            if (heights.get(0) == 0) {
                LinkedList<Integer> returnList = new LinkedList();
                returnList.push(0);
                returnList.push(0);
                return returnList;
            } else {
                return null;
            }
        }
        if (heights.peek() < 0) {
            return null;
        }
        String heightsKey = heights.toString();
        if(memoized.containsKey(heightsKey)) {

            if (memoized.get(heightsKey) != null) {
                System.out.println("---------------start-----------------");
                System.out.println("Currently at::::  Heights");
                for (int i = 0; i < heights.size(); i++) {
                    System.out.print(heights.get(i) + ", ");
                }
                System.out.println("");

                System.out.println("and returning MEMO, results");
                for (int i = 0; i < memoized.get(heightsKey).size(); i++) {
                    System.out.print(memoized.get(heightsKey).get(i) + ", ");
                }
                System.out.println("");
                System.out.println("---------------end-----------------");
                return memoized.get(heights);
            }
            return null;
        }



        //create return array
        LinkedList<Integer> output;

        LinkedList<Integer> upStack = new LinkedList((LinkedList) heights.clone());
        LinkedList<Integer> downStack = new LinkedList((LinkedList) heights.clone());

        upStack.push(upStack.pop() + upStack.pop());
        downStack.push(downStack.pop() - downStack.pop());

        output = leastHeighted(climb(upStack), climb(downStack));

        if (output == null) {
            memoized.put(heightsKey, null);
            return null;
        }

        int currentHeight = heights.peek();
        output.push(currentHeight);

        if(output.peekLast() < currentHeight){
            output.pollLast();
            output.offerLast(currentHeight);
        }

        System.out.println("heigths :" + heights.toString());
        System.out.println("output :" + output.toString());

        memoized.put(heightsKey, new LinkedList<>());
        for(int i = 0; i < output.size(); i++){
            //while(!output.isEmpty()){
            memoized.get(heightsKey).offerLast(output.get(i));
        }
        System.out.println("memoized: "+ memoized );
        System.out.println("-----------------------");
        return memoized.get(heightsKey);
    }

    static LinkedList<Integer> leastHeighted(LinkedList<Integer> stack1, LinkedList<Integer> stack2) {
        if (stack1 == null) {
            return stack2;
        }
        if (stack2 == null) {
            return stack1;
        }

        if (stack1.peek() < stack2.peek()) {
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


