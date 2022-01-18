/**
 * The bTree class creates a binary tree that stores the bBall objects.
 * We compare bSize of the different objects, and then sort them accordingly.
 */

public class bTree {

	// initializing variables for the bTree class
	int YSorted;		// y position of ball after it is sorted
	int XSorted;		// same for x
	int currentSize;		// current ball size
	int startLocation = 0;		// starting location of the ball
	
	public class bNode {	// this is a nested class 
		bBall cBall;		// bBall called cBall
		bNode left;		// left node
		bNode right;		// right node
	}
	
	bNode root = null;		// setting root to null
	
	/**
	 * calling on the method now
	 * the constructor for rNode contains:
	 *  1. the variable root, of type bNode
	 *  2. the variable cBall, of type bBall
	 * @param root
	 * @param cBall
	 * @return
	 */
	private bNode rNode(bNode root, bBall cBall) {
		if (root==null) {		// if the root is null (starting condition)
			bNode node = new bNode();		// create a new node
			node.cBall = cBall;		// copy the data into the node
			node.left = null;		// left child of node is null
			node.right = null;		// right child of node is null
			root = node;		// root is the node created
			return root;
		}
		else if ((int)cBall.bSize < (int)root.cBall.bSize) {		// if bSize is less than the root's bSize
			root.left = rNode(root.left,cBall);		// traverse to the left
		}
		else {		// if not (if bSize is greater than or equal to root's bSize)
			root.right = rNode(root.right,cBall);	// traverse to the right	
		}
		return root;
	}
	
	
	public void addNode(bBall cBall) {		// define bBall as the type for nodes
		root = rNode(root,cBall);		// the node refers to the root node
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
		if (root.left != null) checkRunning(root.left);		// if the left root is not null, perform checkRunning method on it
		running = root.cBall.isRunning;
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
		if (root.left != null) 		// if left node is not null
			{
				traverse(root.left);		// method calls on itself to traverse to the left
			}
		
		if (root.cBall.bSize > currentSize) {		// if the ball is larger than current size
			YSorted = (int) (600-6*2*root.cBall.bSize);		// setting y position of current ball (sits on horizon)
			XSorted = XSorted + ((int)root.cBall.bSize*2*6);		// setting x position of current ball (
			root.cBall.myBall.setLocation(XSorted, YSorted);		// setting start location for the first ball
			currentSize = (int) root.cBall.bSize + 1;		// incrementing currentSize to the next size category
			
		}else {		// if the ball is the same size
			root.cBall.myBall.setVisible(false);		// hide it
		}
		
		if (root.right != null) 
			{
				traverse(root.right);	
			}
		}
	
	/**
	 * the moveSort method operates on bTree and moves the bBall in each node
	 * it does this by calling on the traverse method
	 */
	public void moveSort() {
		traverse(root);
	}
	
	/**
	 * the stopAll method stops the balls (called on in bSim)
	 * it calls on the stop method
	 */
	public void stopAll() {
		stop(root);
	}
	
	/**
	 * the stop method stops the bTree
	 */
	public void stop(bNode end) {
		if(end.left != null)
			stop(end.left);
			end.cBall.isRunning=false;
			
		if(end.right != null)
			stop(end.right);
	}
	
}