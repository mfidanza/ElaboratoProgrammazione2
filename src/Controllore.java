


public class Controllore {
	/**
	 * Il controllore è l'oggetto che si occupa della gestione delle mosse riguardanti la scacchiera s
	 */
	private Scacchiera s;
	private boolean turno;
	
	public Controllore(Scacchiera s, boolean turno){
		this.s = s;
		this.turno = turno;
	}
	
	
	public Scacchiera getScacchiera(){
		return this.s;
	}
	
	//metodo che conta il numero di pedine biianche rimaste
	public int contaBianchi(){
		int count = 0;
		for (int x = 0; x<8; x++)
			for (int y = 0; y<8; y++)
				if(s.getScacchiera()[x][y]!=null)
					if(!s.getScacchiera()[x][y].getNero())
						count++;
		return count;
	}
	
	
	//metodo che conta il numero di pedine nere rimaste
	public int contaNeri(){
		int count=0;
		for (int x=0; x<8; x++)
			for (int y=0; y<8; y++)
				if(s.getScacchiera()[x][y]!=null)
					if(s.getScacchiera()[x][y].getNero())
						count++;
		return count;
	}
	
	
	//metodo che trasforma una pedina in un damone
	public void promuoviPedina(int x, int y){
		s.getScacchiera()[x][y] = new Damone (s.getScacchiera()[x][y].getNero());
	}
	
	//ritorna true se la mossa presa in input è una mossa valida 
	public boolean mossaValida(int xi, int yi, int xf, int yf){
		
		if (s.getScacchiera()[xi][yi]==null || s.getScacchiera()[xf][yf]!=null)
			return false;
		
		if(s.getScacchiera()[xi][yi].getNero()!=turno)
			return false;
		
		int mat[][] = this.mangio();
		//se ci sono mangiate possibili e sto mangiando, allora va bene
		if (mat[0][0]!=-1 && contains(mat, xi, yi) && this.eUnaMangiata(xi, yi, xf, yf))
			return true;
		if (mat[0][0]!=-1 && !contains(mat, xi, yi))
			return false;
		if (mat[0][0]!=-1 && contains(mat, xi, yi) && !eUnaMangiata(xi, yi, xf, yf))
			return false;
		
		if (this.eUnaMangiata(xi, yi, xf, yf))
			return true;
		
		if (this.eUnoSpostamento(xi, yi, xf, yf))
			return true;
			return false;	
		}
	
	//metodo per cambiare turno	
	public void cambiaTurno(){
		this.turno = !this.turno;
	}

	
	//metodo che effettua il movimento reale di una pedina sulla scacchiera
	public boolean muovi(int xi, int yi, int xf, int yf){
		boolean mangiata = false;
		if (mossaValida(xi, yi, xf, yf)){
			if(this.eUnaMangiata(xi, yi, xf, yf)){
				s.getScacchiera()[(xf+xi)/2][(yf+yi)/2] = null;
				mangiata = true;
			}
			s.getScacchiera()[xf][yf] = s.getScacchiera()[xi][yi];
			s.getScacchiera()[xi][yi] = null;
			if((yf==7 && s.getScacchiera()[xf][yf] instanceof Pedina && s.getScacchiera()[xf][yf].getNero()) || 
					yf==0 && s.getScacchiera()[xf][yf] instanceof Pedina && !s.getScacchiera()[xf][yf].getNero())
					promuoviPedina(xf, yf);
			if(mangiata && puoMangiare(xf, yf)){
				return true;
			}
			else
				cambiaTurno();
	
			return true;
		}
		else{
			return false;	
		}
	}
		
		
	//ritorna true se la pedina in posizione x, y può mangiare
	public boolean puoMangiare(int x, int y){
		int app[][] = new int[4][2];
		for(int i=0; i<4; i++)
			for(int j=0; j<2; j++)
				app[i][j]=-1;
		if(s.getScacchiera()[x][y]==null)
			return false;
		int damone = s.getScacchiera()[x][y] instanceof Damone? 2:1;
		int appoggio = this.s.getScacchiera()[x][y].getNero()? 1:-1; 
		if(y>=6 && s.getScacchiera()[x][y] instanceof Pedina && s.getScacchiera()[x][y].getNero())
			return false;
		if(y<=1 && s.getScacchiera()[x][y] instanceof Pedina && !s.getScacchiera()[x][y].getNero())
			return false;
		
		for (int i=0; i<damone; i++){
			if(x<6 && y+2*appoggio<8 && y+2*appoggio>=0)	{
				if (this.s.getScacchiera()[x][y]!=null && this.s.getScacchiera()[x+1][y+appoggio]!=null && this.s.getScacchiera()[x+2][y+2*appoggio]==null){
					 if(this.s.getScacchiera()[x+1][y+appoggio].getNero()!=this.s.getScacchiera()[x][y].getNero() &&
							 s.getScacchiera()[x][y].compareTo(this.s.getScacchiera()[x+1][y+appoggio])>=0){
						 return true;
					 }
				}
			}
			if(x>1 && y+2*appoggio<8 && y+2*appoggio>=0)	{
				if (this.s.getScacchiera()[x][y]!=null && this.s.getScacchiera()[x-1][y+appoggio]!=null && this.s.getScacchiera()[x-2][y+2*appoggio]==null){
				 if(this.s.getScacchiera()[x-1][y+appoggio].getNero()!=this.s.getScacchiera()[x][y].getNero() &&
						 s.getScacchiera()[x][y].compareTo(s.getScacchiera()[x-1][y+appoggio])>=0){
					 return true;
				 }
			}
		}
		appoggio = -appoggio; //se è damone, faccio controllo in entrambe le direzioni
	}		
	return false;
}
		
