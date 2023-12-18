import java.util.Stack;

public class ReverseSubString {

    public static void main(String[] args) {
        String test1 = "abd(jnb)asdf";
        String test2 = "abdjnbasdf";
        String test3 = "dd(df)a(ghhh)";

        System.out.println("Test1 : " + reverseSubstring(test1));
        System.out.println("Test2 : " + reverseSubstring(test2));
        System.out.println("Test3 : " + reverseSubstring(test3));
    }

    public static String reverseSubstring(String s) {
        Stack<Character> stack=new Stack<>();
        String ans="";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=')'){
                stack.push(s.charAt(i));
            }else{
                while(stack.peek()!='('){
                    ans+=stack.pop();
                }
                ans+=")";
                for(int j=0;j<ans.length();j++){
                    stack.push(ans.charAt(j));
                }
                ans="";
            }
        }
        while(!stack.isEmpty()){
            ans=stack.pop()+ans;
        }
        return ans;
    }
}
