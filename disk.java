/**
 * Jorie Noll
 * Disk Scheduling Algorithms
 * File: disk.java
 */
import java.io.File;
import java.util.*;
import java.io.IOException;
import java.util.Scanner;

public class disk {

  //total counters
  static int numRequests = 0;
  static int movement = 0;
  static double seekTime = 0;
  static double rotationalLatency = 0;
  static double transferTime = 0;
  static double accessTime = 0;

  public static void main(String[] args) throws IOException{
      //grabs input parameters from terminal line executed
      int headPosition = Integer.parseInt(args[0]);
      int maxBlocks = Integer.parseInt(args[1]);
      String schedulingAlgorithm = args[2];
      String fileRef = args[3];

      //create an arraylist of scanned numbers to store values from .txt file
      String scanned = "";
      Scanner solutionScanner = new Scanner(new File(fileRef));
      while (solutionScanner.hasNextLine() == true){
          scanned = scanned + solutionScanner.nextLine() + " ";
      }
      scanned = scanned.substring(0, scanned.length()-1);
      String []arr = scanned.split(" ");
      ArrayList<Integer> numbers = new ArrayList<Integer>(arr.length);
      for(int j = 0; j < arr.length; j++){
          numbers.add( Integer.parseInt(arr[j]));
      }

       // calls function based on the algorithm entered
       if(schedulingAlgorithm.equals("FCFS")){
          FCFS(headPosition, maxBlocks, numbers);
       }
       else if(schedulingAlgorithm.equals("SSTF")){
          SSTF(headPosition, maxBlocks, numbers);
       }
       else if(schedulingAlgorithm.equals("SCAN")){
          SCAN(headPosition, maxBlocks, numbers);
       }
       else{
          System.out.println("Invalid Algorithm.");
       }

       System.out.printf("Total number of disk requests: %d\n", numRequests);
       System.out.printf("Total amount of disk head movement: %d\n", movement);
       System.out.printf("Total amount of seek time: %f\n", seekTime);
       System.out.printf("Total amount of rotational latency: %f\n", rotationalLatency);
       System.out.printf("Total amount of transfer time: %f\n", transferTime);
       System.out.printf("Total amount of access time: %f\n", accessTime);
  }

  //runs FCFS scheduling algorithm
  public static void FCFS(int headPosition, int maxBlocks, ArrayList<Integer> numbers){
      int currentHead = headPosition;
      Iterator iterate = numbers.iterator();
      while(iterate.hasNext()){
          int targetHead = (int)iterate.next();

          //check if exceeding maxBlocks
          if(maxBlocks <= targetHead){
              System.out.printf("Block number of service request exceeds maximum block number.\n");
              break;
          }

          // run incremental operations
          numRequests++;
          movement += (int)Math.abs(targetHead-currentHead);
          seekTime += (double)Math.abs(targetHead-currentHead)*.1;
          double rot = (double)(1.0 * Math.random())*8.3;
          rotationalLatency += rot;
          transferTime += .031;
          accessTime += (double)(Math.abs(targetHead-currentHead)*.1) + rot + .031;

          currentHead = targetHead;
      }
  }

  //runs SSTF scheduling algorithm
  public static void SSTF(int headPosition, int maxBlocks, ArrayList<Integer> numbers){
      int currentHead = headPosition;
      while(numbers.size() > 0){
          int minimumDistance = Integer.MAX_VALUE;
          int minimum = 0;

          Iterator iterate = numbers.iterator();

          while(iterate.hasNext()){
              int targetHead = (int)iterate.next();
              if((Math.abs(targetHead - currentHead)) < minimumDistance){
                  minimum = targetHead;
                  minimumDistance = Math.abs(targetHead - currentHead);
              }
          }

          //check if exceeding maxBlocks
          if(maxBlocks <= minimum){
              System.out.printf("Block number of service request exceeds maximum block number.\n");
              break;
          }

          // the closest service request becomes the minimum
          int index = numbers.indexOf(minimum);
          //take minimum number out of int list numbers
          numbers.remove(index);

          // run incremental operations
          numRequests++;
          movement += minimumDistance;
          seekTime += (double)minimumDistance*.1;
          double rot = (double)(1.0 * Math.random())*8.3;
          rotationalLatency += rot;
          transferTime += .031;
          accessTime += (double)(minimumDistance*.1) + rot + .031;

          currentHead = minimum;
      }
  }

