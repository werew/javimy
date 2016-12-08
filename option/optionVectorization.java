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
import window.*;

public class optionVectorization extends JDialog implements ActionListener
{
	private	JTextField champNbCouleur=new JFormattedTextField();	
	private	JTextField champPrecision=new JFormattedTextField();

    private JLabel Txt_nbcouleurs = new JLabel("Nb colors");
    private JLabel Txt_precision  = new JLabel("Percent precision");


	
	private ImageIcon icone=new ImageIcon("icone.jpg");
	private	 JButton browse = new JButton ("Browse");
	public	JButton submit=new JButton("valider");
	public Filter image;
	public BufferedImage src;
	private JFileChooser choix = null; 
	private JPanel panel = new JPanel();


	public optionVectorization(BufferedImage src)
	{
		this.src=src;

		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(500,100);
		this.setLocationRelativeTo(null);
		this.setModal(false);
        this.setResizable(false);



		champNbCouleur.setPreferredSize(new Dimension(50,30));
		champPrecision.setPreferredSize(new Dimension(50,30));

        panel.add(Txt_nbcouleurs);
        panel.add(champNbCouleur);
        panel.add(Txt_precision);
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
				execute(src);
				this.dispose();
			}
		}
	}

}
