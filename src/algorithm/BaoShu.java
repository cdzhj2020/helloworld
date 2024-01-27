package algorithm;

import java.util.Arrays;
//约瑟夫环
public class BaoShu {


    public static void main(String[] args) {
        leave(2,50);
    }


    public static String leave(int m,int n){
        //初始化一个全为0的数组
        int[] arr=new int[n+1];
        //记录每个人报的数字1~m
        int x=1;
        //记录当前还剩几个人，如果小于m个人则结束
        int y=n;
        for (int i = 1;  ; i++) {
            //如果循环结束则从头开始
            if(i>=arr.length) {i=1;}
            //如果报到数字m
            if(x==m){
                //先判断是否还在游戏中，
                if(arr[i]==0){
                    //如果在游戏中，则置为1，淘汰出局
                    arr[i]=1;
                    //重新从1开始报数
                    x=1;
                    //删除一个人
                    y--;
                    //剩下的人小于m则结束循环
                    if(y<m)break;
                }else{
                    //如果该位置上已经为1说明已经出局，则一直向后找到不为1的
                    continue;
                }
            }else{
                //如果没有报到m则一直报数
                if(arr[i]==0){
                    x++;
                }
            }
        }

        for (int i = 1; i < arr.length; i++) {
            if(arr[i]==0) System.out.println(i);
        }

        return Arrays.toString(arr);
    }



}
