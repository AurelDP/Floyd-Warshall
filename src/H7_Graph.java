public class H7_Graph {

	private final Integer[][] adjacencyMatrix;
	
	/*-----------------------------------------------------------------------------
	 * Constructeurs
	 ----------------------------------------------------------------------------*/
	
	public H7_Graph(Integer[][] adjacencyMatrix) {
		this.adjacencyMatrix = copy(adjacencyMatrix);
	}

	/*-----------------------------------------------------------------------------
	 * Méthodes
	 ----------------------------------------------------------------------------*/
	
	private static void printMatrix(Integer[][] matrix, int u) {
		int n = matrix.length;
		
		if (u == 1)
			H7_Launcher.println("\nNouvelle matrice L (distances) :\n");
		else if (u == 2)
			H7_Launcher.println("\nNouvelle matrice P (prédécesseurs) :\n");
		else {
			H7_Launcher.println("\n" + "-".repeat(40));
			H7_Launcher.println("\tGraphe choisi :");
			H7_Launcher.println("-".repeat(40) + "\n");
		}
		
		H7_Launcher.print("   |  ");
		for (int i = 0; i < n; ++i) {
			H7_Launcher.print(i + "     ");
		}
		H7_Launcher.println("\n----" + "------".repeat(n));
		
		for (int i = 0; i < n; ++i) {
			H7_Launcher.print(i + "  | ");
			for (int j = 0; j < n; ++j) {
				if (matrix[i][j] == null)
					H7_Launcher.print(" X ");
				else {
					switch (String.valueOf(matrix[i][j]).length()) {
						case 1 -> H7_Launcher.print(" " + matrix[i][j] + " ");
						case 2 -> H7_Launcher.print(" " + matrix[i][j]);
						default -> H7_Launcher.print("" + matrix[i][j]);
					}
				}
				H7_Launcher.print("   ");
			}
			H7_Launcher.println("");
		}
		
	}
	
	private static Integer[][] createPFromL(Integer[][] dist) {
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
	
	public static Boolean isAbsorbent(Integer[][] dist) {
		for (int k = 0; k < dist.length; k++) {
			if (dist[k][k] == null || dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	public static Integer[][][] floydWarshall(Integer[][] dist) {
		int n = dist.length;
		int i, j, k;
		
		Integer[][] preds = createPFromL(dist);
		
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tInitialisation :");
		H7_Launcher.println("-".repeat(40));
		printMatrix(dist, 1);
		printMatrix(preds, 2);
		
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tEtapes Floyd Warshall :");
		H7_Launcher.println("-".repeat(40));
		
		Integer[][][] resTabs = new Integer[2][n][n];
		
		floydWarshallLoop:
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					if ((dist[j][k] != null && dist[k][i] != null && dist[j][i] != null && dist[j][k] + dist[k][i] < dist[j][i]) || (dist[j][k] != null && dist[k][i] != null && dist[j][i] == null)) {
						dist[j][i] = dist[j][k] + dist[k][i];
						preds[j][i] = preds[k][i];
						printMatrix(dist, 1);
						printMatrix(preds, 2);
						if (isAbsorbent(dist)) {
							H7_Launcher.println("\nPrésence d'un circuit absorbant, arrêt de l'algorithme");
							break floydWarshallLoop;
						}
						H7_Launcher.println("\n");
					}
				}	
			}
		}
		
		resTabs[0] = dist;
		resTabs[1] = preds;
		
		return resTabs;
	}
		
	public static void displayBestRoutes(Integer[][] dist, Integer[][] preds) {
		
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tRésultat :");
		H7_Launcher.println("-".repeat(40));
		printMatrix(dist, 1);
		printMatrix(preds, 2);
				
		int matrixLength = dist.length;
		
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tPlus courts chemins :");
		H7_Launcher.println("-".repeat(40) + "\n");
		
		for (int row = 0; row < matrixLength; row++) {
			for (int col = 0; col < matrixLength; col++) {
				if (dist[row][col] == null)
					H7_Launcher.println("De " + row + " à " + col + " : Aucun chemin existant");
				else {
					int tempCol = col;
					// On ajoute une condition start pour que les boucles sur le même état soient prises en compte
					boolean start = true;
					
					String str = "" + tempCol;
					
					while (row != tempCol || start) {
						tempCol = preds[row][tempCol];
						str += "-" + tempCol;
						start = false;
					}
					
					StringBuilder sb = new StringBuilder(str);
					sb.reverse();
					str = sb.toString();
					
					H7_Launcher.println("De " + row + " à " + col + " : " + str + ", distance = " + dist[row][col]);
				}
			}
		}
	}
	
	public void print() {
		printMatrix(this.adjacencyMatrix, 3);
	}
	
	
	private Integer[][] copy(Integer[][] matrice) {
		int n = matrice.length;
		Integer[][] res = new Integer[n][n];
				
		for (int i = 0; i < n; i++) {
			for (int u = 0; u < n; u++) {
				res[i][u] = matrice[i][u];
			}
		}
		
		return res;
	}
	
}
