/*
 * Parallel merge sort algorithm implemented in Java
 */
package lab1;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Random;

class run {

    public static void main(String[] args) {
        System.out.println("LAB1 - array merge sort ...");
        int threads, size;
        long seed;
        long startTime;
        
        threads = Integer.parseInt(args[0]);
        seed = Long.parseLong(args[1]);
        size = Integer.parseInt(args[2]);
        
        System.out.println("Number of threads: " + threads + "\n" +
                        "seed: " + seed + "\n" +
                        "size of array: " + size);
        
        startTime = System.nanoTime();
        tester t = new tester(threads, seed, size);
        t.start();
        System.out.println("Total execution time: " + 
                        (double)((System.nanoTime() - startTime)/100000000.0) + " seconds.");
    }  
}

/*
* A Tester class to test this merge sort program
*/
class tester {
    public int nThread;
    public long randSeed;
    public int arrSize;
    
    public ArrayList<Integer> myArr = new ArrayList<>();
    tester(int threads, long seed, int size){
        this.nThread = threads;
        this.randSeed = seed;
        this.arrSize = size;
        // initialise the array list
        this.arr_init();
    }
    
    public void arr_init() {
        
        Random rand = new Random(randSeed);
        
        // initialise the array, print random numbers in the range of 1 - 1000
        for (int i=0; i<arrSize; i++) {
            this.myArr.add(rand.nextInt(1000 - 1) + 1);
        }
        
        System.out.println("\n === Before sorting === \n");
        // print out original array
        for(int i = 0; i < arrSize; i++) {  
            if (i == (arrSize - 1)) {
                System.out.print(this.myArr.get(i) + " ");
            }
            else {
                System.out.print(this.myArr.get(i) + ", ");
            }
        }
        System.out.println();
    }
    
    public void start() {
        // instentiate an object of the sorter class and apply the sort
        int length = myArr.size();
        int rIndex = length - 1;
        sorter st = new sorter(this.myArr, 0, rIndex);
        
        System.out.println("\n === After sorting ===\n");
        
        if (this.seq_checker() == false) {
            System.out.println("Array is NOT sorted!");
        }
        else {
            // print the sorted array
            System.out.println("Array is sorted!");
            for(int i = 0; i < this.myArr.size(); i++) {  
                if (i == (this.myArr.size() - 1)) {
                    System.out.print(this.myArr.get(i) + " ");
                }
                else {
                    System.out.print(this.myArr.get(i) + ", ");
                }
            }
            System.out.println();        
        }
        
        
    }    
    
    public boolean seq_checker() {
        for (int i=0; i<this.myArr.size(); i++) {
            if (i == (this.myArr.size() - 1)) {
                continue;
            }
            else {
                if (this.myArr.get(i) > this.myArr.get(i + 1)) {
                    System.out.println("Found " + i + "-th element is greater than " +
                            (i+1) + "-th element! ");
                    return false;
                }
            }
        }
        return true;
    }
}




 

