package option;
import java.awt.image.BufferedImage;
import filters.*;
import javax.swing.*;
import java.awt.*;


public class ItemAction extends JMenuItem
{
	public Option opt;

	public ItemAction(Option opt,String nom)
	{
		super(nom);
		this.opt=opt;
	}

	public void affiche(BufferedImage src)
	{
		opt.affiche(src);
	}

	public Filter getImg()
	{
		return opt.getImg();
	}
}
