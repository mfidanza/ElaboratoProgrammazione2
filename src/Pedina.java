public class Pedina extends Pezzo{
	
	
	public Pedina(boolean nero) {
		super(nero);
	}
	
	//costruttore copia
	public Pedina(Pezzo p){
		super(p.getNero());
	}
	
	/**
	 * -1 se this<p, altrimenti 0 (Una pedina non può essere più grande di alcun pezzo)
	 */
	public int compareTo(Pezzo p){
		if (p instanceof Damone)
			return -1;
		return 0;
	}
	
}
