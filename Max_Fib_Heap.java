import java.util.ArrayList;
import java.util.List;
/*
 * Author- Priyam Saikia (UFID 9414-5292) 
 * 
 Version History-
 11/09/2018 - Priyam Saikia - Initial Version
 11/15/2018 - Priyam Saikia - Removed return_max function
 
 Functionality-
 This class is an implementation of the Max Fibonacci Heap Data Structure
 
 Functions:
	1. void insert(Node node)
	2. void increase_key(Node node, int k)
	3. void update_max()
	4. void cut(Node node)
	5. void cascade_cut(Node node)
	6. Node remove_max()
	7. void meld()
	8. Node merge_heaps(Node node1, Node node2)
	9. void move_children(Node node)
	10. void delete(Node node)
 */
public class Max_Fib_Heap {
	// **************************************************************************	
	// Pointer to store the top of the heap - Max Value Node of the Fibonacci PQ
	// **************************************************************************	
	private Node maxNode;

	// **************************************************************************
	// Class: Node
	// **************************************************************************
	// Node Class to define the node structure for a Fib Heap Data Structure
	// **************************************************************************
	public class Node {
		// Degree - number of Children
		int degree = 0;
		// Keyword
		String keyword;
		// Frequency of the given keyword
		int freq;
		// Flag to keep track of children lost
		boolean childCut = false;
		// Sibling, Parent and Child Pointers
		Node left, right, child, parent;
		
		// Initialization
		Node(String keyword, int freq){
			this.right = this;
			this.left = this;
			this.parent = null;
			this.degree = 0;
			this.keyword = keyword;
			this.freq = freq;
			this.childCut = false;
		}
		
	}

	// **************************************************************************
	// Function: insert
	// Return: void
	// Input: Node node
	// **************************************************************************
	// Function to insert a node into the Max Fib Heap
	// **************************************************************************
	public void insert (Node node) {
		// There are some nodes in the Fib Heap
		if (maxNode != null) {

			// Add to the immediate right of maxNode
			node.right = maxNode.right;
			maxNode.right = node;
			node.left = maxNode;
			node.right.left = node;

			// update maxNode
			if (node.freq > maxNode.freq) {
				maxNode = node;
			}
			
		} else {
			// There are no nodes in the Fib Heap yet
			maxNode = node;
		}
		/*System.out.println("This node: " + node.keyword);
		System.out.println("Right: " + node.right.keyword);
		System.out.println("Left: " + node.left.keyword);
		*/
	}
	
	// **************************************************************************
	// Function: increase_key
	// Return: void
	// Input: Node node, int k
	// **************************************************************************
	// Function to increase the frequency of a node to k
	// **************************************************************************
	public void increase_key (Node node, int k) {
		//k = new value
		node.freq = k;

		// if node is not a root, check if it exceeds parent's frequency
		if (node.parent != null) {
			// Exceeds
			if (node.freq > node.parent.freq) {
				Node P = node.parent;
				// Cut Node and place in Top-level list
				cut(node);	
				// Check of parent requires Cascading cut
				cascade_cut(P);
				// Update maxNode
				update_max();
			}
		} else {
		// Node is a root node in top level list, can increase as much as it wants
			// Update maxNode 
			if (node.freq > maxNode.freq) {
				maxNode = node;
			}
		}
	}
	
	// **************************************************************************
	// Function: update_max
	// Return: void
	// Input: --
	// **************************************************************************
	// Function to update maxNode
	// **************************************************************************
	public void update_max() {
		Node temp = maxNode.right;
		Node orig = maxNode;
		// Traverse through the top-level list and update the maxNode
		while (temp!=orig) {
			if (temp.freq > maxNode.freq) {
				maxNode = temp;
			}
			temp = temp.right;
		}
	}

	// **************************************************************************
	// Function: cut
	// Return: void
	// Input: Node node
	// **************************************************************************
	// Function to perform cut operation on a Node and remove it from a 
	// heap and move to top level-list
	// **************************************************************************
	public void cut (Node node) {
		Node parent = node.parent;
		
		//remove node from  parent's child list
		if(parent != null) {
			// More than one child exists
			if (parent.degree > 1) {
				if (parent.child == node) {
					parent.child = node.right;
				}
				node.right.left = node.left;
				node.left.right = node.right;
				
				parent.degree = parent.degree - 1;
			} else {
				//only child
				parent.child = null;
				
				parent.degree = 0;
			}
		}
		
		//add node to top list;
		node.parent = null;
		insert(node);
	} 
	