  //runs SCAN scheduling algorithm
  public static void SCAN(int headPosition, int maxBlocks, ArrayList<Integer> numbers){
      int currentHead = headPosition;
      int direction = (currentHead < (maxBlocks - currentHead)) ? 0 : 1;

      // front is all service request smaller targetHead, back is all greater than targetHead
      ArrayList<Integer> front = new ArrayList<Integer>();
      ArrayList<Integer> back = new ArrayList<Integer>();

      Iterator iterate = numbers.iterator();
      while(iterate.hasNext()){
          int targetHead = (int)iterate.next();

          if(maxBlocks <= targetHead){
            System.out.printf("Block number of service request exceeds maximum block number.\n");
            break;
          }

          //separate into front and back
          if(targetHead <= currentHead){
              front.add(targetHead);
          }
          if(targetHead > currentHead){
              back.add(targetHead);
          }
      }

      if(direction != 0){
        // service back then front
        while(back.size() >0){
            // remove min from back
            int targetHead = Collections.min(back);
            int index = back.indexOf(targetHead);
            back.remove(index);

            // run incremental operations
            numRequests++;
            movement += (int)Math.abs(targetHead-currentHead);
            seekTime += (double)Math.abs(targetHead-currentHead)*.1;
            double rot = (double)(1.0 * Math.random())*8.3;
            rotationalLatency += rot;
            transferTime += .031;
            accessTime += (double)(Math.abs(targetHead-currentHead)*.1) + rot + .031;

            currentHead = targetHead;
        }
        // head is maxBlocks - 1

        movement += (maxBlocks - currentHead - 1);
        currentHead = maxBlocks - 1;
        while(front.size() > 0){
            // remove max from front
            int targetHead = Collections.max(front);
            int index = front.indexOf(targetHead);
            front.remove(index);

            // run incremental operations
            numRequests++;
            movement += (int)Math.abs(targetHead-currentHead);
            seekTime += (double)Math.abs(targetHead-currentHead)*.1;
            double rot = (double)(1.0 * Math.random())*8.3;
            rotationalLatency += rot;
            transferTime += .031;
            accessTime += (double)(Math.abs(targetHead-currentHead)*.1) + rot + .031;

            currentHead = targetHead;
        }
      }
      else{
        while(front.size() > 0){
            // remove max from front
            int targetHead = Collections.max(front);
            int index = front.indexOf(targetHead);
            front.remove(index);

            // run incremental operations
            numRequests++;
            movement += (int)Math.abs(targetHead-currentHead);
            seekTime += (double)Math.abs(targetHead-currentHead)*.1;
            double rot = (double)(1.0 * Math.random())*8.3;
            rotationalLatency += rot;
            transferTime += .031;
            accessTime += (double)(Math.abs(targetHead-currentHead)*.1) + rot + .031;

            currentHead = targetHead;
        }

        //head is 0
        movement += (currentHead - 0);
        currentHead = 0;

        while(back.size() > 0){
            // remove min from back
            int targetHead = Collections.min(back);
            int index = back.indexOf(targetHead);
            back.remove(index);

            // run incremental operations
            numRequests++;
            movement += (int)Math.abs(targetHead-currentHead);
            seekTime += (double)Math.abs(targetHead-currentHead)*.1;
            double rot = (double)(1.0 * Math.random())*8.3;
            rotationalLatency += rot;
            transferTime += .031;
            accessTime += (double)(Math.abs(targetHead-currentHead)*.1) + rot + .031;

            currentHead = targetHead;
      }
  }
}
}
