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
		
		Color max=new Color(0,0,0);
		for (int i=rayon; i<src.getWidth()-rayon; i++){
          	  for (int j=rayon; j<src.getHeight()-rayon; j++){
          	      Color val = convolution(i,j);
          	      if (val.getRed()>max.getRed())	max=new Color(val.getRed(),max.getGreen(),max.getBlue());
			if(val.getGreen()>max.getGreen()) max=new Color(max.getRed(),val.getGreen(),max.getBlue());
			if(val.getBlue()>max.getBlue()) max=new Color(max.getRed(),max.getGreen(),val.getBlue());
			}
       		 }


		int i,j;
		Color val;
		for(i=rayon;i<src.getHeight()-rayon;i++)
		{
			for(j=rayon;j<src.getWidth()-rayon;j++)
			{
				val=convolution(j,i);
				// Truncate values
                int valRed = (int) Math.floor((255*val.getRed())/max.getRed());
                int valGreen = (int) Math.floor((255*val.getGreen())/max.getGreen());
                int valBlue = (int) Math.floor((255*val.getBlue())/max.getBlue());
                // Normalize


/*                if (val > 255 - 100) val = 255;
                else if (val < 100) val = 0;
  */  
                // Set pixel
                Color nc = new Color(valRed,valGreen,valBlue);
                image.setRGB(j-1,i-1,nc.getRGB());

			}
		}


	}

	private Color convolution(int y,int x)
	{
		Color total=new Color(0,0,0);
		int i=0,j=0;

		int decalage_hauteur=(dimNoyau-1)/2;
		int decalage_largeur=(dimNoyau-1)/2;

		for(j=0;j<dimNoyau;j++)
		{
			for(i=0;i<dimNoyau;i++)
			{
		//		int c=src.getRGB(y-decalage_hauteur+i,x-decalage_largeur+j);
				Color c = new Color(src.getRGB(y-decalage_hauteur+i,x-decalage_largeur+j));
				int red = (int)(c.getRed() * noyau[i][j]);
				int green = (int)(c.getGreen() * noyau[i][j]);
				int blue = (int)(c.getBlue() * noyau[i][j]);

				total=new Color(total.getRed()+red,total.getGreen()+green,total.getBlue()+blue);
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
