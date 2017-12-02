package parbst.util;

import parbst.collections.BST;
import parbst.collections.ParallelBST;

public class ParallelBSTUtil {

	public static void insertMany(ParallelBST tree, int[] dataSet, int threadCount) {
		try {
			Thread[] threads = new Thread[threadCount];
			int seg = dataSet.length / threadCount;

			for (int i = 0; i < threadCount; i++) {
				threads[i] = new InsertWorker(tree, dataSet, i * seg, seg);
				threads[i].start();
			}

			for (int i = 0; i < threadCount; i++) threads[i].join();

		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public static void deleteMany(ParallelBST tree, int[] dataSet, int threadCount) {
		try {
			Thread[] threads = new Thread[threadCount];
			int seg = dataSet.length / threadCount;

			for (int i = 0; i < threadCount; i++) {
				threads[i] = new DeleteWorker(tree, dataSet, i * seg, seg);
				threads[i].start();
			}

			for (int i = 0; i < threadCount; i++) threads[i].join();

		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}



	static class InsertWorker extends Thread {

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

	static class DeleteWorker extends Thread {

		private BST tree;
		private int[] values;
		private int offset;
		private int length;

		DeleteWorker(BST tree, int[] values, int offset, int length) {
			this.tree = tree;
			this.values = values;
			this.offset = offset;
			this.length = length;
		}

		@Override
		public void run() {
			for (int i = offset; i < offset+length; i++) {
				tree.delete(values[i]);
			}

		}
	}
}
