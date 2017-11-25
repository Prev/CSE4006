import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;


public class BSTTest {

	private int[] dataSet;
	private Random random;
	private static final int INSERTION_COUNT = 100000;

	@Before
	public void init() {
		random = new Random();

		dataSet = new int[INSERTION_COUNT];
		for (int i = 0; i < INSERTION_COUNT; i++)
			dataSet[i] = random.nextInt();
	}

	@Test
	public void insertTestSingle() throws InterruptedException {
		insertWithMulitthreading(1);
	}

	private void insertWithMulitthreading(int threadCount) throws InterruptedException {
		int seg = INSERTION_COUNT / threadCount;
		Thread[] threads = new Thread[threadCount];

		BST tree = new BST();
		for (int i = 0; i < threadCount; i++) {
			threads[i] = new InsertWorker(tree, dataSet, i * seg, seg);
			threads[i].start();
		}

		for (int i = 0; i < threadCount; i++)
			threads[i].join();

		for (int d: dataSet)
			assertTrue(tree.search(d));

	}

	class InsertWorker extends Thread {

		private BST tree;
		private int[] values;
		private int offset;
		private int length;

		InsertWorker(BST tree, int[] values, int offset, int length) {
			this.tree = tree;
			this.values = values;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public void run() {
			for (int i = offset; i < offset+length; i++)
				tree.insert(values[i]);

		}
	}
}
