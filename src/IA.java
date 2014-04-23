import java.util.Random;

public class IA {

	Controllore c;
	
	public IA(Controllore c){
		this.c = c;
	}
	
	
	//metodo che sulla base del punteggio della mossa, mi restituisce la mossa migliore (random in caso di punteggio uguale)
	private int[] confrontaMosse(int mossa1[], int mossa2[]){
		Random r = new Random();
		int a = r.nextInt(2);
		if(calcolaPunteggio(mossa1) > calcolaPunteggio(mossa2)){
			return mossa1;
		}
		else if (calcolaPunteggio(mossa1) < calcolaPunteggio(mossa2)){
			return mossa2;
		}
		else{
			if(a==0){
				return mossa1;
			}
			else{
				return mossa2;
			}
		}
	}
	
	//metodo che restituisce il punteggio di una determinata mossa
	public int calcolaPunteggio(int mossa[]){
		int nDamoniBianchiPrima;
		int nDamoniNeriPrima;
		int nDamoniBianchiDopo;
		int nDamoniNeriDopo;
		int xi=mossa[0]; 
		int yi=mossa[1];
		int xf=mossa[2];
		int yf=mossa[3];
		int punteggio=0;
		int distanzaPrima;
		int distanzaDopo;
		if(xi==-1)
			return -1000000;
		boolean mangiata=false;
		boolean pedina = c.getScacchiera().getPezzo(xi, yi) instanceof Pedina? true: false;
		boolean appoggio = c.getTurno();
		boolean rischio = false;
		Scacchiera copia = new Scacchiera (c.getScacchiera());
		Controllore prova = new Controllore(copia, appoggio);
		prova.setTurno(false);
		int pedineRischioPrima=prova.numeroPedineARischio();
		prova.setTurno(true);
		int app [] = prova.pedinaPiuVicina(xi, yi);
		
		distanzaPrima = prova.distanza(xi, yi, app[0], app[1]);
		boolean mangiateMultiplePrima = prova.puoMangiareMultiplo(false);
		if(yi < 2 && pedina){
			punteggio-=10;
			if(yi==0)
				punteggio-=10;
		}
		if(yi > 4 && pedina){
			punteggio+=10;
			if(yi==5 )
				punteggio+=10;
			if(yi==6)
				punteggio+=30;
		}
		
		if(prova.eUnaMangiata(xi, yi, xf, yf)){
			punteggio+=100;
			if(prova.getScacchiera().getScacchiera()[(xf+xi)/2][(yf+yi)/2] instanceof Damone)
				punteggio+=50;
			mangiata = true;
		}
		

		prova.muovi(xi, yi, xf, yf);
		
		
		if(prova.puoMangiare(xf, yf) && mangiata)
			punteggio += 100;
		
		distanzaDopo = prova.distanza(mossa[2], mossa[3], app[0], app[1]);
		if(distanzaDopo<distanzaPrima)
			punteggio+=10;
		
		boolean mangiateMultipleDopo = prova.puoMangiareMultiplo(false);
		if(mangiateMultiplePrima && !mangiateMultipleDopo)
			punteggio += 40;
		if(!mangiateMultiplePrima && mangiateMultipleDopo)
			punteggio-=80;
		
		prova.setTurno(false);
		int pedineRischioDopo = prova.numeroPedineARischio();
		System.out.println("Mangiata multipla prima " + mangiateMultiplePrima + " e mangiata multipla dopo " + mangiateMultipleDopo 
				+ " Pedine a rischio prima " + pedineRischioPrima + " e pedine a rischio dopo " + pedineRischioDopo + 
				"con mossa: " + xi + "" + yi + " " + xf + "" + yf);
		
		if(!prova.puoEssereMangiato(xf, yf)){
			punteggio += 20;
			if(rischio){
				punteggio += 30;
				if(!pedina)
					punteggio += 10;
				}
			prova.setTurno(true);
			if(prova.puoMangiare(xf, yf)){
				if(pedineRischioDopo<= pedineRischioPrima){
					punteggio+=30;				}
			}
		}
		prova.setTurno(false);
		if(prova.puoEssereMangiato(xf,  yf)){
			punteggio -= 30;
			if(prova.getScacchiera().getScacchiera()[xf][yf] instanceof Damone)
				punteggio -= 10;
		}
		if(pedineRischioDopo<pedineRischioPrima)
			punteggio+=55;
		if(pedineRischioDopo>pedineRischioPrima)
			punteggio-=40;
		return punteggio;
	}
	
	
	//metodo per l'esecuzione di una mossa dell'ia
	public void compiMossa(Grafica g){
		int mossa[][];
		int count=0;
		int mossaMigliore[] = null;
		for(int x=0; x<8; x++){
			for(int y=0; y<8; y++){
				mossa = c.mosseValide(x, y);
				System.out.println("La pedina in posizione "+x+" "+y+
						"puo muoversi in "+mossa[0][2]+" "+mossa[0][3] + "con punteggio "+ this.calcolaPunteggio(mossa[0])+ "  ||  "
						+mossa[1][2] +" "+mossa[1][3] + "con punteggio "+ this.calcolaPunteggio(mossa[1]) + "  ||  " 
						+mossa[2][2]+" "+mossa[2][3] + "con punteggio "+ this.calcolaPunteggio(mossa[2]) + "  ||  "
						+mossa[3][2] +" "+mossa[3][3] + "con punteggio "+ this.calcolaPunteggio(mossa[3]));
			
				
				while(count<4 && mossa[count][0]!=-1){
					if (mossaMigliore == null)
						mossaMigliore=mossa[0];
						
					mossaMigliore = confrontaMosse(mossa[count], mossaMigliore);
					count++;
				}
				count=0;
			}
		}
		count=0;
		
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
		c.muovi(mossaMigliore[0], mossaMigliore[1], mossaMigliore[2], mossaMigliore[3]);
		g.aggiornaScacchiera();
		while(c.getTurno()){
			mossaMigliore[0]=mossaMigliore[2];
			mossaMigliore[1]=mossaMigliore[3];
			mossa = c.mosseValide(mossaMigliore[0], mossaMigliore[1]);
			
			mossaMigliore=null;
			while(count<4 && mossa[count][0]!=-1){
				if (mossaMigliore == null)
					mossaMigliore=mossa[0];
				System.out.println("Mossa valida: " + mossa[count][2] + " " + mossa[count][3]);
					
				mossaMigliore = confrontaMosse(mossa[0], mossaMigliore);
				count++;
			}
			count=0;
			System.out.println(mossaMigliore[0] + " " + mossaMigliore[1] + " " + mossaMigliore[2] +" " + mossaMigliore[3]);
		
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			c.muovi(mossaMigliore[0], mossaMigliore[1], mossaMigliore[2], mossaMigliore[3]);
			g.aggiornaScacchiera();
			
		}
	}
}


