package GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.Timer;

import Graph.Graph;
import Graph.Node;
import Recolha.RecolhadeResiduos;
import Graph.Edge;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	Queue<Edge> path;
	ArrayList<Edge> edges = new ArrayList<Edge>();


	private final int X = 40;
	private final int Y = 75;
	private final int L = 50;

	private Graph graph;
	private BufferedImage roadV;
	private BufferedImage roadH;
	private BufferedImage roadX;
	private BufferedImage bin;
	private BufferedImage dump;
	private BufferedImage grass;
	private BufferedImage truckB;
	private BufferedImage truckT;
	private BufferedImage truckL;
	private BufferedImage truckR;
	private BufferedImage truck;
	private BufferedImage start;
	private BufferedImage garbageLevel;

	private float truckX = 0;
	private float truckY = 0;
	private int truckSpeed = 100;
	private double garbage = 0;

	RecolhadeResiduos recolha;

	static Vector<Vector<String>> infoDaViagem;
	static HashMap<String,String> nodeValuesDisplay;
	int nrViagem = 0;
	String valueGarbageDisplay = "";

	public MainPanel(int j) {

		recolha = new RecolhadeResiduos(j);
		graph = recolha.getGraph();
		infoDaViagem = recolha.getInfoDaViagem();
		nodeValuesDisplay = recolha.getNodeValuesDisplay();

		try {
			roadV = ImageIO.read(new File("resources/roadV.png"));
			roadH = ImageIO.read(new File("resources/roadH.png"));
			roadX = ImageIO.read(new File("resources/roadX.png"));
			bin = ImageIO.read(new File("resources/bin.png"));
			dump = ImageIO.read(new File("resources/dump.png"));
			grass = ImageIO.read(new File("resources/grass.jpg"));
			truckB = ImageIO.read(new File("resources/truckB.png"));
			truckT = ImageIO.read(new File("resources/truckT.png"));
			truckR = ImageIO.read(new File("resources/truckR.png"));
			truckL = ImageIO.read(new File("resources/truckL.png"));

			start = ImageIO.read(new File("resources/start.png"));

			garbageLevel = ImageIO.read(new File("resources/garbageLevel.png"));
			truck = truckR;
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String,Node> copy = new HashMap<String,Node>(this.graph.getNodes());
		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();

			for(int i=0;i<((Node) pair.getValue()).getArestas().size();i++){
				edges.add(((Node) pair.getValue()).getArestas().get(i));
			}
			it.remove();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(garbageLevel, 80, 5,50,50,null);
		g.drawImage(truckR, 200, 0,60,60,null);

		g.setFont(new Font("Arial", 0, 30));
		g.setColor(Color.BLUE);
		//load
		g.drawString(Integer.toString((int)garbage), 140, 50);
		g.drawString(Integer.toString((int)nrViagem), 260, 50);

		for(int i=0;i<=10;i++){
			for(int j=0;j<=10;j++){
				g.drawImage(grass, X + i * L, Y + j * L, L, L, null);
			}
		}

		for(Edge i : edges) {


			int x1,x2,y1,y2, deltax, deltay;
			x1 = i.getOrigem().getPosX();
			x2 = i.getDestino().getPosX();
			y1 = i.getOrigem().getPosY();
			y2 = i.getDestino().getPosY();
			deltax = x1 - x2;
			deltay = y1 - y2;

			if(deltax == 0) {
				BufferedImage image =  roadV;
				int inc = deltay < 0 ? 1 : -1;

				int y = y1 + inc;
				while(y != y2) {
					g.drawImage(image, X + x1 * L, Y + y * L, L, L, null);
					y += inc;
				}
			}
			else if(deltay == 0) {
				BufferedImage image =  roadH;
				int inc = deltax < 0 ? 1 : -1;

				int x = x1 + inc;

				while(x != x2) {
					g.drawImage(image, X + x * L, Y + y1 * L, L, L, null);
					x += inc;
				}
			}else{
				BufferedImage image =  roadH;
				int inc = deltax < 0 ? 1 : -1;
				int x = inc;
				while(deltax+x!=0){
					g.drawImage(image, X + (x+x1) * L, Y + y1 * L, L, L, null);
					x += inc;	
				}
				inc = deltay < 0 ? 1 : -1;
				int y = inc;
				image =  roadV;
				while(deltay+y!=0){
					g.drawImage(image, X + x2 * L, Y + (y+y1) * L, L, L, null);
					y += inc;	
				}
				g.drawImage(image, X + (x2) * L, Y + (y1) * L, L, L, null);
			}


		}

		HashMap<String,Node> copy = new HashMap<String,Node>(this.graph.getNodes());

		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();

		while(it.hasNext()) {

			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();

			g.drawImage(grass, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY() * L, L, L, null);

			if(((Node) pair.getValue()) != null) {

				g.drawImage(roadX, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY() * L, L, L, null);

				if(((Node) pair.getValue()).getId().equals("Central")) 
					g.drawImage(start, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY()* L, L, L, null);

				else if(((Node) pair.getValue()).getId().equals("Estacao")) 

					g.drawImage(dump, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY() * L, L, L, null);
				else {

					g.drawImage(bin, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY()* L, L, L, null);

					g.setFont(new Font("Arial", 0, 30));

					valueGarbageDisplay=nodeValuesDisplay.get(((Node) pair.getValue()).getId());

					if(((Node) pair.getValue()).isIsGarbage()){
						g.setColor(Color.RED);

					}

					else 
						g.setColor(Color.WHITE);

					g.drawString(valueGarbageDisplay,X + (((Node) pair.getValue()).getPosX()) * L, Y + (((Node) pair.getValue()).getPosY()+1)* L );
				}
			}
			it.remove();
		}

		g.drawImage(truck,(int) (X + truckX * L), (int)(Y + truckY * L),L,L, null);

		g.dispose();
	}

	public void startSimulation() {
		path=recolha.getShitDone();
		new Timer(truckSpeed,paintTimer).start();
		setDoubleBuffered(true);
	}

	Action paintTimer = new AbstractAction() {
		public void actionPerformed(ActionEvent event) {
			Edge e = path.peek();

			if(e == null)
				return;

			int x1,x2,y1,y2, deltax, deltay;



			x1 = e.getOrigem().getPosX();
			x2 = e.getDestino().getPosX();
			y1 = e.getOrigem().getPosY();
			y2 = e.getDestino().getPosY();


			deltax = x1 - x2;
			deltay = y1 - y2;
			if(deltay == 0) {

				float inc = (float) (deltax < 0 ? 0.1 : -0.1);
				truck = inc < 0 ? truckL : truckR;

				truckX += inc;

				if((x2 > x1 && truckX >= x2) || (x2 < x1 && truckX <= x2)) {
					truckX = path.peek().getDestino().getPosX();
					path.remove();
					garbUpdate(e.getDestino());
				}
			}
			else if (deltax == 0) {
				float inc = (float) (deltay < 0 ? 0.1 : -0.1);
				truck = inc < 0 ? truckT : truckB;
				truckY += inc;

				if((y2 > y1 && truckY >= y2) || (y2 < y1 && truckY <= y2)) {
					truckY = path.peek().getDestino().getPosY();					
					path.remove();
					garbUpdate(e.getDestino());
				}
			}else{
				float inc = (float) (deltax < 0 ? 0.1 : -0.1);
				truck = inc < 0 ? truckL : truckR;
				truckX += inc;
				if((x2 > x1 && truckX >= x2) || (x2 < x1 && truckX <= x2)) {
					truckX = path.peek().getDestino().getPosX();
					inc = (float) (deltay < 0 ? 0.1 : -0.1);
					truck = inc < 0 ? truckT : truckB;
					truckY += inc;
					if((y2 > y1 && truckY >= y2) || (y2 < y1 && truckY <= y2)){
						truckY = path.peek().getDestino().getPosY();
						path.remove();

						garbUpdate(e.getDestino());

						if(e.getDestino().getId().equals("Estacao")){
							truckY=0;
							truckX=0;
							garbage=0;
							nrViagem++;
						}
					}
				}
			}



			repaint();
		}
	};

	public void garbUpdate(Node node){

		if(infoDaViagem.size() >= nrViagem){
			for(int x = 0; x < infoDaViagem.elementAt(nrViagem).size(); x++){
				String allInfo = infoDaViagem.elementAt(nrViagem).elementAt(x);
				String[] tokens = allInfo.split("-");
				String nodeId = tokens[0];
				String value = tokens[1];
				String lixo = tokens[2];

				System.out.println(nodeId + "-" + value);
				if(node.getId().equals(nodeId) && node.isIsGarbage()){
					node.setIsGarbage(Boolean.getBoolean(lixo));
					garbage+=Integer.parseInt(value);
					
					System.out.println("GARBAGE: " + garbage);
					
					DecimalFormat format = new DecimalFormat();
			        format.setDecimalSeparatorAlwaysShown(false);
			        
					nodeValuesDisplay.replace(nodeId, format.format((Integer.parseInt(nodeValuesDisplay.get(nodeId)) - Integer.parseInt(value))));
				}

			}
		}
	}
}
