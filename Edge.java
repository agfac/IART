
public abstract class Edge {
	private static int intid = 0;
	private int graphid;
	private Node destino;
	private int distancia;

	public Edge(Node destino, int distancia) {
		this.destino = destino;
		this.distancia = distancia;
		this.graphid = intid;
		intid++;
	}

	public Node getDestino() {
		return destino;
	}

	public void setDestino(Node destino) {
		this.destino = destino;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public int getGraphid() {
		return this.graphid;
	}
}