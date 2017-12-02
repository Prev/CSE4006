package parbst.collections;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import parbst.util.ParallelBSTUtil;

import java.util.Random;



public class ParallelBSTTest {
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

	protected void testInsertAndDeleteBySearch(int threadCount) {
		ParallelBST tree = new ParallelBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);

		for (int d: dataSet)
			assertTrue(tree.search(d));

		ParallelBSTUtil.deleteMany(tree, dataSet, threadCount);

		for (int d: dataSet)
			assertFalse(tree.search(d));
	}

	@Test
	public void singleThreadTest() {
		testInsertAndDeleteBySearch(1);
	}

	@Test
	public void dualThreadTest() {
		testInsertAndDeleteBySearch(2);
	}

	@Test
	public void quadThreadTest() {
		testInsertAndDeleteBySearch(4);
	}

	@Test
	public void octaThreadTest(){
		testInsertAndDeleteBySearch(8);
	}
}
