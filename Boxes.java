import java.util.ArrayList;
import java.util.Scanner;

public class Boxes {
	public static int[] Table;
	public static Scanner Scan;
	public static ArrayList<Node> list;

	static class Graph {
		Graph(int boxes) {
			for (int i = 0; i < boxes; i++) {
				int value1 = Scan.nextInt();
				int value2 = Scan.nextInt();
				int value3 = Scan.nextInt();
				if (value1 <= value2 && value1 <= value3) {
					if (value2 <= value3) {
						int[] array = { value1, value2, value3 };
						Node node = new Node(array);
						list.add(node);
					} else {
						int[] array = { value1, value3, value2 };
						Node node = new Node(array);
						list.add(node);
					}
				} else if (value2 <= value1 && value2 <= value3) {
					if (value1 <= value3) {
						int[] array = { value2, value1, value3 };
						Node node = new Node(array);
						list.add(node);
					} else {
						int[] array = { value2, value3, value1 };
						Node node = new Node(array);
						list.add(node);
					}
				} else {
					if (value1 <= value2) {
						int[] array = { value3, value1, value2 };
						Node node = new Node(array);
						list.add(node);
					} else {
						int[] array = { value3, value2, value1 };
						Node node = new Node(array);
						list.add(node);
					}
				}
			}
		}
	}

	static class Node {
		private int[] dimensions;
		private ArrayList<Integer> direction;

		Node(int[] size) {
			this.dimensions = size;
			this.direction = new ArrayList<Integer>();
		}

		public void addDirection(int next) {
			this.direction.add(next);
		}
	}

	static class DiGraph {
		DiGraph(Graph graph, int boxes) {
			for (int i = 0; i < boxes; i++) {
				Node current = list.get(i);
				for (int j = 0; j < i; j++) {
					Node comparison = list.get(j);
					if (greaterThan(current, comparison)) {
						current.addDirection(j);
					}
					if (greaterThan(comparison, current)) {
						comparison.addDirection(i);
					}
				}
			}
		}
	}

	public static boolean greaterThan(Node first, Node second) {
		return ((first.dimensions[0] < second.dimensions[0]) && (first.dimensions[1] < second.dimensions[1])
				&& (first.dimensions[2] < second.dimensions[2]));
	}

	public static void search(Node now) {
		Table[list.indexOf(now)] = 1;
		ArrayList<Integer> edge = now.direction;
		for (int j = 0; j < edge.size(); j++) {
			int value = edge.get(j);
			if (Table[value] == 0) {
				search(list.get(value));
			}
			Table[list.indexOf(now)] = Math.max(Table[list.indexOf(now)], Table[value] + 1);
		}
	}

	public static void main(String[] args) {
		Scan = new Scanner(System.in);
		int Cases = 1;
		while (true) {
			int boxes = Scan.nextInt();
			if (boxes == -1) {
				break;
			}
			list = new ArrayList<Node>();
			Graph graph = new Graph(boxes);
			@SuppressWarnings("unused")
			DiGraph diGraph = new DiGraph(graph, boxes);
			Table = new int[boxes];
			for (int i = 0; i < boxes; i++) {
				if (Table[i] == 0) {
					search(list.get(i));
				}
			}
			int max = 0;
			for (int i = 0; i < boxes; i++) {
				max = Math.max(max, Table[i]);
			}
			if (max == 1) {
				System.out.println("Case " + Cases + ": 1 box");
			} else {
				System.out.println("Case " + Cases + ": " + max + " boxes");
			}
			System.out.println();
			Cases = Cases + 1;
		}
	}
}