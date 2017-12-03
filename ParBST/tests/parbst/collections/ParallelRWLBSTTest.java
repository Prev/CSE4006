package parbst.collections;

import org.junit.Before;
import org.junit.Test;
import parbst.util.ParallelBSTUtil;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ParallelRWLBSTTest {
	private int[] dataSet;

	private Random random;
	private static final int DATASET_COUNT = 10000;

	@Before
	public void init() {
		random = new Random();

		dataSet = new int[DATASET_COUNT];
		for (int i = 0; i < dataSet.length; i++)
			dataSet[i] = i+1;//random.nextInt();
	}

	protected void testInsertBySearch(int threadCount) {
		ParallelRWLBST tree = new ParallelRWLBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);

		for (int d: dataSet) {
			boolean rt = tree.search(d);
			if (!rt) System.out.println("Fail: " + d);
			assertTrue(rt);
		}

//		ParallelBSTUtil.deleteMany(tree, dataSet, threadCount);
//
//		for (int d: dataSet)
//			assertFalse(tree.search(d));
	}

	@Test
	public void singleThreadTest() {
		testInsertBySearch(1);
	}

	@Test
	public void dualThreadTest() {
		testInsertBySearch(2);
	}

	@Test
	public void quadThreadTest() {
		testInsertBySearch(4);
	}

	@Test
	public void octaThreadTest(){
		testInsertBySearch(8);
	}
}
