package parbst.report;

import org.junit.Before;
import org.junit.Test;
import parbst.collections.ParallelBST;
import parbst.util.ParallelBSTUtil;

import java.util.Random;

public class BSTPerformanceTest {

	private int[] dataSet;

	private Random random;
	private static final int DATASET_COUNT = 1000000;

	@Before
	public void init() {
		random = new Random();

		dataSet = new int[DATASET_COUNT];
		for (int i = 0; i < dataSet.length; i++)
			dataSet[i] = random.nextInt();
	}

	@Test
	public void a_singleThreadInsertionTest() {
		ParallelBSTUtil.insertMany(new ParallelBST(), dataSet, 1);
	}
	@Test
	public void a_dualThreadInsertionTest() {
		ParallelBSTUtil.insertMany(new ParallelBST(), dataSet, 2);
	}
	@Test
	public void a_quadThreadInsertionTest() {
		ParallelBSTUtil.insertMany(new ParallelBST(), dataSet, 4);
	}
	@Test
	public void a_octaThreadInsertionTest() {
		ParallelBSTUtil.insertMany(new ParallelBST(), dataSet, 8);
	}

}
