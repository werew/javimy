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
import filters.*;

class window extends JFrame implements ActionListener
{
		JMenuBar menu=new JMenuBar();

		JMenu file=new JMenu("Fichier");
		JMenu filtre=new JMenu("Filtre");

		JMenuItem btnOuvrir=new JMenuItem("Ouvrir");
		JMenuItem btnEnregistrer=new JMenuItem("Enregistrer");
		JMenuItem btnEnregistrerSous=new JMenuItem("Enregistrer sous");
		JMenuItem btnQuitter=new JMenuItem("Quitter");
		JMenuItem btnSobel=new JMenuItem("Sobel");
		JMenuItem btnPrewitt=new JMenuItem("Prewitt");

		File fichier_image;	//TODO en attribut si jamais on veux ecraser le fichier courant
		Filter newFile;
		BufferedImage image;

		JFileChooser fichier=new JFileChooser(new File("."));	//TODO filtre

		ImageIcon icone=new ImageIcon("icone.jpg");

	public window()
	{
		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(menu);

		menu.add(file);
		menu.add(filtre);

		file.add(btnOuvrir);
		btnOuvrir.addActionListener(this);

		file.add(btnEnregistrer);
		btnEnregistrer.addActionListener(this);

		file.add(btnEnregistrerSous);
		btnEnregistrerSous.addActionListener(this);

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
		int boite=fichier.showOpenDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as ouvert: "+fichier.getSelectedFile());	//XXX
			fichier_image=new File(""+fichier.getSelectedFile());
			try{
			image=ImageIO.read(fichier_image);
			} catch (IOException e) {
				e.printStackTrace();
			}/*
			Graphics g=image.createGraphics();
			g.drawImage(image,0,0,null);
			this.validate();*/ //TODO
		}
		else
		{
			System.out.println("Perdu");	//XXX
		}
	}

	private void enregistrerSous()
	{
		int boite=fichier.showSaveDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as enregistrer: "+fichier.getSelectedFile());	//XXX
			File output=new File(""+fichier.getSelectedFile());
			try {
			ImageIO.write(newFile.getImg(),"jpg",output);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void enregistrer()
	{
			File output=new File(""+fichier.getSelectedFile());
			try {
			ImageIO.write(newFile.getImg(),"jpg",output);
			} catch (IOException e) {
				e.printStackTrace();
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
		if(e.getSource()==btnEnregistrer)
		{
			this.enregistrer();
		}
		if(e.getSource()==btnEnregistrerSous)
		{
			this.enregistrerSous();
		}
		if(e.getSource()==btnSobel)
		{
			newFile = new Sobel(image);
		}
		if(e.getSource()==btnPrewitt)
		{
			newFile = new Prewitt(image);
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
