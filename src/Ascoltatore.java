
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//action listener della scacchiera
public class Ascoltatore implements ActionListener{
	private Partita p;
	private int xi, yi, xf, yf;
	private Grafica g;
	private boolean obbligato;
	private int nMosse;
	
	public Ascoltatore(Partita p, Grafica g){
		this.p=p;
		xi=-1;
		yi=-1;
		xf=-1;
		yf=-1;
		this.g = g;
		obbligato = false;
		nMosse = -1;
	}
	
	//metodo che cambia l'icona della pedina/damone selezionato
	public void setBottoneSelezionato(){
		if(p.getScacchiera().getPezzo(xi,  yi) instanceof Pedina && !p.getScacchiera().getPezzo(xi, yi).getNero())
			g.setIconCasella(xi, yi, new ImageIcon(g.directory +"pedinaBiancaSelezionata.png"));
		else if(p.getScacchiera().getPezzo(xi,  yi) instanceof Damone && !p.getScacchiera().getPezzo(xi, yi).getNero())
			g.setIconCasella(xi, yi, new ImageIcon(g.directory +"damoneBiancoSelezionato.png"));
		
	}
	
	
	
	public void actionPerformed(ActionEvent event) {
		JButton b = (JButton)event.getSource();
		if(xi==-1){
			xi=(int)b.getClientProperty(0);
			yi=(int)b.getClientProperty(1);
			setBottoneSelezionato();
			
			int mossa[][] = p.getControllore().mosseValide(xi, yi);
			int count = 0;
			while(count<4 && mossa[count][0]!=-1){
				g.setIconCasella(mossa[count][2], mossa[count++][3], new ImageIcon(g.directory +"casellaNeraPossibileMossa.png"));
			}
		}
		else{
			xf=(int)b.getClientProperty(0);
			yf=(int)b.getClientProperty(1);
			if(p.getControllore().eUnaMangiata(xi, yi, xf, yf))
				nMosse=-1;
			if(p.getControllore().muovi(xi, yi, xf, yf) ){
				g.aggiornaScacchiera();
				if(p.getControllore().partitaFinita()==1){

					g.vittoria(1);
				}
				if(p.getControllore().partitaFinita()==-1){

					g.vittoria(-1);
				}
				if(++nMosse==40){
					g.vittoria(0);
				}
				obbligato = false;
				if(!p.getControllore().getTurno()){
					g.aggiornaScacchiera();
					xi=xf;
					yi=yf;
					setBottoneSelezionato();
					int mossa[][] = p.getControllore().mosseValide(xi, yi);
					int count = 0;
					while(count<4 && mossa[count][0]!=-1){
						g.setIconCasella(mossa[count][2], mossa[count++][3], new ImageIcon(g.directory +"casellaNeraPossibileMossa.png"));
					}
					obbligato = true;
				}
				else{
					
					if(p.getControllore().partitaFinita()==0){

						
					 new Thread(){
						public void run(){
							
							p.getIA().compiMossa(g);
							g.aggiornaScacchiera();
							
							if(p.getControllore().partitaFinita()==-1){

								g.vittoria(-1);
							}
							if(p.getControllore().partitaFinita()==1){

								g.vittoria(1);
							}
						}
					}.start();
					
					}	
				}
			}
			
			if(obbligato){
				xf=-1;
				yf=-1;
			}
			else{
				g.aggiornaScacchiera();
				xf=-1;
				yf=-1;
				xi=-1;
				yi=-1;
			}
			
			
			}
			
			
		}
		
		
	}

