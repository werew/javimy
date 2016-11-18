import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.image.BufferedImage;

class window extends JFrame implements ActionListener
{
		JMenuBar menu=new JMenuBar();

		JMenu file=new JMenu("Fichier");
		JMenu filtre=new JMenu("Filtre");

		JButton btnOuvrir=new JButton("Ouvrir");
		JButton btnEnregistrer=new JButton("Enregistrer");
		JButton btnQuitter=new JButton("Quitter");
		JButton btnSobel=new JButton("Sobel");
		JButton btnPrewitt=new JButton("Prewitt");

		File fichier_image;	//TODO en attribut si jamais on veux ecraser le fichier courant
		BufferedImage image;

	public window()
	{
		this.setTitle("Filtre");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(menu);

		menu.add(file);
		menu.add(filtre);

		file.add(btnOuvrir);
		btnOuvrir.addActionListener(this);

		file.add(btnEnregistrer);
		btnEnregistrer.addActionListener(this);

		file.add(btnQuitter);
		btnQuitter.addActionListener(this);

		filtre.add(btnSobel);
		btnSobel.addActionListener(this);

		filtre.add(btnPrewitt);
		btnPrewitt.addActionListener(this);


		this.setVisible(true);

	
	}

	private void ouvrir()
	{
		JFileChooser fichier=new JFileChooser(new File("."));
		int boite=fichier.showOpenDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as choisi: "+fichier.getSelectedFile());	//XXX
		/*	fichier_image=new File(""+fichier.getSelectedFile());
			try{
			image=ImageIO.read(fichier_image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Graphics g=image.createGraphics();
			g.drawImage(image,0,0,null);
			this.validate();*/ //TODO
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


/*class image_afficher extends JPanel
{
	public void paintComponent(Graphics g)
	{
			File fichier_image=new File("img.jpg");
			try{
			Image image=ImageIO.read(fichier_image);
		g.drawImage(image,0,0,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
*/

public class IHM
{
	public static void main(String[] args)
	{
		window fenetre=new window();
	}
}
