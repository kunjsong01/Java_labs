package test;
import java.util.Scanner;
import java.util.InputMismatchException;

class hello_world {

    public static void main(String[] args) {
    		
    		int nThread = 0;
    		System.out.println("Please enter the number of threads: ");
    		Scanner in = new Scanner(System.in);
    		try {
    			nThread = in.nextInt();
    			in.close();
    		}
    		catch (InputMismatchException e) {
    			throw new InputMismatchException("Please enter an integer!");
    		}

        // making some print objects to print the lines in threads   		
    		for(int i=nThread; i>0; i--){
    			print p = new print(i);
    			p.start();
        }    		
    }

}

class print extends Thread {
    int myID;
    //long startTimeCreated;
    long startTimeRun;
    print(int myID) {
    		this.myID = myID;
    		// here we do not time the creation of a Thread object. 
    		//startTimeCreated = System.nanoTime();
    }

    public void run() {
    		startTimeRun = System.nanoTime();
        if (myID % 2 == 0) {
        		System.out.println("Hello, World, I'm thread " + myID);
        }
        else
        {
        		System.out.println("Hello World, I'm thread " + myID +
        				" - Thread " + myID + " lasted for " + 
        				(double)((System.nanoTime() - startTimeRun)/100000000.0) + " seconds.");
        }
    }
}




 
