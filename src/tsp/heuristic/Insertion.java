package tsp.heuristic;

import java.util.ArrayList;

import tsp.Instance;

	/** Nous déclarons la méthode d'insertion
	 * @author Lancheros-
	 */
	 
public class Insertion extends AHeuristic {

	public Insertion(Instance instance, String name) throws Exception {
		super(instance, name);
		// TODO Auto-generated constructor stub
	}
	
	/** À partir du dépôt 0, nous déterminons le nœud le plus proche "C"
	 * @param i, nonvisite 
	 * @author Lancheros-
	 * */
	public int getplusproche(int i, ArrayList<Integer> nonvisite) {
		long[] distancei=this.m_instance.getDistances()[i];
		int NBcities=this.m_instance.getNbCities();
		int min = nonvisite.get(0);
		long distmin=distancei[min];
		for(int k :nonvisite) {
			if(distancei[k]<distmin) {
				min=k;
				distmin=distancei[k];
			}
		}
		nonvisite.remove(nonvisite.indexOf(min));
		return min;
	}


	/** Nous avons construit le subtour 0-c-0
	 * @author Lancheros
	 *  */
	public void solve() throws Exception {
		ArrayList<Integer> nonvisite=new ArrayList<Integer>(this.m_instance.getNbCities());
		for(int k=1;k<this.m_instance.getNbCities();k++) {
			nonvisite.add(k);
		}
		int villeprecedente=0;
		int villesuivante=0;
		int villefinal = 0;
					
			// se construye el arreglo 0-c-0
			System.err.println(villeprecedente);
			villesuivante=this.getplusproche(villeprecedente, nonvisite);
			System.err.println(villesuivante);
			System.err.println(villefinal);
			System.err.println(nonvisite);
			villeprecedente=villesuivante;
	
			this.m_solution.setCityPosition(villesuivante,1);
			 
			//  Nous trouvons le nœud "K" le plus proche (qui n'est pas dans le subtour) 
			
			for(int k=1;k<this.m_instance.getNbCities()-1;k++) {
				
				System.err.println(villeprecedente);
				villesuivante=this.getplusproche(villeprecedente, nonvisite);
				System.err.println(villesuivante);
				System.err.println(nonvisite);
				villeprecedente=villesuivante;
	
				this.m_solution.setCityPosition(villesuivante,1);
				
			}
			
			
			
			
		 	
			
			
		
	
	
	
	
	
	
	/** 
	 * On trouve l'arc (i, j) du subtour qui permet l'insertion la plus 
	 * économique dans le nœud K 
	 * coût d'insertion = d(i,k) + d(k,j)
	 *  @author Lancheros - 
	 *  */
	
	
	
	
	
	
	
	
	//ici, on insère le noeud k entre i et j
	
	
	
	
	
	
	
	
	
	
	
	}


	
}



