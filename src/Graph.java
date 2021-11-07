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
	
	
	
}
