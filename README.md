# Keyword Counter implementation using Max Fibonacci Heap and Hash table

* Name: Priyam Saikia
* UFID: **** ****
* Email: priyam.saikia@ufl.edu

# ** ---------------  FILES in Folder ---------------- **

## Java Files:

keywordcounter.java       -> Primary .java file, acts as the entry-point. 

Most_Frequent_Search.java -> Populates the hash-map, calls Fibonacci Heap functions and returns top n keywords.

Max_Fib_Heap.java         -> Max Fibonacci Heap Functions Implementation.

## Other Files:

makefile    -> Compiles the java files Readme.md -> Instructions to run

Report.pdf  -> Report for the project

sample1.txt -> Sample Test File 1

sample2.txt -> Sample Test File 2

sample_large.txt -> Sample Test File 3

output_file1.txt -> Output to Sample Test File 1

output_file2.txt -> Output to Sample Test File 2

output_large.txt -> Output to Sample Test File 3

*NOTE*: sample1.txt is the one that professor provided us along with the problem
statement. sample2.txt and sample3.txt are two new test cases.

# ** --------------  Instructions to run -------------  **
1. Login to thunder.cise.ufl.edu 
2. Extract files from Zip folder
3. Change directory to corresponding extracted folder 
4. Type ‘make’ without the quotes and enter. The java files should compile. 
5. Type ‘java keywordcounter <test_file_name.txt>’ and enter 
6. Output can be accessed from the newly generated “output_file.txt” file in 
   the same folder as input file.

# ** ----------------  Language used ---------------  **
Java

# ** ------------  Data Structures used ------------  **
1. Max Fibonacci heap: Keeps track of the keywords as Nodes. The maximum node or root contains the keyword with maximum frequency.
2. Hash Map: Keeps track of the keywords and their corresponding nodes in the Max Fibonacci Heap using key-value pair with key being the keyword and value as the pointer to the corresponding node.
