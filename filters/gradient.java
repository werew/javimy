package filters;

public class gradient
{
	public int Gx;
	public int Gy;
	public int Norme;
	public double Angle;
	public boolean etat;

	public gradient(int x,int y)
	{
		this.Gx=x;
		this.Gy=y;
        	Norme=(int) Math.ceil(Math.sqrt((Gx*Gx) + (Gy*Gy)));	//XXX Int?
		Angle= (int) Math.atan2(Gy,Gx);

		this.etat=true;
	}

	public void setNorme()
	{
        	Norme= (int) Math.ceil(Math.sqrt((Gx*Gx) + (Gy*Gy)));	//XXX Int?
	}

	public void setAngle()
	{
		Angle= Math.toDegrees(Math.atan2(Gy,Gx));
	}
}
