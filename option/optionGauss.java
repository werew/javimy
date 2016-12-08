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
public	JTextField sigma=new JFormattedTextField();
public	JTextField rayon=new JFormattedTextField();
public JLabel TxtSigma = new JLabel("Sigma");
public JLabel TxtRayon = new JLabel("Rayon");
	
//TODO mettre sous forme comme les autres

	public optionGauss()
	{
		super();

		this.setSize(300,200);

		Dimension dim = new Dimension(50,30);

		sigma.setPreferredSize(dim);
		rayon.setPreferredSize(dim);

		panel.add(TxtSigma);
		panel.add(sigma);
		panel.add(TxtRayon);
		panel.add(rayon);

		panel.add(submit);

		GridLayout gl = new GridLayout(3,2);
		gl.setHgap(5);
		gl.setVgap(5);
		this.panel.setLayout(gl);


		this.setContentPane(panel);
	}

	public void execute(BufferedImage src)
	{
		int r= Integer.parseInt(rayon.getText());
		double s= Double.parseDouble(sigma.getText());
		image=new Gauss(src,r,s).getImg();
	}
}
