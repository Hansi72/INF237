public class testCaseCreator {



    public class Detour {
        public int n;
        public int m;
        public int[] intersections;


        public Detour(){

        }

    }


   public class restaurantOrders {
       public int n;
       public int[] prices;
       public int orderCount;
       public int[] orders;
       public restaurantOrders() {
           int max = 100;
           int min = 1;
           int range = max - min;
           n = (int) (Math.random() * range) + min;
           max = 1000;
           min = 1;
           range = max - min;
           prices = new int[n];
           for (int i = 0; i < n; i++) {
               prices[i] = (int) (Math.random() * range) + min;
           }
           max = 1000;
           min = 1;
           range = max - min;
           orderCount = (int) (Math.random() * range) + min;

           max = 30000;
           min = 1;
           range = max - min;
           orders = new int[orderCount];
           for (int i = 0; i < orderCount; i++) {
               orders[i] = (int) (Math.random() * range) + min;
           }

           System.out.println("Test data:");
           System.out.println("n = " + n);
           System.out.println("Prices");
           for (int i : prices) {
               System.out.print(i + ", ");
           }
           System.out.println("");
           System.out.println("Nr of orders: " + orderCount);
           System.out.println("orders");
           for (int i : orders) {
               System.out.print(i + ", ");
           }
       }
   }


}
