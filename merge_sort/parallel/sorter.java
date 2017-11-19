/*
 * A sorter class that applies merge sort algorithm to
 * sort a array.
 */
package lab1;

import java.util.ArrayList;
import java.lang.Thread;

/**
 *
 * @author KSong
 */
public class sorter implements Runnable{
    // a private variable to hold
    private ArrayList<Integer> arr = new ArrayList<>();
    private int l;
    private int r;
    
    sorter(ArrayList<Integer> arrSegment, int l, int r) {
        this.arr = arrSegment;
        this.l = l;
        this.r = r;
        //this.sort(l, r);
    }
    
    public void run() {
        this.sort(this.l, this.r);
    }
    
    public ArrayList<Integer> get_sorted() {
        return this.arr;
    }
    
    public void sort(int l, int r) {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;
 
            // Sort first and second halves
            this.sort(l, m);
            this.sort(m+1, r);
 
            // Merge the sorted halves
            this.merge(l, m, r);
        }
    }  

    // merge function
    public void merge(int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> R = new ArrayList<>();
 
        /*Copy the array segments in to Left and Right temp arrays*/
        for (int i=0; i<n1; ++i)
            L.add(this.arr.get(l+i));
        for (int j=0; j<n2; ++j)
            R.add(this.arr.get(m + 1+ j));
 
        // merge the L and R arrays
 
        // Initial indices for L and R arrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L.get(i) <= R.get(j))
            {
                this.arr.set(k, L.get(i));
                i++;
            }
            else
            {
                this.arr.set(k, R.get(j));
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[]*/
        while (i < n1)
        {
            this.arr.set(k, L.get(i));
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[]*/
        while (j < n2)
        {
            this.arr.set(k, R.get(j));
            j++;
            k++;
        }
    }  
}
