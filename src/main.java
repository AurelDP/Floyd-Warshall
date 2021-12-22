public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int I = 99999999;
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
		
		System.out.println("test");
		
		
		
		
	}

}
