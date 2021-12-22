public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*int I = 99999999;
		int n = 5;
		
		int matrice[][] = { 
				{ 0, 3, 8, I, -4 },
				{ I, 0, I, 1, 7 }, 
				{ I, 4, 0, I, I }, 
				{ 2, I, -5, 0, I },
				{ I, I, I, 6, 0 } 
				};
		Graph graphe1  = new Graph(n, matrice);
		
		Graph graphe2  = new Graph(graphe1);
		
		System.out.println("test");*/
		
		Double L[][] = { 
				{ 0.0, 7.0, 4.0, 0.1, 0.1 },
				{ 0.1, 0.0, 1.0, 0.1, 0.1 }, 
				{ 0.1, 0.1, 0.0, 0.1, 0.1 },
				{ 0.1, 0.1, 0.1, 0.0, 2.0 },
				{ 0.1, 0.1, 0.1, 0.1, 0.0 }
		};
		
		Double P[][] = Graph.createPFromL(L);
		
		Double[][][] resTabs = Graph.floydWarshall(L, P);
		Double newL[][] = resTabs[0];
		Double newP[][] = resTabs[1];
		
		if (!Graph.isAbsorbent(newL)) {
			System.out.println("\nNo absorbent circuit\n\n");
			Graph.displayBestRoutes(newL, newP);
		} else
			System.out.println("\nAbsorbent circuit : no best routes");

	}

}
