import java.util.Vector;

public class Node {
	private static int intid = 0;
	private int graphid;
	private int valor;
	private int tempdist;
	private Vector<String> temproute;
	private boolean tempvisited;
	private String id;
	private Vector<Edge> arestas;
	private boolean isGarbage;
	
	public Node(String id){
		this.id = id;
		Vector<Edge> emptyedge = null;
		this.arestas = emptyedge;
		this.valor = -1;
		this.isGarbage = false;
		this.tempdist = Integer.MAX_VALUE;
		Vector<String> empty = null;
		this.temproute = empty;
		this.tempvisited = false;

		this.graphid = intid;
		intid++;
	}
	
	public Node(String id, int valor) {
		this.id = id;
		Vector<Edge> emptyedge = null;
		this.arestas = emptyedge;
		this.valor = valor;
		this.isGarbage = true;
		this.tempdist = Integer.MAX_VALUE;
		Vector<String> empty = null;
		this.temproute = empty;
		this.tempvisited = false;

		this.graphid = intid;
		intid++;
	}
	
	public Node(String id, Vector<Edge> arestas){
		this.id = id;
		this.arestas = arestas;
		this.valor = -1;
		this.isGarbage = false;
		this.tempdist = Integer.MAX_VALUE;
		Vector<String> empty = null;
		this.temproute = empty;
		this.tempvisited = false;

		this.graphid = intid;
		intid++;
	}
	
	public Node(String id, int valor, Vector<Edge> arestas){
		this.id = id;
		this.arestas = arestas;
		this.valor = valor;
		this.isGarbage = true;
		this.tempdist = Integer.MAX_VALUE;
		Vector<String> empty = null;
		this.temproute = empty;
		this.tempvisited = false;

		this.graphid = intid;
		intid++;
	}
	public Vector<Edge> getArestas(){
		return arestas;
	}
	
	public void setArestas(Vector<Edge> arestas){
		this.arestas = arestas;
	}
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public boolean isIsGarbage(){
		return isGarbage;
	}
	
	public void setIsGarbage(boolean isGarbage){
		this.isGarbage = isGarbage;
	}
	
	public int getValor(){
		return valor;
	}
	
	public void setValor(int valor){
		this.valor = valor;
	}
	
	public int getTempdist(){
		return tempdist;
	}
	
	public void setTempdist(int tempdist){
		this.tempdist = tempdist;
	}
	
	
	public Vector<String> getTemproute(){
		return temproute;
	}
	
	public void setTemproute(Vector<String> temproute){
		this.temproute = temproute;
	}
	
	public boolean isTempvisited(){
		return tempvisited;
	}
	
	public void setTempvisited(boolean tempvisited){
		this.tempvisited = tempvisited;
	}
	
	public void addEdge(Edge e){
		this.arestas.add(e);
	}

	int getGraphid() {
		return graphid;
	}
}