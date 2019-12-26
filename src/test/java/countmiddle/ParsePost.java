package countmiddle;

public class ParsePost {

    private StackX stackX;
    private String input;

    /**
     * 构造方法,初始化栈数组
     * @param in
     */
    public ParsePost(String in){
        input=in;
    }


    public int doParse(){
            stackX=new StackX(20);
            char ch ;
            int j;
            int num1,num2,interAns;
        for (int i = 0; i <input.length() ; i++) {
            for (j = 0; j < input.length(); j++) {
                ch=input.charAt(j);
                stackX.dispalyStack(""+ch+" ");
                if (ch >='0' && ch <='9')stackX.push(ch-'0');
                else {
                    num2=stackX.pop();
                    num1=stackX.pop();
                    switch (ch){
                        case '+':
                            interAns=num1+num2;
                            break;
                        case '-':
                            interAns=num1-num2;
                            break;
                        case '*':
                            interAns=num1*num2;
                            break;
                        case '/':
                            interAns=num1/num2;
                            break;
                        default:
                            interAns=0;
                    }
                    stackX.push(interAns);
                }
            }
        }
            interAns=stackX.pop();
            return interAns;
    }
}
