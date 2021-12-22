import java.util.ArrayList;

public class Graph {
	private int nbrStates;
	private int nbrTransitions;
	private ArrayList<ArrayList<Integer>> adjacencyMatrix;
	
	public Graph(int nbrStates, int nbrTransitions, ArrayList<ArrayList<Integer>> adjacencyMatrix) {
		this.nbrStates = nbrStates;
		this.nbrTransitions = nbrTransitions;
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	public Graph(Graph graph) {
		this.nbrStates = graph.nbrStates;
		this.nbrTransitions = graph.nbrTransitions;
		this.adjacencyMatrix = new ArrayList<ArrayList<Integer>>();
		int u = 0;
		for (ArrayList<Integer> aI : graph.adjacencyMatrix) {
			this.adjacencyMatrix.add(new ArrayList<Integer>());
			for (Integer i : aI) {
				this.adjacencyMatrix.get(u).add(i);
			}
			u ++;
		}
	}
	
	/*-----------------------------------------------------------------------------
	 * Getters & Setters
	 ----------------------------------------------------------------------------*/

	public int getNbrStates() {
		return nbrStates;
	}

	public void setNbrStates(int nbrStates) {
		this.nbrStates = nbrStates;
	}

	public int getNbrTransitions() {
		return nbrTransitions;
	}

	public void setNbrTransitions(int nbrTransitions) {
		this.nbrTransitions = nbrTransitions;
	}

	public ArrayList<ArrayList<Integer>> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(ArrayList<ArrayList<Integer>> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	public static void printMatrix(int dist[][]) {
		System.out.println("New Matrix: ");
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (dist[i][j] == I)
					System.out.print("I   \t");
				else
					System.out.print(dist[i][j] + "   \t");
			}
			System.out.println();
		}
	}

	private static Booleen isAbsorbent(int dist[][]) {
		for (int k = 0, k < dist.lenght, k++) {
			if (dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	private static void FloydWarshall(int tab[][]) {
		int n = tab.lenght;
		int[][] dist = new int[n][n];
		int i, j, k;
	
		dist = tab;
	
		// Floyd-Warshall Algorithm
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					// If k is on the shortest path from
					// i to j, then update the value of dist[i][j]
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}	
			}	
		}
		printMatrix(dist);
	}
	
}
