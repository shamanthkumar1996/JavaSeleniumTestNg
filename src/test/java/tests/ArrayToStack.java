package tests;

public class ArrayToStack {
    private int[] arr;
    private int top;
    private int capacity;
    public ArrayToStack(int size){
        arr = new int[size];
        top = -1; 
        capacity = size;
       
    }

    public void push(int x){
        if(isFull()){
            return; 
        }
        arr[++top] = x;
    }
     public int pop(){
        if(isEmpty()){
            return -1; 
        }
        return arr[top--];
    }

    public int peak(){
        if(isEmpty()){
            return -1; 
        }
       return arr[top] ;
    }

    public boolean isFull(){
       if (top == capacity -1){
        return true;
       }
       else {
        return false;
       }
    }

    public boolean isEmpty(){
       if (top ==  -1){
        return true;
       }
       else {
        return false;
       }
    }

    

}

 class Main {
    public static void main(String[] args) {
        ArrayToStack stack1 = new ArrayToStack(5);

        stack1.push(10);
        stack1.push(20);
        stack1.push(30);

        System.out.println(stack1.pop());  // 30
        System.out.println(stack1.peak()); // 20
    }
}