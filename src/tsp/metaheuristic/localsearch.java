package tsp.metaheuristic;

import tsp.Instance;
import tsp.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class localsearch.
 */
public class localsearch extends AMetaheuristic{
	
	/**
	 * Instantiates a new localsearch.
	 *
	 * @param instance the instance
	 * @param name the name
	 * @throws Exception
	 */
	public localsearch(Instance instance, String name) throws Exception {
		super(instance, name);
	}
	

	/**Local search in the tsp problem with a swap operator 
	 * 
	 * @param sol une solution pour l'instance
	 * @return the first better solution find with swap
	 * @see tsp.metaheuristic.AMetaheuristic#solve(tsp.Solution)
	 */
	@Override
	public Solution solve(Solution sol) throws Exception {
		double longueuractuelle=sol.evaluate();
		int n=this.m_instance.getNbCities();
		for(int i=0;i<n;i++) {
			for(int j=0;j<i;j++) {
				Solution nouvellesolution=sol.copy();
				int ville=nouvellesolution.getCity(i);
				nouvellesolution.setCityPosition(sol.getCity(j),i);
				nouvellesolution.setCityPosition(ville, j);
				nouvellesolution.setCityPosition(nouvellesolution.getCity(0), n);
				double nouvellelongueur=nouvellesolution.evaluate();
				if(nouvellelongueur<longueuractuelle) {
					return nouvellesolution;
				}
			}
			for(int j=i+1;j<n;j++) {
				Solution nouvellesolution=sol.copy();
				int ville=nouvellesolution.getCity(i);
				nouvellesolution.setCityPosition(sol.getCity(j),i);
				nouvellesolution.setCityPosition(ville, j);
				nouvellesolution.setCityPosition(nouvellesolution.getCity(0), n);
				double nouvellelongueur=nouvellesolution.evaluate();
				if(nouvellelongueur<longueuractuelle) {
					return nouvellesolution;
				}
			}
		}
		return sol;
	}

	


}
