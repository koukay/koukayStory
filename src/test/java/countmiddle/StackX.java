package countmiddle;

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
    private int[] stackArray;
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
        stackArray=new int[maxSize];
        top=-1;
    }

    /**
     * 入栈
     * @param j 入栈的参数
     */
    public void push(int j){
        stackArray[++top]=j;
    }

    /**
     * 出栈
     * @return
     */
    public int  pop(){
        return stackArray[top--];
    }

    /**
     * 获取栈顶参数
     * @return
     */
    public int peek(){
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
     * 数组大小
     * @return
     */
    public int size(){
        return top+1;
    }

    public int peekN(int n){
        return stackArray[n];
    }
    /**
     * 判断数组是否满了
     * @return
     */
    public boolean idFull(){
        return (top==maxSize-1);
    }

    public void dispalyStack(String s){
        System.out.print(s);
        System.out.print("Stack (bottom --> top): ");
        for (int i = 0; i <size() ; i++) {
            System.out.print(peekN(i));
            System.out.print(' ');
        }
        System.out.println("");
    }
}
