import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//classe che mi stampa il menu iniziale di selezione scacchiera
public class Menu {
	
	private String directory = null;
	private JFrame frame;
	private JPanel panel;
	private Partita p;
	
	
	public Menu(Partita p){
		this.p=p;
		panel = new JPanel();
		frame = new JFrame();
		frame.setSize(500,500);
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon("iconaFrame.jpg").getImage());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		JButton min = new JButton();
		JButton marm = new JButton();
		JButton legno = new JButton();
		JButton poke = new JButton();

		panel.setLayout(new GridLayout(2,2));
		panel.add(min);
		panel.add(marm);
		panel.add(legno);
		panel.add(poke);
		min.setIcon(new ImageIcon("menuMinimal.png"));
		marm.setIcon(new ImageIcon("menuMarmo.png"));
		legno.setIcon(new ImageIcon("menuLegno.png"));
		poke.setIcon(new ImageIcon("menuPokemon.png"));
		min.setRolloverIcon(new ImageIcon ("anteprimaMinimal.png"));
		marm.setRolloverIcon(new ImageIcon ("anteprimaMarmo.png"));
		legno.setRolloverIcon(new ImageIcon ("anteprimaLegno.png"));
		poke.setRolloverIcon(new ImageIcon ("anteprimaPokemon.png"));
		min.putClientProperty(0, 1);
		marm.putClientProperty(0, 2);
		legno.putClientProperty(0, 3);
		poke.putClientProperty(0, 4);
		
		
		min.addActionListener(new AscoltatoreMenu(this));

		marm.addActionListener(new AscoltatoreMenu(this));
		
		legno.addActionListener(new AscoltatoreMenu(this));

		poke.addActionListener(new AscoltatoreMenu(this));
	}
	

	
	public void setDirectory(String s){
		this.directory=s;
	}
	
	public String getDirectory(){
		return this.directory;
	}
	public Partita getPartita(){
		return this.p;
	}
	
	public JFrame getFrame(){
		return this.frame;
	}
	
	public JPanel getPanel(){
		return this.panel;
	}
}
