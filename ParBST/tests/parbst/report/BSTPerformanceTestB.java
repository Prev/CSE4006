package parbst.report;

import org.junit.Before;
import org.junit.Test;
import parbst.collections.BST;
import parbst.collections.ParallelBST;
import parbst.util.ParallelBSTUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BSTPerformanceTestB {

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
		ParallelBST tree = new ParallelBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);
		SpecialWorker.doRandomWorks(tree, DATASET_COUNT/2, DATASET_COUNT/2, threadCount);
	}

	private void testWithRatio14(int threadCount) {
		ParallelBST tree = new ParallelBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);
		SpecialWorker.doRandomWorks(tree, DATASET_COUNT/5, DATASET_COUNT/5*4, threadCount);
	}

	private void testWithRatio19(int threadCount) {
		ParallelBST tree = new ParallelBST();
		ParallelBSTUtil.insertMany(tree, dataSet, threadCount);
		SpecialWorker.doRandomWorks(tree, DATASET_COUNT/10, DATASET_COUNT/10*9, threadCount);
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


	static class SpecialWorker extends Thread {

		private static final int WORK_INSERT = 0;
		private static final int WORK_SEARCH = 1;

		static void doRandomWorks(BST tree, int insertCount, int searchCount, int threadCount) {
			// Init works
			List<Integer> works = new ArrayList<>();
			for (int i = 0; i < insertCount; i++) works.add(WORK_INSERT);
			for (int i = 0; i < searchCount; i++) works.add(WORK_SEARCH);
			Collections.shuffle(works);

			int seg = works.size() / threadCount;

			try {
				Thread[] threads = new Thread[threadCount];

				for (int i = 0; i < threadCount; i++) {
					threads[i] = new SpecialWorker(tree, works.subList(i*seg, (i+1)*seg));
					threads[i].start();
				}

				for (int i = 0; i < threadCount; i++) threads[i].join();

			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}

		private BST tree;
		private Random random;
		private List<Integer> works;

		SpecialWorker(BST tree, List<Integer> works) {
			this.tree = tree;
			this.random = new Random();
			this.works = works;
		}

		@Override
		public void run() {
			for (int w: works) {
				if (w == WORK_INSERT)
					tree.insert(random.nextInt());
				else
					tree.search(random.nextInt());
			}
		}
	}
}
