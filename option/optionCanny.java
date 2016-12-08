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

public class optionCanny extends Option
{
	private	JTextField thr_min_conv=new JFormattedTextField();
	private	JTextField thr_max_conv=new JFormattedTextField();
	private	JTextField thr_min_hyst=new JFormattedTextField();
	private	JTextField thr_max_hyst=new JFormattedTextField();

	private JLabel Txt_thr_min_conv = new JLabel("thresh min ");
	private JLabel Txt_thr_max_conv = new JLabel("thresh max ");
	private JLabel Txt_thr_min_hyst = new JLabel("double thresh min");
	private JLabel Txt_thr_max_hyst = new JLabel("double thresh max");
	


	public optionCanny()
	{
		super();

		this.setSize(300,200);


		//Ajout des différents composants

		Dimension dim = new Dimension(80,30);

		thr_min_conv.setPreferredSize(dim);
		thr_max_conv.setPreferredSize(dim);
		thr_min_hyst.setPreferredSize(dim);
		thr_max_hyst.setPreferredSize(dim);

		this.panel.add(Txt_thr_min_conv);
		this.panel.add(thr_min_conv);
		this.panel.add(Txt_thr_max_conv);
		this.panel.add(thr_max_conv);
		this.panel.add(Txt_thr_min_hyst);
		this.panel.add(thr_min_hyst);
		this.panel.add(Txt_thr_max_hyst);
		this.panel.add(thr_max_hyst);


		this.panel.add(submit);

		GridLayout gl = new GridLayout(7,2);
		gl.setHgap(5);
		gl.setVgap(5);
		this.panel.setLayout(gl);

		this.setContentPane(this.panel);
	}

	//Execution de Canny
	public void execute(BufferedImage src)
	{
		String ttmic= thr_min_conv.getText();
		String ttmac= thr_max_conv.getText();
		String ttmih= thr_min_hyst.getText();
		String ttmah= thr_max_hyst.getText();

		if(ttmic.length()==0 || ttmac.length()==0 || ttmih.length()==0 || ttmah.length()==0)
		{
			new popup("Option invalide");
			return;
		}

		int tmic= Integer.parseInt(ttmic);
		int tmac= Integer.parseInt(ttmac);
		int tmih= Integer.parseInt(ttmih);
		int tmah= Integer.parseInt(ttmah);

		image=new Canny(src,tmic,tmac,tmih,tmah,1,0.8).getImg();
	}
}
