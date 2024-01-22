package algorithm;
import java.util.Arrays;

//leetcode -455 贪心算法
public class Leetcode455 {

    public static void main(String[] args) {
        int[] g={1,2};
        int[] s={1,2,3};

        Solution(g,s);
    }

    public static void Solution(int[] g, int[] s) {

        Arrays.sort(g);
        Arrays.sort(s);

        //计数，需要返回的结果
        int accout = 0;
        //上一轮内层循环结束后第第二个数组的下标到几了，下次循环不从0开始，从上一次循环结束的下一个位置开始
        int num=0;
        for (int i = 0; i < g.length; i++) {
            int j=num;
            while (j < s.length) {
                if(s[j]>=g[i]){
                    accout++;
                    break;
                }else {
                    j++;
                }
            }
            if(j==s.length-1){
                break;
            }else{
                num=j+1;
            }
        }

        System.out.println(accout);
    }

}
