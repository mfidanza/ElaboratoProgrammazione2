
public class Pezzo {
	
	//Un pezzo(pedina o damone) contiene solo il proprio colore (utile ai fini del controllo delle mosse)
	private boolean nero;
	
	//costruttore
	public Pezzo(boolean nero){
		this.nero = nero;
	}
	
	//metodo per accedere al colore della pedina
	public boolean getNero(){
		return this.nero;
	}
	
	//Metodo che che restituisce 1 se this>p, -1 se this<p, 0 se uguali
	public int compareTo(Pezzo p){
		if((this instanceof Damone && p instanceof Damone) || (this instanceof Pedina && p instanceof Pedina))
			return 0;
		else if (this instanceof Damone && p instanceof Pedina)
			return 1;
		else 
			return -1;
	}
}
