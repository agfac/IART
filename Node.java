import java.util.Vector;

public class Node {
	private static int intid = 0;
	private int graphid;
	private int valor;
	private int tempdist;
	private int distanciaEstacao;
	private Vector<String> temproute;
	private boolean tempvisited;
	private String id;
	private Vector<Edge> arestas;
	private boolean isGarbage;
	private Node parent;
	private int fValue;
	
	public Node(String id){
		this.id = id;
		this.arestas = new Vector<Edge>();
		this.valor = -1;
		this.isGarbage = false;
		this.tempdist = Integer.MAX_VALUE;
		this.temproute = new Vector<String>();
		this.tempvisited = false;

		this.graphid = intid;
		intid++;
	}
	
	public Node(String id, int valor) {
		this.id = id;
		this.arestas = new Vector<Edge>();
		this.valor = valor;
		this.isGarbage = true;
		this.tempdist = Integer.MAX_VALUE;
		this.temproute = new Vector<String>();
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
		this.temproute = new Vector<String>();
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
		this.temproute = new Vector<String>();
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

	public int getDistanciaEstacao() {
		return distanciaEstacao;
	}

	public void setDistanciaEstacao(int distanciaEstacao) {
		this.distanciaEstacao = distanciaEstacao;
	}
	
	public Vector<Node> getConnectedNodes(){
		Vector<Node> connected = new Vector<Node>();
		
		for(int i = 0; i<arestas.size(); i++){
			Edge edge = arestas.get(i);
			Node no = edge.getDestino();
			connected.add(no);
		}
		
		return connected;
	}
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getfValue() {
		return fValue;
	}

	public void setfValue(int fValue) {
		this.fValue = fValue;
	}
	
	public int getValueEdge(String nome){
		
		for(int i = 0; i< arestas.size(); i++){
			Edge e = arestas.get(i);
			if(e.getDestino().getId().equals(nome))
				return e.getDistancia();
		}
		
		return -1;
	}

}