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
import vectorization.*;
import javax.swing.ImageIcon;
import window.popup;

public class optionVectorization extends JDialog implements ActionListener
{
	public	JTextField champNbCouleur=new JFormattedTextField();	//TODO filtrer si il y a de caractere
	public	JTextField champPrecision=new JFormattedTextField();	//DOUBLE
	
	private ImageIcon icone=new ImageIcon("icone.jpg");
	private	 JButton browse = new JButton ("Browse");
	public	JButton submit=new JButton("valider");
	public Filter image;
	public BufferedImage src;
	private JFileChooser choix = null; 


	public optionVectorization(BufferedImage src)
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

		browse.addActionListener(this);
		submit.addActionListener(this);

		this.setContentPane(panel);

		this.setVisible(true);
	}


	public void execute(BufferedImage src)
	{
		int nbCouleur=Integer.parseInt(champNbCouleur.getText());
		float precision=Float.parseFloat((champPrecision.getText()));
	//Segmentation
		Clusterizator f = new Clusterizator(src, nbCouleur); 

		ConverterSVG svg=new ConverterSVG(f.getImg() ,precision); 
        try {

		svg.export(choix.getSelectedFile().toString());
        } catch (IOException e){
        }

	}


	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==browse)
		{
			choix=new JFileChooser(".");
			choix.showSaveDialog(null);
		}
		else if (e.getSource()==submit)
		{
			if(choix==null)
			{
				new popup("Pas de chemin de sauvegarde");
			}
			else
			{
				new popup("Veuillez patienter");	
				execute(src);
				new popup("Fichier enregistrer");
				this.dispose();
			}
		}
	}

}
