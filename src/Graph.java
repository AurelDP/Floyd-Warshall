public class Graph {
	
	private int nbrStates;
	private Double[][] adjacencyMatrix;
	
	public Graph(int nbrStates, Double[][] adjacencyMatrix) {
		this.nbrStates = nbrStates;
		this.adjacencyMatrix = new Double[nbrStates][nbrStates];
		this.adjacencyMatrix = (Double[][]) adjacencyMatrix.clone();
	}
	
	public Graph(Graph graph) {
		this.nbrStates = graph.nbrStates;
		this.adjacencyMatrix = new Double[this.nbrStates][this.nbrStates];
		this.adjacencyMatrix = (Double[][]) graph.adjacencyMatrix.clone();;
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

	public Double[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(Double[][] AdjacencyMatrix) {
		this.adjacencyMatrix = (Double[][]) AdjacencyMatrix.clone();
	}
	
	/*-----------------------------------------------------------------------------
	 * Methods
	 ----------------------------------------------------------------------------*/
	
	private static void printMatrix(Double matrix[][], int u) {
		int n = matrix.length;
		
		if (u == 1)
			System.out.println("\nNew L Matrix (distances) :\n");
		else
			System.out.println("\nNew P Matrix (preds) :\n");
		System.out.print("    ");
		
		for (int i = 0; i < n; ++i) {
			System.out.print(i + "  ");
		}
		System.out.println();
		
		for (int i = 0; i < n; ++i) {
			System.out.print(i + "   ");
			for (int j = 0; j < n; ++j)
				if (matrix[i][j] == 0.1)
					System.out.print("X  ");
				else
					System.out.print(matrix[i][j].intValue() + "  ");
			System.out.println();
		}
		
	}
	
	public static Double[][] createPFromL(Double dist[][]) {
		int n = dist.length;
		Double[][] preds = new Double[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int u = 0; u < n; u++) {
				if (dist[i][u] != 0.1)
					preds[i][u] = i + 0.0;
				else
					preds[i][u] = 0.1;
			}
		}
		
		return preds;
	}
	
	public static Boolean isAbsorbent(Double dist[][]) {
		for (int k = 0; k < dist.length; k++) {
			if (dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	public static Double[][][] floydWarshall(Double dist[][], Double preds[][]) {		
		int n = dist.length;
		int i, j, k;
		
		System.out.println("Initialization :");
		printMatrix(dist, 1);
		printMatrix(preds, 2);
		
		System.out.println("\n" + "-".repeat(20));
		System.out.println("\nSteps :");
		System.out.println("\n" + "-".repeat(20));
		
		Double[][][] resTabs = new Double[2][n][n];
		
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					if ((dist[j][k] + dist[k][i] < dist[j][i] && dist[j][k] != 0.1 && dist[k][i] != 0.1 && dist[j][i] != 0.1) || (dist[j][k] != 0.1 && dist[k][i] != 0.1 && dist[j][i] == 0.1)) {
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
		
	public static void displayBestRoutes(Double dist[][], Double preds[][]) {
		
		printMatrix(dist, 1);
		printMatrix(preds, 2);
				
		int matrixLength = dist.length;
		
		System.out.println("\nBest routes :");
		
		for (int row = 0; row < matrixLength; row++) {
			for (int col = 0; col < matrixLength; col++) {
				if (row == col)
					System.out.println("State " + row + " to " + col + " : No state change, distance = 0");
				else if (dist[row][col] == 0.1)
					System.out.println("State " + row + " to " + col + " : No route between these states");
				else {
					int tempCol = col;
					
					String str = "" + tempCol;
					
					while (row != tempCol) {
						tempCol = preds[row][tempCol].intValue();
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
	
}
