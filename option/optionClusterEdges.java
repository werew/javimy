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

public class optionClusterEdges extends Option
{
	private	JTextField n_colors=new JFormattedTextField();
	private JLabel Txt_n_colors = new JLabel("How many colors:");
	


	public optionClusterEdges()
	{
		super();

		this.setSize(300,100);

		Dimension dim = new Dimension(50,30);
		n_colors.setPreferredSize(dim);

		this.panel.add(Txt_n_colors);
		this.panel.add(n_colors);
		this.panel.add(submit);

		GridLayout gl = new GridLayout(3,2);
		gl.setHgap(5);
		gl.setVgap(5);
		this.panel.setLayout(gl);

		this.setContentPane(this.panel);
	}

	public void execute(BufferedImage src)
	{
		String input = n_colors.getText();

		if(input.length()==0)
		{
			new popup("Option invalide");
			return;
		}

		int n = Integer.parseInt(input);

		image = new Clusterizator(src,n).getImg();
	}
}
