package GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
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

	ArrayList<Edge> edges = new ArrayList<Edge>();
	

	private final int X = 40;
	private final int Y = 75;
	private final int L = 50;

	private Graph graph;
	private BufferedImage roadV;
	private BufferedImage roadH;
	private BufferedImage roadL;
	private BufferedImage roadR;
	private BufferedImage roadT;
	private BufferedImage roadB;
	private BufferedImage roadX;
	private BufferedImage bin;
//	private BufferedImage bin2;
	private BufferedImage gas;
	private BufferedImage dump;
	private BufferedImage grass;
	private BufferedImage truckB;
	private BufferedImage truckT;
	private BufferedImage truckL;
	private BufferedImage truckR;
	private BufferedImage truck;
	private BufferedImage start;
	private BufferedImage footPrints;


	private BufferedImage gasLevel;
	private BufferedImage garbageLevel;

	private float truckX = 0;
	private float truckY = 0;
	private int truckSpeed = 100;
	private double distance = 0;
	private double fuel = 0;
	private double garbage = 0;





	public MainPanel() {

		RecolhadeResiduos recolha = new RecolhadeResiduos();
		graph = recolha.getGraph();
		System.out.println(graph.getNodes().size());
		try {
			roadV = ImageIO.read(new File("resources/roadV.png"));
			roadH = ImageIO.read(new File("resources/roadH.png"));
			roadR = ImageIO.read(new File("resources/roadR.png"));
			roadL = ImageIO.read(new File("resources/roadL.png"));
			roadT = ImageIO.read(new File("resources/roadT.png"));
			roadB = ImageIO.read(new File("resources/roadB.png"));
			roadX = ImageIO.read(new File("resources/roadX.png"));
			bin = ImageIO.read(new File("resources/bin.png"));
		//	bin2 = ImageIO.read(new File("resources/bin2.png"));
			gas = ImageIO.read(new File("resources/gas.png"));
			dump = ImageIO.read(new File("resources/dump.png"));
			grass = ImageIO.read(new File("resources/grass.jpg"));
			truckB = ImageIO.read(new File("resources/truckB.png"));
			truckT = ImageIO.read(new File("resources/truckT.png"));
			truckR = ImageIO.read(new File("resources/truckR.png"));
			truckL = ImageIO.read(new File("resources/truckL.png"));
			
			start = ImageIO.read(new File("resources/start.png"));
			
			gasLevel = ImageIO.read(new File("resources/gasLevel2.png"));
			garbageLevel = ImageIO.read(new File("resources/garbageLevel.png"));
			footPrints = ImageIO.read(new File("resources/distance.png"));
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
		g.drawImage(gasLevel, 220, 5,50,50,null);
		g.drawImage(footPrints, 380, 5,50,50,null);

		g.setFont(new Font("Arial", 0, 12));
		g.setColor(Color.WHITE);

		//g.drawString((int)ProgramData.getInstance().getTruck().getCapacity()+"", 93, 45);
		//g.drawString("500", 240, 38);


		g.setFont(new Font("Arial", 0, 30));
		g.setColor(Color.RED);
		//load
		g.drawString(Integer.toString((int)garbage), 140, 50);
		//GAS
		//g.drawString(Integer.toString((int)fuel - (int)Math.floor(distance) * ProgramData.getInstance().getMultiple()), 280, 50);
		g.drawString(Integer.toString((int)fuel),280,50);
		g.drawString(Integer.toString((int)Math.floor(distance)), 440, 50);
		
		
		
		

		for(Edge i : edges) {


			int x1,x2,y1,y2, deltax, deltay;
			x1 = i.getOrigem().getPosX();
			x2 = i.getDestino().getPosX();
			y1 = i.getOrigem().getPosY();
			y2 = i.getDestino().getPosY();
			deltax = x1 - x2;
			deltay = y1 - y2;

			//System.out.printf("V1(%d,%d) V2(%d,%d) Dx=%d Dy=%d",x1,y1,x2,y2,deltax,deltay);
			if(deltax == 0) {
				BufferedImage image =  roadV;
				//System.out.printf("  X\n");
				int inc = deltay < 0 ? 1 : -1;

				int y = y1 + inc;
				while(y != y2) {
					g.drawImage(image, X + x1 * L, Y + y * L, L, L, null);
					y += inc;
				}
			}
			else if(deltay == 0) {
				BufferedImage image =  roadH;
				//System.out.printf("  Y\n");
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
				System.out.println("no " + i.getOrigem().getId());
				System.out.println("deltax "+ deltax);
				while(deltax+x!=0){
					System.out.println(x);
					g.drawImage(image, X + (x+x1) * L, Y + y1 * L, L, L, null);
					x += inc;	
				}
				inc = deltay < 0 ? 1 : -1;
				int y = inc;
				image =  roadV;
				System.out.println("deltay "+ deltay);
				while(deltay+y!=0){
					System.out.println(y);
					g.drawImage(image, X + x2 * L, Y + (y+y1) * L, L, L, null);
					y += inc;	
				}
				g.drawImage(image, X + (x2) * L, Y + (y1) * L, L, L, null);
			}
			

		}
		
		HashMap<String,Node> copy = new HashMap<String,Node>(this.graph.getNodes());
		Iterator<Entry<String, Node>> it = copy.entrySet().iterator();
		System.out.println(this.graph.getNodes().size());
		while(it.hasNext()) {
			Map.Entry<String, Node> pair = (Map.Entry<String, Node>)it.next();
			g.drawImage(grass, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY() * L, L, L, null);
			if(((Node) pair.getValue()) != null) {
				g.drawImage(roadX, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY() * L, L, L, null);
				if(((Node) pair.getValue()).getId().equals("Central")) 
					g.drawImage(start, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY()* L, L, L, null);
				else if(((Node) pair.getValue()).getId().equals("Estacao")) 
					g.drawImage(dump, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY() * L, L, L, null);
				else g.drawImage(bin, X + ((Node) pair.getValue()).getPosX() * L, Y + ((Node) pair.getValue()).getPosY()* L, L, L, null);
			}
			it.remove();
		}

		g.drawImage(truck,(int) (X + truckX * L), (int)(Y + truckY * L),L,L, null);

		Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
		g.dispose();

	}

	public void startSimulation() {
		new Timer(truckSpeed,paintTimer).start();
		setDoubleBuffered(true);
	}

	Action paintTimer = new AbstractAction() { // functionality of our timer:
		public void actionPerformed(ActionEvent event) {
			
			//Edge e = path.peek();

//			if(e == null)
//				return;

			int x1,x2,y1,y2, deltax, deltay;

//			x1 = e.getSource().getX();
//			x2 = e.getTarget().getX();
//			y1 = e.getSource().getY();
//			y2 = e.getTarget().getY();

//			deltax = x1 - x2;
//			deltay = y1 - y2;



//			if(deltay == 0) {
//				float inc = (float) (deltax < 0 ? 0.1 : -0.1);
//				truck = inc < 0 ? truckL : truckR;
//
//				truckX += inc;
//
////				if((x2 > x1 && truckX >= x2) || (x2 < x1 && truckX <= x2)) {
////					truckX = path.peek().getTarget().getX();
////					ProgramData.getInstance().setActualIndex(ProgramData.getInstance().getActualIndex()+1);
////					if(ProgramData.getInstance().isGarbageIndex())
////						garbage += 100;
////					
////					if(ProgramData.getInstance().isFuelIndex())
////						fuel = ProgramData.getInstance().getTruck().getFuel();
////					
////					if(path.peek().getTarget().getType() == Node.DUMP && garbage == ProgramData.getInstance().getTruck().getCapacity())
////						garbage = 0;
////					
////					path.remove();
////				}
//			}
////			else if (deltax == 0) {
////				float inc = (float) (deltay < 0 ? 0.1 : -0.1);
////				truck = inc < 0 ? truckT : truckB;
////
////				truckY += inc;
////
////				if((y2 > y1 && truckY >= y2) || (y2 < y1 && truckY <= y2)) {
////					ProgramData.getInstance().setActualIndex(ProgramData.getInstance().getActualIndex()+1);
////					truckY = path.peek().getTarget().getY();
////					
////					if(ProgramData.getInstance().isGarbageIndex())
////						garbage += 100;
////					
////					if(ProgramData.getInstance().isFuelIndex())
////						fuel = ProgramData.getInstance().getTruck().getFuel();
////						
////					if(path.peek().getTarget().getType() == Node.DUMP && garbage == ProgramData.getInstance().getTruck().getCapacity())
////						garbage = 0;
////					
////					path.remove();
////				}
////			}
////
////			distance += 0.1*ProgramData.getInstance().getMultiple();
////			fuel -= 0.1*ProgramData.getInstance().getMultiple();
//
//			repaint();
//
//		}
		}
	};

}
