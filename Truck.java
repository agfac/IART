
public class Truck {
	private static int intid = 0;
	private final int carga_max = 50;
	private int distancia_percorrida = 0;
	private int carga_disponivel = carga_max;
	
	public Truck(){
		intid++;
	}

	public static int getIntid() {
		return intid;
	}

	public int getDistancia_percorrida() {
		return distancia_percorrida;
	}

	public void updateDistancia_percorrida(int distancia_percorrida) {
		this.distancia_percorrida += distancia_percorrida;
	}

	public int getCarga_disponivel() {
		return carga_disponivel;
	}

	public void updateCarga_disponivel(int carga_disponivel) {
		this.carga_disponivel -= carga_disponivel;
	}

	public int getCargaMax() {
		return carga_max;
	}
	
	public int getGvalue(){
		return distancia_percorrida + ((carga_disponivel/carga_max)*distancia_percorrida);
	}
}
