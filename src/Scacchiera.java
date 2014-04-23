
public class Scacchiera {
	//matrice contenente i vari pezzi
	private Pezzo[][] scacchiera= new Pezzo[8][8];;
	
	
	public Scacchiera(){
	/*	for (int x=0; x<8; x++){
			for (int y=0; y<8; y++){
				scacchiera[x][y] = null;
			}
		}
		scacchiera[0][6] = new Damone(false);
		scacchiera[2][6] = new Pedina (true);
		scacchiera[4][4] = new Pedina (true);
	*/
		//inizializzo pedine nere
		for (int x=0; x<8; x++){
			for (int y=0; y<3; y++){
				if ((x+y)%2 == 0)
					this.scacchiera[x][y] = new Pedina(true);
				else
					this.scacchiera[x][y] = null;
			}
		}
		//inizializzo pedine bianche
		for (int x=0; x<8; x++){
			for (int y=5; y<8; y++){
				if ((x+y)%2 == 0)
					this.scacchiera[x][y] = new Pedina(false);
				else
					this.scacchiera[x][y] = null;
			}
		}
	}
	
	//metodo che mi crea una scacchiera copia
	public Scacchiera(Scacchiera s){
		
		for (int x=0; x<8; x++){
			for (int y=0; y<8; y++){
				if(s.scacchiera[x][y]!=null){
					if(s.scacchiera[x][y] instanceof Damone){
						this.scacchiera[x][y] = new Damone(s.scacchiera[x][y]);
					}
					else{
						scacchiera[x][y] = new Pedina(s.scacchiera[x][y]);
					}
					
				}
				else{
					this.scacchiera[x][y] = null;
				}
			}
		}
	}

	
	//ritorna la matrice che rappresenta la scacchiera
	public Pezzo[][] getScacchiera(){
		return this.scacchiera;
	}
	
	
	//ritorna il pezzo in posizione x, y
	public Pezzo getPezzo(int x, int y){
		return this.scacchiera[x][y];
	}

}
