package queue;

/**
 * Queue类,有insert remove peek isFull isEmpty 和size方法
 */
public class Queue2 {
    /**
     * 初始化数组大小
     */
    private int maxSize;
    /**
     * 初始化声明数组
     */
    private long[] queArray;
    /**
     * 队首
     */
    private int front;
    /**
     * 队尾
     */
    private int rear;


    /**
     * 类的构造方法,初始化队列数组及队首队尾和数组容量
     * @param s
     */
    public Queue2(int s){
        maxSize=s;
        queArray=new long[maxSize];
        front=0;
        rear=-1;
    }

    /**
     * 插入数据
     * @param j
     */
    public void insert(long j){
        if (isFull()) throw new RuntimeException("队列满了,请扩容");
        //如果队尾达到最大容量,从队首重新插入
        if(rear==maxSize-1) rear=-1;
        //插入数据并增加队尾索引
        queArray[++rear]=j;
    }

    /**
     * 从队首取出数据
     * @return
     */
    public long remove(){
        if (isEmpty())throw new RuntimeException("队列为空");
        //拿到数据,并且队首自增1,从第一个开始,依次移除,移除后空出位置,暂时不占用
        long temp = queArray[front++];
        //如果队首和数组大小一样,队首为0
        if (front==maxSize)front=0;
        //移除一个,数组元素-1
        return temp;
    }

    /**
     * 获取队首元素
     * @return
     */
    public long peekFront(){
        return queArray[front];
    }

    public  long peek(){
        return queArray[front];
    }
    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty(){
        return rear+1==front|| front+maxSize-1==rear;
    }

    /**
     * 判断队列是否满了
     * @return
     */
    public boolean isFull(){
        return rear+2==front|| front+maxSize*2==rear;
    }

    /**
     * 队列的size大小
     * @return
     */
    public int size(){
        if (rear>=front)
        return rear-front+1;
        return maxSize-front+rear+1;
    }

    public static void main(String[] args) {
        Queue2 queue = new Queue2(5);
        queue.insert(10);
        queue.insert(20);
        queue.insert(30);
        queue.insert(40);

        queue.remove();
        queue.remove();
        queue.remove();

        queue.insert(50);
        queue.insert(60);
        queue.insert(70);
        queue.insert(80);

        while (!queue.isEmpty()){
           long n= queue.remove();
            System.out.print(n);
            System.out.print(" ");
        }
        System.out.println("");
    }
}
