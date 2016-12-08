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

public class optionRoberts extends Option
{
	private	JTextField thr_min=new JFormattedTextField();
	private	JTextField thr_max=new JFormattedTextField();
	private JLabel Txt_thr_min = new JLabel("thr min");
	private JLabel Txt_thr_max = new JLabel("thr max");
	


	public optionRoberts()
	{
		super();

		this.setSize(300,100);

		Dimension dim = new Dimension(50,30);

		thr_min.setPreferredSize(dim);
		thr_max.setPreferredSize(dim);

		this.panel.add(Txt_thr_min);
		this.panel.add(thr_min);
		this.panel.add(Txt_thr_max);
		this.panel.add(thr_max);


		this.panel.add(submit);

		GridLayout gl = new GridLayout(3,2);
		gl.setHgap(5);
		gl.setVgap(5);
		this.panel.setLayout(gl);

		this.setContentPane(this.panel);
	}

	public void execute(BufferedImage src)
	{
		String ttmi= thr_min.getText();
		String ttma= thr_max.getText();

		if(ttmi.length()==0 || ttma.length()==0)
		{
			new popup("Option invalide");
			return;
		}

		int tmic= Integer.parseInt(ttmi);
		int tmac= Integer.parseInt(ttma);

		image=new Roberts(src,tmic,tmac).getImg();
	}
}
