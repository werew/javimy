package filters;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Gauss extends Filter
{
	private double noyau[][];
	private int dimNoyau;
	private int rayon;
	private double sigma;
	private BufferedImage image;
	private BufferedImage src;

	public Gauss(BufferedImage src,int rayon, double sigma)
	{
		//TODO testet rayon et sigma

		this.dimNoyau=2*rayon+1;
		this.noyau=new double[dimNoyau][dimNoyau];
		this.rayon=rayon;
		this.sigma=sigma;
		this.src=src;
		this.image=new BufferedImage(src.getWidth()-rayon,src.getHeight()-rayon,src.getType());

		generationNoyau();

		//TODO changer
		int max=0;
		for (int i=1; i<src.getWidth()-1; i++){
          	  for (int j=1; j<src.getHeight()-1; j++){
          	      int val = convolution(i,j);
          	      if (val>max) max = val;
          	  }
       		 }


		int i,j;
		int val;
		for(i=rayon;i<src.getHeight()-rayon;i++)
		{
			for(j=rayon;j<src.getWidth()-rayon;j++)
			{
				val=convolution(j,i);
				// Truncate values
                val = (int) Math.floor((255*val)/max);
                // Normalize
                if (val > 255 - 100) val = 255;
                else if (val < 100) val = 0;
    
                // Set pixel
                Color nc = new Color(val,val,val);
                image.setRGB(j-1,i-1,nc.getRGB());

			}
		}


	}

	private int convolution(int y,int x)
	{
		int total=0;
		int i=0,j=0;

		int decalage_hauteur=(dimNoyau-1)/2;
		int decalage_largeur=(dimNoyau-1)/2;

		for(j=0;j<dimNoyau;j++)
		{
			for(i=0;i<dimNoyau;i++)
			{
		//		int c=src.getRGB(y-decalage_hauteur+i,x-decalage_largeur+j);
				Color c = new Color(src.getRGB(y-decalage_hauteur+i,x-decalage_largeur+j));
				int red = (int)(c.getRed() * 0.3);
				int green = (int)(c.getGreen() * 0.3);
				int blue = (int)(c.getBlue() * 0.3);
				int rgb = red+green+blue;

				total+=noyau[i][j]*rgb;
			}
		}
//		System.out.println(total);
//		this.image.setRGB(i,j,total);
		return total;
	}

	private void generationNoyau()
	{
		double diviseur=0.;

		int i,j;
		for(i=-rayon;i<=rayon;i++)
		{
			for(j=-rayon;j<=rayon;j++)
			{
				noyau[j+rayon][i+rayon]=(1./(2*Math.PI*sigma*sigma))*(Math.exp(-((j*j+i*i)/(2*sigma*sigma))));
				diviseur+=noyau[j+rayon][i+rayon];
			}
		}

		//XXX pour ne pas eclaircir ou assombrir l'image
		for(i=0;i<2*rayon+1;i++)
		{
			for(j=0;j<2*rayon+1;j++)
			{
				noyau[j][i]/=diviseur;
				System.out.println(noyau[j][i]);
			}
		}
	}

	public BufferedImage getImg()
	{
		return this.image;
	}
}
