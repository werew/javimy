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

public class optionGauss extends JFrame implements ActionListener
{
public	JTextField seuil=new JFormattedTextField();
public	JTextField sigma=new JFormattedTextField();
public	JTextField rayon=new JFormattedTextField();
public	JButton submit=new JButton("Valider");
	
	Filter image;
	BufferedImage src;


	ImageIcon icone=new ImageIcon("icone.jpg");

	public optionGauss(BufferedImage src,window w)
	{
		this.src=src;

		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(500,500);
		this.setLocationRelativeTo(null);


		seuil.setPreferredSize(new Dimension(100,50));
		sigma.setPreferredSize(new Dimension(100,50));
		rayon.setPreferredSize(new Dimension(100,50));

		JPanel panel=new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.add(seuil);
		panel.add(sigma);
		panel.add(rayon);

		submit.addActionListener(w);
		panel.add(submit);

		this.setContentPane(panel);
		this.setVisible(true);
	}


	public Filter getImg()
	{
		return image;
	}

	public void actionPerformed(ActionEvent e)
	{
	}
}
