public class Matrice
{
	private int[][] data;
	private int hauteur;
	private int largeur;

	public Matrice(int h,int l)
	{
		this.data=new int[h][l];
		this.largeur=l;
		this.hauteur=h;
	}

	public int getHauteur()
	{ return this.hauteur;}

	public int getLargeur()
	{ return this.largeur;}

	public int getElt(int x,int y)
	{
		return this.data[x][j];
	}

	private int convolution(Matrice m,int y,int x)
	{
		int ml=m.getLargeur();
		int mh=m.getHauteur();

		if(ml%2 != 0 || mh%2 != 0)
		{
			//TODO Exception
		}

		Matrice resultat=new Matrice(mh,ml);
		int total=0;
		int i,j;

		int decalage_hauteur=(mh-1)/2;
		int decalage_largeur=(ml-1)/2;

		for(i=0;i<ml;i++)
		{
			for(j=0;j<mh;j++)
			{
				total+=(this.getElt[y-decalage_hauteur+i][x-decalage_largeur+j] *
					m.getElt[i][j];
			}
		}
		return total;
	}
}
