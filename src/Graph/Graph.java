package Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Vector;

public class Graph {

	HashMap<String,Node> nodes = new HashMap<String,Node>();
	Node root;
	Node destino;
	int MIN_VALUE_GARBAGE = 10;
	int MAX_TRUCK_VALUE =50;
	Comparator<Node> comparator = new NodeComparator();
    PriorityQueue<Node> openQueue = new PriorityQueue<Node>(comparator);
	
	public Graph() {
	}
	
	public void resetGraphForVoyage(){
		HashMap<String,Node> copy = new HashMap<String,Node>(nodes);
		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			((Node) pair.getValue()).setParent(null);
			((Node) pair.getValue()).setAvailableCapacity(50);
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

	public void resetMap() {
		Iterator<Entry<String, Node>> it = nodes.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			((Node) pair.getValue()).setTempdist(99999);
			((Node) pair.getValue()).setTempvisited(false);
			it.remove();
		}
	}

	public void getDistfromNode(String Node1, String Node2, int dist, Vector<String> route) {
		resetMap();
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		Vector<String> emptyvec = new Vector<String>();
		nodes.get(Node1).setTempdist(0);
		nodes.get(Node1).setTemproute(emptyvec);
		nodes.get(Node1).setTempvisited(true);
		pq.add(nodes.get(Node1));
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			Vector<Edge> tempEdges = temp.getArestas();
			for(int i = 0; i < tempEdges.size(); i++) {
				int tempdist = 0;
				tempdist = temp.getTempdist() + tempEdges.get(i).getDistancia();
				if(tempdist < tempEdges.get(i).getDestino().getTempdist()) {
					tempEdges.get(i).getDestino().setTempdist(tempdist);
					Vector<String> tempnodes = temp.getTemproute();
					tempnodes.add(temp.getId());
					tempEdges.get(i).getDestino().setTemproute(tempnodes);
				}
				if(!(tempEdges.get(i).getDestino().isTempvisited())){
					tempEdges.get(i).getDestino().setTempvisited(true);
					pq.add(tempEdges.get(i).getDestino());
				}
			}
		}
		dist = nodes.get(Node2).getTempdist();
		route = nodes.get(Node2).getTemproute();
	}

	public void getMinRoute(Vector<String> garbage,Vector<String> currentRoute, int currentDist, Vector<String> bestRoute, int bestDist) {
		for(int i = 0; i < garbage.size(); i++) {
			Vector<String> temproute = currentRoute;
			Vector<String> partialroute = new Vector<String>();
			int dist = 0;
			int tempdist = currentDist;


			if(currentRoute.isEmpty()) {
				getDistfromNode(root.getId(),garbage.get(i),dist,partialroute);
				tempdist += dist;
				for(int j = 0; j < partialroute.size(); j++) {
					temproute.add(partialroute.get(j));
				}
				temproute.add(garbage.get(i));
			}
			else {
				getDistfromNode(temproute.lastElement(),garbage.get(i),dist,partialroute);
				tempdist += dist;
				temproute.remove(temproute.lastElement());
				for(int j = 0; j < partialroute.size(); j++) {
					temproute.add(partialroute.get(j));
				}
				temproute.add(garbage.get(i));
			}
			if(tempdist >= bestDist) {
				continue;
			}

			else {
				Vector<String> tempgarbage = garbage;
				tempgarbage.remove(i);
				getMinRoute(tempgarbage,temproute,tempdist,bestRoute,bestDist);
			}

		}
		if(garbage.isEmpty()) {
			Vector<String> partialroute = new Vector<String>();
			int dist = 0;
			getDistfromNode(currentRoute.lastElement(),destino.getId(),dist,partialroute);
			currentDist += dist;
			currentRoute.remove(0);
			for(int j = 0; j < partialroute.size(); j++) {
				currentRoute.add(partialroute.get(j));
			}
			currentRoute.add(destino.getId());

			if(currentDist < bestDist) {
				bestRoute = currentRoute;
				bestDist = currentDist;
			}
		}
	}

	public Vector<String> getGarbageNodes() {
		HashMap<String,Node> copy = new HashMap<String,Node>(nodes);
		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();
		Vector<String> finale = new Vector<String>();
		while(it.hasNext()) {
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			if(((Node) pair.getValue()).isIsGarbage())
				if(((Node) pair.getValue()).getValor() > MIN_VALUE_GARBAGE){
					finale.add((String) pair.getKey());
				}
			it.remove();
		}
		return finale;
	}

	/*public void generateRoutes(Vector<String> gn, Vector<String> best, int bestvalue) {
		int value = 0;
		for(int i = 0; i < gn.size(); i++) {
			value += nodes.get(gn.get(i)).getValor();
		}
		if(value > MAX_TRUCK_VALUE) {
			for(int i = 0; i < gn.size(); i++) {
				Vector<String> temp = gn;
				temp.removeElementAt(i);
				generateRoutes(temp,best,bestvalue);
			}
		}
		else if(value > bestvalue) {
			best = gn;
			bestvalue = value;
		}
	}*/

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

	Edge findAresta(Node src, String dest) {
		for(int i = 0 ; i < src.getArestas().size();i++) {
			Edge e = src.getArestas().get(i);
			Node n = e.getDestino();
			if(n.getId() == dest){
				return e;
			}
		}
		return null;
	}
	
	public void aStar(Vector <String> garbageNodes){
	    openQueue = new PriorityQueue<Node>(comparator);
		openQueue.add(nodes.get("Central"));
		
		Boolean stop=true;
		while(!openQueue.isEmpty() && stop){
			
			Node node = openQueue.poll();
			Vector<Node> nodeSons = node.getConnectedNodes();		
			for(int i=0;i<nodeSons.size();i++){		
				Node son = nodeSons.get(i);
				Vector<Node> astar = astarRecursive(son,garbageNodes,node);
				for(int j=0;j<astar.size();j++){
					if(!openQueue.contains(astar.get(j)) ){
						openQueue.add(astar.get(j));
					}
				}	
				if(nodeSons.get(i).getId().equals("Estacao"))
					stop=false;
			}
		}
	}
	
	public Vector<Node> astarRecursive(Node son, Vector<String> garbageNodes, Node parent){
		Vector <Node> roBeReturned = new Vector<Node>();
		if(!son.getId().equals("Estacao")){
			if(garbageNodes.contains(son.getId()) && son.calculateTempGValue(parent)<son.getgValue() && parent.getAvailableCapacity()>0){
				if(parent.getAvailableCapacity()>=son.getValor())
					son.setAvailableCapacity(parent.getAvailableCapacity()-son.getValor());
				else son.setAvailableCapacity(son.getValor()-parent.getAvailableCapacity());
				son.setParent(parent);
				son.updateGValue();			
				son.updateFValue();
				roBeReturned.add(son);
			}else if(son.calculateTempGValueWithoutPassing(parent)<son.getgValue()){
				son.setAvailableCapacity(parent.getAvailableCapacity());
				son.setParent(parent);
				son.updateGValue();
				son.updateFValue();
				for(int i=0;i< son.getConnectedNodes().size();i++){
					Vector <Node> temp = astarRecursive( son.getConnectedNodes().get(i),garbageNodes, son);
					for(int j=0;j<temp.size();j++){
						roBeReturned.addElement(temp.get(j));
					}
				}
			}
		}else{
			if(son.calculateTempGValue(parent)<son.getgValue() && parent.getAvailableCapacity()<50){
				son.setParent(parent);
				son.setAvailableCapacity(parent.getAvailableCapacity());
				son.updateGValue();
				son.updateFValue();
			}
			
		}
		return roBeReturned;		
	}

	public void printResults(){
		
		Node node = nodes.get("Estacao");
		while(true){
			if(node.getParent()!=null){
				System.out.println("vou do " + node.getParent().getId() + " para o " + node.getId() + " gvalue= " + node.getgValue());
			}
			else break;
			node = node.getParent();
		}
	}
	
	

	/*Node getNextNode(Vector<Node> nodes){
		Node nextNode = null;
		//int gValue = currentTruck.getGvalue();
		int gPlusH = Integer.MAX_VALUE;
		
		for(int i = 0; i<nodes.size();i++){
			Node no = nodes.get(i);
			int distanciaEstacaoNo = no.getDistanciaEstacao();
			
			if( gValue + distanciaEstacaoNo < gPlusH ){
				gPlusH = gValue + distanciaEstacaoNo;
				nextNode = no;
			}
		}
		currentTruck.updateDistancia_percorrida(nextNode.getValueEdge(nextNode.get));
		return nextNode;
	}*/
}