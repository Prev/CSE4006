package parbst.collections;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The sequential Binary Search Tree (for storing int values)
 * Support Parallel execution
 */
public class ParallelBST implements BST {
	private Node root;
	Lock lock;

	public ParallelBST() {
		root = null;
		lock = new ReentrantLock();

	}

	/**
	 * Insert data to BST
	 * @param data: Data to insertMany
	 */
	@Override
	public void insert(int data) {
		this.lock.lock();

		if (root == null) {
			root = new Node(data);
			this.lock.unlock();

		}else {
			Node cur = root;
			Node next;
			cur.lock.lock();
			lock.unlock();

			while (true) {
				if (cur.data == data) {
					cur.lock.unlock();
					return;

				}else {
					if (data < cur.data) {
						if (cur.left == null)
							cur.left = new Node(data);

						next = cur.left;

					}else {
						if (cur.right == null)
							cur.right = new Node(data);

						next = cur.right;
					}

					next.lock.lock();
					cur.lock.unlock();
					cur = next;
				}
			}
		}
	}

	/**
	 * Find min value of BST
	 * @return: Minimal value of tree
	 */
	@Override
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
	 */
	@Override
	public boolean search(int toSearch) {
		lock.lock();

		if (root == null) {
			lock.unlock();
			return false;
		}else {
			Node cur = root;
			Node next;

			cur.lock.lock();
			lock.unlock();

			while (true) {
				if (cur.data == toSearch) {
					cur.lock.unlock();
					return true;

				}else if (toSearch < cur.data) {
					next = cur.left;
				}else {
					next = cur.right;
				}

				if (next == null) {
					cur.lock.unlock();
					return false;
				}

				next.lock.lock();
				cur.lock.unlock();
				cur = next;
			}
		}
	}

	/**
	 * Delete value from BST
	 * @param toDelete
	 * @return True if deletion is success
	 * 		   False if fail
	 */
	@Override
	public boolean delete(int toDelete) {
		lock.lock();

		if (root == null) {
			lock.unlock();
			return false;
		}

		root.lock.lock();
		Node cur, parent;

		if (root.data == toDelete) {
			Node oldRoot = root;
			root = boundaryNode(oldRoot);

			oldRoot.lock.unlock();
			lock.unlock();

		}else {
			parent = root;
			cur = (toDelete < root.data) ? root.left : root.right;

			cur.lock.lock();
			lock.unlock();

			while (true) {
				if (cur.data == toDelete) {
					Node tmp = boundaryNode(cur);

					if (parent.left == cur) parent.left = tmp;
					else parent.right = tmp;

					cur.lock.unlock();
					parent.lock.unlock();
					break;

				} else {
					parent.lock.unlock();
					parent = cur;

					if (toDelete < cur.data)
						cur = cur.left;
					else
						cur = cur.right;
				}

				if (cur == null)
					return false;

				cur.lock.lock();
			}
		}
		return true;
	}

	private Node boundaryNode(Node origin) {
		if (origin.left == null)
			return origin.right;
		else if (origin.right == null)
			return origin.left;
		else {
			// Retrieve Data
			Node cur = origin.left;
			Node parent = origin;

			cur.lock.lock();

			while (cur.right != null) {
				if (parent != origin) parent.lock.unlock();
				parent = cur;
				cur = cur.right;
				cur.lock.lock();
			}

			if (cur.left != null) cur.left.lock.lock();

			if (parent == origin)
				parent.left = cur.left;
			else {
				parent.right = cur.left;
				parent.lock.unlock();
			}
			if (cur.left != null) cur.left.lock.unlock();


			cur.left = origin.left;
			cur.right = origin.right;

			cur.lock.unlock();
			return cur;
		}
	}

	/**
	 * Traversal BST by pre-order
	 */
	@Override
	public void preOrderTraversal() {
		lock.lock();
		if (root == null) {
			lock.unlock();
			return;
		}
		root.lock.lock();
		lock.unlock();
		preOrderHelper(root);
	}
	private void preOrderHelper(Node node) {
		System.out.print(node+" ");
		node.lock.unlock();

		if (node.left != null) {
			node.left.lock.lock();
			preOrderHelper(node.left);
		}
		if (node.right != null) {
			node.right.lock.lock();
			preOrderHelper(node.right);
		}
	}

	/**
	 * Traversal BST by in-order
	 */
	@Override
	public void inOrderTraversal() {
		lock.lock();
		if (root == null) {
			lock.unlock();
			return;
		}
		root.lock.lock();
		lock.unlock();
		inOrderHelper(root);
	}
	private void inOrderHelper(Node node) {
		if (node.left != null) {
			node.left.lock.lock();
			inOrderHelper(node.left);
		}
		System.out.print(node+" ");

		if (node.right != null) {
			node.right.lock.lock();
			inOrderHelper(node.right);
		}

		node.lock.unlock();
	}


	/**
	 * Node class in ParallelBST
	 */
	class Node {
		int data;
		Node left, right;

		Lock lock;

		public Node(int data, Node l, Node r)
		{
			left = l; right = r;
			this.data = data;
			this.lock = new ReentrantLock();
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