		//true se la mossa in input è valida ed è una mangiata
	public boolean eUnaMangiata(int xi, int yi, int xf, int yf){
		if(s.getScacchiera()[xi][yi]==null || s.getScacchiera()[xf][yf]!=null)
			return false;
		int appoggio = s.getScacchiera()[xi][yi].getNero()? 2:-2;
		int damone = s.getScacchiera()[xi][yi] instanceof Damone? 2:1;		
		for (int i=0; i<damone; i++){
			if(Math.abs(xf-xi)==2 && (yf-yi)==appoggio && s.getScacchiera()[(xf+xi)/2][(yf+yi)/2]!=null 
					&& s.getScacchiera()[xi][yi].getNero()!=s.getScacchiera()[(xf+xi)/2][(yf+yi)/2].getNero() && 
							s.getScacchiera()[xi][yi].compareTo(s.getScacchiera()[(xf+xi)/2][(yf+yi)/2])>=0)
				return true;
			appoggio = -appoggio;
		}
		return false;
	}
		
	//true se la mossa in input è valida ed è un samplice spostamento
	private boolean eUnoSpostamento(int xi, int yi, int xf, int yf){
		int appoggio = s.getScacchiera()[xi][yi].getNero()? 1:-1;
		int damone = s.getScacchiera()[xi][yi] instanceof Damone? 2:1;
		
		for (int i=0; i<damone; i++){
			if((yf-yi)==appoggio && Math.abs(xf-xi)==1 && s.getScacchiera()[xf][yf]==null)
				return true;
			appoggio = -appoggio;
		}
		return false;
	}
	
	//metodo per settare il turno ad un valore scelto
	public void setTurno(boolean turno){
		this.turno=turno;
	}
		
	//ritorna true se la pedina x, y
	public boolean puoEssereMangiato(int x, int y){
		if(x>0 && x<7 && y>0 && y<7){
			if(eUnaMangiata(x+1, y-1, x-1, y+1))
				return true;
			if(eUnaMangiata(x-1, y-1, x+1, y+1))
				return true;
			if(eUnaMangiata(x-1, y+1, x+1, y-1))
				return true;
			if(eUnaMangiata(x+1, y+1, x-1, y-1))
				return true;
		}
		return false;
	}
			
		
		//restituisce una matrice con le coordinate di tutte le pedine che possono mangiare
	public int[][] mangio(){
		int[][] mat = new int[12][2];
		for (int i=0; i<12; i++)
			for (int j=0; j<2; j++)
				mat[i][j]=-1;
		int count=0;
		for (int x=0; x<8; x++){
			for (int y=0; y<8; y++){
				if(s.getScacchiera()[x][y]!=null){
					if (this.turno == this.s.getScacchiera()[x][y].getNero() && puoMangiare(x, y)){
						mat[count][0] = x;
						mat[count++][1] = y;
					}
				}
			}
		}
		
		return mat;
	}
		

