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

	@Override
	public boolean delete(int toDelete) {
		lock.lock();

		if (root == null) {
			lock.unlock();
			return false;
		}

		root.lock.lock();
		Node cur, parent, tmp;

		if (root.data == toDelete) {
			Node oldRoot = root;
			root = boundaryNode(root);

			if (root != null) {
				root.left = oldRoot.left;
				root.right = oldRoot.right;
			}

			oldRoot.lock.unlock();
			lock.unlock();

		}else {
			parent = root;
			cur = (toDelete < root.data) ? root.left : root.right;

			cur.lock.lock();
			lock.unlock();

			while (true) {
				if (cur.data == toDelete) {
					tmp = boundaryNode(cur);

					if (parent.left == cur) parent.left = tmp;
					else parent.right = tmp;

					if (tmp != null) {
						tmp.left = cur.left;
						tmp.right = cur.right;
					}

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
		Node cur;
		Node parent = origin;

		if (origin.left != null) {
			cur = origin.left;
			cur.lock.lock();

			while (cur.right != null) {
				if (parent != origin) parent.lock.unlock();
				parent = cur;
				cur = cur.right;
				cur.lock.lock();
			}

			if (cur.left != null) cur.left.lock.lock();
			if (parent == origin) parent.left = cur.left;
			else {
				parent.right = cur.left;
				parent.lock.unlock();
			}
			if (cur.left != null) cur.left.lock.unlock();

			cur.lock.unlock();

		} else if (origin.right != null) {
			cur = origin.right;
			cur.lock.lock();

			while (cur.left != null) {
				if (parent != origin) parent.lock.unlock();
				parent = cur;
				cur = cur.left;
				cur.lock.lock();
			}

			if (cur.right != null) cur.right.lock.lock();
			if (parent == origin) parent.right = cur.right;
			else {
				parent.left = cur.right;
				parent.lock.unlock();
			}
			if (cur.right != null) cur.right.lock.unlock();

			cur.lock.unlock();

		} else {
			return null;
		}
		return cur;
	}

	/*@Override
	public boolean delete(int toDelete) {
		lock.lock();
		if (root == null) {
			lock.unlock();
			return false;
		}
		try {
			root.lock.lock();
			lock.unlock();

			root = delete(root, toDelete);
			return true;

		} catch (RuntimeException e) {
			return false;
		}
	}
	private Node delete(Node p, int toDelete) {
		if (p == null) {
			throw new RuntimeException("cannot delete.");
		}

		if (toDelete < p.data) {
			System.out.println("l");
			if (p.left != null) {
				p.left.lock.lock();
				//p.lock.unlock();

				p.left = delete(p.left, toDelete);

			}else {
				p.lock.unlock();
				throw new RuntimeException("cannot delete.");
			}

		} else if (toDelete > p.data) {
			System.out.println("r");
			if (p.right != null) {
				p.right.lock.lock();
				//p.lock.unlock();

				p.right = delete(p.right, toDelete);
			}else {
				p.lock.unlock();
				throw new RuntimeException("cannot delete.");
			}


		} else {
			System.out.println("e");
			if (p.left == null) {
				System.out.println("er"); p.lock.unlock(); return p.right; }
			else if (p.right == null) {
				System.out.println("el"); p.lock.unlock(); return p.left;}
			else {
				System.out.println("e+");
				// get data from the rightmost node in the left subtree
				p.left.lock.lock();
				p.data = retrieveData(p.left);
				// delete the rightmost node in the left subtree
				p.left =  delete(p.left, p.data);

			}
		}
		p.lock.unlock();
		return p;
	}
	private int retrieveData(Node p) {
		while (p.right != null) {
			p = p.right;
		}
		return p.data;
	}
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
