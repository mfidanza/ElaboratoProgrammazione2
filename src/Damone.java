public class Damone extends Pezzo{
	
	
	public Damone(boolean nero){
		super(nero);
	}
	
	//Costruttore copia
	public Damone(Pezzo p){
		super(p.getNero());
	}
	
	/**
	 * ritorna 0 se p è damone, altrimenti 1 (damone non è più piccolo di alcun pezzo)
	 */
	public int compareTo(Pezzo p){
		if (p instanceof Damone)
			return 0;
		return 1;
	}
	
}
