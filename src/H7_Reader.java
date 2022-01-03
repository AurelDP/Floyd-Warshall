import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class H7_Reader {
	
	private int graphNumber;
	
	/*-----------------------------------------------------------------------------
	 * Constructeurs
	 ----------------------------------------------------------------------------*/
	
	public H7_Reader(int graphNumber) {
		if (graphNumber > 13 || graphNumber < 0)
			System.out.println("Erreur : Graph inexistant");
		else
			this.graphNumber = graphNumber;
	}

	/*-----------------------------------------------------------------------------
	 * M�thodes
	 ----------------------------------------------------------------------------*/
	
	public Integer[][] getMatrice() {
		
		// On essaye d'ouvrir le fichier s�lectionn� dans le launcher
		try {
			// On initialise les variables qui nous permettront de lire le contenu
			File thisFile = new File("graphs/H7_G"+ String.valueOf(graphNumber) +".txt");			
			FileReader fr = new FileReader(thisFile.getAbsolutePath());
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			// On r�cup�re la premi�re ligne du fichier qui correspond au nombre d'�tats
			int stateNumber = Integer.parseInt(br.readLine());
			
			// On cr�e une matrice temporaire de la taille du nombre d'�tats
			Integer[][] temporaryMatrice = new Integer[stateNumber][stateNumber];
			int i = 0;
			
			// On parcourt chaque ligne
			while ((line = br.readLine()) != null) {
				
				// On d�compose chaque ligne dans un tableau part
				String[] part = line.split(" ");
				
				// Pour chaque �l�ment de part, s'il n'est pas �gal � la lettre "X", on l'ajoute dans la matrice temporaire
				// sinon on applique la valeur nulle
		        for (int j = 0; j < stateNumber; j++) {
		        	if (!part[j].equals("X"))
		        		temporaryMatrice[i][j] = Integer.parseInt(part[j]);
		        	else
		        		temporaryMatrice[i][j] = null;
		    	}
		        
		        i++;
		    }

			fr.close();
			br.close();
			
			// On retourne cette matrice temporaire qui est �quivalente � la matrice d'adjacence
			return temporaryMatrice;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
