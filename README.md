# disk-scheduling-algorithms

This program computes the FCFS, SSTF, and SCAN disk-scheduling algorithms and simulates a
simple disk drive, which has a specified number of logical blocks numbered from 0.
The program receives a sequence of disk requests, runs the specified scheduling algorithm
to determine the order of servicing the requests, and calculates the time of servicing
each request, and reports the total time.


To Run This Program:
javac disk.java
java disk (headPosition) (maxBlocks) (algorithm) (.txt)

For Example,
javac disk.java
java disk 53 200 FCFS requests.txt


Sample Output:
~/prog3$ java disk 53 200 FCFS requests.txt

Total number of disk requests: 8
Total amount of disk head movement: 640
Total amount of seek time: 64.000000
Total amount of rotational latency: 27.669027
Total amount of transfer time: 0.248000
Total amount of access time: 91.917027
