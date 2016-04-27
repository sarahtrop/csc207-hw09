import java.util.ArrayList;
import java.util.Arrays;

public class PriorityQueue<T extends Comparable<T>> {
	public ArrayList<T> tree;

	public PriorityQueue() {
		tree = new ArrayList<>();
	}

	public void enqueue(T v) {
		tree.add(v);
		enqueueH(tree.size() - 1, (tree.size() - 1) / 2);
	}

	private void enqueueH(int index, int parentIndex) {
		if (tree.get(index).compareTo(tree.get(parentIndex)) > 0) {
			swap(index, parentIndex);
			enqueueH(index, parentIndex);
		} else {
			return;
		}
	}

	public void dequeue() {
		swap(0, tree.size() - 1);
		tree.remove(tree.size() - 1);
		dequeueH(0);
	}

	private void dequeueH(int index) {
		if (tree.get(index).compareTo(tree.get(index + 1)) > 0 && tree.get(index).compareTo(tree.get(index + 2)) > 0) {
			return;
		} else {
			if (tree.get(index + 1).compareTo(tree.get(index + 2)) > 0) {
				swap(index, index + 1);
				dequeueH(index + 1);
			} else {
				swap(index, index + 2);
				dequeueH(index + 2);
			}
		}
	}
	
	public T peek() {
		return tree.get(0);
	}

	private void swap(int i, int j) {
		T temp = tree.get(i);
		tree.set(i, tree.get(j));
		tree.set(j, temp);
	}

	public static void main(String[] args) {
		PriorityQueue<Integer> test = new PriorityQueue<>();
		test.enqueue(10);
		test.enqueue(6);
		test.enqueue(5);
		test.enqueue(3);
		test.enqueue(2);
		test.enqueue(1);
		test.enqueue(4);
		System.out.println(Arrays.toString(test.tree.toArray()));

		test.dequeue();
		System.out.println(Arrays.toString(test.tree.toArray()));
	}
}
