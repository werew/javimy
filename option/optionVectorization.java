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

public class optionVectorization extends Option
{
public	JTextField champNbCouleur=new JFormattedTextField();
	


	public optionVectorization()
	{
		super();

		champNbCouleur.setPreferredSize(new Dimension(100,50));

		JPanel panel=new JPanel();
		panel.add(champNbCouleur);

		panel.add(submit);

		this.setContentPane(panel);
	}

	public void execute(BufferedImage src)
	{
		int nbCouleur=Integer.parseInt(champNbCouleur.getText());
        try {
            Clusterizator f = new Clusterizator(src, 2); 
            image = f.getImg();
            File output = new File("out.jpg");


        } catch (IOException e){
        };


	}
}
