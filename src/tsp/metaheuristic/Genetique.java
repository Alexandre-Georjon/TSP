package tsp.metaheuristic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import tsp.Instance;
import tsp.Solution;

public class Genetique extends AMetaheuristic{

	public Genetique(Instance instance, String name) throws Exception {
		super(instance, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Solution solve(Solution sol) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Solution[] genererpopulation(int n) throws Exception {
		Solution[] population=new Solution[n];
		Solution soluce=new Solution(m_instance);
		soluce.setCityPosition(0, this.m_instance.getNbCities());
		for(int k=0;k<this.m_instance.getNbCities();k++) {
			soluce.setCityPosition(k, k);
		}
		Random rnd = new Random();
		
		for(int k=0;k<n;k++) { //mélange de Fisher-Yates
			population[k]=soluce.copy();
			for(int i=this.m_instance.getNbCities()-1;i>0;i--) {
				int j=rnd.nextInt(i)+1;
				int temp=population[k].getCity(j);
				population[k].setCityPosition(population[k].getCity(i), j);
				population[k].setCityPosition(temp, i);
				//System.err.println(i);
			}
			//for(int l=0;l<=this.m_instance.getNbCities();l++) {
			//	System.err.print(population[k].getCity(l)+" ");
			//}
			//System.err.println("");
			
		}
		return population;
	}
	public void selection(Solution[] population) throws Exception {
		double eval=0;
		for(Solution s:population) {
			eval=s.evaluate();
		}
		 Arrays.sort(population);
	}
	public Solution[] hybridation1(Solution parent1,Solution parent2) throws Exception {
		Solution fils1= new Solution(m_instance);
		Solution fils2= new Solution(m_instance);
		ArrayList<Integer> nonvisite1=new ArrayList<Integer>();
		ArrayList<Integer> nonvisite2=new ArrayList<Integer>();
		int tailleSolution=parent1.getInstance().getNbCities();
		int index1=0;
		int index2=0;
		boolean trouver1=false;
		boolean trouver2=false;
		for(int i=0;i<tailleSolution/2;i++) {
			fils1.setCityPosition(parent1.getCity(i), i);
			fils2.setCityPosition(parent2.getCity(i), i);			
		}
		for(int i=tailleSolution/2;i<tailleSolution;i++) {
			nonvisite1.add(parent1.getCity(i));
			nonvisite2.add(parent2.getCity(i));
		}
		for(int i=tailleSolution/2;i<tailleSolution;i++) {
			while(!trouver1) {
				if(nonvisite1.contains(parent2.getCity(index2))){
					fils1.setCityPosition(parent2.getCity(index2), i);
					nonvisite1.remove(nonvisite1.indexOf(parent2.getCity(index2)));
					trouver1=true;
				}else {
					index2++;
				}
			}
			while(!trouver2) {
				if(nonvisite2.contains(parent1.getCity(index1))){
					fils2.setCityPosition(parent1.getCity(index1), i);
					nonvisite2.remove(nonvisite2.indexOf(parent1.getCity(index1)));
					trouver2=true;
				}else {
					index1++;
				}
			}
			trouver1=false;
			trouver2=false;
			
		}
		Solution[] res={fils1,fils2};
		return res;
	}
	public void mutation(Solution sol) throws Exception {
		Random rnd = new Random();
		int j=rnd.nextInt(sol.getInstance().getNbCities()-2)+1;
		int i=rnd.nextInt(sol.getInstance().getNbCities()-2)+1;
		int ville=sol.getCity(i);
		sol.setCityPosition(i, sol.getCity(j));
		sol.setCityPosition(j, ville);
	}
	public void mutation(Solution[] population,double pourcentage) throws Exception {
		for(int k=0;k<population[0].getInstance().getNbCities()*pourcentage;k++) {
			Random rnd = new Random();
			int j=rnd.nextInt(population[0].getInstance().getNbCities());
			mutation(population[j]);
		}
	}
	
	public void evolution(Solution[] population) throws Exception {
		selection(population);
		int milieu=population.length/2;
		for(int k=0;k+1<milieu-2;k=k+2) {
			Solution[] fils=hybridation1(population[k], population[k+1]);
			population[milieu+k]=fils[0];
			population[milieu+k+1]=fils[1];
		}
		mutation(population,0);
		selection(population);
	}

}
