public class H7_Graph {

	private final Integer[][] adjacencyMatrix;
	
	/*-----------------------------------------------------------------------------
	 * Constructeurs
	 ----------------------------------------------------------------------------*/
	
	public H7_Graph(Integer[][] adjacencyMatrix) {
		this.adjacencyMatrix = copy(adjacencyMatrix);
	}

	/*-----------------------------------------------------------------------------
	 * M�thodes
	 ----------------------------------------------------------------------------*/
	
	// Cette m�thode est utilis�e pour afficher les matrices dans la console et dans la trace
	private static void printMatrix(Integer[][] matrix, int u) {
		int n = matrix.length;
		
		// u est une variable qui permet de choisir la matrice � afficher
		if (u == 1)
			H7_Launcher.println("\nNouvelle matrice L (distances) :\n");
		else if (u == 2)
			H7_Launcher.println("\nNouvelle matrice P (pr�d�cesseurs) :\n");
		else {
			H7_Launcher.println("\n" + "-".repeat(40));
			H7_Launcher.println("\tGraphe choisi :");
			H7_Launcher.println("-".repeat(40) + "\n");
		}
		
		// On affiche dans un premier temps la premi�re ligne et le s�parateur horizontal
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
					// pr�sents dans la matrice
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
	
	// M�thode permettant de cr�er la matrice des pr�d�cesseurs depuis la matrice des distances
	private static Integer[][] createPFromL(Integer[][] dist) {
		int n = dist.length;
		// On cr�e une nouvelle matrice de m�me taille que celle des distances
		Integer[][] preds = new Integer[n][n];
		
		// On v�rifie les cases de la matrice des distances n'�tant pas null et on initialise
		// ces m�mes cases dans la matrice des pr�d�cesseurs � la valeur de la ligne
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
	
	// M�thode renvoyant vrai s'il y a un circuit absorbant dans la matrice, faux sinon
	public static Boolean isAbsorbent(Integer[][] dist) {
		// Si il y a une valeur nulle ou n�gative dans la diagonale de la matrice, alors il y a un circuit absorbant
		for (int k = 0; k < dist.length; k++) {
			if (dist[k][k] == null || dist[k][k] < 0)
				return true;
		}
		return false;
	}
	
	// M�thode permettant d'effectuer l'algorithme de Floyd Warshall
	public static Integer[][][] floydWarshall(Integer[][] dist) {
		int n = dist.length;
		int i, j, k;
		
		// On cr�e la matrice des pr�d�cesseurs
		Integer[][] preds = createPFromL(dist);
		
		// On affiche l'�tat des matrices avant modification (initialisation)
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tInitialisation :");
		H7_Launcher.println("-".repeat(40));
		printMatrix(dist, 1);
		printMatrix(preds, 2);
		
		// On affiche un s�p�rateur indiquant le d�but de l'aglorithme Floyd Warshall
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tEtapes Floyd Warshall :");
		H7_Launcher.println("-".repeat(40));
		
		// On cr�e une matrice 3D resTabs qui stockera la matrice des distances et la matrice des pr�d�cesseurs
		// afin de pouvoir retourner le tout en fin d'algorithme
		Integer[][][] resTabs = new Integer[2][n][n];
		
		// On cr�e la r�f�rence "floydWarshallLoop" afin de pouvoir arr�ter l'algorithme si un circuit absorbant est d�tect�
		floydWarshallLoop:
		for (k = 0; k < n; k++) {
			for (j = 0; j < n; j++) {
				for (i = 0; i < n; i++) {
					// On parcourt la matrice des distances et on v�rifie deux conditions :
					// Si, pour un chemin direct de A vers C il existe un chemin plus court passant par B, alors on change le pr�d�cesseur correspondant et on modifie la distance
					// Ou si aucun chemin direct n'existe entre A et C mais qu'il en existe un passant par B, on applique �galement les modifications
					if ((dist[j][k] != null && dist[k][i] != null && dist[j][i] != null && dist[j][k] + dist[k][i] < dist[j][i]) || (dist[j][k] != null && dist[k][i] != null && dist[j][i] == null)) {
						dist[j][i] = dist[j][k] + dist[k][i];
						preds[j][i] = preds[k][i];
						printMatrix(dist, 1);
						printMatrix(preds, 2);
						// Si un circuit absorbant est d�tect� durant l'algorithme, celui-ci s'arr�te
						if (isAbsorbent(dist)) {
							H7_Launcher.println("\nPr�sence d'un circuit absorbant, arr�t de l'algorithme");
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
	
	// M�thode permettant d'afficher les plus courts chemins
	public static void displayBestRoutes(Integer[][] dist, Integer[][] preds) {
		
		// Affichage des r�sultats de l'algorithme Floyd Warshall
		H7_Launcher.println("\n\n\n" + "-".repeat(40));
		H7_Launcher.println("\tR�sultat :");
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
				// Si une valeur est nulle, aucun chemin n'a �t� trouv� entre l'�tat A (ligne) et l'�tat B (colonne)
				if (dist[row][col] == null)
					H7_Launcher.println("De " + row + " � " + col + " : Aucun chemin existant");
				// Sinon, on r�cup�re le chemin en parcourant la matrice des pr�d�cesseurs
				else {
					// On cr�e un index de colonne temporaire (�tat B temporaire) que l'on initialise � la valeur de l'�tat B initial
					int tempCol = col;
					
					// On ajoute une condition start pour que les boucles sur le m�me �tat soient prises en compte
					boolean start = true;
					
					String str = "" + tempCol;
					
					// On ne s'arr�te pas tant que l'�tat A (ligne) et l'�tat B (colonne) ne sont pas �gaux
					while (row != tempCol || start) {
						
						// L'�tat B temporaire (colonne temporaire) prend la valeur de son pr�d�cesseurs dans la matrice des pr�decesseurs
						// et on l'ajoute � notre cha�ne de caract�res correspondant au chemin
						tempCol = preds[row][tempCol];
						str += "-" + tempCol;
						start = false;
					}
					
					// A la fin, on obtient un chemin de B vers A que l'on inverse
					StringBuilder sb = new StringBuilder(str);
					sb.reverse();
					str = sb.toString();
					
					// Finalement on affiche ce chemin et la distance correspondante dans la matrice des distances
					H7_Launcher.println("De " + row + " � " + col + " : " + str + ", distance = " + dist[row][col]);
				}
			}
		}
	}
	
	public void print() {
		printMatrix(this.adjacencyMatrix, 3);
	}
	
	// M�thode permettant de copier une matrice
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
