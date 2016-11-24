package filters;

public gradient
{
	public int Gx;
	public int Gy;

	public gradient(int x,int y)
	{
		this.Gx=x;
		this.Gy=y;
	}

	public int getNorme()
	{
        	return (int) Math.ceil(Math.sqrt((Gx*Gx) + (Gy*Gy)));	//XXX Int?
	}

	public int getAngle()
	{
		return (int) Math.atan2(Gy,Gx);
	}
}
