package option;
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
import javax.swing.JFormattedTextField;
import window.*;

public class optionVectorization extends JDialog
{
public	JTextField champNbCouleur=new JFormattedTextField();
public	JTextField champPrecision=new JFormattedTextField();	//DOUBLE
	
//TODO ajouter specificite JDIALOG

	public optionVectorization()
	{
		super();

		champNbCouleur.setPreferredSize(new Dimension(100,50));
		champPrecision.setPreferredSize(new Dimension(100,50));

		JPanel panel=new JPanel();
		panel.add(champNbCouleur);
		panel.add(champPrecision);

		panel.add(submit);

		this.setContentPane(panel);
	}

	public void execute(BufferedImage src)
	{
		int nbCouleur=Integer.parseInt(champNbCouleur.getText());
		int precision=Double.parseDouble((champPrecision.getText());
	//Segmentation
        try {
            Clusterizator f = new Clusterizator(src, nbCouleur); 
            image = f.getImg();

        } catch (IOException e){
        };

	//Vect
	ConverterSVG svg=new ConverterSVG(image/*,precision*/);
	svg.export


	}
}
