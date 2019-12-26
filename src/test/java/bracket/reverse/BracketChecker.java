package bracket.reverse;

public class BracketChecker {
    /**
     * 输入字符串
     */
    private String input;


    /**
     * 构造方法,初始化输入字符串
     * @param in
     */
    public BracketChecker(String in){
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
    public boolean check(){
        boolean b=true;
        int length = input.length();
        StackBracket stackReverseChar= new StackBracket(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            switch (c){
                case '{':
                case '[':
                case '(':
                    stackReverseChar.push(c);
                    break;
                case '}':
                case ']':
                case ')':
                    if (!stackReverseChar.isEmpty()){
                        char pop = stackReverseChar.pop();
                        if (c=='}' && pop !='{'||
                            c==']' && pop !='['||
                            c==')' && pop !='('){
                            System.out.println("Error: "+c+" at "+ i);
                            b= false;
                        }
                    }else{
                        System.out.println("Error: "+c+" at "+ i);
                        b= false;
                    }
                    break;
                default:
                    break;
            }
        }
        if (!stackReverseChar.isEmpty()){
            b=false;
            System.out.println("Error: missing right delimiter");
        }
        return b;
    }
}
