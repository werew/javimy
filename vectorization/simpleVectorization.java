package vectorization;
import filters.*;
import java.awt.image.*;
import java.awt.*;

public class simpleVectorization
{
	private BufferedImage imageVect;
	private Filter imageFiltrer;
	private int seuil=200;

	public simpleVectorization(BufferedImage image)
	{
		this.imageFiltrer=new Sobel(image);	//TODO changer en fonction du filtre
		int w=imageFiltrer.getImg().getWidth();
		int h=imageFiltrer.getImg().getHeight();

		imageVect=new BufferedImage(w,h,image.getType());
		int i,j;

		for(i=0;i<h;i++)
		{
			for(j=0;j<w;j++)
			{
		//		System.out.println("w: "+w+"\nh: "+h+"\ni: "+i+"\nj: "+j+"\n");
				Color c=new Color(imageFiltrer.getImg().getRGB(j,i));
				int red = (int)(c.getRed() * 0.3);
				int green = (int)(c.getGreen() * 0.3);
				int blue = (int)(c.getBlue() * 0.3);
				int rgb = red+green+blue;

				if(rgb>seuil)
				{

					if(imageVect.getRGB(j,i)==imageFiltrer.getImg().getRGB(j,i))
					{
						continue;
					}

					int x,y;
					for(x=-1;x<=1;x++)
					{
						for(y=-1;y<=1;y++)
						{
							if(x==0 && y==0)	//TODO changer, pas beau
							{	
							}
							else
							{
								if(i+x<0 || j+y<0 || j+y>=imageFiltrer.getImg().getWidth() || i+x>=imageFiltrer.getImg().getHeight())
								{
									continue;
								}

								//verifier que le pixel n'est pas deja reporter sur l'image vectorise
								if(imageVect.getRGB(j+y,i+x)==imageFiltrer.getImg().getRGB(j+y,i+x))
								{
									continue;
								}

								c=new Color(imageFiltrer.getImg().getRGB(j,i));
								red = (int)(c.getRed() * 0.3);
								green = (int)(c.getGreen() * 0.3);
								blue = (int)(c.getBlue() * 0.3);
								rgb = red+green+blue;


								if(rgb<seuil)
								{
									continue;
								}
								vectRec(j,i,y,x);
							}
						}
					}
				}
			}
		}
		System.out.println("Fini");
	}

	private void vectRec(int j,int i,int diry,int dirx)
	{
		//verifier qu'on n'est pas sortit de l'image

		if(i<0 || j<0 || j>=imageFiltrer.getImg().getWidth() || i>=imageFiltrer.getImg().getHeight())
		{
			return;
		}

		//verifier que le pixel n'est pas deja reporter sur l'image vectorise
		if(imageVect.getRGB(j,i)==imageFiltrer.getImg().getRGB(j,i))
		{
			return;
		}

		Color c=new Color(imageFiltrer.getImg().getRGB(j,i));
		int red = (int)(c.getRed() * 0.3);
		int green = (int)(c.getGreen() * 0.3);
		int blue = (int)(c.getBlue() * 0.3);
		int rgb = red+green+blue;


		if(rgb<seuil)
		{
			return;
		}
		System.out.println(rgb);
		//marquer pixel dans image vect
		imageVect.setRGB(j,i,rgb);

		vectRec(j+diry,i+dirx,diry,dirx);
	}

	public BufferedImage getImg()
	{
		return this.imageVect;
	}
}
