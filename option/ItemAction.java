package option;
import java.awt.image.BufferedImage;
import filters.*;
import javax.swing.*;
import java.awt.*;


public class ItemAction extends JMenuItem
{
	//Fenetre d'option
	public Option opt;

	public ItemAction(Option opt,String nom)
	{
		super(nom);
		this.opt=opt;
	}

	//Provoque l'affichage de la fenetre d'option
	public void affiche(BufferedImage src)
	{
		opt.affiche(src);
	}

	//Return l'image
	public BufferedImage getImg()
	{
		return opt.getImg();
	}
}
