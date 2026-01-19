package tests;

import java.util.HashMap;
import java.util.Stack;

public class DSAPrep {
    static String str = "automation";
    static String str2 = "I love India";
    // char[] chrs = str.toCharArray();
    public static void main(String [] args){

        // System.out.println(countFreq(str));
        System.out.println(stringReversalWithSpacesPreserved(str2));

        System.out.println(reverseWord(str2));

    }

    public static HashMap<Character,Integer> countFreq(String str){
        char[] chrs = str.toCharArray();
        HashMap<Character,Integer> map = new HashMap<>();
        for(char ch : chrs){
            map.put(ch, map.getOrDefault(ch,0)+1);
        }

        return map;
    }

    public static String stringReversalWithSpacesPreserved(String str){
        char[] chrs = str.toCharArray();
        int left =0;
        int right = str.length() -1;

        while(left<right){
            if(chrs[left]==' '){

                left ++;
               
            }
            else if (chrs[right] == ' ') {
            right--;
        } 
            else{
                char temp = chrs[left];
                chrs[left] =chrs[right];
                chrs[right] = temp;
                 left ++;
                right --;
            }
        }
        System.out.println();
        return new String(chrs);

    }

    public static String leaveSymbols(String str){
        char[] chrs = str.toCharArray();
        int left =0;
        int right = str.length() -1;

        while(left<right){
            if(!Character.isAlphabetic(chrs[left])){

                left ++;
               
            }
            else if (!Character.isAlphabetic(chrs[right])) {
            right--;
        } 
            else{
                char temp = chrs[left];
                chrs[left] =chrs[right];
                chrs[right] = temp;
                 left ++;
                right --;
            }
        }
        System.out.println();
        return new String(chrs);

    }

    // public static boolean stack(String s){

    //     Stack<Character> stack = new Stack();
        
    //     for(char c : s.toCharArray()){
    //         if(c =='('|c =='{'|c =='['){
    //             stack.push(c);
    //         }
    //         if(stack.isEmpty()) return false;

    //         char top = stack.pop();
    //          if(c ==')' && top =='('|c =='}'|c ==']'){
    //             stack.push(c);
    //         }
    //     }

    // }

    public static String reverseWord(String str){
        StringBuilder builder = new StringBuilder();
        String[] strArray = str.trim().split("\\s+");

        for(int i = strArray.length-1;i>=0;i--){
            builder.append(strArray[i]+" ");
        }

        return builder.toString().trim();

    }

}
