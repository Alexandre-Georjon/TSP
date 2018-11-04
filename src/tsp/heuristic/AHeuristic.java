package tsp.heuristic;

import java.util.ArrayList;

import tsp.Instance;
import tsp.Solution;

/**
 * This is the abstract class for Heuristic
 * @author Axel Grimault
 * @version 2017
 *
 */
abstract public class AHeuristic {

	// -----------------------------
	// ----- ATTRIBUTS -------------
	// -----------------------------
	
	/** The data of the problem */
	protected Instance m_instance;
	
	/** The solution built */
	protected Solution m_solution;
	
	/** The name of the heuristic */
	protected String m_name;
	
	
	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------
	
	/**
	 * Constructor
	 * @param instance the instance of the problem
	 * @param name the name of the metaheuristic
	 */
	public AHeuristic(Instance instance, String name) throws Exception {
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_name = name;
	}
	
	
	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------

	/** Apply the heuristic to build m_solution */
	//public abstract void solve() throws Exception;
	
	
	// -----------------------------
	// ----- GETTERS / SETTERS -----
	// -----------------------------
	
	/**
	 * Returns the solution built with this heuristic
	 */
	public Solution getSolution() {
		return m_solution;
	}
	
	/**
	 * Returns the name of the heuristic
	 */
	public String getName() {
		return m_name;
	}


	/** � partir du d�p�t 0, nous d�terminons le n�ud le plus proche "C"
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
			 
			//  Nous trouvons le n�ud "K" le plus proche (qui n'est pas dans le subtour) 
			
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
	 * �conomique dans le n�ud K 
	 * co�t d'insertion = d(i,k) + d(k,j)
	 *  @author Lancheros - 
	 *  */
	
	
	
	
	
	
	
	
	//ici, on ins�re le noeud k entre i et j
	
	
	
	
	
	
	
	
	
	
	
	}

}








