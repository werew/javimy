package option;
import java.awt.image.BufferedImage;
import filters.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Option extends JDialog implements ActionListener
{
	private ImageIcon icone=new ImageIcon("icone.jpg");
	public	JButton submit=new JButton("valider");
	public Filter image;
	public BufferedImage src;

	public Option()
	{
		
		submit.addActionListener(this);
	}

	public void affiche(BufferedImage src)
	{
		this.src=src;

		this.setTitle("Javimy");
		this.setIconImage(icone.getImage());
		this.setSize(400,100);
		this.setLocationRelativeTo(null);
		this.setModal(true);


		this.setVisible(true);
	}

	abstract public void execute(BufferedImage src);

	public Filter getImg()
	{
		return image;
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==submit)
		{
			this.execute(src);
			this.dispose();
		}
	}
}
