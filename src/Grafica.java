
import java.io.File;
import java.awt.Button;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


//classe per la stampa/aggiornamento della scacchiera
public class Grafica {
	Menu m;
	JFrame frame;
	JPanel panel;
	private Partita p;
	private Ascoltatore asc;
	public JButton mat[][] = new JButton[8][8];
	private ImageIcon damoneNero ;
	private ImageIcon damoneBianco;
	private ImageIcon pedinaNera ;
	private ImageIcon pedinaBianca ;
	private ImageIcon casellaNera ;
	private ImageIcon casellaBianca ;
	private ImageIcon damoneBiancoMuovibile;
	private ImageIcon pedinaBiancaMuovibile;
	String directory;

	
	public Grafica(Menu m){
		this.p = m.getPartita();
		directory = m.getDirectory();
		panel = new JPanel();

		panel.setLayout(new GridLayout(8,8));

  		damoneNero = new ImageIcon(directory +"damoneNero.png");
  		damoneBianco = new ImageIcon(directory +"damoneBianco.png");
  		pedinaNera = new ImageIcon(directory +"pedinaNera.png");
  		pedinaBianca = new ImageIcon(directory +"pedinaBianca.png");
  		casellaNera = new ImageIcon(directory +"casellaNera.png");
  		casellaBianca = new ImageIcon(directory +"casellaBianca.png");
  		damoneBiancoMuovibile = new ImageIcon(directory +"damoneBiancoMuovibile.png");
  		pedinaBiancaMuovibile = new ImageIcon(directory +"pedinaBiancaMuovibile.png");

  		for(int y=0; y<8; y++){
  			for(int x=0; x<8; x++){
  				mat[x][y]=new JButton();
  				panel.add(mat[x][y]);
  				
  			}
  		}
		frame = m.getFrame();
		frame.add(panel);
		asc = new Ascoltatore(p, this);
		this.inizializzaScacchiera();
		this.aggiornaScacchiera();
  			
	}
	
	public Partita getPartita(){
		return this.p;
	}
	public JButton[][] getMat(){
		return this.mat;
	}
	
	public void inizializzaScacchiera(){
		for (int y=0; y<8; y++){
			for(int x=0; x<8; x++){
				
				if(p.getScacchiera().getPezzo(x, y)==null && (x+y)%2==0){
					mat[x][y].setIcon(casellaNera);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					mat[x][y].addActionListener(asc);

				}
				
				else if(p.getScacchiera().getPezzo(x, y)==null && (x+y)%2!=0){
					mat[x][y].setIcon(casellaBianca);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
				}	
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Pedina && p.getScacchiera().getPezzo(x, y).getNero()){	

					mat[x][y].setIcon(pedinaNera);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					mat[x][y].addActionListener(asc);
				}
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Pedina && !p.getScacchiera().getPezzo(x, y).getNero()){	
					mat[x][y].setIcon(pedinaBianca);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					mat[x][y].addActionListener(asc);
				}
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Damone && p.getScacchiera().getPezzo(x, y).getNero()){	
					mat[x][y].setIcon(damoneNero);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					mat[x][y].addActionListener(asc);
				}
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Damone && !p.getScacchiera().getPezzo(x, y).getNero()){	
					mat[x][y].setIcon(damoneBianco);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					mat[x][y].addActionListener(asc);
				}
			}
		}

	}
	
	public void aggiornaScacchiera(){
		for (int y=0; y<8; y++){
			for(int x=0; x<8; x++){
				
				if(p.getScacchiera().getPezzo(x, y)==null && (x+y)%2==0){
					mat[x][y].setIcon(casellaNera);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					
				}
				
				else if(p.getScacchiera().getPezzo(x, y)==null && (x+y)%2!=0){
					mat[x][y].setIcon(casellaBianca);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					
				}	
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Pedina && p.getScacchiera().getPezzo(x, y).getNero()){	
					mat[x][y].setIcon(pedinaNera);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					
				}
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Pedina && !p.getScacchiera().getPezzo(x, y).getNero()){	
					if(p.getControllore().puoMuoversi(x,y))
						mat[x][y].setIcon(pedinaBiancaMuovibile);
					else
						mat[x][y].setIcon(pedinaBianca);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					
				}
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Damone && p.getScacchiera().getPezzo(x, y).getNero()){	
					mat[x][y].setIcon(damoneNero);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					
				}
				
				else if(p.getScacchiera().getPezzo(x, y) instanceof Damone && !p.getScacchiera().getPezzo(x, y).getNero()){	
					if(p.getControllore().puoMuoversi(x,y))
						mat[x][y].setIcon(damoneBiancoMuovibile);
					else
						mat[x][y].setIcon(damoneBianco);
					mat[x][y].putClientProperty(0, x);
					mat[x][y].putClientProperty(1, y);
					
				}
			}
		}
	}
	public Menu getMenu(){
		return this.m;
	}
	public JPanel getPanel(){
		return this.panel;
	}
	public void setIconCasella(int x, int y, ImageIcon i){
		mat[x][y].setIcon(i);
	}

	
	//stampa la schermata finale
	public void vittoria(int vincitore){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//JFrame f = new JFrame();
		JPanel pan = new JPanel();
		frame.remove(panel);
		pan.setLayout(new GridLayout(1,1));
		frame.add(pan);
		JButton vittoria = new JButton();
		vittoria.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				Partita p = new Partita();
				Menu m = new Menu(p);
			}
			
		});
		if(vincitore == -1)
			vittoria.setIcon(new ImageIcon("vittoria.png"));
		if(vincitore == 1)
			vittoria.setIcon(new ImageIcon("sconfitta.png"));
		if(vincitore == 0)
			vittoria.setIcon(new ImageIcon("pareggio.png"));
		pan.add(vittoria);
		frame.validate();
	}
	

	

}
