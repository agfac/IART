package GUI;



import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JComboBox;
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
		frmAAlgorithmWaste.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/truckR.png"));
		frmAAlgorithmWaste.setTitle("A* Algorithm: Waste collection problem");
		frmAAlgorithmWaste.setBounds(100, 100, 800, 800);
		frmAAlgorithmWaste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MainPanel(1);
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
		
		JPanel panel_2 = new JPanel();
		frmAAlgorithmWaste.getContentPane().add(panel_2, BorderLayout.EAST);
		
		String[] graphListArray = {"Map 1", "Map 2", "Map 3", "Map 4"};
		JComboBox graphList = new JComboBox(graphListArray);
		
		graphList.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JComboBox<String> combo = (JComboBox<String>) event.getSource();
		        int selectedGraph = combo.getSelectedIndex() + 1;
		        
		        frmAAlgorithmWaste.getContentPane().remove(panel);
		        panel = new MainPanel(selectedGraph);
		        frmAAlgorithmWaste.getContentPane().add(panel);
		        frmAAlgorithmWaste.getContentPane().validate();		        
		        frmAAlgorithmWaste.getContentPane().repaint();
		        

		        	
		    }
		});
		
		panel_2.add(graphList);
	}

	/**
	 * @return the frmAAlgorithmWaste
	 */
	public JFrame getFrmAAlgorithmWaste() {
		return frmAAlgorithmWaste;
	}

}
