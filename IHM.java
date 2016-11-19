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

		ImageIcon imageAfficher;
		JLabel labelImageAfficher;

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
			}

			//this.printImage(image);	//TODO marche presque, si on ouvre une image alors qu'il y en a deja une ouverte = bug
			//TODO factoriser code dans méthode
			this.getContentPane().removeAll();
			imageAfficher=new ImageIcon(image);
			labelImageAfficher=new JLabel("",SwingConstants.CENTER);
			labelImageAfficher.setIcon(imageAfficher);
			this.getContentPane().add(labelImageAfficher,BorderLayout.CENTER);
			this.revalidate();	

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
			this.printImage(newFile.getImg());

		}
		if(e.getSource()==btnPrewitt)
		{
			newFile = new Prewitt(image);
			this.printImage(newFile.getImg());
		}
		
	}

	private void printImage(BufferedImage i)
	{
			this.getContentPane().remove(labelImageAfficher);
			imageAfficher=new ImageIcon(i);
			labelImageAfficher=new JLabel("",SwingConstants.CENTER);	//TODO Inutile?
			labelImageAfficher.setIcon(imageAfficher);
			this.getContentPane().add(labelImageAfficher,BorderLayout.CENTER);
			this.revalidate();	
	}
	
}



public class IHM
{
	public static void main(String[] args)
	{
		window fenetre=new window();
	}
}
