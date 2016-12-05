package window;
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
import vectorization.*;
import option.*;

public class window extends JFrame implements ActionListener
{
		JMenuBar menu=new JMenuBar();

		JMenu file=new JMenu("Fichier");
		JMenu filtre=new JMenu("Filtre");

		JMenuItem btnOuvrir=new JMenuItem("Ouvrir");
		JMenuItem btnEnregistrer=new JMenuItem("Enregistrer");
		JMenuItem btnEnregistrerSous=new JMenuItem("Enregistrer sous");
		JMenuItem btnVectorisation=new JMenuItem("Exporter comme SVG");
		//JMenuItem btnFermer=new JMenuItem("Fermer");
		JMenuItem btnQuitter=new JMenuItem("Quitter");
		JMenuItem btnSobel=new JMenuItem("Sobel");
		JMenuItem btnPrewitt=new JMenuItem("Prewitt");
		JMenuItem btnKirsche=new JMenuItem("Kirsche");
		JMenuItem btnRoberts=new JMenuItem("Roberts");
		ItemAction btnGauss=new ItemAction(new optionGauss(),"Gauss");
		JMenuItem btnCanny=new JMenuItem("Canny");
		JMenuItem btnEffacer=new JMenuItem("Effacer");

		ImageIcon imageAfficher;
		JLabel labelImageAfficher;

		File fichierImage;	//TODO en attribut si jamais on veux ecraser le fichier courant
		Filter newImage;
		BufferedImage imageOriginal;

		JFileChooser choix=new JFileChooser(new File("."));	//TODO filtre

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

		file.add(btnVectorisation);
		btnVectorisation.addActionListener(this);

		/*file.add(btnFermer);
		btnFermer.addActionListener(this);
*/
		file.add(btnQuitter);
		btnQuitter.addActionListener(this);

		filtre.add(btnSobel);
		btnSobel.addActionListener(this);

		filtre.add(btnPrewitt);
		btnPrewitt.addActionListener(this);

		filtre.add(btnKirsche);
		btnKirsche.addActionListener(this);

		filtre.add(btnRoberts);
		btnRoberts.addActionListener(this);

		filtre.add(btnGauss);
		btnGauss.addActionListener(this);
		btnGauss.opt.submit.addActionListener(this);

		filtre.add(btnCanny);
		btnCanny.addActionListener(this);

		filtre.add(btnEffacer);
		btnEffacer.addActionListener(this);

		
		this.setVisible(true);

	
	}


	private void ouvrir()
	{
		int boite=choix.showOpenDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as ouvert: "+choix.getSelectedFile());	//XXX
			fichierImage=new File(""+choix.getSelectedFile());
			try{
			imageOriginal=ImageIO.read(fichierImage);
			} catch (IOException e) {
				e.printStackTrace();
			}

			//this.printImage(image);	//TODO marche presque, si on ouvre une image alors qu'il y en a deja une ouverte = bug
			//TODO factoriser code dans méthode
			this.getContentPane().removeAll();
			imageAfficher=new ImageIcon(imageOriginal);
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
		int boite=choix.showSaveDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as enregistrer: "+choix.getSelectedFile());	//XXX
			File output=new File(""+choix.getSelectedFile());
			try {
			ImageIO.write(newImage.getImg(),"jpg",output);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void enregistrer()
	{
			File output=new File(""+choix.getSelectedFile());
			try {
			ImageIO.write(newImage.getImg(),"jpg",output);
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
		else if(e.getSource()==btnQuitter)
		{
			this.dispose();
		}
		else if(e.getSource()==btnEnregistrer)
		{
			this.enregistrer();
		}
		else if(e.getSource()==btnEnregistrerSous)
		{
			this.enregistrerSous();
		}

		else if(e.getSource()==btnVectorisation)
		{
			//TODO
		}

	/*	if(e.getSource()==btnFermer)
		{
			this.getContentPane().removeAll();
			this.revalidate();
		}
		if(e.getSource()==btnSobel)
		{
			newImage = new Sobel(imageOriginal);
			this.printImage(newImage.getImg());

		}
		if(e.getSource()==btnPrewitt)
		{
			newImage = new Prewitt(imageOriginal);
			this.printImage(newImage.getImg());
		}
		if(e.getSource()==btnKirsche)
		{
			newImage = new Kirsche(imageOriginal);
			this.printImage(newImage.getImg());
		}
		if(e.getSource()==btnRoberts)
		{
			newImage = new Roberts(imageOriginal);
			this.printImage(newImage.getImg());
		}
*/		else if(e.getSource() instanceof ItemAction)
		{
			ItemAction item=(ItemAction)e.getSource();
			item.affiche(imageOriginal);
			newImage=item.getImg();
			this.printImage(newImage.getImg());
		}

/*		if(e.getSource()==btnCanny)
		{
			newImage=new Canny(imageOriginal);
			this.printImage(newImage.getImg());
		}
*/

		if(e.getSource()==btnEffacer)
		{
			this.printImage(imageOriginal);
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
