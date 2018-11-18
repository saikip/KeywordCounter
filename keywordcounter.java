import java.io.IOException;
/*
 * Author- Priyam Saikia (UFID 9414-5292) 
 * 
Version History-
11/09/2018 - Priyam Saikia - Initial Version

Functionality-
This function is the entry point that calls function to determine the most used
keywords in an internet search by DuckDuckGo. The input is in form of a text file
with keywords and corresponding frequency they have been searched for.

Functions:
1. void main(String[] args)
*/
public class keywordcounter {
	// **************************************************************************
	// Function: main
	// Return: void
	// Input: String[] args
	// **************************************************************************
	public static void main(String[] args){
		
		Most_Frequent_Search mfs = new Most_Frequent_Search();
		try {
			mfs.main(args[0]);
		} catch (IOException e) {
			System.out.println(e);;
		}
		
	}
}
