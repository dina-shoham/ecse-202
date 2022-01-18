/**
 * The bTree class creates a binary tree that stores the bBall objects.
 * We compare bSize of the different objects, and then sort them accordingly.
 */

public class bTree {

	public class bNode {	// this is a nested class 
		bBall data;
		bNode left;
		bNode right;
	}
	
	// initiating instance variables for the class
	bNode root = null;		// default for bNode is null
	double startLocation = 0;		// start location of the first ball
	
	public void addNode(bBall data) {		// define bBall as the type for nodes
		root = rNode(root,data);		// the node refers to the root node
	}
	
	/**
	 * calling on the method now
	 * the constructor for rNode contains:
	 *  1. the variable root, of type bNode
	 *  2. the variable data, of type bBall
	 * @param root
	 * @param data
	 * @return
	 */
	private bNode rNode(bNode root, bBall data) {
		if (root==null) {		// if the root is null (starting condition)
			bNode node = new bNode();		// create a new node
			node.data = data;		// copy the data into the node
			node.left = null;		// left child of node is null
			node.right = null;		// right child of node is null
			root = node;		// root is the node created
			return root;
		}
		
		else if (data.bSize < root.data.bSize) {		// if the bSize is less than the root's bSize
			root.left = rNode(root.left,data);		// traverse to the left
		}
		
		else {		// if not (so if bSize is greater than or equal to root's bSize)
			root.right = rNode(root.right,data);	// traverse to the right	
		}
		return root;
	}
	
	/**
	 * the isRunning method calls on checkRunning (next method)
	 * @return
	 */
	public boolean isRunning() {
		return checkRunning(root);
	}
	
	/**
	 * the checkRunning method checks whether the balls are moving
	 * @param root
	 * @return
	 */
	private boolean checkRunning(bNode root) {
		boolean running = false;		// local variable running keeps track of whether the balls are moving
		if (root.left != null) checkRunning(root.left);		// if the left root is not null, perform checkRunning method
		running = root.data.isRunning;
		if (root.right != null) checkRunning(root.right);		// same for right root
		return running;
	}
	
	/**
	 * traverse method recursively traverses the binary tree and places the ball in each node
	 * it descends to the left successor and then returns back to the current root node
	 * then it repeats but along the right successor
	 * @param root
	 */
	private void traverse(bNode root) {
		if (root.left != null) traverse(root.left);
		root.data.myBall.setLocation(startLocation,root.data.myBall.getY());		// setting start location for the first ball
		startLocation += root.data.bSize;		// setting location for the following balls
		if (root.right != null) traverse(root.right);
		
	}
	
	/**
	 * moveSort method operates on bTree and moves the bBall in each node
	 */
	public void moveSort() {
		traverse(root);

	}
}