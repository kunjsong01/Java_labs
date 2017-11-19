/*
 * A merger class that merges all sorted segments into one array, i.e.
 * merge array list of size N into another array of size M+N recursively
 */
package lab1;

import java.util.ArrayList;

/**
 *
 * @author KSong
 */
public class merger {
 
    /* Merges two arraylist of different sizes */
    public static ArrayList<Integer> merge(ArrayList<Integer> baseArr, ArrayList<Integer> arrToMerge, int m, int n) 
    {
        ArrayList<Integer> tmpArr = new ArrayList<>();
        
        int i = 0, j = 0, k = 0;
     
        // Traverse both array
        while (i<m && j <n)
        {
            // compare the values in base array and target array and 
            // insert them into the new temporary array by order
            if (baseArr.get(i) < arrToMerge.get(j))
                tmpArr.add(baseArr.get(i++));
            else
                tmpArr.add(arrToMerge.get(j++));
        }
     
        // Store remaining elements of first array
        while (i < m)
            tmpArr.add(baseArr.get(i++));
     
        // Store remaining elements of second array
        while (j < n)
            tmpArr.add(arrToMerge.get(j++));
        
        return tmpArr; 
    }
}
