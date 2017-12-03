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
				// fucking root is changed
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
					boolean cmp = (data < cur.data);
					next = cmp ? cur.left : cur.right;

					/*if (next == null) {
						boolean ret = cur.writeTask((node) -> {
							if (cmp) {
								if (node.left != null) { return false;} // Concurrency error
								node.left = new Node(data);
							}
							else {
								if (node.right != null) {return false;} // Concurrency error
								node.right = new Node(data);
							}
							return true;
						});

						if (ret) {
							cur.lock.unlock();
							return;
						}else {
							continue;
						}
					}*/
					if (next == null) {
						cur.lock.unlock();
						cur.writeLock.lock();

						next = new Node(data);
						if (data < cur.data) {
							if (cur.left != null) {
								// Concurrency error
//								System.out.println(Thread.currentThread().getName() + ": one more");
								cur.writeLock.unlock();
								cur.lock.lock();
								continue;
							}
							cur.left = next;
						}
						else {
							if (cur.right != null) {
								// Concurrency error
//								System.out.println(Thread.currentThread().getName() + ": one more");
								cur.writeLock.unlock();
								cur.lock.lock();
								continue;
							}
							cur.right = next;
						}

						cur.writeLock.unlock();
						return; // Success
					}

//					if (data < cur.data) {
//						if (cur.left == null)
//							cur.changeLeft(new Node(data));
//
//						next = cur.left;
//
//					}else {
//						if (cur.right == null)
//							cur.changeRight(new Node(data));
//
//						next = cur.right;
//					}

					next.lock.lock();
					cur.lock.unlock();
					cur = next;
				}
			}
		}
	}

	@Override
	public int findMin() {
		return 0;
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

	@Override
	public void preOrderTraversal() {

	}

	@Override
	public void inOrderTraversal() {

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
