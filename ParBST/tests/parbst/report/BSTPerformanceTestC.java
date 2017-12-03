package parbst.report;

import org.junit.Before;
import org.junit.Test;
import parbst.collections.ParallelRWLBST;
import parbst.util.ParallelBSTUtil;
import java.util.Random;

public class BSTPerformanceTestC {

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

	private void testWithRatio11(int threadCount) {
		ParallelRWLBST tree = new ParallelRWLBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);
		BSTPerformanceTestB.SpecialWorker.doRandomWorks(tree, DATASET_COUNT/2, DATASET_COUNT/2, threadCount);
	}

	private void testWithRatio14(int threadCount) {
		ParallelRWLBST tree = new ParallelRWLBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);
		BSTPerformanceTestB.SpecialWorker.doRandomWorks(tree, DATASET_COUNT/5, DATASET_COUNT/5*4, threadCount);
	}

	private void testWithRatio19(int threadCount) {
		ParallelRWLBST tree = new ParallelRWLBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);
		BSTPerformanceTestB.SpecialWorker.doRandomWorks(tree, DATASET_COUNT/10, DATASET_COUNT/10*9, threadCount);
	}

	@Test
	public void singleThreadTest11() {
		testWithRatio11(1);
	}
	@Test
	public void dualThreadTest11() {
		testWithRatio11(2);
	}
	@Test
	public void quadThreadTest11() {
		testWithRatio11(4);
	}
	@Test
	public void octaThreadTest11() {
		testWithRatio11(8);
	}


	@Test
	public void singleThreadTest14() {
		testWithRatio14(1);
	}
	@Test
	public void dualThreadTest14() {
		testWithRatio14(2);
	}
	@Test
	public void quadThreadTest14() {
		testWithRatio14(4);
	}
	@Test
	public void octaThreadTest14() {
		testWithRatio14(8);
	}

	@Test
	public void singleThreadTest19() {
		testWithRatio19(1);
	}
	@Test
	public void dualThreadTest19() {
		testWithRatio19(2);
	}
	@Test
	public void quadThreadTest19() {
		testWithRatio19(4);
	}
	@Test
	public void octaThreadTest19() {
		testWithRatio19(8);
	}

}
