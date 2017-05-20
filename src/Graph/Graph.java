package Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Vector;

import Recolha.Utils;

public class Graph {

	HashMap<String,Node> nodes = new HashMap<String,Node>();
	
	Node root;
	
	Node destino;
	
	Comparator<Node> comparator = new NodeComparator();
    
	PriorityQueue<Node> openQueue;
	
	ArrayList<Node> closeQueue;
	
	public Graph() {
		
	}
	
	public void resetGraphForVoyage(){
	
		HashMap<String,Node> copy = new HashMap<String,Node>(nodes);
		
		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();
		
		while(it.hasNext()) {
		
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			
			((Node) pair.getValue()).setParent(null);
			
			((Node) pair.getValue()).setAvailableCapacity(Utils.MAX_TRUCK_VALUE);
			
			((Node) pair.getValue()).setGValue(Integer.MAX_VALUE);
			
			((Node) pair.getValue()).setFValue(Integer.MAX_VALUE);
			
			it.remove();
		}
	}
	
	public Node getDestino(){
		return destino;
	}

	void setDestino(Node destino1) {
		destino = destino1;
	}

	public  HashMap<String,Node> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<String,Node> nodes1) {
		nodes = nodes1;
	}

	public Node getRoot(){
		return root;
	}

	public void setRoot(Node root1) {
		root = root1;
	}

	public void addNode(Node node) {
		nodes.put(node.getId(), node);
	}

	public Vector<String> getGarbageNodes() {
		
		HashMap<String,Node> copy = new HashMap<String,Node>(nodes);
		
		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();
		
		Vector<String> finale = new Vector<String>();
		
		while(it.hasNext()) {
			
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			
			if(((Node) pair.getValue()).isIsGarbage())
				if(((Node) pair.getValue()).getValor() > Utils.MIN_VALUE_GARBAGE){
					finale.add((String) pair.getKey());
				}
			it.remove();
		}
		return finale;
	}

	public void loadNodes(String filename){

		String idNode;
		
		int valorNode, valorDistanciaEstacao;
		
		Node node;
		
		Boolean isNumericNode = false;
		
		int posX, posY;
		
		String line;
		
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();
			while (line != null){
				valorNode = 0;
				valorDistanciaEstacao = 0;
				isNumericNode = false;
				posX = 0;
				posY = 0;
				
				String[] dividedStr = line.split(" ");
				if (!dividedStr[0].equals("No")){
					System.out.println("Erro 1");
					br.close();
					return;
				}
				if (!dividedStr[1].equals("-")){
					System.out.println("Erro 2");
					br.close();
					return;
				}
				idNode=dividedStr[2];

				if (dividedStr.length>3 && !dividedStr[3].equals("-")){
					System.out.println("Erro 3");
					br.close();
					return;
				}

				if(dividedStr.length>3)
					valorNode=Integer.parseInt(dividedStr[4]);

				if (dividedStr.length>3 && !dividedStr[5].equals("-")){
					System.out.println("Erro 4");
					br.close();
					return;
				}

				if(dividedStr.length>3){
					valorDistanciaEstacao=Integer.parseInt(dividedStr[6]);
					isNumericNode = true;
				}
				
				if (dividedStr.length>3 && !dividedStr[7].equals("-")){
					System.out.println("Erro 5");
					br.close();
					return;
				}

				if(dividedStr.length>3){
					posX=Integer.parseInt(dividedStr[8]);
					isNumericNode = true;
				}
				
				if (dividedStr.length>3 && !dividedStr[9].equals("-")){
					System.out.println("Erro 6");
					br.close();
					return;
				}

				if(dividedStr.length>3){
					posY=Integer.parseInt(dividedStr[10]);
					isNumericNode = true;
				}


				if (valorNode==0){
					node = new Node(idNode);
					if(isNumericNode)
						node.setDistanciaEstacao(valorDistanciaEstacao);
					addNode(node);
				}else{
					node = new Node(idNode, valorNode, posX, posY);
					if(isNumericNode)
						node.setDistanciaEstacao(valorDistanciaEstacao);
					addNode(node);
				}
				if(node.getId().equals("Central")) {
					setRoot(node);
				}
				else if(node.getId().equals("Estacao")) {
					setDestino(node);
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadEdges(String filename){

		BufferedReader br;
		String idNodeOrigem,idNodeDestino;
		int distancia;

		String line;
		try {
			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();
			while (line != null){

				String[] dividedStr = line.split(" ");

				if (!dividedStr[0].equals("Aresta")){
					System.out.println("Erro 1");
					br.close();
					return;
				}

				if (!dividedStr[1].equals("-")){
					System.out.println("Erro 2");
					br.close();
					return;
				}


				idNodeOrigem=dividedStr[2];

				if (!dividedStr[3].equals("-")){
					System.out.println("Erro 3");
					br.close();
					return;
				}

				idNodeDestino=dividedStr[4];


				if (!dividedStr[5].equals("-")){
					System.out.println("Erro 4");
					br.close();
					return;
				}

				distancia=Integer.parseInt(dividedStr[6]);
				Edge e = new Edge(nodes.get(idNodeOrigem),nodes.get(idNodeDestino), distancia);
				nodes.get(idNodeOrigem).addEdge(e);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e1) {
			System.out.println("Ficheiro nao existe");
			return;
		}
	}
	
	public void aStar(Vector <String> garbageNodes){
	   
		openQueue = new PriorityQueue<Node>(comparator);
		
		closeQueue = new ArrayList<Node>();
		
		openQueue.add(nodes.get("Central"));
		System.out.println("começouuuuuuuuuuuuuuuuuuuuuuuuuuu");
		System.out.println("tamanho do lixo " + garbageNodes.size());
		
		boolean stop = true;
		
		while(!openQueue.isEmpty() && stop){
			
			Node node = openQueue.poll();
			
			
		
			Vector<Node> nodeSons = node.getConnectedNodes();		
			
			for(int i=0;i<nodeSons.size();i++){		
			
				Node son = nodeSons.get(i);
				
				Vector<Node> astar = astarRecursive(son,garbageNodes,node);
				
				for(int j=0;j<astar.size();j++){
				
					if(!openQueue.contains(astar.get(j)) ){
						System.out.println("vou addicionar o no " + astar.get(j).getId());
						openQueue.add(astar.get(j));
					}
				}	
				
				if(nodeSons.get(i).getId().equals("Estacao")){
				
					stop=false;
					
					System.out.println("encontre");
				}
			}
			closeQueue.add(node);
		}
	}
	
	public Vector<Node> astarRecursive(Node son, Vector<String> garbageNodes, Node parent){
		
		Vector <Node> toBeReturned = new Vector<Node>();
		
		if(!son.getId().equals("Estacao")){
			
			if((garbageNodes.contains(son.getId()) && son.calculateTempGValue(parent)<son.getgValue() && parent.getAvailableCapacity()>0) || (canBypass() && (garbageNodes.contains(son.getId())||(parent.getAvailableCapacity()<Utils.MAX_TRUCK_VALUE)))){
				//System.out.println("entrou aqui " + son.getId() + " " + son.getValor());
				if(parent.getAvailableCapacity()>=son.getValor())
					
					son.setAvailableCapacity(parent.getAvailableCapacity()-son.getValor());
				else 
					son.setAvailableCapacity(0);
				
				if(son.getParent()!=null){
					//System.out.println("tou a substituir " + son.getId() + " o pai " + parent.getId());
				}
				
				son.setParent(parent);
				
				son.updateGValue();			
				
				son.updateFValue();
				
				toBeReturned.add(son);
				
			}else if(son.calculateTempGValueWithoutPassing(parent)<son.getgValue() ){
				
				if(son.getParent()!=null){
					//System.out.println("tou a substituir " + son.getId() + " o pai " + parent.getId());
				}
				
				son.setAvailableCapacity(parent.getAvailableCapacity());
				
				son.setParent(parent);
				
				son.updateGValue();
				
				son.updateFValue();
				
				for(int i=0;i< son.getConnectedNodes().size();i++){
					
					Vector <Node> temp = astarRecursive( son.getConnectedNodes().get(i),garbageNodes, son);
					
					for(int j=0;j<temp.size();j++){
						toBeReturned.addElement(temp.get(j));
					}
				}
			}
		}
		else{
			if(son.calculateTempGValue(parent)<son.getgValue() && parent.getAvailableCapacity()<Utils.MAX_TRUCK_VALUE){
				
				son.setParent(parent);
				
				son.setAvailableCapacity(parent.getAvailableCapacity());
				
				son.updateGValue();
				
				son.updateFValue();
			}
			
		}
		return toBeReturned;		
	}
	
	private boolean canBypass(){
		
		for(int i = 0; i < closeQueue.size(); i++){
			
			Node node = closeQueue.get(i);
			if(node.getValor() > Utils.MIN_VALUE_GARBAGE)
				return false;
			
		}
		
		return true;
	}

	public void printResults(){
		
		Node node = nodes.get("Estacao");
		
		while(true){
			
			if(node.getParent()!=null){
				System.out.println("vou do " + node.getParent().getId() + " para o " + node.getId() + " gvalue= " + node.getgValue());
			}
			else 
				break;
			
			node = node.getParent();
		}
	}
}