package filters;

public class gradient
{
	public int Gx;
	public int Gy;
	public int Norme;
	public int Angle;
	public boolean etat;

	public gradient(int x,int y)
	{
		this.Gx=x;
		this.Gy=y;
        	Norme=(int) Math.ceil(Math.sqrt((Gx*Gx) + (Gy*Gy)));	//XXX Int?
		Angle= (int) Math.atan2(Gy,Gx);
	}

	public void setNorme()
	{
        	Norme= (int) Math.ceil(Math.sqrt((Gx*Gx) + (Gy*Gy)));	//XXX Int?
	}

	public void setAngle()
	{
		Angle= (int) Math.atan2(Gy,Gx);
	}
}
