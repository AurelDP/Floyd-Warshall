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
	
	// Cette méthode est utilisée pour afficher les matrices dans la console et dans la trace
	private static void printMatrix(Integer[][] matrix, int u) {
		int n = matrix.length;
		
		// u est une variable qui permet de choisir la matrice à afficher
		if (u == 1)
			H7_Launcher.println("\nNouvelle matrice L (distances) :\n");
		else if (u == 2)
			H7_Launcher.println("\nNouvelle matrice P (prédécesseurs) :\n");
		else {
			H7_Launcher.println("\n" + "-".repeat(40));
			H7_Launcher.println("\tGraphe choisi :");
			H7_Launcher.println("-".repeat(40) + "\n");
		}
		
		// On affiche dans un premier temps la première ligne et le séparateur horizontal
		H7_Launcher.print("   |  ");
		for (int i = 0; i < n; ++i) {
			H7_Launcher.print(i + "     ");
		}
		H7_Launcher.println("\n----" + "------".repeat(n));
		
		// Ensuite on affiche toutes les autres lignes et les valeurs correspondantes dans la matrice
		for (int i = 0; i < n; ++i) {
			H7_Launcher.print(i + "  | ");
			for (int j = 0; j < n; ++j) {
				if (matrix[i][j] == null)
					H7_Launcher.print(" X ");
				else {
					// Le switch ci-dessous permet d'adapter la largeur des colonnes selon la taille des nombres
					// présents dans la matrice
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
	
	// Méthode permettant de créer la matrice des prédécesseurs depuis la matrice des distances
	private static Integer[][] createPFromL(Integer[][] dist) {
		int n = dist.length;
		// On crée une nouvelle matrice de même taille que celle des distances
		Integer[][] preds = new Integer[n][n];
		
		// On vérifie les cases de la matrice des distances n'étant pas null et on initialise
		// ces mêmes cases dans la matrice des prédécesseurs à la valeur de la ligne
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
	
	// Méthode renvoyant vrai s'il y a un circuit absorbant dans la matrice, faux sinon
	public static Boolean isAbsorbent(Integer[][] dist) {
		// Si il y a une valeur nulle ou négative dans la diagonale de la matrice, alors il y a un circuit absorbant
		for (int k = 0; k < dist.length; k++) {
			if (dist[k][k] == null || dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	// Méthode permettant d'effectuer l'algorithme de Floyd Warshall
	public static Integer[][][] floydWarshall(Integer[][] dist) {
		int n = dist.length;
		int i, j, k;
		
		// On crée la matrice des prédécesseurs
		Integer[][] preds = createPFromL(dist);
		
		// On affiche l'état des matrices avant modification (initialisation)
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tInitialisation :");
		H7_Launcher.println("-".repeat(40));
		printMatrix(dist, 1);
		printMatrix(preds, 2);
		
		// On affiche un sépérateur indiquant le début de l'aglorithme Floyd Warshall
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tEtapes Floyd Warshall :");
		H7_Launcher.println("-".repeat(40));
		
		// On crée une matrice 3D resTabs qui stockera la matrice des distances et la matrice des prédécesseurs
		// afin de pouvoir retourner le tout en fin d'algorithme
		Integer[][][] resTabs = new Integer[2][n][n];
		
		// On crée la référence "floydWarshallLoop" afin de pouvoir arrêter l'algorithme si un circuit absorbant est détecté
		floydWarshallLoop:
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					// On parcourt la matrice des distances et on vérifie deux conditions :
					// Si, pour un chemin direct de A vers C il existe un chemin plus court passant par B, alors on change le prédécesseur correspondant et on modifie la distance
					// Ou si aucun chemin direct n'existe entre A et C mais qu'il en existe un passant par B, on applique également les modifications
					if ((dist[j][k] != null && dist[k][i] != null && dist[j][i] != null && dist[j][k] + dist[k][i] < dist[j][i]) || (dist[j][k] != null && dist[k][i] != null && dist[j][i] == null)) {
						dist[j][i] = dist[j][k] + dist[k][i];
						preds[j][i] = preds[k][i];
						printMatrix(dist, 1);
						printMatrix(preds, 2);
						// Si un circuit absorbant est détecté durant l'algorithme, celui-ci s'arrête
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
	
	// Méthode permettant d'afficher les plus courts chemins
	public static void displayBestRoutes(Integer[][] dist, Integer[][] preds) {
		
		// Affichage des résultats de l'algorithme Floyd Warshall
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tRésultat :");
		H7_Launcher.println("-".repeat(40));
		printMatrix(dist, 1);
		printMatrix(preds, 2);
		
		int matrixLength = dist.length;
		
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tPlus courts chemins :");
		H7_Launcher.println("-".repeat(40) + "\n");
		
		// On parcourt la matrice des distances
		for (int row = 0; row < matrixLength; row++) {
			for (int col = 0; col < matrixLength; col++) {
				// Si une valeur est nulle, aucun chemin n'a été trouvé entre l'état A (ligne) et l'état B (colonne)
				if (dist[row][col] == null)
					H7_Launcher.println("De " + row + " à " + col + " : Aucun chemin existant");
				// Sinon, on récupère le chemin en parcourant la matrice des prédécesseurs
				else {
					// On crée un index de colonne temporaire (état B temporaire) que l'on initialise à la valeur de l'état B initial
					int tempCol = col;
					
					// On ajoute une condition start pour que les boucles sur le même état soient prises en compte
					boolean start = true;
					
					String str = "" + tempCol;
					
					// On ne s'arrête pas tant que l'état A (ligne) et l'état B (colonne) ne sont pas égaux
					while (row != tempCol || start) {
						
						// L'état B temporaire (colonne temporaire) prend la valeur de son prédécesseurs dans la matrice des prédecesseurs
						// et on l'ajoute à notre chaîne de caractères correspondant au chemin
						tempCol = preds[row][tempCol];
						str += "-" + tempCol;
						start = false;
					}
					
					// A la fin, on obtient un chemin de B vers A que l'on inverse
					StringBuilder sb = new StringBuilder(str);
					sb.reverse();
					str = sb.toString();
					
					// Finalement on affiche ce chemin et la distance correspondante dans la matrice des distances
					H7_Launcher.println("De " + row + " à " + col + " : " + str + ", distance = " + dist[row][col]);
				}
			}
		}
	}
	
	public void print() {
		printMatrix(this.adjacencyMatrix, 3);
	}
	
	// Méthode permettant de copier une matrice
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
