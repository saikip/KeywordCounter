import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * Author- Priyam Saikia (UFID 9414-5292) 
 * 
Version History-
11/09/2018 - Priyam Saikia - Initial Version

Functionality-
This class is an implementation of determining the most used keywords in an
internet search by DuckDuckGo. The input is in form of a text file with keywords
and corresponding frequency they have been searched for.

Functions:
1. void main(String in_file)
2. String find_first_n_keywords(Max_Fib_Heap max_fh, int n)
3. static boolean isNumeric(String str)
*/
public class Most_Frequent_Search {
	// **************************************************************************
	// Function: main
	// Return: void
	// Input: String in_file
	// **************************************************************************
	// Function to call various insert, increase_key and remove_max functions
	// of the fibonacci heap data structure. Also calls the sub function to get
	// top n most used keywords.
	// **************************************************************************
	public void main(String in_file) throws IOException {
		// keep track of time
		
		try {
		//Save/Create output file in same path as input file
		String in_path = in_file.substring(0, in_file.lastIndexOf("\\")+1);
		if(in_path.isEmpty()) {
			in_path = in_file.substring(0, in_file.lastIndexOf("/")+1);
		}

		// Hard code output file
	    File file = new File(in_path + "output_file.txt");
	    // Create writer pointer to write results to
	    BufferedWriter writer = new BufferedWriter( new FileWriter(file));

		Max_Fib_Heap max_fh= new Max_Fib_Heap();
		// Hash-map to store keyword as key and Pointer to Fib Heap Node as value
		HashMap<String,Max_Fib_Heap.Node> hm = new HashMap<String, Max_Fib_Heap.Node>();
			
		// Read data from input file
		BufferedReader reader = new BufferedReader(new FileReader(in_file)); 
		
		String line; 
        // Read each line one by one
	    while ((line = reader.readLine()) != null) { 
	    	 //System.out.println(line);
			 // parse line
			 if (line.indexOf('$') == 0) {
				 // Case 1: input keyword
				 String delimiter = "[ ]+";
				 String[] tokens = line.split(delimiter);
				 if(tokens.length == 2) {
					 // remove $ sign
					 String keyword = tokens[0].substring(1);
					 
					 if(isNumeric(tokens[1])) {
						 int freq = Integer.parseInt(tokens[1]);
						 // insert new keyword
						 if ( !hm.containsKey(keyword)){
			                //Create new node and insert in fibonacci heap and hash map
							 Max_Fib_Heap.Node mynode = max_fh.new Node(keyword,freq);
							 // Insert in Fib Heap
							 max_fh.insert(mynode);
							 // Also insert in hashmap
			                 hm.put(keyword,mynode);
		                     //System.out.println("Node Inserted: " + mynode.keyword + " : " + mynode.freq);
			             } else {
		
		                    //if already in hash-map then call increase key in fibonacci heap
		                    int new_val = hm.get(keyword).freq + freq;
		                    max_fh.increase_key(hm.get(keyword),new_val);
		                    //System.out.println("Node increased: " + keyword + " : " + freq);
		                }
					} else {
						//Error if required - Ignore invalid line with second token non-numeric
						//System.out.println("Invalid line in input file : " + line);
					}
				} else {
					//Error if required - Ignore invalid line with tokens more or less than 2 
					//System.out.println("Invalid line in input file : " + line);
				}
				 
			 } else if(isNumeric(line)) {
				 // Case 2: Output the n keywords
				 int n = Integer.parseInt(line);
				 String result = find_first_n_keywords(max_fh,n);
				 if(!result.isEmpty())
					 // Write result to output
					 //System.out.println(result);
					 writer.write(result);
					 writer.newLine();
			 } else if(line.trim().equals("stop")) {
				 // Case 3: Stop processing
				 break;
			 } else {
				 //Error if required - Ignore invalid line as of now
				 //System.out.println("Invalid line in input file : " + line);
			 }
		 } 
		
	    // Close both files
	    reader.close();
		writer.close();
		} catch(IOException e) {
			//e.printStackTrace();
			System.out.println(e);
		}
	}	

	// **************************************************************************
	// Function: find_first_n_keywords
	// Return: String
	// Input: Max_Fib_Heap max_fh, int n
	// **************************************************************************
	// Function to find the top n frequent words by calling Fib Heap
	// **************************************************************************
	public String find_first_n_keywords(Max_Fib_Heap max_fh, int n){
		// error if n >20?
	    String keyword_list = new String();
	    //String f_list = new String();
	    ArrayList<Max_Fib_Heap.Node> max_nodes = new ArrayList<Max_Fib_Heap.Node>(n);
	    // RemoveMax as many times as n is requested
	    for(int i = 0; i<n; i++) {
	    	Max_Fib_Heap.Node temp = max_fh.remove_max();
	    	if(temp != null) {
		    	if(i==0) {
		    		keyword_list = temp.keyword;
		    		//f_list = Integer.toString(temp.freq);
		    	} else {
		    		keyword_list = keyword_list + ',' + temp.keyword;
		    		//f_list = f_list + ',' + Integer.toString(temp.freq);
		    	}
	    	}
	    	// Keep track of removed nodes
	    	max_nodes.add(temp);
	    }
	    //insert removed nodes
	    for ( Max_Fib_Heap.Node curr : max_nodes)
        {   
	    	if(curr != null) {
	    		// Set degree, child, parent to null 
		    	curr.degree = 0;
		    	curr.child = null;
		    	curr.parent = null;
		    	curr.right = curr;
		    	curr.left = curr;
		    	max_fh.insert(curr);
	    	}
        }
	    //System.out.println(keyword_list);
	    //System.out.println(f_list);
		return keyword_list;
	}

	// **************************************************************************
	// Function: isNumeric
	// Return: boolean
	// Input: String str
	// **************************************************************************
	// Function to validate of a given string is numeric - consists of only numbers
	// **************************************************************************
	public static boolean isNumeric(String str){  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
