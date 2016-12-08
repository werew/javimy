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

public class mainWindow extends JFrame implements ActionListener
{
		//Barre de menu
		JMenuBar menu=new JMenuBar();

		//Menu de la barre
		JMenu file=new JMenu("Fichier");
		JMenu filtre=new JMenu("Filtre");

		//Item
		JMenuItem btnOuvrir=new JMenuItem("Ouvrir");
		JMenuItem btnEnregistrer=new JMenuItem("Enregistrer");
		JMenuItem btnEnregistrerSous=new JMenuItem("Enregistrer sous");
		JMenuItem btnVectorisation=new JMenuItem("Exporter comme SVG");
		JMenuItem btnQuitter=new JMenuItem("Quitter");
		ItemAction btnSobel=new ItemAction(new optionSobel(),"Sobel");
		ItemAction btnPrewitt=new ItemAction(new optionPrewitt(),"Prewitt");
		ItemAction btnKirsch=new ItemAction(new optionKirsch(),"Kirsch");
		ItemAction btnRoberts=new ItemAction(new optionRoberts(),"Roberts");
		ItemAction btnGauss=new ItemAction(new optionGauss(),"Gauss");
		ItemAction btnCanny=new ItemAction(new optionCanny(),"Canny");
		ItemAction btnSegmentation=new ItemAction(new
optionSegmentation(),"Segmentation");
		ItemAction btnClusterEdges=new ItemAction(new
optionClusterEdges(),"Cluster-Edges");
		JMenuItem btnEffacer=new JMenuItem("Effacer");

		//Image que l'on souhaite afficher
		ImageIcon imageAfficher;
		JLabel labelImageAfficher;

		//Fichier image source
		File fichierImage;

		//Image filtrer
		BufferedImage newImage=null;

		//Image original
		BufferedImage imageOriginal=null;

		JFileChooser choix=new JFileChooser(new File("."));

		//Icone du programme
		ImageIcon icone=new ImageIcon("icone.jpg");

		//Chemin du fichier original
		String pathOriginal;

	public mainWindow()
	{
		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setJMenuBar(menu);

		//Initialisation de l'interface

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

		file.add(btnQuitter);
		btnQuitter.addActionListener(this);

		filtre.add(btnSobel);
		btnSobel.addActionListener(this);

		filtre.add(btnPrewitt);
		btnPrewitt.addActionListener(this);

		filtre.add(btnKirsch);
		btnKirsch.addActionListener(this);

		filtre.add(btnRoberts);
		btnRoberts.addActionListener(this);

		filtre.add(btnGauss);
		btnGauss.addActionListener(this);
		btnGauss.opt.submit.addActionListener(this);

		filtre.add(btnCanny);
		btnCanny.addActionListener(this);

		filtre.add(btnSegmentation);
		btnSegmentation.addActionListener(this);

		filtre.add(btnClusterEdges);
		btnClusterEdges.addActionListener(this);

		filtre.add(btnEffacer);
		btnEffacer.addActionListener(this);

		
		this.setVisible(true);

	
	}


	private void ouvrir()
	{
		int boite=choix.showOpenDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			fichierImage=new File(choix.getSelectedFile().toString());
			try{
				imageOriginal=ImageIO.read(fichierImage);
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Initialisation du chemin du fichier source
			this.pathOriginal=choix.getSelectedFile().toString();

			printImg(imageOriginal);
		}
		else
		{
			System.out.println("Erreur lors de l'ouverture du fichier");
		}
	}

	private void enregistrerSous()
	{
		//Si on a pas ouvert d'image
		if(imageOriginal==null)
		{
			new popup("Pas d'image ouverte");
			return;
		}

		//Si on a pas modifier l'image, on empeche l'enregistrement
		if(newImage==null)
		{
			new popup("Image non modifier");
			return;
		}
		int boite=choix.showSaveDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			File output=new File(choix.getSelectedFile().toString());
			try {
				ImageIO.write(newImage,"jpg",output);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void enregistrer()
	{
		//Si on a pas ouvert d'image
		if(imageOriginal==null)
		{
			new popup("Pas d'image ouverte");
			return;
		}

		//Si on a pas modifier l'image, on empeche l'enregistrement
		if(newImage==null)
		{
			new popup("Image non modifier");
			return;
		}

		File output=new File(pathOriginal);
		try {
			ImageIO.write(newImage,"jpg",output);
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
			//Si on a pas ouvert d'image
			if(imageOriginal==null)
			{
				new popup("Pas d'image ouverte");
			}
			else
			{
				new optionVectorization(imageOriginal);
			}
		}

		else if(e.getSource() instanceof ItemAction)
		{
			//Si on a pas ouvert d'image
			if(imageOriginal==null)
			{
				new popup("Pas d'image ouverte");
			}
			else
			{
				ItemAction item=(ItemAction)e.getSource();
				item.affiche(imageOriginal);
				newImage=item.getImg();
				if(newImage!=null)
				{
					this.printImage(newImage);
				}
			}
		}

		if(e.getSource()==btnEffacer)
		{
			this.printImage(imageOriginal);
		}
		
	}

	private void printImage(BufferedImage i)
	{
		if(i!=null)
		{
			this.getContentPane().remove(labelImageAfficher);
			imageAfficher=new ImageIcon(i);
			labelImageAfficher=new JLabel("",SwingConstants.CENTER);
			labelImageAfficher.setIcon(imageAfficher);
			this.getContentPane().add(labelImageAfficher,BorderLayout.CENTER);
			this.revalidate();	
		}
	}
	
}
