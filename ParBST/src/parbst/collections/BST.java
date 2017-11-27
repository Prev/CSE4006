package parbst.collections;

public interface BST {

	/**
	 * Insert data to BST
	 * @param data: Data to insert
	 */
	void insert(int data);

	/**
	 * Find min value of BST
	 * @return: Minimal value of tree
	 */
	int findMin();

	/**
	 * Search value from BST
	 * @param toSearch
	 * @return True if search data exists
	 */
	boolean search(int toSearch);


	/**
	 * Delete value from BST
	 * @param toDelete
	 * @return True if deletion is success
	 * 		   False if fail
	 */
	boolean delete(int toDelete);

	/**
	 * Traversal BST by pre-order
	 */
	void preOrderTraversal();

	/**
	 * Traversal BST by in-order
	 */
	void inOrderTraversal();

}