	public boolean getTurno(){
		return this.turno;
	}

	
	//metodo che controlla se all'interno di una matrice è presente lo spostamento che parte dalla casella x, y
	public boolean contains(int mat[][], int x, int y){
		int count=0;
		while(mat[count][0]!=-1 && count<mat.length){
			if(mat[count][0]==x && mat[count][1]==y){
				return true;
			}
			count++;
		}
		return false;				
	}
	
	
	//ritorna una matrice contenente tutte le mosse fattibili dal pezzo xy
	public int[][] mosseValide(int x, int y){
		int count = 0, xf, yf;
		int mosse[][] = new int[4][4];
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				mosse[i][j] = -1;
		
		if (s.getScacchiera()[x][y]==null)
			return mosse;
		if(s.getScacchiera()[x][y].getNero()!=turno)
			return mosse;
		
		
		int damone = this.s.getScacchiera()[x][y] instanceof Damone? 2:1;
		int spostamento = this.s.getScacchiera()[x][y].getNero()? 1:-1;
		
		for(int i=0; i<damone; i++){
			xf = x+1;
			yf = y+spostamento;
			if(xf<8 && yf<8 && yf>=0){
				if(this.mossaValida(x, y, xf, yf)){
					mosse[count][0] = x;
					mosse[count][1] = y;
					mosse[count][2] = xf;
					mosse[count][3] = yf;
					count++;
				}
			}
			xf = x-1;
			if(xf>=0 && yf<8 && yf>=0)
				if(this.mossaValida(x, y, xf, yf)){
					mosse[count][0] = x;
					mosse[count][1] = y;
					mosse[count][2] = xf;
					mosse[count][3] = yf;
					count++;
				}
			
			
			xf = x+2;
			yf = y+(2*spostamento);
			if(xf<8 && yf<8 && yf>=0)
				if(this.mossaValida(x, y, xf, yf)){
					mosse[count][0] = x;
					mosse[count][1] = y;
					mosse[count][2] = xf;
					mosse[count][3] = yf;
					count++;
				}
			
			
			xf = x-2;
			yf = y+(2*spostamento);
			if(xf>=0 && yf<8 && yf>=0)
				if(this.mossaValida(x, y, xf, yf)){
					mosse[count][0] = x;
					mosse[count][1] = y;
					mosse[count][2] = xf;
					mosse[count][3] = yf;
					count++;
				}
			
			spostamento = -spostamento;
		}
		return mosse;
	}

	//metodo che serve per rilevare eventuali mangiate multiple del player
	public boolean puoMangiareMultiplo(boolean nero){

		int[][] mosse;
		boolean app = turno;
		turno = nero;
		
		for(int x=0; x<8; x++){
			for(int y=0; y<8; y++){
				
				mosse = mosseValide(x, y);
				int count = 0;
				while(count<4 && mosse[count][0]!=-1){
					
						turno = false;
						Controllore prova = new Controllore(new Scacchiera(s), turno);
						if(prova.eUnaMangiata(mosse[count][0], mosse[count][1],mosse[count][2],mosse[count][3])){
							prova.muovi(mosse[count][0], mosse[count][1],mosse[count][2],mosse[count][3]);

							if(prova.getTurno())
								prova.cambiaTurno();
							if(prova.puoMangiare(mosse[count][2], mosse[count][3])){
								turno = app;
								return true;
							}
							}
						count++;
					}
				count = 0;
			}
		}
		turno = app;
		return false;
	}
	
	
	//calcola il numero di pedine che sono mangiabili dal player 
	public int numeroPedineARischio(){
			int count=0;
			for(int x=0; x<8; x++)
				for(int y=0; y<8; y++)
					if(s.getPezzo(x, y)!=null){
						if(s.getPezzo(x, y).getNero() && puoEssereMangiato(x,y)){
							count++;
						}
					}
			return count;

			
		}

	
	//ritorna 1 se hanno vinto i neri
	//-1 se hanno vinto i bianchi
	public int partitaFinita(){
		int [][]appNeri = mosseDisponibiliNeri();
		int [][]appBianchi = mosseDisponibiliBianchi();
		boolean mosseDisponibiliBianchi = false;
		boolean mosseDisponibiliNeri = false;
		
		int pv = partitaVinta();
		for(int x=0; x<48; x++){
			for(int y=0; y<4; y++){
				if(appNeri[x][y]!=-1){
					mosseDisponibiliNeri = true;
				}
				if(appBianchi[x][y]!=-1){
					mosseDisponibiliBianchi = true;
				}
			}
		}

		if((!mosseDisponibiliNeri && turno) || pv==-1){
			return -1;
			
		}
		if((!mosseDisponibiliBianchi && !turno) || pv==1){
			return 1;
		}
		return 0;
	}
	
