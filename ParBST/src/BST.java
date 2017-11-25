/**
 * The sequential Binary Search Tree (for storing int values)
 */
public class BST {

	private Node root;

	public BST() {
		root = null;
	}

	/**
	 * Insert data to BST
	 * @param data
	 */
	public void insert(int data) {
		root = insert(root, data);
	}

	private Node insert(Node p, int toInsert) {
		if (p == null)
			return new Node(toInsert);

		if (toInsert == p.data)
			return p;

		if (toInsert < p.data)
			p.left = insert(p.left, toInsert);
		else
			p.right = insert(p.right, toInsert);

		return p;
	}

	/**
	 * Find min value of BST
	 * @return
	 */
	// you don't need to implement hand-over-hand lock for this function.
	public int findMin() {
		if (root == null) {
			throw new RuntimeException("cannot findMin.");
		}
		Node n = root;
		while (n.left != null) {
			n = n.left;
		}
		return n.data;
	}

	/**
	 * Search value from BST
	 * @param toSearch
	 * @return True if search data exists
	 * 		   False otherwise
	 */
	public boolean search(int toSearch) {
		return search(root, toSearch);
	}
	private boolean search(Node p, int toSearch) {
		if (p == null)
			return false;
		else
		if (toSearch == p.data)
			return true;
		else
		if (toSearch < p.data)
			return search(p.left, toSearch);
		else
			return search(p.right, toSearch);
	}

	/**
	 * Delete value from BST
	 * @param toDelete
	 * @return True if deletion is success
	 * 		   False if fail
	 */
	public boolean delete(int toDelete) {
		try {
			root = delete(root, toDelete);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}
	private Node delete(Node p, int toDelete) {
		if (p == null) {
			throw new RuntimeException("cannot delete.");
		} else if (toDelete < p.data) {
			p.left = delete (p.left, toDelete);
		} else if (toDelete > p.data) {
			p.right = delete (p.right, toDelete);
		} else {
			if (p.left == null) { return p.right; }
			else if (p.right == null) { return p.left;}
			else {
				// get data from the rightmost node in the left subtree
				p.data = retrieveData(p.left);
				// delete the rightmost node in the left subtree
				p.left =  delete(p.left, p.data) ;
			}
		}
		return p;
	}
	private int retrieveData(Node p) {
		while (p.right != null) {
			p = p.right;
		}
		return p.data;
	}

	/**
	 * Traversal BST by pre-order
	 */
	public void preOrderTraversal() {
		preOrderHelper(root);
	}
	private void preOrderHelper(Node r) {
		if (r != null)
		{
			System.out.print(r+" ");
			preOrderHelper(r.left);
			preOrderHelper(r.right);
		}
	}

	/**
	 * Traversal BST by in-order
	 */
	public void inOrderTraversal() {
		inOrderHelper(root);
	}
	private void inOrderHelper(Node r)
	{
		if (r != null)
		{
			inOrderHelper(r.left);
			System.out.print(r+" ");
			inOrderHelper(r.right);
		}
	}

	/**
	 * Node class in BST
	 */
	private class Node {
		private int data;
		private Node left, right;

		public Node(int data, Node l, Node r)
		{
			left = l; right = r;
			this.data = data;
		}

		public Node(int data)
		{
			this(data, null, null);
		}

		public String toString()
		{
			return ""+data;
		}
	}
}
