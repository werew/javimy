package option;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JComponent.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.image.BufferedImage;
import filters.*;
import vectorization.*;
import javax.swing.JFormattedTextField;
import window.*;
import vectorization.*;
import javax.swing.ImageIcon;

public class optionVectorization extends JDialog
{
public	JTextField champNbCouleur=new JFormattedTextField();
public	JTextField champPrecision=new JFormattedTextField();	//DOUBLE
	
private ImageIcon icone=new imageicon("icone.jpg");
private JButton browse = new JButton ("Browse");
public	JButton submit=new JButton("valider");
public Filter image;
public BufferedImage src;
private JFileChooser choix = new JFileChooser(".");

//TODO ajouter specificite JDIALOG

	public optionVectorization()
	{
		this.src=src;

		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setModal(true);



		champNbCouleur.setPreferredSize(new Dimension(100,50));
		champPrecision.setPreferredSize(new Dimension(100,50));

		JPanel panel=new JPanel();
		panel.add(champNbCouleur);
		panel.add(champPrecision);

		panel.add(submit);
		panel.add(browse);

		this.setContentPane(panel);

		this.setVisible(true);
	}

	private void enregistrerSous()
	{
		int boite=choix.showSaveDialog(null);
		if(boite==JFileChooser.APPROVE_OPTION)
		{
			System.out.println("Bravo, tu as enregistrer: "+choix.getSelectedFile().toString());	//XXX
		}
	}

	public void execute(BufferedImage src)
	{
		int nbCouleur=Integer.parseInt(champNbCouleur.getText());
		double precision=Double.parseDouble((champPrecision.getText()));
	//Segmentation
        try {
            Clusterizator f = new Clusterizator(src, nbCouleur); 
            image = f.getImg();

        } catch (IOException e){
        };

	//Vect
	ConverterSVG svg=new ConverterSVG(image/*,precision*/);
//	svg.export;


	}
}