	//ritorna true se la pedina x, y può muoversi
	public boolean puoMuoversi(int x, int y){
		if (mosseValide(x, y)[0][0]!=-1)
			return true;
		return false;
	}
	
	//ritorna una matrice contenente tutte le mosse diponibili per le pedine bianche
	public int[][] mosseDisponibiliBianchi(){

		int mosse[][] = new int[48][4];
		int count = 0;
		for(int x=0; x<48; x++)
			for(int y=0; y<4; y++)
				mosse[x][y] = -1;
		for(int x=0; x<8; x++){
			for(int y=0; y<8; y++){
				if(s.getPezzo(x, y)!=null){
					if(!s.getPezzo(x, y).getNero()){
						mosse[count++] = mosseValide(x, y)[0];
						mosse[count++] = mosseValide(x, y)[1];
						mosse[count++] = mosseValide(x, y)[2];
						mosse[count++] = mosseValide(x, y)[3];
					}
				}
			}
		}

		return mosse;
	}
	//ritorna una matrice contenente tutte le mosse diponibili per le pedine nere
	public int[][] mosseDisponibiliNeri(){
		int mosse[][] = new int[48][4];

		int count = 0;
		for(int x=0; x<48; x++)
			for(int y=0; y<4; y++)
				mosse[x][y] = -1;
		for(int x=0; x<8; x++){
			for(int y=0; y<8; y++){
				if(s.getPezzo(x, y)!=null){
					if(s.getPezzo(x, y).getNero()){
						mosse[count++] = mosseValide(x, y)[0];
						mosse[count++] = mosseValide(x, y)[1];
						mosse[count++] = mosseValide(x, y)[2];
						mosse[count++] = mosseValide(x, y)[3];
					}
				}
			}
		}
		return mosse;
	}
	
	//ritorna 1 se hanno vinto i neri
	//ritorna 0 se hanno vinto i bianchi
	//ritorna -1 se non ha vinto nessuno
	public int partitaVinta(){
		int bianchi=contaBianchi();
		int neri=contaNeri();
		if (neri==0)
			return -1;
		if(bianchi==0)
			return 1;
		return 0;
			
	}

	//calcola il numero di spostamenti necessari per raggiungere una determinata casella
public int distanza(int xi, int yi, int xf, int yf){
	int distanza = 0;
	while (xi!=xf || yi!=yf){
		if(xi<=xf)
			xi+=1;
		else
			xi-=1;
		if(yi<=yf)
			yi+=1;
		else
			yi-=1;
		distanza++;
	}
	return distanza;
}

//trova la pedina avversaria più vicina a quella data
public int[] pedinaPiuVicina(int xi, int yi){
	int pedina [] = new int[2];
	pedina[0]=-1;
	pedina[1]=-1;
	if(s.getScacchiera()[xi][yi]==null)
		return pedina;
	for(int x=0; x<8; x++){
		for(int y=0; y<8; y++){
			if(s.getScacchiera()[x][y]!=null && xi!=x && yi!=y){
				if(s.getScacchiera()[x][y].getNero()!=turno){
					if(pedina[0]==-1){
						pedina[0]=x;
						pedina[1]=y;
					}
					else if(distanza(xi, yi, pedina[0], pedina[1]) > distanza(xi, yi, x, y)){
						pedina[0] = x;
						pedina[1] = y;
					}
				}
			}
		}
	}
	return pedina;
}
}