package Recolha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

import Graph.Graph;
import Graph.Node;
import Graph.Edge;

public class RecolhadeResiduos {
	static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	public static String newline = System.getProperty("line.separator");
	private static Graph g = new Graph();
	
	static Vector<Vector<String>> infoDaViagem = new Vector<Vector<String>>();
	
	public RecolhadeResiduos(){
		try {
			menuload(1);
		} catch (NumberFormatException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
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
		case 9:
			System.out.println("Exiting");
			Thread.sleep(3000);
			System.exit(0);
			break;
		case 1:
			g.loadNodes("nodes.txt");
			g.loadEdges("edges.txt");
			break;
		case 2:
			g.loadNodes("nodes2.txt");
			g.loadEdges("edges2.txt");
			break;
		case 3:
			g.loadNodes("nodes3.txt");
			g.loadEdges("edges3.txt");
			break;
		case 4:
			g.loadNodes("nodes4.txt");
			g.loadEdges("edges4.txt");
			break;
		default:
			System.out.println("Unknown Input!");
			Thread.sleep(1000);
			break;
		}

		//getShitDone();
		//g.printResults();
	}
	
	public Queue<Edge> getShitDone() {
		Vector <String> garbageNodes = g.getGarbageNodes();
		Queue<Edge> path = new LinkedList<Edge>();
		Vector<Node> semiPath = new Vector<Node>();
		while(garbageNodes.size()!=0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.aStar(garbageNodes);
			semiPath=getVoyage();
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
			if(!son.getId().equals("Estacao")){
				nosDaViagem.add(son.getId()+"-"+son.getValor());
			}
			System.out.println("vou do no "+ son.getParent().getId()+ " para o "+son.getId());
			son=son.getParent();
			nodes.add(son);
		}
		infoDaViagem.add(nosDaViagem);
		
		int capacity=Utils.MAX_TRUCK_VALUE;
		
		for(int i=nodes.size()-1;i>=0;i--){
			if(capacity>0){
				if(capacity>nodes.get(i).getValor()){
					capacity-=nodes.get(i).getValor();
					nodes.get(i).setValor(0);
				}else{
					nodes.get(i).setValor(nodes.get(i).getValor()-capacity);
					capacity=0;
				}
			}
		}
		return nodes;
	}

}
