package Recolha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;
import java.util.Map.Entry;

import Graph.Graph;
import Graph.Node;
import Graph.Edge;

public class RecolhadeResiduos {
			
	private static Graph g = new Graph();
	
	static Vector<Vector<String>> infoDaViagem = new Vector<Vector<String>>();
	
	private static HashMap<String,String> nodeValuesDisplay = new HashMap<String,String>();
	
	public RecolhadeResiduos(int i){
		
		try {
			
			menuload(i);
			loadNodeDisplay();
			
		} catch (NumberFormatException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	public Vector<Vector<String>> getInfoDaViagem(){
		return infoDaViagem;
	}
	
	public Graph getGraph(){
		return g;
	}
	
	static void menuload(int i) throws InterruptedException, NumberFormatException, IOException {

		switch(i) {
		
		case 1:
			g.loadNodes("resources/nodes.txt");
			g.loadEdges("resources/edges.txt");
			break;
		case 2:
			g.loadNodes("resources/nodes2.txt");
			g.loadEdges("resources/edges2.txt");
			break;
		case 3:
			g.loadNodes("resources/nodes3.txt");
			g.loadEdges("resources/edges3.txt");
			break;
		case 4:
			g.loadNodes("resources/nodes4.txt");
			g.loadEdges("resources/edges4.txt");
			break;
		default:
			System.out.println("Unknown Input!");
			Thread.sleep(1000);
			break;
		}
	}
	
	private void loadNodeDisplay(){
		
		HashMap<String,Node> nodes = g.getNodes();
		
		Iterator<Entry<String, Node>> it = nodes.entrySet().iterator();
		
		while(it.hasNext()){
			
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			
			addNodeDisplay((String)pair.getKey(), String.valueOf((pair.getValue().getValor())));
		}
	}
	
	public Queue<Edge> getShitDone() {
		
		Vector <String> garbageNodes = g.getGarbageNodes();
		
		Queue<Edge> path = new LinkedList<Edge>();
		
		Vector<Node> semiPath = new Vector<Node>();
		
		while(garbageNodes.size()!=0){
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			g.aStar(garbageNodes);
			
			semiPath = getVoyage();
			
			for(int i=semiPath.size()-1;i>0;i--){
			
				for(int j=0;j<semiPath.get(i).getArestas().size();j++){
				
					if(semiPath.get(i).getArestas().get(j).getDestino().getId().equals(semiPath.get(i-1).getId())){
					
						path.add(semiPath.get(i).getArestas().get(j));
						
						break;
					}
				}
			}
			resetGraphForVoyage();
		
			garbageNodes = g.getGarbageNodes();
		}
		return path;
	}
	
	private static void resetGraphForVoyage(){
		g.resetGraphForVoyage();
	}
	
	private static Vector<Node> getVoyage(){
		
		Node son = g.getNodes().get("Estacao");
		
		Vector<Node> nodes = new Vector<Node>();
		
		nodes.add(son);
		
		System.out.println("nova viagem");
		
		Vector<String> nosDaViagem = new Vector<String>();
		
		while(son.getParent()!=null){
			
			System.out.println("vou do no "+ son.getParent().getId()+ " para o "+son.getId());
			
			son=son.getParent();
			
			nodes.add(son);
		}
		
		int capacity=Utils.MAX_TRUCK_VALUE;
		
		for(int i=nodes.size()-1;i>=0;i--){
			
			if(capacity>0 && nodes.get(i).isIsGarbage()){
			
				int num=nodes.get(i).getValor();
				
				if(capacity>nodes.get(i).getValor()){
				
					capacity-=nodes.get(i).getValor();
					
					nodes.get(i).setValor(0);
				
				}else{
				
					nodes.get(i).setValor(nodes.get(i).getValor()-capacity);
					
					num=capacity;
					
					capacity=0;
				}

				if(!son.getId().equals("Estacao")){
					
					if(nodes.get(i).getValor()>Utils.MAX_TRUCK_VALUE )
						nosDaViagem.add(nodes.get(i).getId()+"-"+num+"-true");
					else 
						nosDaViagem.add(nodes.get(i).getId()+"-"+num+"-false");
					
				}
			}
		}
		infoDaViagem.add(nosDaViagem);
		return nodes;
	}

	/**
	 * @return the nodeValuesDisplay
	 */
	public HashMap<String, String> getNodeValuesDisplay() {
		return nodeValuesDisplay;
	}

	/**
	 * @param nodeValuesDisplay the nodeValuesDisplay to set
	 */
	public void setNodeValuesDisplay(HashMap<String, String> nodeValuesDisplay) {
		this.nodeValuesDisplay = nodeValuesDisplay;
	}
	
	public static void addNodeDisplay(String id, String value){
		nodeValuesDisplay.put(id, value);
	}
	
	public String getValueNodeDisplay(String id){
		
		return nodeValuesDisplay.get(id);
	}
}
