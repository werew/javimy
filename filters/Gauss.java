package filters;

public class Gauss
{
	private double noyau[][];
	private int rayon;
	private double sigma;
	private BufferedImage image;

	public Gauss(BufferedImage src,int rayon, double sigma)
	{
		//TODO testet rayon et sigma

		this.noyau=new float[2*rayon+1,2*rayon+1];
		this.rayon=rayon;
		this.sigma=sigma;
		this.image=new BufferedImage(src.getWidth()-2,src.getHeight()-2,src.getType())

		generationNoyau();

		
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
			}
		}
	}
}
