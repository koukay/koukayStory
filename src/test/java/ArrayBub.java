public class ArrayBub {
    private long[] a; //数组
    private int nElems; //数组索引

    /**
     * 构造函数,初始化创建数组
     * 数组索引从0开始
     * @param max
     */
    public ArrayBub(int max){
        a=new long[max];
        nElems=0;
    }

    /**
     * 数组中插入数据
     * @param value
     */
    public void insert(long value){
        a[nElems]=value;
        nElems++;
    }

    /**
     * 遍历原始数组
     */
    public void display(){
        for (int i = 0; i < nElems; i++)
            System.out.print(a[i]+" ");
        System.out.println("");
    }

    /**
     * 冒泡排序
     */
    public void bubbleSort(){
        int in,out;
        for (out=nElems-1;out>1;out--){//外循环控制循环趟数
            for (in=0;in<out;in++){//内循环控制元素交换,选出本趟循环最小的
                if (a[in]>a[in+1])swap(in,in+1);//小的放在前面
            }
        }
    }

    /**
     * 元素互换
     * @param in
     * @param i
     */
    private void swap(int in, int i) {
        long temp=a[in];
        a[in]=a[i];
        a[i]=temp;
    }

    //主函数,测试结果
    public static void main(String[] args) {

        int maxSize=100;
        //初始化数组
        ArrayBub arrayBub=new ArrayBub(maxSize);
        //给数组加入数据
        arrayBub.insert(11);
        arrayBub.insert(66);
        arrayBub.insert(22);
        arrayBub.insert(99);
        arrayBub.insert(44);
        arrayBub.insert(77);
        arrayBub.insert(88);
        arrayBub.insert(33);
        arrayBub.insert(55);
        arrayBub.insert(45);
        arrayBub.insert(65);
        //显示数组原始数据
        arrayBub.display();

        //冒泡排序
        arrayBub.bubbleSort();

        //显示排序后的结果
        arrayBub.display();
    }
}
