import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class H7_Launcher {
	
	public static PrintStream ps = null;

	public static void main(String[] args) {
		int entry;
		Scanner sc;
		boolean run = true;
		
		ArrayList<String> namesTxtFiles = findNamesInFile(new File("./graphs"));

		if (namesTxtFiles.size() != 0) {
			
			do {
				
				/*-----------------------------------------------------------------------------
				 * Choix du graphe
				 ----------------------------------------------------------------------------*/
				
				System.out.print("\n\nChoisissez le graphe à traiter parmi la liste suivante :\n");
				
				for (int u = 1; u <= namesTxtFiles.size(); u++)
					System.out.println(u + " - " + namesTxtFiles.get(u - 1));
		
				do {
					System.out.print("\nVotre choix (entrez le numéro du graphe) : ");
					sc = new Scanner(System.in);
					entry = sc.nextInt();
				} while (entry <= 0 || entry > namesTxtFiles.size());
				

				/*-----------------------------------------------------------------------------
				 * Choix de la trace (logs)
				 ----------------------------------------------------------------------------*/
				String nameOutputFile = namesTxtFiles.get(entry - 1);
				nameOutputFile = nameOutputFile.replace(".txt", "");
				nameOutputFile =  "./traces/" + nameOutputFile + "-trace.txt";
				
				// On crée un nouveau txt traces et on supprime l'ancien
				File f = new File(nameOutputFile);
				try {
					if (!f.createNewFile()) {
						f.delete();
						f.createNewFile();
					}
				} catch (Exception e) {
		            System.err.println(e);
		        }
				
				// On crée un nouveau printer dans ce fichier txt
				try {
					ps = new PrintStream(nameOutputFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				/*-----------------------------------------------------------------------------
				 * Traitement du graphe
				 ----------------------------------------------------------------------------*/
				
				// System.out.print et System.out.println ont été remplacés par print et println dans le projet
				// afin que l'on puisse afficher en console et en même temps écrire dans le fichier trace
				println("Graphe choisi : " + namesTxtFiles.get(entry - 1) + "\n");
				
				// On récupère le graphe depuis le fichier choisi ainsi que la matrice d'adjacence
				H7_Reader testReader = new H7_Reader(entry);
				Integer[][] adjacencyMatrix = testReader.getMatrice();
				H7_Graph graphe = new H7_Graph(adjacencyMatrix);
				
				// On affiche notre graphe initial
				graphe.print();
				
				// On applique l'algorithme de Floyd Warshall
				Integer[][][] resTabs = H7_Graph.floydWarshall(adjacencyMatrix);
				Integer[][] newL = resTabs[0];
				Integer[][] newP = resTabs[1];
				
				// On vérifie les circuits absorbants et on affiche les chemins les plus courts selon le résultat
				if (!H7_Graph.isAbsorbent(newL)) {
					println("Pas de circuit absorbant");
					H7_Graph.displayBestRoutes(newL, newP);
				} else
					println("\nCircuit absorbant : pas de plus courts chemins\n\n");
								
				/*-----------------------------------------------------------------------------
				 * Choix de fin (quitter ou recommencer)
				 ----------------------------------------------------------------------------*/
				
				System.out.print("\n\nQue voulez-vous faire :\n"
						+ "1) Choisir un autre graphe\n"
						+ "2) Quitter le programme\n");
				do {
					System.out.print("\nVotre choix (entrez le numéro du choix) : ");
					sc = new Scanner(System.in);
					entry = sc.nextInt();
				} while (entry < 1 || entry > 2);
				
				if (entry == 2)
					run = false;				
			
			} while (run);
			
			sc.close();
			
		} else {
			System.out.println("Le dossier des graphes est vide !");
		}
	}
			
	/*-----------------------------------------------------------------------------
	 * Méthodes
	 ----------------------------------------------------------------------------*/
	
	public static void print(String s) {
		PrintStream orig = System.out;
		System.setOut(ps);
		System.out.print(s);
		System.setOut(orig);
		System.out.print(s);
	}
	
	public static void println(String s) {
		PrintStream orig = System.out;
		System.setOut(ps);
		System.out.println(s);
		System.setOut(orig);
		System.out.println(s);
	}
		
	private static ArrayList<String> findNamesInFile(File file) {
		File[] files = file.listFiles();
		
		ArrayList<String> names = new ArrayList<String>();

		if (files != null) {
			for (File f : files) {
				if (f.isFile()) {
					names.add(f.getName());
				}
			}
		}

		names.sort((s1, s2) -> compare(s1, s2));
		return names;
	}

	private static int compare(String s1, String s2) {
		// Si s1 est plus court que s2, s1 est décallé avant s2
		if (s1.length() < s2.length())
			return -1;
		// Si s1 est plus long que s2, s1 est décallé après s2
		if (s1.length() > s2.length())
			return 1;
		// S1 s1 et s2 sont de même longueur, on tri par ordre alphabétique
		if(s1.compareTo(s2) < 0)
			return -1;
		if(s1.compareTo(s2) > 0)
			return 1;
		return 0;
	}

}



