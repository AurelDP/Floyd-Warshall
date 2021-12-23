package TEST;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Reader {
	
	private int graphNumber;
	private int stateNumber = 0;
	
	public Reader(int graphNumber) {
		if(graphNumber > 13 || graphNumber < 0) {
			System.out.println("graph inexistant");
		}
		else {
			this.graphNumber = graphNumber;
		}
	}

	public int getStateNumber() {
		return stateNumber;
	}
	
	public Integer[][] getMatrice() {
		
		try {
			File thisFile = new File("graphs/H7_G"+ String.valueOf(graphNumber) +".txt");			
			FileReader fr = new FileReader(thisFile.getAbsolutePath());
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			stateNumber = Integer.parseInt(br.readLine());
			
			Integer[][] temporaryMatrice = new Integer[stateNumber][stateNumber];
			int i = 0;
			
			while ((line = br.readLine()) != null) {
				
				String[] part = line.split(",");
				
		        for(int j = 0; j < stateNumber; j++) {
		        	if(Integer.parseInt(part[j]) != 0){
		        		temporaryMatrice[i][j] = Integer.parseInt(part[j]);
		        	}
		        	else {
		        		temporaryMatrice[i][j] = null;
		        	}
		    	}
		        i++;
		    }

			
			fr.close();
			br.close();
			return temporaryMatrice;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
