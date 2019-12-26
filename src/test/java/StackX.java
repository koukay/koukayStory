/**
 * 堆栈测试类
 */
public class StackX {
    /**
     * 栈数组容量
     */
    private int maxSize;
    /**
     * 定义数组
     */
    private long[] stackArray;
    /**
     * 栈顶
     */
    private int top;

    /**
     * 构造方法,创建栈数组
     * @param s 数组容量
     */
    public StackX(int s){
        maxSize=s;
        stackArray=new long[maxSize];
        top=-1;
    }

    /**
     * 入栈
     * @param j 入栈的参数
     */
    public void push(long j){
        stackArray[++top]=j;
    }

    /**
     * 出栈
     * @return
     */
    public long  pop(){
        return stackArray[top--];
    }

    /**
     * 获取栈顶参数
     * @return
     */
    public long peek(){
        return stackArray[top];
    }

    /**
     * 判断是否为空数组
     * @return
     */
    public boolean isEmpty(){
        return (top==-1);
    }

    /**
     * 判断数组是否满了
     * @return
     */
    public boolean idFull(){
        return (top==maxSize-1);
    }

    /**
     * 测试主函数
     * @param args
     */
    public static void main(String[] args) {
        //创建栈对象,初始化栈数组
        StackX theStack=new StackX(10);

        //入栈
        theStack.push(10);
        theStack.push(20);
        theStack.push(30);
        theStack.push(40);
        theStack.push(50);
        //栈数组不为空,遍历
        while (!theStack.isEmpty()){
            long pop = theStack.pop();
            System.out.print(pop);
            System.out.print(" ");
        }
        System.out.println("");
    }
}
