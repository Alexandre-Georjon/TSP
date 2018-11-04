package tsp.heuristic;

import java.util.ArrayList;

import tsp.Instance;
import tsp.Solution;

public class Insertion {

	private Node tour;
	private int size;
	private Instance mInstance;
	
	/**
	 * Create an empty tour
	 */
	public Insertion(Instance inst)
	{
		size = 0;
		mInstance = inst;
	}
	
	/**
	 * Returns  a text string that shows the tour
	 */
	public String toString()
	{
		String rta = "";
		
		for( int i = 0; i < size; i++ )
		{
			if(i != size - 1)
			{
				rta += tour.get( i ).getCityIndex() + "-";
			}
			else
			{
				rta += tour.get( i ).getCityIndex();
			}
		}
		
		return rta;
	}
	
	/**
	 * Number of points of the tour
	 * @return Number of points of the tour
	 */
	public int  size() { return size; }
	
	/**
	 * Returns the total distance of the tour
	 * @return distance
	 */
	public double distance() throws Exception
	{
		double distance = 0;
		
		for( int i = 1; i < size; i++ )
		{
			distance += mInstance.getDistances(tour.get(i).getCityIndex(), tour.get(i - 1).getCityIndex());
		}
		
		distance += mInstance.getDistances(tour.get(0).getCityIndex(), tour.get(size - 1).getCityIndex());
		
		return distance;
	}
	
	public Solution heuristicaInsercion() throws Exception
	{
		// Load indexes of cities in an arrangement to find out which ones to add to the tour
		ArrayList<Integer> points = new ArrayList<Integer>();
		
		for (int i = 0; i < mInstance.getNbCities(); i++)
		{
			points.add(i);
		}
		
		// I add the first city to the route and remove it from the list of pending
		int first = 0;
		
		tour = new Node(first);
		size++;
		
		points.remove(0);
		
		// I look for the city near the prime minister, I add it to the orientation and eliminate it from the waiting
		int nearestToFirst = 0;
		double nearestDistToFirst = mInstance.getDistances(0, first);
		
		for (int i = 0; i < points.size(); i++)
		{
			if(mInstance.getDistances(i, first) < nearestDistToFirst)
			{
				nearestToFirst = i;
				nearestDistToFirst = mInstance.getDistances(i, first);
			}
		}
		
		tour.insert(points.get(nearestToFirst), 0);
		size++;
		
		points.remove(nearestToFirst);
		
		while(points.size() > 0)
		{
			// I am looking for the point k least distant from any other node already inserted
			int nearestIndex = 0;
			double nearestDist = Double.POSITIVE_INFINITY;
			
			for (int i = 0; i < points.size(); i++) 
			{
				// I am looking for the shortest distance at the current point
				int p = points.get(i);
				double nearestCurrentDist = Double.POSITIVE_INFINITY;
				
				for (int j = 0; j < size; j++) 
				{
					double dist = mInstance.getDistances(p, j);
					if(dist < nearestCurrentDist)
					{
						nearestCurrentDist = dist;
					}
				}
				
				// If the smallest distance from the already added nodes is less than the overall distance, replace it
				if(nearestCurrentDist < nearestDist)
				{
					nearestDist = nearestCurrentDist;
					nearestIndex = i;
				}
			}
			
			// As I have the node k closest to anyone already inserted, insert it into the arc that allows the most economical insertion,
			// that is, the one that has less distance adds to the current tour
			int toInsert = points.get(nearestIndex);
			insertSmallest(toInsert);
			
			// As already inserted in the tour, it is removed from the list of points by adding
			points.remove(nearestIndex);
		}
		
		// Create the solution and return it
		Solution mSolution = new Solution(mInstance);
		
		for (int i = 0; i < size; i++) 
		{
			mSolution.setCityPosition(tour.get(i).getCityIndex(), i);
		}
		
		return mSolution;
	}
	
	/**
	 * Insert the point P using the heuristic of the smallest increment of the total distance (look for the arc with the most economical insertion)
	 * @param p
	 */
	public void  insertSmallest(int p) throws Exception
	{
		if( size == 0 )
		{
			// The tour is empty, so I just add the new point at the beginning
			size = 1;
			tour = new Node(p);
			return;
		}
		
		double smallestIncrease = Double.POSITIVE_INFINITY;
		int index = 0;// To manage the second point
		double originalD, newD;
		
		// I look first for if it is inserted in the tour
		for( int i = 1; i < size; i++)
		{
			originalD = mInstance.getDistances(tour.get(i).getCityIndex(), tour.get(i - 1).getCityIndex());//distancia de A a B
			newD = mInstance.getDistances(p, tour.get(i).getCityIndex()) + mInstance.getDistances(p, tour.get(i - 1).getCityIndex());//distancia de A a P a B
			if( newD - originalD <= smallestIncrease )
			{
				smallestIncrease = newD - originalD;
				index = i - 1;
			}
		}
		
		// I check the case of if this key insert between the first and the last node of the turn
		originalD = mInstance.getDistances(tour.get(0).getCityIndex(), tour.get(size - 1).getCityIndex());//distancia del primero al ultimo
		newD = mInstance.getDistances(p, tour.get(0).getCityIndex()) + mInstance.getDistances(p, tour.get(size - 1).getCityIndex());//distancia del primero a P al ultimo
		if( newD - originalD <= smallestIncrease )
		{
			smallestIncrease = newD - originalD;
			index = size - 1;
		}
		
		// Insert the new node next to the index found
		tour.insert( p, index );
		size++;
	}
	
	/**
	 * Class that represents a node in the turn. 
	 * This allows me to recursively create the circuit in a linked list, for the sake of efficiency.
	 */
	private class Node
	{
		/**
		 * Point p of the node
		 */
		private int cityIndex;
		
		/**
		 * next node
		 */
		private Node next;
		
		/**
		 * Build a single node
		 * @param p - point of the node
		 */
		public Node(int p)
		{
			this.cityIndex = p;
			next = null;
		}
		
		/**
		 * Build a knot with one's neighbor
		 * @param p - Node point
		 * @param n - next node
		 */
		public Node(int p, Node n)
		{
			this.cityIndex = p;
			next = n;
		}
		
		/**
		 * Recursively insert a node next to node i
		 * @param point - New node point
		 * @param i - Index of the node next to which the new is inserted
		 */
		public void insert(int point, int i)
		{
			i--;
			if(i >= 0)
			{
				next.insert(point, i);
			}
			else
			{
				Node n = next;
				next = new Node( point, n );
			}
		}
		
		/**
		 * Returns the point associated with the node
		 * @return
		 */
		public int getCityIndex(){ return cityIndex; }
		
		/**
		 * Gets the node in the given index as a parameter
		 * @param index - Index where the node is. 0 <= index <= size
		 * @return Node in the given position as a parameter
		 */
		public Node get(int index)
		{
			index--;
			if(index >= 0){ return next.get(index); }
			
			return this;
		}
	}
}
