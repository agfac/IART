package GUI;



import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow {
	private MainPanel panel;
	JFrame frmAAlgorithmWaste;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmAAlgorithmWaste.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmAAlgorithmWaste = new JFrame();
		frmAAlgorithmWaste.setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\truckIcon.png"));
		frmAAlgorithmWaste.setTitle("A* Algorithm: Waste collection problem");
		frmAAlgorithmWaste.setBounds(100, 100, 800, 800);
		frmAAlgorithmWaste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MainPanel();
		frmAAlgorithmWaste.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		frmAAlgorithmWaste.getContentPane().add(panel_1, BorderLayout.SOUTH);

		JButton btnStartSimulation = new JButton("Start simulation");
		btnStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.startSimulation();
			}
		});

		panel_1.add(btnStartSimulation);
	}

	/**
	 * @return the frmAAlgorithmWaste
	 */
	public JFrame getFrmAAlgorithmWaste() {
		return frmAAlgorithmWaste;
	}

	/*private void test1() {
		Class<? extends DefaultEdge> edgeClass = null;
		ProgramData data = ProgramData.getInstance();
		data.setMultiple(100);
		Graph graph = new Graph(edgeClass);
		Node n1 = new Node(Node.CROSSROAD);
		n1.setPosition(1, 1);
		Node n2 = new Node(Node.CROSSROAD);
		n2.setPosition(3, 1);
		Node n3 = new Node(Node.CROSSROAD);
		n3.setPosition(6, 1);
		Node n4 = new Node(Node.CROSSROAD);
		n4.setPosition(9, 1);
		Node n6 = new Node(Node.CROSSROAD);
		n6.setPosition(3, 4);
		Node n5 = new Node(Node.GARBAGE_CONTAINER);
		n5.setPosition(1, 4);
		Node n7 = new Node(Node.CROSSROAD);
		n7.setPosition(6, 4);
		Node n8 = new Node(Node.GARBAGE_CONTAINER);
		n8.setPosition(9, 4);
		Node n9 = new Node(Node.CROSSROAD);
		n9.setPosition(3, 6);
		Node n10 = new Node(Node.GARBAGE_CONTAINER);
		n10.setPosition(6, 6);
		Node n11 = new Node(Node.PETROL_STATION);
		n11.setPosition(1, 8);
		Node n12 = new Node(Node.CROSSROAD);
		n12.setPosition(6, 8);
		Node n13 = new Node(Node.DUMP);
		n13.setPosition(9, 8);

		graph.addVertex(n1);
		graph.addVertex(n2);
		graph.addVertex(n3);
		graph.addVertex(n4);
		graph.addVertex(n5);
		graph.addVertex(n6);
		graph.addVertex(n7);
		graph.addVertex(n8);
		graph.addVertex(n9);
		graph.addVertex(n10);
		graph.addVertex(n11);
		graph.addVertex(n12);
		graph.addVertex(n13);

		graph.addEdge(n1,n2,20,false);
		graph.addEdge(n2,n6,20,false);
		graph.addEdge(n3,n2,30,true);
		graph.addEdge(n3,n4,30,false);
		graph.addEdge(n3,n7,30,false);
		graph.addEdge(n5,n6,30,false);
		graph.addEdge(n7,n8,30,false);
		graph.addEdge(n7,n10,30,false);
		graph.addEdge(n9,n10,30,false);
		graph.addEdge(n6,n9,30,false);
		graph.addEdge(n5,n11,30,false);
		graph.addEdge(n10,n12,30,false);
		graph.addEdge(n11,n12,30,false);
		graph.addEdge(n12,n13,30,false);
		graph.addEdge(n8,n13,30,false);

		DijkstraShortestPath<Node, Edge> b = new DijkstraShortestPath<Node, Edge>(graph, n1, n12);
		DijkstraShortestPath<Node, Edge> c = new DijkstraShortestPath<Node, Edge>(graph, n4, n8);
		Truck truck = new Truck(3500,200);
		graph.calculateDistances();
		data.setTruck(truck);
		data.setGraph(graph);

		Queue<Edge> s = data.searchPath( n1, n13);

		for(Edge e : s)
		{
			System.out.print(e.getSourceId()+"->"+e.getTargetId());
		}
		System.out.println();
		data.getGraph().setTruckPath(s);
		System.out.println(b.getPath());
		System.out.println(b.getPathLength());
		System.out.println();
		System.out.println(c.getPath());
		System.out.println(c.getPathLength());
	}*/

}
