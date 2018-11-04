package tsp.heuristic;

import java.util.ArrayList;

import tsp.Instance;
import tsp.Solution;

public class Plus_proche_voisin extends AHeuristic {

	public Plus_proche_voisin(Instance instance, String name) throws Exception {
		super(instance, name);
		// TODO Auto-generated constructor stub
	}

	
	public int getplusproche(int i,ArrayList<Integer> nonvisite) {
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
	
	public void solve() throws Exception {
		ArrayList<Integer> nonvisite=new ArrayList<Integer>(this.m_instance.getNbCities());
		for(int k=1;k<this.m_instance.getNbCities();k++) {
			nonvisite.add(k);
		}
		int villeprecedente=0;
		int villesuivante=0;
		for(int k=0;k<this.m_instance.getNbCities()-1;k++) {
			//System.err.println(villeprecedente)
			
			
			System.err.println(villeprecedente);
			villesuivante=this.getplusproche(villeprecedente, nonvisite);
			//System.err.println(villesuivante);
			//System.err.println(nonvisite);
			villeprecedente=villesuivante;

			this.m_solution.setCityPosition(villesuivante, k+1);
			
		}
		
		
	}
	
	
		

}
