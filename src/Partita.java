
public class Partita {
	
	//una partita ha come attributi un IA, un controllore e una scacchiera 
	private IA ia;
	private Controllore c;
	private Scacchiera s;
	
	//costruttore 
	public Partita(){
		s = new Scacchiera();
		c = new Controllore (s, false);
		ia = new IA(c);
	}
	
	public Scacchiera getScacchiera(){
		return this.s;
	}
	public Controllore getControllore(){
		return this.c;
	}
	public IA getIA(){
		return this.ia;
	}
}