	// **************************************************************************
	// Function: cascade_cut
	// Return: void
	// Input: Node node
	// **************************************************************************
	// Function to perform cascade cut operation on a parent Node to check
	// if it has a ChildCut value of True. 
	// **************************************************************************
	public void cascade_cut (Node node) {
		// if it is not a root node
		if (node.parent != null) {
			// Childcut Value is False - set to true
			if (node.childCut==false) {
				node.childCut = true;
			} else {
		    // Childcut Value is True - Cut node from heap and move to top-level list
				Node P = node.parent;
				cut(node);
				// Perform CasecadeCut on parent
				cascade_cut(P);
			}
		}
	}	

	// **************************************************************************
	// Function: remove_max
	// Return: Node
	// Input: --
	// **************************************************************************
	// Function to remove max value of the heap
	// **************************************************************************
	public Node remove_max() {
		// Temporary node
		Node temp = new Node("",0);
		// Keep node to return later
		temp = maxNode;
		if (maxNode != null) {
			temp = maxNode;
			// more top level nodes exist
			if (maxNode.right != maxNode) {
				
				//move all children to top level list
				move_children(maxNode);
				//remove node;
				maxNode.right.left = maxNode.left;
				maxNode.left.right = maxNode.right;
				
				maxNode = maxNode.right; //random temp max
				
				// Perform Meld() and Update_max() operations
				meld();
				update_max();
				
			} else {
				// maxNode was the only heap
				// So, make children top level list				
				if(maxNode.degree>0) {
					maxNode = maxNode.child;
					maxNode.parent = null;
					Node curr_node = maxNode.right;
					while(curr_node != maxNode) {
						curr_node.parent = null;
						curr_node = curr_node.right;
					}
					update_max();
				} else {
					// Max was the only value in the list
					maxNode = null;
				}
			}
		} else {
			// return error - no heaps
			//System.out.println("No value in list yet!");
		}
		// return removed maxNode
		return temp;
	}

	// **************************************************************************
	// Function: meld
	// Return: void
	// Input: --
	// **************************************************************************
	// Function to perform Meld operation via degree wise merging using degree
	// table after removeMax operation
	// **************************************************************************
	public void meld () {
		int num_roots = 0;

		List<Node> degTable = new ArrayList<Node>();
		// Find number of roots in top level list
		Node thisNode = maxNode;
		while (thisNode.right != maxNode) {
			num_roots++;
			thisNode = thisNode.right;
		}
				
		if (maxNode != null) {
			Node curr_node = maxNode;
			Node next_node;
			Boolean meld_on = true;
			while (num_roots>0) {
				// Increase degree table size dynamically as heaps grow bigger
				while (degTable.size()<curr_node.degree+1) {
					degTable.add(null);
				}
				
				meld_on = true;
				next_node = curr_node.right;
				// While same degree heaps exists
				while(meld_on) {
					while (degTable.size()<curr_node.degree+1) {
						degTable.add(null);
					}
					int curr_degree = curr_node.degree;
					Node other_heap = degTable.get(curr_degree);
		            if (other_heap == null) {
		                degTable.set(curr_degree, curr_node);
		                if (curr_node.freq > maxNode.freq) {
		                	maxNode = curr_node;
		                }
		                meld_on = false;
		                //break;
		            } else {
		            	
		            	if (other_heap == next_node) {
		            		next_node = next_node.right;
		            	}
		                curr_node = merge_heaps(curr_node, other_heap);
		                // Set older degree null as we merged the heaps
		                degTable.set(curr_degree, null);
		                // Increase table size dynamically
		                if (degTable.size()<curr_node.degree+1) {
		                	while (degTable.size()<curr_node.degree+1) {
		    					degTable.add(null);
		    				}
		                }
		                
		                Node another_heap = degTable.get(curr_node.degree);
		                if(another_heap == null) {
		                	meld_on = false;
		                } else {
		                	// phir se karo
		                	meld_on = true;
		                }
		                
		            }
				}
	            
				// Set current degree as we merged the heaps
				degTable.set(curr_node.degree, curr_node);
				if (curr_node.freq > maxNode.freq) 
					maxNode = curr_node;
	            curr_node = next_node;
	            num_roots--;
			}
		}
	}
	
