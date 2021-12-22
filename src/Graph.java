package projet_floyd;

public class Graph {
	
	private int nbrStates;
	private int[][] adjacencyMatrix;
	
	public Graph(int nbrStates, int[][] adjacencyMatrix) {
		this.nbrStates = nbrStates;
		this.adjacencyMatrix = new int[nbrStates][nbrStates];
		this.adjacencyMatrix = (int[][]) adjacencyMatrix.clone();
	}
	
	public Graph(Graph graph) {
		this.nbrStates = graph.nbrStates;
		this.adjacencyMatrix = new int[this.nbrStates][this.nbrStates];
		this.adjacencyMatrix = (int[][]) graph.adjacencyMatrix.clone();;
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

	public int[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(int[][] AdjacencyMatrix) {
		this.adjacencyMatrix = (int[][]) AdjacencyMatrix.clone();
	}
	
	/*-----------------------------------------------------------------------------
	 * methods
	 ----------------------------------------------------------------------------*/
	
	
	
	
}
