package option;
import java.awt.image.BufferedImage;
import filters.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Option extends JDialog implements ActionListener
{
	public ImageIcon icone=new ImageIcon("icone.jpg");
	public JButton submit=new JButton("valider");

	//Image traité
	public BufferedImage image=null;

	//Image source
	public BufferedImage src;

	public JPanel panel=new JPanel();

	public Option()
	{
		
		submit.addActionListener(this);
	}

	public void affiche(BufferedImage src)
	{
		this.src=src;

		this.setTitle("Options");
		this.setIconImage(icone.getImage());
		this.setLocationRelativeTo(null);
		this.setModal(true);
	        this.setResizable(false);


		this.setVisible(true);
	}

	//Execute le traitement à effectuer sur l'image source
	abstract public void execute(BufferedImage src);

	public BufferedImage getImg()
	{
		return image;
	}

	public void actionPerformed(ActionEvent e)
	{
		//Si on valide les options, on traite l'image
		if(e.getSource()==submit)
		{
			this.execute(src);
			this.dispose();
		}
	}
}