	// **************************************************************************
	// Function: merge_heaps
	// Return: Node
	// Input: Node node1, Node node2
	// **************************************************************************
	// Merge two given heaps of same degree based on which one has higher frequency value
	// **************************************************************************
    public Node merge_heaps (Node node1, Node node2) {
        if (node1.freq > node2.freq) {
        	node2.parent = node1;
        	
        	//removing smaller node from top level list
        	node2.left.right = node2.right;
        	node2.right.left = node2.left;
        	
        	Node temp = node1.child;
        	// If children of bigger node exists, make smaller node their sibling
        	if (temp != null) {
	        	node2.right = temp;
	        	node2.left = temp.left;
	        	temp.left.right = node2;
	        	temp.left = node2;
	        	//node1.child = node2;
	        	//temp.parent = null;
        	} else {
        	// smaller node is the only child of bigger node
        		node2.right = node2;
        		node2.left = node2;
        		node1.child = node2;
        	}
        	
        	// Increase degree value
        	node1.degree = node1.degree + 1;
        	// Newly created child - ChildCut value -s set to false
        	node2.childCut = false;
        	
        	maxNode = node1;
        	return node1;
        } else {
        	node1.parent = node2;
        	
        	//removing from top level list
        	node1.left.right = node1.right;
        	node1.right.left = node1.left;
        	
        	Node temp = node2.child;
        	
        	if (temp != null) {
	        	node1.right = temp;
	        	node1.left = temp.left;
	        	temp.left.right = node1;
	        	temp.left = node1;
	        	//node2.child = node1;
	        	//temp.parent = null;
        	} else {
        		node1.right = node1;
        		node1.left = node1;
        		node2.child = node1;
        	}
        	
        	node2.degree = node2.degree + 1;
        	node1.childCut = false;
        	
        	maxNode = node2;
        	return node2;
        }
	}
	
	// **************************************************************************
	// Function: move_children
	// Return: void
	// Input: Node node
	// **************************************************************************
	// Function to remove all children from a Node 
	// **************************************************************************
	public void move_children(Node node) {
          Node child = node.child;
          if (child !=null) {
        	  Node temp1 = child;
        	  Node temp2 = maxNode.right;
        	  
        	  child.left.right = maxNode.right;
        	  maxNode.right.left = child.left;
        	  child.left = maxNode;
        	  maxNode.right = child;
        	  
        	  while(temp1!=temp2) {
        		  temp1.parent = null;
        		  temp1 = temp1.right;
        	  }
          }
          node.child = null;
	}
	
	// **************************************************************************
	// Function: delete
	// Return: void
	// Input: Node node
	// **************************************************************************
	// Arbitary Delete
	// **************************************************************************
	public void delete (Node node) {
        if (node.degree > 0) {
        	//children exists - move them
        	move_children(node);
        } else {
        	node.child = null;
        }
		if (node.parent != null) {
			// Not a root node
			Node parent = node.parent;
			
			//remove node from parent's child list
			if (parent.degree > 1) {
				if (parent.child == node) {
					parent.child = node.right;
				}
				node.right.left = node.left;
				node.left.right = node.right;
				
				parent.degree = parent.degree - 1;
			} else {
				//only child
				parent.child = null;
				
				parent.degree = 0;
			}
			
		} else {
			// if it is a root node
			if (node.right != node) {
				// remove from top level list
				node.right.left = node.left;
				node.left.right = node.right;
				
				// Update maxNode
				if (maxNode == node) {
					maxNode = node.right;
					update_max();
				}
				node = null;
			} else {
				//empty list
				maxNode = null;
				node = null;
			}
		}
	}
	
	// return maxNode
	//public Node return_max() {
	//	return maxNode;
	//}
}
