package parbst.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;


public class MonoBSTTest {

	private int[] dataSet;

	private Random random;
	private static final int DATASET_COUNT = 10000;

	@Before
	public void init() {
		random = new Random();

		dataSet = new int[DATASET_COUNT];
		for (int i = 0; i < dataSet.length; i++)
			dataSet[i] = random.nextInt();
	}

	@Test
	public void insertionTest() {
		BST tree = new MonoBST();
		for (int d: dataSet)
			tree.insert(d);

		for (int d: dataSet) assertTrue(tree.search(d));
	}

	@Test
	public void deletionTest() {
		BST tree = new MonoBST();
		for (int d: dataSet)
			tree.insert(d);

		for (int d: dataSet) assertTrue(tree.search(d));

		for (int d: dataSet)
			tree.delete(d);

		for (int d: dataSet) assertFalse(tree.search(d));
	}

}
