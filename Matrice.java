public class Matrice
{
	private int[][] data;
	private int hauteur;
	private int largeur;

	public Matrice(int h,int l)
	{
		this.data=new int[l][h];
		this.largeur=l;
		this.hauteur=h;
	}
}
