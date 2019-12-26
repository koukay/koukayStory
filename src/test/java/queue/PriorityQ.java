package queue;

public class PriorityQ {
    private int maxSize;
    private long[] queArray;
    private int nItems;

    /**
     * 类构造方法
     * @param s
     */
    public PriorityQ(int s){
        maxSize=s;
        queArray=new long[maxSize];
        nItems=0;
    }

    /**
     * 插入数据
     * @param item
     */
    public void insert(long item){
        if (isFull())throw new RuntimeException("队列已满");
        int j;
        //如果没有数据,第一位插入一条数据
        if (nItems==0){
            queArray[nItems++]=item;
        }else {
            //如果有数据,从后往前插入数据
            //和前一个数据比较,如果大于前一个,就换位置,如果小于前一个,就不动
            for (j = nItems-1; j >=0 ; j--) {
                //如果新元素大于最后一位的元素
//                相当于插入排序
                if (item>queArray[j]){
                    //交换位置
                    queArray[j+1]=queArray[j];
                }else {
                    break;
                }

            }
//            在最后插入数据
            queArray[j+1]=item;
            nItems++;
        }
    }

    /**
     * 移除数据
     * @return
     */
    public long remove(){
        if (isEmpty())throw new RuntimeException("队列为空");
        return queArray[--nItems];
    }

    /**
     * 取出最后一个元素
     * @return
     */
    public long peekMin(){
        return queArray[nItems-1];
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty(){
        return nItems==0;
    }

    /**
     * 判断是否满了
     * @return
     */
    public boolean isFull(){
        return nItems==maxSize;
    }

    public static void main(String[] args) {
        PriorityQ priorityQ = new PriorityQ(5);

        priorityQ.insert(30);
        priorityQ.insert(50);
        priorityQ.insert(10);
        priorityQ.insert(40);
        priorityQ.insert(20);


        while (!priorityQ.isEmpty()){
            long item = priorityQ.remove();
            System.out.print(item+" ");
        }
        System.out.println("");
    }
}
