package tsp.heuristic;

import java.util.ArrayList;

import tsp.Instance;
import tsp.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class Plus_proche_voisin.
 */
public class Plus_proche_voisin extends AHeuristic {

    /**
     * Instantiates a new plus proche voisin.
     *
     * @param instance the instance
     * @param name the name
     * @throws Exception the exception
     */
    public Plus_proche_voisin(Instance instance, String name) throws Exception {
        super(instance, name);
        // TODO Auto-generated constructor stub
    }


    /* (non-Javadoc)
     * @see tsp.heuristic.AHeuristic#getplusproche(int, java.util.ArrayList)
     */
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

    /* (non-Javadoc)
     * @see tsp.heuristic.AHeuristic#solve()
     */
    public void solve() throws Exception {
        ArrayList<Integer> nonvisite=new ArrayList<Integer>(this.m_instance.getNbCities());
        for(int k=1;k<this.m_instance.getNbCities();k++) {
            nonvisite.add(k);
        }
        int villeprecedente=0;
        int villesuivante=0;
        for(int k=0;k<this.m_instance.getNbCities()-1;k++) {
            System.err.println(villeprecedente);
            villesuivante=this.getplusproche(villeprecedente, nonvisite);
            villeprecedente=villesuivante;

            this.m_solution.setCityPosition(villesuivante, k+1);

        }


    }




}