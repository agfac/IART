package Recolha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import Graph.Graph;
import Graph.Node;

public class RecolhadeResiduos {
	static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	public static String newline = System.getProperty("line.separator");
	private static Graph g = new Graph();
	
	public RecolhadeResiduos(){
		try {
			menuload(1);
		} catch (NumberFormatException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public Graph getGraph(){
		return g;
	}
	
	static void menuload(int i) throws InterruptedException, NumberFormatException, IOException {

		System.out.println("Choose File.");
		System.out.println("1 - nodes.txt/edges.txt");
		System.out.println("2 - nodes2.txt/edges2.txt");
		System.out.println("3 - nodes3.txt/edges3.txt");
		System.out.println("4 - nodes4.txt/edges4.txt");
		System.out.println("9 to exit!");

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
			//menuload();
			break;
		}

		
		System.out.println(g.getNodes().size());
		//getShitDone();
		//g.printResults();
		//GraphViewer gv = new GraphViewer(800, 800, true);
		//g.display(gv);
		//menu1(g);
	}
	
	public static void getShitDone() {
		Vector <String> garbageNodes = g.getGarbageNodes(); 
		while(garbageNodes.size()!=0){
			System.out.println("tamanho do lixo "+ garbageNodes.size());
			g.aStar(garbageNodes);
			getVoyage();
			resetGraphForVoyage();
			garbageNodes = g.getGarbageNodes();
		}
	}
	
	private static void resetGraphForVoyage(){
		g.resetGraphForVoyage();
	}
	
	private static void getVoyage(){
		Node son = g.getNodes().get("Estacao");
		Vector<Node> nodes = new Vector<Node>();
		nodes.add(son);
		System.out.println("nova viagem");
		while(son.getParent()!=null){
			System.out.println("vou do no "+ son.getParent().getId()+ " para o "+son.getId());
			son=son.getParent();
			nodes.add(son);
		}
		int capacity=60;
		
		for(int i=nodes.size()-1;i>=0;i--){
			if(capacity>0){
				if(capacity>nodes.get(i).getValor()){
					System.out.println(" no " + nodes.get(i).getId() + " com valor " + 0 + " valor " + nodes.get(i).getValor());
					capacity-=nodes.get(i).getValor();
					nodes.get(i).setValor(0);
				}else{
					System.out.println(" no " + nodes.get(i).getId() + " com valor " + (nodes.get(i).getValor()-capacity));
					nodes.get(i).setValor(nodes.get(i).getValor()-capacity);
					capacity=0;
				}
			}
		}
	}

}
