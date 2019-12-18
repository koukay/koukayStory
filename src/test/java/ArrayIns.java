public class ArrayIns {
    private long[] a; //数组
    private int nElems; //数组索引

    /**
     * 构造函数,初始化创建数组
     * 数组索引从0开始
     * @param max
     */
    public ArrayIns(int max){
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
    public void insertSort(){
        int in,out;
        for (out=1;out<nElems;out++){//out未排序部分的最左端数据,从开始向右移动
            long temp=a[out];
            in=out;//从out变量开始,向左移动
            while (in>0 &&a[in-1]>=temp){//temp变量小于in所指的数组数据
                a[in]=a[in-1];  //交换位置,大的放在右边
                --in;
            }
            a[in]=temp;
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
        ArrayIns ArrayIns=new ArrayIns(maxSize);
        //给数组加入数据
        ArrayIns.insert(11);
        ArrayIns.insert(66);
        ArrayIns.insert(22);
        ArrayIns.insert(99);
        ArrayIns.insert(44);
        ArrayIns.insert(77);
        ArrayIns.insert(88);
        ArrayIns.insert(33);
        ArrayIns.insert(55);
        ArrayIns.insert(45);
        ArrayIns.insert(65);
        //显示数组原始数据
        ArrayIns.display();

        //冒泡排序
        ArrayIns.insertSort();

        //显示排序后的结果
        ArrayIns.display();
    }
}
