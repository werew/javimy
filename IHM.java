import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class window extends JFrame implements ActionListener
{
		JMenuBar menu=new JMenuBar();
		JMenu file=new JMenu("Fichier");
		JButton btnOuvrir=new JButton("Ouvrir");
		JButton btnEnregistrer=new JButton("Enregistrer");
		JButton btnQuitter=new JButton("Quitter");

	public window()
	{
		this.setTitle("Filtre");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(menu);

		menu.add(file);

		file.add(btnOuvrir);
		btnOuvrir.addActionListener(this);

		file.add(btnEnregistrer);

		file.add(btnQuitter);
		btnQuitter.addActionListener(this);

		this.setVisible(true);

	
	}

	private void ouvrir()
	{
		JFileChooser fichier=new JFileChooser(new File("."));
		int boite=fichier.showOpenDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as choisi: "+fichier.getSelectedFile());	//XXX
			
		}
		else
		{
			System.out.println("Perdu");	//XXX
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btnOuvrir)
		{
			this.ouvrir();
		}
		if(e.getSource()==btnQuitter)
		{
			this.dispose();
		}
	}
	
}


public class IHM
{
	public static void main(String[] args)
	{
		window fenetre=new window();
	}
}
