package parbst.collections;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Parallel BST that uses ReadWriteLock
 */
public class ParallelRWLBST implements BST {

	protected Node root;
	ReadWriteLock rwLock;
	Lock writeLock;
	Lock lock;

	public ParallelRWLBST() {
		root = null;
		rwLock = new ReentrantReadWriteLock();

		lock = rwLock.readLock();
		writeLock = rwLock.writeLock();
	}

	@Override
	public void insert(int data) {
		lock.lock();

		if (root == null) {
			lock.unlock();
			writeLock.lock();

			if (root != null) {
				// Try again
				writeLock.unlock();
				insert(data);
				return;
			}

			root = new Node(data);
			writeLock.unlock();

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
					next = (data < cur.data) ? cur.left : cur.right;

					if (next == null) {
						cur.lock.unlock(); cur.writeLock.lock();

						next = new Node(data);
						if (data < cur.data) {
							if (cur.left != null) {
								// Try again
								cur.writeLock.unlock(); cur.lock.lock();
								continue;
							}
							cur.left = next;
						}
						else {
							if (cur.right != null) {
								// Try again
								cur.writeLock.unlock(); cur.lock.lock();
								continue;
							}
							cur.right = next;
						}
						cur.writeLock.unlock();
						return; // Success
					}

					next.lock.lock();
					cur.lock.unlock();
					cur = next;
				}
			}
		}
	}

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
				}

				next = (toSearch < cur.data) ? cur.left : cur.right;

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

	@Override
	public boolean delete(int toDelete) {
		throw new UnsupportedOperationException("Currently deletion is not supported in ParallelRWLBST");
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
	 * Node class in ParallelRWLBST
	 */
	class Node {
		int data;
		Node left, right;

		Lock lock;
		Lock writeLock;

		public Node(int data, Node l, Node r)
		{
			left = l; right = r;
			this.data = data;

			ReadWriteLock rwLock = new ReentrantReadWriteLock();
			this.lock = rwLock.readLock();
			this.writeLock = rwLock.writeLock();
		}

		public Node(int data)
		{
			this(data, null, null);
		}

		public String toString()
		{
			return ""+data;
		}


		boolean writeTask(Function<Node, Boolean> callback) {
			this.lock.unlock();
			this.writeLock.lock();

			try {
				return callback.apply(this);

			}finally {
				this.writeLock.unlock();
				this.lock.lock();
			}
		}
	}
}
