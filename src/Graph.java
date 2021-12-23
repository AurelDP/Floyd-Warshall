package TEST;

public class Graph {
	
	private int nbrStates;
	private Integer[][] adjacencyMatrix;
	
	public Graph(int nbrStates, Integer[][] adjacencyMatrix) {
		this.nbrStates = nbrStates;
		this.adjacencyMatrix = new Integer[nbrStates][nbrStates];
		this.adjacencyMatrix = (Integer[][]) adjacencyMatrix.clone();
	}
	
	public Graph(Graph graph) {
		this.nbrStates = graph.nbrStates;
		this.adjacencyMatrix = new Integer[this.nbrStates][this.nbrStates];
		this.adjacencyMatrix = (Integer[][]) graph.adjacencyMatrix.clone();;
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

	public Integer[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(Integer[][] AdjacencyMatrix) {
		this.adjacencyMatrix = (Integer[][]) AdjacencyMatrix.clone();
	}
	
	/*-----------------------------------------------------------------------------
	 * Methods
	 ----------------------------------------------------------------------------*/
	
	private static void printMatrix(Integer matrix[][], int u) {
		int n = matrix.length;
		
		if (u == 1)
			System.out.println("\nNew L Matrix (distances) :\n");
		else if (u == 2)
			System.out.println("\nNew P Matrix (preds) :\n");
		else 
			System.out.println("\nVotre graphe :\n");
		System.out.print("  | ");
		for (int i = 0; i < n; ++i) {
			System.out.print(i + " | ");
		}
		System.out.println();		
		for (int i = 0; i < n; ++i) {
			System.out.print(i + " |");
			for (int j = 0; j < n; ++j) {
				if (matrix[i][j] == null) {
					System.out.print(" X ");
				}
				else {
					switch(String.valueOf(matrix[i][j]).length()) {
					case 1:
						System.out.print(" " + matrix[i][j] + " ");
						break;
						
					case 2:
						System.out.print(matrix[i][j] + " ");
						break;
					
					default :
						System.out.print(matrix[i][j] );
						break;
					}					
				}
				System.out.print("|");
			}
			System.out.println();
		}
		
	}
	
	public static Integer[][] createPFromL(Integer dist[][]) {
		int n = dist.length;
		Integer[][] preds = new Integer[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int u = 0; u < n; u++) {
				if (dist[i][u] != null)
					preds[i][u] = i;
				else
					preds[i][u] = null;
			}
		}
		
		return preds;
	}
	
	public static Boolean isAbsorbent(Integer dist[][]) {
		for (int k = 0; k < dist.length; k++) {
			if (dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	public static Integer[][][] floydWarshall(Integer dist[][], Integer preds[][]) {		
		int n = dist.length;
		int i, j, k;
		
		System.out.println("Initialization :");
		printMatrix(dist, 1);
		printMatrix(preds, 2);
		
		System.out.println("\n" + "-".repeat(20));
		System.out.println("\nSteps :");
		System.out.println("\n" + "-".repeat(20));
		
		Integer[][][] resTabs = new Integer[2][n][n];
		
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					if ((dist[j][k] != null && dist[k][i] != null && dist[j][i] != null && dist[j][k] + dist[k][i] < dist[j][i]) || (dist[j][k] != null && dist[k][i] != null && dist[j][i] == null)) {
						dist[j][i] = dist[j][k] + dist[k][i];
						preds[j][i] = preds[k][i];
						printMatrix(dist, 1);
						printMatrix(preds, 2);
						System.out.println("\n" + "-".repeat(20));
					}
				}	
			}
		}
		
		resTabs[0] = dist;
		resTabs[1] = preds;
		
		return resTabs;
	}
		
	public static void displayBestRoutes(Integer dist[][], Integer preds[][]) {
		
		printMatrix(dist, 1);
		printMatrix(preds, 2);
				
		int matrixLength = dist.length;
		
		System.out.println("\nBest routes :");
		
		for (int row = 0; row < matrixLength; row++) {
			for (int col = 0; col < matrixLength; col++) {
				if (row == col)
					System.out.println("State " + row + " to " + col + " : No state change, distance = 0");
				else if (dist[row][col] == null)
					System.out.println("State " + row + " to " + col + " : No route between these states");
				else {
					int tempCol = col;
					
					String str = "" + tempCol;
					
					while (row != tempCol) {
						tempCol = preds[row][tempCol];
						str += "-" + tempCol;
					}
					
					StringBuilder sb = new StringBuilder(str);
					sb.reverse();
					str = sb.toString();
					
					System.out.println("State " + row + " to " + col + " : " + str + ", distance = " + dist[row][col]);
				}
			}
		}
	}
	
	public void print() {
		printMatrix(this.adjacencyMatrix, 3);
	}
	
}