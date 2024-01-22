package algorithm.heap;

//堆排序
public class HeapSort {

    public static void main(String[] args) {

        int[] arr={8,45,1,9,30,15,5,2,65};
        //创建一个新的数组，从下标为1的位置开始存储原始数组，方便数组下标和元素个数能够对应上
        int[] arr2=new int[arr.length+1];
        for (int i=1;i<arr.length+1;i++){
            arr2[i]=arr[i-1];
        }
        int[] arr3=creatHeap(arr2,arr2.length);
        sort(arr3);
    }

    //生成最大堆，只是将最大数放到了堆顶，顺序并未排好
    //参数2为要排序的元素个数，即数组中的前几个元素参与堆排序
    public static int[] creatHeap(int[] arr2,int l){

        //生成最大堆的过程是，从最后一个非叶子结点开始，与其子节点比较
        //长度为n的数组（下标为0的位置不存储元素），最后一个非叶子结点的下标为n/2

        for (int i=l/2;i>0;i--){
            //数组（下标为0的位置不存储元素）中下标为i的元素，
            // 其左子节点的下标为2*i,右子节点下标为2*i+1
            //注意要确保2*i和2*i+1没有数组越界
            //将该结点和其左子节点和右子节点比较
            int largest=i;
            //如果左子节点大
            if( 2*i<l&& arr2[i]<arr2[2*i]){
                largest=2*i;
            }
            //如果右子节点大
            if( 2*i+1<l && arr2[i]<arr2[2*i+1]){
                largest=2*i+1;
            }
            //和大的交换，将大的元素上升
            if(largest!=i){
                int temp=arr2[i];
                arr2[i]=arr2[largest];
                arr2[largest]=temp;
                //有可能左右子结点都比父节点大，交换完以后还需要再比较一遍
                creatHeap(arr2,l);
            }

        }
//        for (int i:arr2){
//            System.out.println(i);
//        }
        return arr2;
    }

    //将最大堆排序
    public static void sort(int[] arr){
        for(int i=arr.length-1;i>0;i--){
            //因为是最大堆，此时数组中下标为1的元素是最大元素，
            //将其与此时堆的最后一个元素互换
            int temp=arr[1];
            arr[1]=arr[i];
            arr[i]=temp;
            //最后一个元素即最大值不再参与后面的堆排序，剩下的元素重新生成最大堆，
            //每次参与堆排序的元素都会减少一个，直到结束
            creatHeap(arr,i);
        }
        for (int i:arr){
            System.out.println(i);
        }
    }
}
