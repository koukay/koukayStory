package reverse;

public class StackReverseChar {
    /**
     * 栈数组容量
     */
    private int maxSize;
    /**
     * 定义数组
     */
    private char[] stackArray;
    /**
     * 栈顶
     */
    private int top;

    /**
     * 构造方法,创建栈数组
     * @param s 数组容量
     */
    public StackReverseChar(int s){
        maxSize=s;
        stackArray=new char[maxSize];
        top=-1;
    }

    /**
     * 入栈
     * @param j 入栈的参数
     */
    public void push(char j){
        stackArray[++top]=j;
    }

    /**
     * 出栈
     * @return
     */
    public char  pop(){
        return stackArray[top--];
    }

    /**
     * 获取栈顶参数
     * @return
     */
    public char peek(){
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
}
