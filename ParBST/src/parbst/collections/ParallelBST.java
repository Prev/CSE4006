package parbst.collections;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParallelBST implements BST {
	private Node root;
	Lock lock;

	public ParallelBST() {
		root = null;
		lock = new ReentrantLock();

	}

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

	@Override
	public int findMin() {
		// TODO
		return 0;
	}

	@Override
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

	@Override
	public boolean delete(int toDelete) {
		// TODO
		return false;
	}

	@Override
	public void preOrderTraversal() {
		// TODO
	}

	@Override
	public void inOrderTraversal() {
		// TODO
	}


	/**
	 * Node class in ParallelBST
	 */
	private class Node {
		private int data;
		private Node left, right;

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
