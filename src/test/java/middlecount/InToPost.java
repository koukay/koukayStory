package middlecount;

public class InToPost {

    private StackX stackX;
    private String input;
    private String output="";

    /**
     * 构造方法,初始化栈数组
     * @param in
     */
    public InToPost(String in){
        input=in;
        int stackSize = input.length();
        stackX=new StackX(stackSize);
    }


    public String doTrans(){
        for (int i = 0; i <input.length() ; i++) {
            char ch = input.charAt(i);
            stackX.dispalyStack("For "+ch+" ");
            switch (ch){
                case '+':
                case '-':
                    getOper(ch,1);
                    break;
                case '*':
                case '/':
                    getOper(ch,2);
                    break;
                case '(':
                    stackX.push(ch);
                    break;
                case ')':
                    getParent(ch);
                    break;
                default:
                    output=output+ch;
                    break;
            }
        }
        while (!stackX.isEmpty()){
            stackX.dispalyStack("While ");
            output=output+stackX.pop();
        }
        stackX.dispalyStack("End ");
        return output;
    }

    public void getOper(char opThis,int Prec1){
        while (!stackX.isEmpty()){
            char opTop = stackX.pop();
            if (opTop == '('){
                stackX.push(opTop);
                break;
            }else {
                int prec2;
                if (opTop=='+'||opTop=='-')
                    prec2=1;
                else prec2=2;
                if (prec2<Prec1){
                    stackX.push(opTop);
                    break;
                }else
                    output=output+opTop;
            }
        }
        stackX.push(opThis);
    }

    public void getParent(char ch){
        while (!stackX.isEmpty()){
            char chx = stackX.pop();
            if (chx =='('){
                break;
            } else{
                output=output+chx;
            }
        }
    }
}
