package tsp.heuristic;

import java.util.ArrayList;

import tsp.Instance;
import tsp.Solution;

public class MetodoInsercion {

	private Node tour;
	private int size;
	private Instance mInstance;
	
	/**
	 * Crea un tour vacio
	 */
	public MetodoInsercion(Instance inst)
	{
		size = 0;
		mInstance = inst;
	}
	
	/**
	 * Retorna una cadena de texto que muestra el tour
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
	 * Numero de puntos en el tour
	 * @return Cantidad de puntos en el tour
	 */
	public int  size() { return size; }
	
	/**
	 * Retorna la distancia total del tour
	 * @return
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
		//Cargo los indices de las ciudades en un arreglo para saber cuales faltan por agregar a la ruta
		ArrayList<Integer> points = new ArrayList<Integer>();
		
		for (int i = 0; i < mInstance.getNbCities(); i++)
		{
			points.add(i);
		}
		
		//Agrego la primera ciudad a la ruta y la remuevo de la lista de pendientes
		int first = 0;
		
		tour = new Node(first);
		size++;
		
		points.remove(0);
		
		//Busco la ciudad mas cercana a la primera, la agrego a la ruta y la elimino de pendientes
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
			//Busco el punto k de menor distancia a cualquier otro nodo ya insertado
			int nearestIndex = 0;
			double nearestDist = Double.POSITIVE_INFINITY;
			
			for (int i = 0; i < points.size(); i++) 
			{
				//Busco la menor distancia para el punto actual
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
				
				//Si la menor distancia contra alguno de los nodos ya agregados es menor a la global, reemplazo
				if(nearestCurrentDist < nearestDist)
				{
					nearestDist = nearestCurrentDist;
					nearestIndex = i;
				}
			}
			
			//Como ya tengo el nodo k mas cercano a cualquiera ya insertado, lo inserto en el arco que permita la insercion mas economica,
			//es decir, la que menos distancia agrega al tour actual
			int toInsert = points.get(nearestIndex);
			insertSmallest(toInsert);
			
			//Como ya se inserto al tour, se quita de la lista de puntos por agregar
			points.remove(nearestIndex);
		}
		
		//Creo la solucion y la retorno
		Solution mSolution = new Solution(mInstance);
		
		for (int i = 0; i < size; i++) 
		{
			mSolution.setCityPosition(tour.get(i).getCityIndex(), i);
		}
		
		return mSolution;
	}
	
	/**
	 * Inserta el punto P usando la heur�stica del menor incremento en la distancia total (busca el arco con la insercion mas economica) 
	 * @param p
	 */
	public void  insertSmallest(int p) throws Exception
	{
		if( size == 0 )
		{
			//El tour esta vacio, asi que simplemente agrego el nuevo punto al inicio
			size = 1;
			tour = new Node(p);
			return;
		}
		
		double smallestIncrease = Double.POSITIVE_INFINITY;
		int index = 0;//Para manejar el segundo punto
		double originalD, newD;
		
		//Busco primero si se inserta dentro del tour actual
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
		
		//Reviso el caso de si toca insertar entre primer y ultimo nodo del tour
		originalD = mInstance.getDistances(tour.get(0).getCityIndex(), tour.get(size - 1).getCityIndex());//distancia del primero al ultimo
		newD = mInstance.getDistances(p, tour.get(0).getCityIndex()) + mInstance.getDistances(p, tour.get(size - 1).getCityIndex());//distancia del primero a P al ultimo
		if( newD - originalD <= smallestIncrease )
		{
			smallestIncrease = newD - originalD;
			index = size - 1;
		}
		
		//Inserto el nodo nuevo al lado del indice encontrado
		tour.insert( p, index );
		size++;
	}
	
	/**
	 * Clase que representa un nodo dentro del tour.
	 * Me permite crear el tour de forma recursiva en una lista encadenada, por eficiencia
	 */
	private class Node
	{
		/**
		 * Punto p del nodo
		 */
		private int cityIndex;
		
		/**
		 * Siguiente nodo
		 */
		private Node next;
		
		/**
		 * Construye un nodo unico
		 * @param p - Punto del nodo
		 */
		public Node(int p)
		{
			this.cityIndex = p;
			next = null;
		}
		
		/**
		 * Construye un nodo con su siguiente
		 * @param p - Punto del nodo
		 * @param n - Siguiente nodo
		 */
		public Node(int p, Node n)
		{
			this.cityIndex = p;
			next = n;
		}
		
		/**
		 * Inserta recursivamente un nodo junto al nodo i
		 * @param point - Punto del nuevo nodo
		 * @param i - Indice del nodo junto al cual se inserta el nuevo
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
		 * Retorna el punto asociado al nodo
		 * @return
		 */
		public int getCityIndex(){ return cityIndex; }
		
		/**
		 * Obtiene el nodo ubicado en el indice dado como parametro
		 * @param index - Indice donde se ubica el nodo. 0 <= index <= size
		 * @return Nodo en la posicion dada como parametro
		 */
		public Node get(int index)
		{
			index--;
			if(index >= 0){ return next.get(index); }
			
			return this;
		}
	}
}
