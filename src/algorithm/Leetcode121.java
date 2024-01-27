package algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//leetcode -121 贪心算法
public class Leetcode121 {

    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        int i=maxProfit(prices);
        System.out.println(i);
    }

    public static int maxProfit(int[] prices) {

        if (prices.length <= 1) return 0;
        int res = 0, min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 记录当前最大收益
            res = Math.max(res, prices[i] - min);
            // 更新最小值，即买入点
            min = Math.min(min, prices[i]);
        }
        return res;

    }
}
