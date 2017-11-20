/*
 * Parallel merge sort algorithm implemented in Java
 */
package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Thread;
import java.lang.InterruptedException;

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
    public List<ArrayList<Integer>> chunks;
    
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
        
        System.out.println("Array slices based on the number of threads: ");
        this.chunks = this.array_slice(this.myArr, this.nThread);
        System.out.println(this.chunks);
        System.out.println("@@DEBUG - This is the 2nd array list: " + this.chunks.get(0));
    }
    
    public void start() {
        // instentiate an object of the sorter class and apply the sort
        int length; // = myArr.size();
        int rIndex; // = length - 1;
        //sorter st = new sorter(this.myArr, 0, rIndex);
        
        // set up a list to hold the sorted segments
        List<ArrayList<Integer>> sortedSegList = new ArrayList<>();
        
        for (int i=1; i<=this.nThread; i++) {
            length = this.chunks.get(i-1).size();
            rIndex = length - 1;
            sorter st = new sorter(this.chunks.get(i-1), 0, rIndex);
        
            Thread t = new Thread(st);
            t.start();
            try{
                t.join(5000);
                sortedSegList.add(st.get_sorted());
            }
            catch (InterruptedException e){
                e.getMessage();
                System.exit(-1);
            }            
        }
        
        System.out.println("@@DEBUG - This is sorted segments list: " + sortedSegList);
                
        System.out.println("\n === After sorting ===\n");
        
        /*
        // test of merger
        ArrayList<Integer> tmpArrHolder = new ArrayList<>();
        int m = sortedSegList.get(0).size(); // size of base array
        int n = sortedSegList.get(1).size(); // size of array to be merged
        tmpArrHolder = merger.merge(sortedSegList.get(0), sortedSegList.get(1), m, n);
        System.out.println("@@DEBUG - This is tmpArrHolder " + tmpArrHolder);
        System.out.println("@@DEBUG - This is sortedSegList partially merged: " + sortedSegList);
        */
        
        // merging!
        while(sortedSegList.size() > 1) {
            int m = sortedSegList.get(0).size(); // size of base array
            int n = sortedSegList.get(1).size(); // size of array to be merged
            ArrayList<Integer> tmpArrHolder = new ArrayList<>();
            tmpArrHolder = merger.merge(sortedSegList.get(0), sortedSegList.get(1), m, n);
            sortedSegList.set(0, tmpArrHolder); 
            sortedSegList.remove(1);
        }
        
        this.myArr = sortedSegList.get(0);
        
        if (this.seq_checker() == false) {
            System.out.println("Array is NOT sorted!");
        }
        else {
            System.out.println("Array is sorted!");
        }
        // print the sorted array
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
    
    public List<ArrayList<Integer>> array_slice(ArrayList<Integer> myArr, int nThread) {
        List<ArrayList<Integer>> chunks = new ArrayList<ArrayList<Integer>>();
        
        int totalSize = myArr.size();
        int chunkSize = totalSize/nThread;
        int remaining = totalSize - (nThread * chunkSize);
        
        //System.out.println("@@DBEUG - chunkSize: " + chunkSize);
        //System.out.println("@@DBEUG - remaining: " + remaining);
        
        // split the task
        for (int i = 0; i < nThread; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            for (int j = i*chunkSize; j < (i*chunkSize+chunkSize); j++) {
                tmp.add(myArr.get(j));
            }
            chunks.add(tmp);
        }
        
        // add remaining
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        for (int i = 0; i < remaining; i++) {
            chunks.get(nThread - 1).add(myArr.get(totalSize - i - 1));
        }
        
        return chunks;
    } 
}




 

