package tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DSAPrep {
    static String str = "automation";
    static String str2 = "I love India";
    // char[] chrs = str.toCharArray();
    public static void main(String [] args){

        // System.out.println(countFreq(str));
        System.out.println(stringReversalWithSpacesPreserved(str2));

        System.out.println(reverseWord(str2));
        System.out.println(countDistinctSubstrings("abcd"));
        System.out.println(firstNonrepeatingChar("abcd"));
        System.out.println(MostRepeatedWords("your love is not a love it it it is a curse for you and your love ones"));

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

      public static int countDistinctSubstrings(String s) {
        Set<String> set = new HashSet<>();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                set.add(s.substring(i, j));
            }
        }
        return set.size();
    }

    public static boolean firstNonrepeatingChar(String str){

        for( char c : str.toCharArray()){
            if(str.indexOf(c) == str.lastIndexOf(c)){
                return true;
            }

        }
    
            return false;
       

    }

    public static String MostRepeatedWords(String str){
        String str2 = "your love is not a love it it it is a curse for you and your love ones";

        String[] strarry = str.split("\\s+");
        HashMap<String,Integer> map = new HashMap<>();
        int max =0;
        String maxStr ="";

        for( String str3: strarry){
            map.put(str3, map.getOrDefault(str3,0)+1);
        }

        for(String m : map.keySet()){
            if (map.get(m)>max){
                max = map.get(m);
                maxStr = m;
            }


        }

        System.out.println(maxStr);
        return maxStr;


    }
}
