import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Launcher {
	
	public static PrintStream ps = null;

	public static void main(String[] args) {
		int entry;
		Scanner sc = null;
		boolean run = true;
		
		// We get the name of all the files contained in the "graphs" folder
		ArrayList<String> namesTxtFiles = findNamesInFile(new File("./graphs"));
		
		if (namesTxtFiles.size() != 0) {
			
			do {
		
				System.out.print("\n\nChoisissez le graphe à traiter parmi la liste suivante :\n");
				
				// We display all the file names and ask the user to choose a graph from the list
				for (int u = 1; u <= namesTxtFiles.size(); u++)
					System.out.println(u + " - " + namesTxtFiles.get(u - 1));
		
				do {
					System.out.print("\nVotre choix (entrez le numéro de du graphe) : ");
					sc = new Scanner(System.in);
					entry = sc.nextInt();
				} while (entry <= 0 || entry > namesTxtFiles.size());
				
				// We create a new nameOutputFile to create our txt trace output
				String nameOutputFile = namesTxtFiles.get(entry - 1);
				nameOutputFile = nameOutputFile.replace(".txt", "");
				nameOutputFile =  "./traces/" + nameOutputFile + "-trace.txt";
				
				// We create the new txt (and we delete the older if there is an older)
				File f = new File(nameOutputFile);
				try {
					if (!f.createNewFile()) {
						f.delete();
						f.createNewFile();
					}
				} catch (Exception e) {
		            System.err.println(e);
		        }
				
				// We create a new printer into the output file
				try {
					ps = new PrintStream(nameOutputFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				// System.out.print and System.out.println are replaced (in the entire project) by print and println
				// which are static public methods, to allow us to print in the console and in the file
				// in the same time (which is impossible by default with System.out)
				println("Graphe choisi : " + namesTxtFiles.get(entry - 1) + "\n");
				
				// We recover the chosen graph and we write it in the program memory
				Graph G = getGraphFromFile(namesTxtFiles.get(entry - 1));
				
				
				
				
				
				
				// RESTE ICI
				
				
				
				
				
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
	 * Printers methods (allow to print in console and trace file)
	 ----------------------------------------------------------------------------*/
	
	// Methods print and println are used to print in file AND in console
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
	
	/*-----------------------------------------------------------------------------
	 * Next methods are used to get Graph information from file
	 ----------------------------------------------------------------------------*/
	
	private static ArrayList<String> findNamesInFile(File file) {
		// We get a list of all the files contained in the folder
		File[] files = file.listFiles();
		ArrayList<String> names = new ArrayList<String>();
		// If the file is a file, then we add it to a list of names
		for (File f : files) {
			if (f.isFile()) {
				names.add(f.getName());
			}
		}
		return names;
	}
	
	private static Integer[][] arrayListToArray2D(ArrayList<ArrayList<Integer>> arrayList) {
		Integer[][] array = new Integer[arrayList.size()][];
		
		for (int i = 0; i < arrayList.size(); i++) {
			array[i] = arrayList.get(i).toArray(new Integer[arrayList.size()]);
		}
		
		return array;
	}
	
	private static Graph getGraphFromFile(final String nom) {

		int nbrStates = 0;
		
		ArrayList<ArrayList<Integer>> temporaryAdjacencyMatrix = new ArrayList<ArrayList<Integer>>();
		
		try {
			// We retrieve the contents of the file chosen by the user
			FileInputStream file = new FileInputStream("./graphs/" + nom);
			Scanner scanner = new Scanner(file);
			
			int i = 1;
			while (scanner.hasNextLine()) {
				
				Scanner lineScanner = new Scanner(scanner.nextLine());
				
				switch (i) {
					
					// First line
					case 1:
						nbrStates = lineScanner.nextInt();
						break;
										
					// Finally, we recover all the transitions
					default:
						String line = lineScanner.next();
						// Line by line, we divide the content of the line using REGEX rules
						String[] part = line.split(" ");
						// The first part of the split line is converted to an int for the name of the pre-transition state
						int initial = Integer.parseInt(part[0]);
						// The second part corresponds to the arrival state
						int arrival = Integer.parseInt(part[1]);
						// The third part is the value of the transition
						int transition = Integer.parseInt(part[2]);
						
						
						
						
						
						// Mettre dans matrice d'adjacence ï¿½ la ligne d'indice 'initial' les valeurs obtenues
						// 'Transition' sera le contenu de la case de la matrice, et 'Arrival' sera la colonne ï¿½ remplir par ce contenu
						
						
						
						
						
						break;
				
				}
				
				i ++;
				
				lineScanner.close();
				
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Integer[][] adjacencyMatrix = arrayListToArray2D(temporaryAdjacencyMatrix);
		
		// With the variables filled in, all that remains is to call the Graph constructor
		return new Graph(nbrStates, adjacencyMatrix);
	}

}



