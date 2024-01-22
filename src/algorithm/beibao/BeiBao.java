package algorithm.beibao;

public class BeiBao {


    /**
     * 01背包问题
     *
     * @param mount 物品的个数
     * @param weight 背包的重量
     * @param weightArr 物品的重量数组
     * @param valueArr  物品的价值数组
     * @return 能够获得的最大价值
     */


    public static void maxValue(int mount,int weight,int[] weightArr,int[] valueArr  ){


        /**
         * 二维数组 每个元素表示当前物品个数下，背包容量下能获得的最大价值
         *
         *         0      1     2     3     4  （背包容量）
         *      0  0      0     0     0     0
         *      1  0    1500   1500  1500  1500
         *      2  0    1500   1500  1500  3000
         *      3  0    1500   1500  2000  3500
         * （物品个数）
         *
         */
        int[][] dp=new int[mount+1][weight+1];
        //当物品个数为0是，无论背包容量多大，最大价值都是0，因为没有物品可选
        for (int i = 0; i <=mount ; i++) {
            dp[i][0]=0;
        }
        //当背包容量为0时，无论有多少商品，最大价值都是0
        for (int i = 0; i <=weight ; i++) {
            dp[0][i]=0;
        }

        for (int i = 1; i <=mount ; i++) {
            for (int j = 1; j <= weight; j++) {
                //如果当前的物品容量大于背包的容量的，则能获得最大价值不放入该物品时的最大价值，即比当前物品个数少1时的最大价值
               if(weightArr[i-1]>j){
                   dp[i][j]=dp[i-1][j];
               }else{
                   //如果当前的物品容量小于等于背包的容量的，比较放入该物品和不放入该物品哪个价值大
                   //如果不放入该物品，最大价值就是比当前物品个数少1时的最大价值
                   //如果放入该物品，则要为该物品预留背包容量，然后加上放入该物品的价值即为获得的最大价值
                   dp[i][j]=Math.max(dp[i-1][j-weightArr[i-1]]+valueArr[i-1],dp[i-1][j]);
               }

            }
        }
        //返回二位数组最后一个元素即为最大价值
        int max= dp[mount][weight];
        System.out.println(max);
        int i=mount;
        int j =weight;
        while(i>0 && j>0){
            if(dp[i][j]!=dp[i-1][j]){
                System.out.println("放入了第"+i+"件物品");
                j=j-weightArr[i-1];
            }
            i--;
        }
    }


/**
 *
 * 台阶问题
 * 一次走一步或两步，n个台阶有多少种走法
 * 方法1递归
 */

    public static long step(int n){
        if(n==0)return 0;
        if(n==1)return 1;
        if(n==2) return 2;
        return step(n-1)+step(n-2);
    }

    /**
     *
     * 台阶问题
     * 一次走一步或两步，n个台阶有多少种走法
     * 方法2迭代
     */
    public static long step2(int n){
        long sum=0;
        long one=1;
        long two=2;
        for (int i = 3; i <=n ; i++) {
           sum=one+two;
            one=two;
            two=sum;
        }
        return sum;
    }


    public static void main(String[] args) {

        System.out.println(BeiBao.step2(10));
        int[] w = {1, 4, 3};//物品的重量
        int[] val = {1500, 3000, 2000};//物品的价值
        int m = 4;//背包的容量
        int n = val.length;//物品的个数
        maxValue(n,m,w,val);

        int[] c = {7, 2, 6, 3, 5};
        //物品的价值
        int[] w1 = {21, 18, 9, 15, 6};
        //物品的个数
        int n1 = w.length;
        //背包的容量
        int v = 14;

        maxValue(n1,v,c,w1);
    }
}
