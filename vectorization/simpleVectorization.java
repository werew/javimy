import filters.*;

public class simpleVectorization
{
	private BufferedImage imageVect;
	private Filter imageFiltrer;
	private int seuil=200;

	public simpleVectorization(BufferedImage image)
	{
		this.imageFiltrer=new Sobel(image);
		int w=image.getWidth();
		int h=image.getHeight();

		imageVect=new BufferedImage(w,h,image.getType());
		int i,j;

		for(i=0;i<w;i++)
		{
			for(j=0;j<h;j++)
			{
				Color c=new Color(imageFiltrer.getRGB(i,j));
				int red = (int)(c.getRed() * 0.3);
				int green = (int)(c.getGreen() * 0.3);
				int blue = (int)(c.getBlue() * 0.3);
				int rgb = red+green+blue;

				if(rgb>seuil)
				{
					vectRec(i,j);
				}
			}
		}
	}

	private void vectRec(int i,int j)
	{
		Color c=new Color(imageFiltrer.getRGB(i,j));
		int red = (int)(c.getRed() * 0.3);
		int green = (int)(c.getGreen() * 0.3);
		int blue = (int)(c.getBlue() * 0.3);
		int rgb = red+green+blue;

		if(rgb<seuil)
		{
			return;
		}
	}
}
