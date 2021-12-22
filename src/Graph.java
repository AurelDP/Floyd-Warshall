import java.util.ArrayList;

public class Graph {
	private int nbrStates;
	private ArrayList<ArrayList<Integer>> adjacencyMatrix;
	
	public Graph(int nbrStates, int nbrTransitions, ArrayList<ArrayList<Integer>> adjacencyMatrix) {
		this.nbrStates = nbrStates;
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	public Graph(Graph graph) {
		this.nbrStates = graph.nbrStates;
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

	public ArrayList<ArrayList<Integer>> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(ArrayList<ArrayList<Integer>> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	/*-----------------------------------------------------------------------------
	 * Methods
	 ----------------------------------------------------------------------------*/
	
	private static void printMatrix(int dist[][]) {
		int n = dist.length;
		System.out.println("New Matrix :\n");
		System.out.print("    ");
		for (int i = 0; i < n; ++i) {
			System.out.println(i + "  ");
		}
		for (int i = 0; i < n; ++i) {
			System.out.println(i + "   ");
			for (int j = 0; j < n; ++j)
				System.out.print(dist[i][j] + "  ");
			System.out.println();
		}
	}
	
	private static Boolean isAbsorbent(int dist[][]) {
		for (int k = 0; k < dist.length; k++) {
			if (dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	private static void floydWarshall(int tab[][]) {
		int n = tab.length;
		int[][] dist = new int[n][n];
		int i, j, k;
		
		dist = tab;
		
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}	
			}	
		}
		printMatrix(dist);
	}
	
}
