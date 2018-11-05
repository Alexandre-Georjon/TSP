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
		double eval1=fils1.evaluate();
		double eval2=fils2.evaluate();
		Solution[] res={fils1,fils2};
		return res;
	}
	public Solution[] hybridation2(Solution parent1,Solution parent2) throws Exception {
		Solution fils1= new Solution(m_instance);
		Solution fils2= new Solution(m_instance);
		Solution fils3= new Solution(m_instance);
		Solution fils4= new Solution(m_instance);
		ArrayList<Integer> nonvisite1=new ArrayList<Integer>();
		ArrayList<Integer> nonvisite2=new ArrayList<Integer>();
		ArrayList<Integer> nonvisite3=new ArrayList<Integer>();
		ArrayList<Integer> nonvisite4=new ArrayList<Integer>();
		int tailleSolution=parent1.getInstance().getNbCities();
		int index1=0;
		int index2=0;
		int index3=0;
		int index4=0;
		boolean trouver1=false;
		boolean trouver2=false;
		boolean trouver3=false;
		boolean trouver4=false;
		for(int i=0;i<tailleSolution/2;i++) {
			fils1.setCityPosition(parent1.getCity(i), i);
			fils2.setCityPosition(parent2.getCity(i), i);
			nonvisite3.add(parent1.getCity(i));
			nonvisite4.add(parent2.getCity(i));
		}
		for(int i=tailleSolution/2;i<tailleSolution;i++) {
			nonvisite1.add(parent1.getCity(i));
			nonvisite2.add(parent2.getCity(i));
			fils3.setCityPosition(parent1.getCity(i), i);
			fils4.setCityPosition(parent2.getCity(i), i);
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
		for(int i=0;i<tailleSolution/2;i++) {
			while(!trouver3) {
				if(nonvisite3.contains(parent2.getCity(index4))){
					fils1.setCityPosition(parent2.getCity(index4), i);
					nonvisite3.remove(nonvisite3.indexOf(parent2.getCity(index4)));
					trouver3=true;
				}else {
					index4++;
				}
			}
			while(!trouver4) {
				if(nonvisite4.contains(parent1.getCity(index3))){
					fils4.setCityPosition(parent1.getCity(index3), i);
					nonvisite4.remove(nonvisite4.indexOf(parent1.getCity(index3)));
					trouver4=true;
				}else {
					index3++;
				}
			}
			trouver3=false;
			trouver4=false;
			
		}
		double eval1=fils1.evaluate();
		double eval2=fils2.evaluate();
		Solution[] res={fils1,fils2,fils3,fils4};
		selection(res);
		return res;
	}
	
	public Solution hybridationPMX(Solution parent1,Solution parent2) throws Exception {
		Solution fils = new Solution(m_instance);
		Random rnd=new Random();
		int longueur=40;
		int indicedebut=rnd.nextInt(m_instance.getNbCities()-longueur);
		int indicefin=indicedebut+longueur;
		ArrayList<Integer> dejavisite=new ArrayList<Integer>();
		for(int i=indicedebut;i<=indicefin;i++) {
			fils.setCityPosition(parent1.getCity(i), i);
			dejavisite.add(parent1.getCity(i));
		}
		for(int i=indicedebut;i<=indicefin;i++) {
			int ville=parent2.getCity(i);
			if(!dejavisite.contains(ville)) {
				int position=i;
				while(position>=indicedebut&&position<=indicefin) {
					position=parent2.getindex(parent1.getCity(position));
				}
				fils.setCityPosition(ville, position);
				dejavisite.add(ville);
			}
		}
		for(int i=0;i<this.m_instance.getNbCities();i++) {
			int ville=parent2.getCity(i);
			if(!dejavisite.contains(ville)) {
				fils.setCityPosition(ville, i);
			}
		}
		fils.setCityPosition(fils.getCity(0),m_instance.getNbCities());
		return fils;
	}
	
	
	
	
	
	
	
	
	
	public void mutation(Solution sol) throws Exception {
		Random rnd = new Random();
		int j=rnd.nextInt(sol.getInstance().getNbCities()-2)+1;
		int i=rnd.nextInt(sol.getInstance().getNbCities()-2)+1;
		int ville=sol.getCity(i);
		sol.setCityPosition(sol.getCity(j),i);
		sol.setCityPosition(ville, j);
	}
	public void mutation(Solution[] population,double pourcentage) throws Exception {
		for(int k=0;k<population[0].getInstance().getNbCities()*pourcentage;k++) {
			Random rnd = new Random();
			int j=rnd.nextInt(population.length);
			mutation(population[j]);
		}
	}
	
	public Solution[] evolution(Solution[] population) throws Exception {
		selection(population);
		Solution[] nouvellepopulation=new Solution[population.length];
		int milieu=(int) (population.length/2);
		for(int k=0;k<milieu;k++) {
			nouvellepopulation[k]=population[k].copy();
		}
		for(int k=0;k+1<milieu;k++) {
			Solution temp=hybridation1(population[k], population[k+1])[0];
			if(temp.compareTo(population[k])<0) {
				nouvellepopulation[milieu+k]=temp;
			}else {
				nouvellepopulation[milieu+k]=population[k].copy();
			}
		
		}
		return nouvellepopulation;
	}
	
	
	public Solution[] evolutionalternative(Solution[] population) throws Exception {
		selection(population);
		Solution[] nouvellepopulation=new Solution[population.length];
		long somme=0;
		for(Solution s: population) {
			somme+=s.evaluate();
		}
		double[] proba=new double[population.length];
		proba[0]=population[0].evaluate()/somme;
		for(int i=1;i<population.length;i++) {
			proba[i]=(population[i].evaluate()/somme+proba[i-1]);
		}
		for(int k=5;k<population.length;k++) {
			double nbAlea=Math.random();
			int position=population.length-1;
			while(nbAlea<proba[position]&&position>0){
				nbAlea-=population[position].getObjectiveValue()/somme;
				position--;
			}
			double nbAlea2=Math.random();
			int position2=population.length-1;
			while(nbAlea2<proba[position2]&&position2>0){
				nbAlea2-=population[position].getObjectiveValue()/somme;
				position2--;
			}
			Solution temp =hybridationPMX(population[position+1], population[position2+1]);
			if(temp.compareTo(population[k])<0) {
				nouvellepopulation[k]=temp;
			}else {
				nouvellepopulation[k]=population[k];
			}
			
			
		}
		for(int k=0;k<5;k++) {
			nouvellepopulation[k]=population[k];
		}
		mutation(nouvellepopulation, 0.0);
		for(int k=0;k<5;k++) {
			nouvellepopulation[k]=population[k];
		}
		
		return nouvellepopulation;
		
		
	}
	
	
	
	
	
	

}
