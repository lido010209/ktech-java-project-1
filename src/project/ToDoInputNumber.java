package project;

import java.util.ArrayList;
import java.util.List;

public class ToDoInputNumber {
    private int number;

    public ToDoInputNumber(String str, int limit){
        try {
            int n =Integer.parseInt(str);
            if (limit==0) System.out.println("No more TODOs left!");
            else {
                if (n > 0 && n <= limit) {
                    this.number = n;
                } else {
                    System.out.println(String.format("Please input from 1 to %d!!",
                            limit));
                }
            }
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
            System.out.println("Please check input again!!!");
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
