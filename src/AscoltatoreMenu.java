import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


//action listener del menu. Setta la directory da cui recuperare le immagini in base alla scelta effettuata
public class AscoltatoreMenu implements ActionListener{
	
	Menu m;
	public AscoltatoreMenu(Menu m){
		this.m=m;
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton b = (JButton)event.getSource();
		if((int)b.getClientProperty(0)==1){
			m.setDirectory("minimal/");
		}
		else if((int)b.getClientProperty(0)==2){
			m.setDirectory("marmo/");
		}
		else if((int)b.getClientProperty(0)==3){
			m.setDirectory("legno/");
		}
		else{
			m.setDirectory("pokemon/");
		}
		m.getFrame().remove(m.getPanel());

		Grafica g = new Grafica(this.m);
		
	}
}
