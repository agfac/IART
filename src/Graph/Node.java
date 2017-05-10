package Graph;

import java.util.Vector;

import Recolha.Utils;

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
	private double fValue=Integer.MAX_VALUE;
	private double gValue = Integer.MAX_VALUE;
	private int distance=0;
	private int availableCapacity=Utils.MAX_TRUCK_VALUE;
	private int posX = 0;
	private int posY = 0;
	
	public Node(String id){
		this.id = id;
		this.arestas = new Vector<Edge>();
		this.valor = 0;
		this.isGarbage = false;
		this.tempdist = Integer.MAX_VALUE;
		this.temproute = new Vector<String>();
		this.tempvisited = false;
		
		if(this.id.equals("Central")){
			this.posX = 0;
			this.posY = 0;
		}else{
			this.posX = 10;
			this.posY = 10;
		}
		
		this.graphid = intid;
		intid++;
	}
	
	public Node(String id, int valor, int posX, int posY) {
		this.id = id;
		this.arestas = new Vector<Edge>();
		this.valor = valor;
		if(valor>Utils.MIN_VALUE_GARBAGE)
			this.isGarbage = true;
		else this.isGarbage = false;
		this.tempdist = Integer.MAX_VALUE;
		this.temproute = new Vector<String>();
		this.tempvisited = false;
		this.posX = posX;
		this.posY = posY;

		this.graphid = intid;
		intid++;
	}
	
	public Node(String id, Vector<Edge> arestas){
		this.id = id;
		this.arestas = arestas;
		this.valor = 0;
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
	
	public void setGValue(double gvalue){
		this.gValue=gvalue;
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

	public double getfValue() {
		return fValue;
	}

	public void setFValue(int fValue) {
		this.fValue = fValue;
	}
	
	public double getgValue() {
		return gValue;
	}
	
	public int distanceToParent(Node parent){
		return parent.getValueEdge(this.id);
	}
	
	public int getAvailableCapacity(){
		return this.availableCapacity;
	}
	
	public void setAvailableCapacity(int availableCapacity){
		this.availableCapacity=availableCapacity;
	}
	
	public void updateGValue(){	
		this.distance=distanceToParent(this.parent)+parent.distance;
		this.gValue = 0.3*distance*distance + 0.7*availableCapacity/(double)Utils.MAX_TRUCK_VALUE*distance*distance;
	}
	
	public void updateFValue(){
		this.fValue=this.gValue+this.distanciaEstacao;
	}
	
	public int getValueEdge(String nome){
		
		for(int i = 0; i< arestas.size(); i++){
			Edge e = arestas.get(i);
			if(e.getDestino().getId().equals(nome))
				return e.getDistancia();
		}
		
		return -1;
	}
	
	public double calculateTempGValueWithoutPassing(Node parent){
		double gValue;
		int distance= distanceToParent(parent) + parent.distance;
		gValue=0.3*distance*distance + 0.7*parent.availableCapacity/(double)Utils.MAX_TRUCK_VALUE*distance*distance;
		
		return gValue;
	}
	
	public double calculateTempGValue(Node parent){
		double gValue;
		int distance= distanceToParent(parent) + parent.distance;
		gValue= (0.3*distance*distance + 0.7*(parent.availableCapacity-this.valor)/(double)Utils.MAX_TRUCK_VALUE*distance*distance);
		return gValue;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

}