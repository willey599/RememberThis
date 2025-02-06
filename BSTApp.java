package practicePackage;

import java.util.Scanner;

class Node {
	int data;
	Node right;
	Node left;
	int height;
	
	public Node (int data) {
		this.data = data;
		//nodes that exist start at height 1
		this.height = 1;
		this.left = null;
		this.right = null;
	}
}

class BST {
	//global scanner
	static Scanner scanner = new Scanner(System.in);	
	
	static Node root = null;

//insert, create, find, delete functions
	public static Node insertNode (Node node, int k) {
		if (node == null) {
			return new Node (k);			
		}
		//place new nodes right or left
		if (k < node.data) {
			node.left = insertNode (node.left, k);
		}
		else if (k > node.data) {
			node.right = insertNode (node.right, k);
		}	
		//duplicate key case
		else {
			System.out.println("Duplicate value, new node not inserted");
			return node;
		}
		updateHeight(node);
		node = balance(node);
		return node;
	}
	
	public static Node findNode(Node node, int key) {
		//base case
		if (node == null || key == node.data) {
			return node;
		}
		//recursively search left or right subtrees
		else if (key < node.data) {
			return findNode(node.left, key);
		}
		else{
			return findNode(node.right, key);
		}
	}
	
	public static Node deleteNode(Node node, int key) {
		//base case: if the node is null, stop recursion if using deletionWhenTwoChildren (input here is successorNode, not root)
		if (node == null) {
			return null;
		}
		//Go to the right node
		if (key < node.data) {
			node.left = deleteNode(node.left, key);
		}
		else if (key > node.data) {
			node.right = deleteNode(node.right, key);
		}
			//node should now reflect key
		else {
			//case 1: node has no children. Just delete
			if (node.left == null && node.right == null) {
			System.out.println("entering 0 children deletion protocol");
			node = null;
			}
			//case 2: node has 1 child. Move child up into node's spot
			else if (node.left == null) {
				System.out.println("entering 1 child deletion protocol");
			node = node.right;
			}
			else if (node.right == null) {
				node = node.left;
			}
			//case 3: node has 2 children. Find child with the greatest value on the left subtree and replace node.
			else {
				System.out.println("entering 2 children deletion protocol");
				//find successor (rightmost node of left subtree) and make copy of node for subsequent transfer of data
				Node successorNode = maxOfLeftSubtree(node.left);
		
				//copy data into the node you want to delete
				node.data = successorNode.data;
		
				//delete the successor node recursively
				node.left = deleteNode(node.left, successorNode.data);
				}
		}	
		//by now the successorNode.data has replaced the old node.data.
		
		//if the node is not null by the end, rotate
		if (node != null) {
			updateHeight(node);
			return balance(node);
		}
		return null;
	}
	
	private static Node maxOfLeftSubtree(Node node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}
	
//height functions	
	
	private static int getHeight(Node n) {
		//if the node is not null, return node height as-is. If it's null, height is 0.
		if (n != null) {
			return n.height;
		}
		else {
			return 0;
		}
	}

	private static void updateHeight(Node node) {
		int leftChildHeight = getHeight(node.left);
		int rightChildHeight = getHeight(node.right);
		node.height = Math.max(leftChildHeight, rightChildHeight) + 1;
	}
	
//balance functions
	
	public static Node balance(Node node) {
		int BFValue = calcBalanceFactor(node);
		if (BFValue > 1) {
			//LR rotation
			if (calcBalanceFactor(node.left) < 0) {
				node.left = rotateLeft(node.left);
			}
			node = rotateRight(node);
		}
		if (BFValue < -1) {
			if (calcBalanceFactor(node.right) > 0) {
				node.right = rotateRight(node.right);
			}
			node = rotateLeft(node);
		}
		System.out.println("Balance successful");
		return node;
	}
	public static int calcBalanceFactor(Node node) {
		if (node == null) {
			return 0;
		}
		return (getHeight(node.left) - getHeight(node.right));
	}

	public static Node rotateRight(Node node) {
		Node leftChild = node.left;
		node.left = leftChild.right;
		leftChild.right = node;
		updateHeight(node);
		updateHeight(leftChild);
		
		return leftChild;
		
		/*
		   n              lc
		  /              / \
		 lc      ----> lc.l n
		/ \                /
	 lc.l  lc.r          lc.r   
		 */
		

	}
	
	public static Node rotateLeft(Node node) {
		Node rightChild = node.right;
		node.right = rightChild.left;
		rightChild.left = node;
		updateHeight(node);
		updateHeight(rightChild);
		return rightChild;
	}

	//traversal functions
public static void inOrder(Node node) {
    if (node != null) {
    	inOrder(node.left);
    	System.out.print(node.data + " ");
    	inOrder(node.right);
    }
}
public static void preOrder(Node node) {
	if (node != null) {
    	System.out.print(node.data + " ");
    	preOrder(node.left);
    	preOrder(node.right);
    }
}
// Post-order traversal
public static void postOrder(Node node) {
    if (node != null) {
    	postOrder(node.left);
    	postOrder(node.right);    	
    	System.out.print(node.data + " ");
    }
}
//menu select	
	public static void menuSelect() {
		int optionSelect;
		boolean treeExists = false;
		do {
			System.out.println("\nMenu options: \n1. Create a binary search tree \n2. Add a node \n3. Delete a node \n4. Print nodes by InOrder \n5. Print nodes by PreOrder \n6. Print nodes by PostOrder \n7. Exit program  \n8. Check if node exists \nYour choice (1-7): ");
				optionSelect = scanner.nextInt();
			//check for immediate exit
			if (optionSelect == 7) {
				break;
			}
			//if user uses other functions before choosing 1
			if (optionSelect != 1 && treeExists == false) {
				System.out.println("Need to create a tree before using other functions!");
				}				
			switch (optionSelect) {
				case 1: 
					//"creates" BST object by setting the root back to null
					root = null;
					System.out.println("New tree created.");
					treeExists = true;
					break;
				case 2: 
					System.out.println("Select an integer to place into the node: ");
					int nodeValue = scanner.nextInt();
					root = insertNode(root, nodeValue);
					System.out.println("Node inserted.");
					break;
				case 3: 
					System.out.println("Type the integer of the node you would like to delete: ");
					int valueToDelete = scanner.nextInt();
					Node nodeToDelete = findNode(root, valueToDelete); 
					if(nodeToDelete != null) {
						System.out.println("Node found. Node: " + nodeToDelete.data + ". Moving to delete.");
						try{
							root = deleteNode(root, valueToDelete);
						}
						catch (Exception e) {
							System.out.println("Delete Node unsuccessful. Error code 2");
						}
					}
					break;
				case 4: 
					System.out.println("inOrder\n");
					System.out.print("{ ");
					inOrder(root);
					System.out.print("}\n");
					break;
				case 5: 
					System.out.println("preOrder\n");
					System.out.print("{ ");
					preOrder(root);
					System.out.print("}\n");
					break;
				case 6: 
					System.out.println("postOrder\n");
					System.out.print("{ ");
					postOrder(root);
					System.out.print("}\n");
					break;
			}
		}
		while (optionSelect != 7);
		System.out.println("Exit selected, terminating program.");
    }		
}

public class BSTApp extends BST{

	public static void main (String[] args) {
		BST.menuSelect();
		scanner.close();
		//breaking the do while loop means terminating the program
	}	
}