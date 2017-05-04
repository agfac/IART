import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class RecolhadeResiduos {
	static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	public static String newline = System.getProperty("line.separator");
	private static Graph g = new Graph();
	private final static int GARBAGE_LIMIT = 15;
	
	public static void main(String[] args) throws NumberFormatException, InterruptedException, IOException {
		menuload();
	}

	static Vector<String> removeNodesVec(Vector<String> src, Vector<String> tg) {

		for(int i = 0; i < src.size(); i++) {
			for(int j = 0; j < tg.size(); j++){
				if( src.get(i).equals(tg.get(j))) {
					src.remove(i);
					i--;
					break;
				}
			}
		}
		return src;

	}

	static void printroute(Vector<String> route) {
		for(int i = 0; i < route.size(); i++) {
			System.out.print(route.get(i));
			if(i < route.size()-1) {
				System.out.print(" | ");
			}
		}
		System.out.println(newline);
	}
	
	static Vector<Node> getGargabeWaste(){
		HashMap<String,Node> a = g.getNodes();
		Vector<Node> nodes = new Vector<Node>();
		
		Iterator it = a.entrySet().iterator();

		while(it.hasNext()){
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			Node n = pair.getValue();
			
			if(n.getValor() >= GARBAGE_LIMIT){
				nodes.add(n);
			}
		}
		return nodes;
	}
	
	
	
	static void menu1(Graph g) throws NumberFormatException, InterruptedException, IOException {
		//		system("CLS");
		Vector<String> gnodes;
		Vector<String> gnodesf;
		gnodes = g.getGarbageNodes();
		gnodesf = gnodes;

		Vector<Vector <String> > temproutes = null;

		System.out.println("Garbage nodes exceeding capacity!");
		printroute(gnodes);
		System.out.println(newline);


		System.out.println("Choose operating mode");
		System.out.println("1 - 1 Truck/Unlimited Space");
		System.out.println("2 - Multiple Trucks/Limited Space");
		int i;
		i = Integer.parseInt(inFromUser.readLine());

		Vector<String> empty = null;
		Vector<String> finalVector = null;
		int distf = Integer.MAX_VALUE;
		int zero = 0;
		int distt = 0;


		switch(i) {
		case 1:
			g.getMinRoute(gnodes,empty,zero,finalVector,distf);
			temproutes.add(finalVector);
			System.out.println(newline);
			printroute(finalVector);
			System.out.println("Distancia total: " + distf + "m");
			Thread.sleep(4000);
			break;
		case 2:
			while(!gnodes.isEmpty()) {
				Vector<String> temp = null;
				finalVector.clear();

				distf = Integer.MAX_VALUE;
				g.generateRoutes(gnodes,temp,zero);

				zero = 0;

				g.getMinRoute(temp,empty,zero,finalVector,distf);
				System.out.println(newline);
				temproutes.add(finalVector);
				printroute(finalVector);
				System.out.println("Distancia parcial: " + distf + "m");
				distt += distf;
				gnodes = removeNodesVec(gnodes, finalVector);
			}
			System.out.println("Distancia total: " + distt + "m");
			Thread.sleep(4000);
			break;
		}

		//g.edit(gv,gnodesf,temproutes);
		//		system("PAUSE");
		menuload();
	}

	static void menuload() throws InterruptedException, NumberFormatException, IOException {

		int i;
		//		system("CLS");
		System.out.println("Choose File.");
		System.out.println("1 - nodes.txt/edges.txt");
		System.out.println("2 - nodes2.txt/edges2.txt");
		System.out.println("3 - nodes3.txt/edges3.txt");
		System.out.println("4 - nodes4.txt/edges4.txt");
		System.out.println("9 to exit!");
		i = Integer.parseInt(inFromUser.readLine());

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
			menuload();
			break;
		}


		 g.aStar();
		 g.printResults();
		//GraphViewer gv = new GraphViewer(800, 800, true);
		//g.display(gv);
		//menu1(g);
	}

}
