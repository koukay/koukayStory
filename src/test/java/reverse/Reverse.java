package reverse;

public class Reverse {
    /**
     * 输入字符串
     */
    private String input;
    /**
     * 输出字符串
     */
    private String output;

    /**
     * 构造方法,初始化输入字符串
     * @param in
     */
    public Reverse(String in){
        input=in;
    }

    /**
     * 字符串做反转操作
     * 1.得到最大的字符串长度
     * 2.创建栈数组
     * 3.for循环将字符串放入栈数组
     * 4.栈数组不为空,遍历拼接
     * @return
     */
    public String doRev(){
        int length = input.length();
        StackReverseChar stackReverseChar= new StackReverseChar(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            stackReverseChar.push(c);
        }
        output="";
        while (!stackReverseChar.isEmpty()){
            char pop = stackReverseChar.pop();
            output+=pop;
        }
        return output;
    }
}
