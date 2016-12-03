package option;
import java.awt.image.BufferedImage;
import filters.*;
import javax.swing.*;
import java.awt.*;

public abstract class Option extends JFrame //implements ActionListener
{
	private ImageIcon icone=new ImageIcon("icone.jpg");
	public	JButton submit=new JButton("Valider");
	public Filter image;

	public void affiche()
	{
		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(500,500);
		this.setLocationRelativeTo(null);


		this.setVisible(true);
	}

	abstract public void execute(BufferedImage src);

	public Filter getImg()
	{
		return image;
	}

/*	public void actionPerformed(ActionEvent e)
	{}*/
}
