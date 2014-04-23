public class Damone extends Pezzo{
	
	
	public Damone(boolean nero){
		super(nero);
	}
	
	//Costruttore copia
	public Damone(Pezzo p){
		super(p.getNero());
	}
	
	/**
	 * ritorna 0 se p � damone, altrimenti 1 (damone non � pi� piccolo di alcun pezzo)
	 */
	public int compareTo(Pezzo p){
		if (p instanceof Damone)
			return 0;
		return 1;
	}
	
}
