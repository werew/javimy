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

public class optionGauss extends Option
{
public	JTextField seuil=new JFormattedTextField();
public	JTextField sigma=new JFormattedTextField();
public	JTextField rayon=new JFormattedTextField();
	


	public optionGauss()
	{
		super();

		seuil.setPreferredSize(new Dimension(100,50));
		sigma.setPreferredSize(new Dimension(100,50));
		rayon.setPreferredSize(new Dimension(100,50));

		JPanel panel=new JPanel();
		panel.add(seuil);
		panel.add(sigma);
		panel.add(rayon);

		panel.add(submit);

		this.setContentPane(panel);
	}

	public void execute(BufferedImage src)
	{
		image=new Gauss(src,2,2);
	}
}
