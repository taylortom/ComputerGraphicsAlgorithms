package datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple object to store edge information
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */

public class Edge
{
	// an array of the vertices, clockwise
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	// the faces which wing this edge 
	private Face[] wingedFaces = new Face[2];
	// used when subdividing...
	private Vertex edgePoint = null;
	// label - just to help with debugging
	private String id = "";
	
	/**
	 * Constructor
	 * @param _startPoint
	 * @param _endPoint
	 */
	public Edge(Vertex _startPoint, Vertex _endPoint, String _id)
	{
		this.vertices.add(_startPoint);
		this.vertices.add(_endPoint);
		this.id = _id;
	}
	public Edge(Vertex _startPoint, Vertex _endPoint)
	{
		new Edge(_startPoint, _endPoint, "no_id");
	}
	
	/**
	 * Adds two faces to the winged faces list
	 * @param the faces to add
	 */
	public void addWingedFaces(Face _face1, Face _face2)
	{
		// check we're not trying to add same faces in reverse order
		if(wingedFaces[0] != _face2 && wingedFaces[1] != _face1 && wingedFaces[0] != _face1 && wingedFaces[1] != _face2) 
		{
			wingedFaces[0] = _face1;
			wingedFaces[1] = _face2;
		}
	}
	
	/**
	 * Adds the current edge to the vertices'
	 * edges lists
	 */
	public void addToVertexValence()
	{
		for (int i = 0; i < this.vertices.size(); i++)
			this.vertices.get(i).addEdge(this);
	}

	/**
	 * Calculates the new face point by averaging 
	 * the two vertices in this edge, and the two
	 * face points of the faces winging this edge  
	 */
	public void calculateEdgePoint()
	{
		Vertex sum = new Vertex(0,0,0);
		
		sum = this.vertices.get(0);
		sum = Vertex.add(sum, this.vertices.get(1));
		sum = Vertex.add(sum, this.getWingedFaces()[0].getFacePoint());
		sum = Vertex.add(sum, this.getWingedFaces()[1].getFacePoint());
		
		this.edgePoint = Vertex.divide(sum, 4);
	}
	
	
	/**
	 * Check to see whether the passed vertex is in this edge
	 * @param _vertex to check for
	 * @return Boolean as appropriate
	 */
	public boolean contains(Vertex _vertex)
	{
		for (int i = 0; i < this.vertices.size(); i++)
		{
			Vertex v = this.vertices.get(i);
			if(v == _vertex) return true;
		}
		return false;
	}
	
	/**
	 * Prints out each vertex
	 */
	public void print()
	{
		System.out.println("Vertex 1: ");
		vertices.get(0).print();
		
		System.out.println("Vertex 2: ");
		vertices.get(1).print();
	}
	
	// public getters/setters
	public Vertex getEdgePoint() { return this.edgePoint; }
	public String getId() { return this.id; }
	public ArrayList<Vertex> getVertices() { return this.vertices; }
	public Face[] getWingedFaces() { return this.wingedFaces; }
	/**
	 * Inverts the vertices of this edge
	 * @return the inverted edge
	 */
	public Edge getInvert()
	{
		return new Edge(this.vertices.get(1), this.vertices.get(0));
	}
}