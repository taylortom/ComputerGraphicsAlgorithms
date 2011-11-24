package datatypes;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL2;

/**
 * A simple object to store face information
 * So far handles tris and quads
 *
 * @author Tom
 * @version 0.1
 * @history 13.10.2011: Created class
 */ 

public class Face
{
	// the edges in the face
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	// the RGB value of the face's colour
	private byte[] colour = new byte[3];
	// holds the direction of each edge
	private byte[] edgeDirection = new byte[3];
	private Vertex facePoint;
	
	private String id = "";
			
	/**
	 * Constructor
	 * @param _edge1
	 * @param _edge2
	 * @param _edge3
	 */
	public Face(Edge _edge1, Edge _edge2, Edge _edge3, byte[] edgeDir, String _id)
	{		
		this.edges.add(_edge1);
		this.edges.add(_edge2);
		this.edges.add(_edge3);
		
		// randomly generate a colour
		Random r = new Random();
		this.colour[0] = (byte)(r.nextInt(254)+1);
		this.colour[1] = (byte)(r.nextInt(254)+1);
		this.colour[2] = (byte)(r.nextInt(254)+1);
		
		this.edgeDirection = edgeDir;		
		this.id = _id;
	}
	
	/**
	 * Draws each edge
	 * @param gl
	 */
	public void draw(GL2 gl)
	{				
		// draw in an anti-clockwise fashion 
		this.edges.get(0).getVertices().get(edgeDirection[0]).draw(gl);		
		this.edges.get(2).getVertices().get(edgeDirection[2]).draw(gl);
		this.edges.get(1).getVertices().get(edgeDirection[1]).draw(gl);
	}
	
	/**
	 * Prints each edge
	 */
	public void print()
	{
		for (int i = 0; i < this.edges.size(); i++)
		{
			System.out.println("Edge " + (i+1) + ": ");
			this.edges.get(i).print();
		}
	}
	
	public void calculateFacePoint()
	{
		ArrayList<Vertex> faceVertices = this.getVertices();		
		Vertex sum = new Vertex(0,0,0);
		
		// sum up the vertices
		for (int j = 0; j < faceVertices.size(); j++) sum = Vertex.add(faceVertices.get(j), sum);
		
		// get the average
		this.facePoint = Vertex.divide(sum, faceVertices.size());
	}
	
	//	public getters/setters
	/**
	 * Gets the edge from it's vertices
	 * @param v1, v2 the vertices
	 * @return the edge
	 */
	public Edge getEdge(Vertex v1, Vertex v2) 
	{ 
		for (int i = 0; i < this.edges.size(); i++)
		{
			Edge e = this.edges.get(i); 
			if(e.contains(v1) && e.contains(v2)) return e; 
		} 
		return null; 
	}
	/**
	 * Returns the edges other than the passed edge
	 * @param edge we don't want returned
	 * @return the edges
	 */
	public ArrayList<Edge> getEdges(Edge edge) 
	{ 
		ArrayList<Edge> otherEdges = new ArrayList<Edge>();
		
		for (int i = 0; i < this.edges.size(); i++)
		{
			Edge e = this.edges.get(i);
			if(e != edge) otherEdges.add(e); 
		}
		
		return otherEdges; 
	}
	/**
	 * Returns the vertex of the face which isn't in the passed edge
	 * @param edge
	 * @return the vertex
	 */
	public Vertex getPoint(Edge edge)
	{		
		for (int i = 0; i < this.edges.size(); i++) 
		{			
			Edge e = this.edges.get(i);
			
			Vertex edgeV1 = edge.getVertices().get(0);
			Vertex edgeV2 = edge.getVertices().get(1);
			
			if(!e.equals(edge))
			{	
				Vertex v1 = e.getVertices().get(0);
				Vertex v2 = e.getVertices().get(1);
				
				if(v2 == edgeV1 || v2 == edgeV2) return v1;
				else if(v1 == edgeV1 || v1 == edgeV2) return v2;
			}
		}
		
		return null;
	}
	public ArrayList<Vertex> getVertices() 
	{ 
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for (int i = 0; i < this.edges.size(); i++)
		{
			vertices.add(this.edges.get(i).getVertices().get(this.edgeDirection[i]));
		}
		
		return vertices; 
	}
	public byte[] getColour() { return this.colour; }
	public Edge getEdge(Edge e) { return this.getEdge(e.getVertices().get(0), e.getVertices().get(1)); }
	public ArrayList<Edge> getEdges() { return this.edges; }
	public byte[] getEdgeDirections() { return this.edgeDirection; }
	public Vertex getFacePoint() { return this.facePoint; }
	public String getId() { return this.id; }
}