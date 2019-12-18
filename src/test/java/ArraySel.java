public class ArraySel {
    private long[] a; //数组
    private int nElems; //数组索引

    /**
     * 构造函数,初始化创建数组
     * 数组索引从0开始
     * @param max
     */
    public ArraySel(int max){
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
    public void selectionSort(){
        int in,out,min;
        for (out=0;out<nElems-1;out++){//外循环控制循环趟数
            min=out;                    //最小的
            for (in=out+1;in<nElems;in++){//内循环控制元素交换,选出本趟循环最小的
                if (a[in]<a[min]){
                    min=in;         //选出新的最小的
                }
            }
            swap(out,min);//选出最小的,然后再和之前最小的交换
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
        ArraySel ArraySel=new ArraySel(maxSize);
        //给数组加入数据
        ArraySel.insert(11);
        ArraySel.insert(66);
        ArraySel.insert(22);
        ArraySel.insert(99);
        ArraySel.insert(44);
        ArraySel.insert(77);
        ArraySel.insert(88);
        ArraySel.insert(33);
        ArraySel.insert(55);
        ArraySel.insert(45);
        ArraySel.insert(65);
        //显示数组原始数据
        ArraySel.display();

        //冒泡排序
        ArraySel.selectionSort();

        //显示排序后的结果
        ArraySel.display();
    }
}
