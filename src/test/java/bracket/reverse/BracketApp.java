package bracket.reverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 测试类
 */
public class BracketApp {
    public static void main(String[] args) throws IOException {
        String input;
        while (true){
            System.out.println(" Please Enter String containing deliumiters: ");
            System.out.flush();
            input=getString();
            if (input.equals("")) break;

            BracketChecker reverse = new BracketChecker(input);
            boolean check = reverse.check();
            if (check){
                System.out.println("Enter String  is matched ");
            }else {
                System.out.println("Enter String  is not matched ");
            }
        }
    }

    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
}
