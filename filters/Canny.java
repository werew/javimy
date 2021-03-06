package filters;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;

public class Canny extends Filter {

    private int max = 1;
    private int thr=0;

    private int[][] mx = {{-1,0,1},{-2,0,2},{-1,0,1}};
    private int[][] my = {{-1,-2,-1},{0,0,0},{1,2,1}};
    private gradient carte[][];
    private int thr_min_conv;
    private int thr_max_conv;
    private int thr_min_hyst;
    private int thr_max_hyst;

    BufferedImage src;


    public Canny(BufferedImage img, int thr_min_conv,int thr_max_conv,
                 int thr_min_hyst,int thr_max_hyst,int rayon,double sigma ){
	src=new Gauss(img,rayon,sigma).getImg();	
	this.thr_min_conv = thr_min_conv;
	this.thr_max_conv = thr_max_conv;
	this.thr_min_hyst = thr_min_hyst;
	this.thr_max_hyst = thr_max_hyst;
	

        int w = src.getWidth();
        int h = src.getHeight();
        image = new BufferedImage(w-2,h-2,src.getType());
	carte = new gradient[image.getWidth()][image.getHeight()];


        // Set max
        for (int i=1; i<h-1; i++){
            for (int j=1; j<w-1; j++){
                gradient val = convolution(j,i);
                if (val.Norme>max) max = val.Norme;
            }
        }

        // Apply filter 
        for (int i=1; i<h-1; i++){
            for (int j=1; j<w-1; j++){
                gradient val = convolution(j,i);

                // Truncate values
                val.Norme = (int) Math.floor((255*val.Norme)/max);
                // Normalize
                if (val.Norme > 255 - thr_max_conv) val.Norme = 255;
                else if (val.Norme < thr_min_conv) val.Norme = 0;

		//Arondissement des angles
		val.Angle=roundedAngle(val.Angle); 

		carte[j-1][i-1]=val;
            }
        }


	//Suppression des non maximum

	for(int i=1;i<h-3;i++)
	{
		for(int j=1;j<w-3;j++)
		{
			nonMaxima(j,i);
		}
	}


	//Seuillage

	ArrayList<Point> incertain=new ArrayList<Point>();

	for(int i=0;i<h-2;i++)
	{
		for(int j=0;j<w-2;j++)
		{
			if(carte[j][i].etat)
			{
				if(carte[j][i].Norme<thr_min_hyst)
				{
					carte[j][i].etat=false;
				}
				else if(carte[j][i].Norme>thr_max_hyst)
				{
					carte[j][i].etat=true;
				}
				else
				{
					incertain.add(new Point(j,i));
				}
			}

		}
	}

	/*un point incertain peux devenir valide si
	au moins un de ces voisins est valide*/
	for(Point p : incertain)
	{
		int x=p.x;
		int y=p.y;

		carte[x][y].etat=false;

		int i,j;
		for(i=-1;i<=1;i++)
		{
			for(j=-1;j<=1;j++)
			{
				if(i!=0 || j!=0)
				{
					if(x+j>=0 && y+i>=0 && x+j<w-2 && y+i<h-2)
					{
						carte[x][y].etat=carte[x][y].etat || carte[x+j][y+i].etat;
					}
				}
			}
		}
	}

	for(int i=0;i<h-2;i++)
	{
		for(int j=0;j<w-2;j++)
		{
			if(carte[j][i].etat)
			{
				Color nc=new Color(255,255,255);
				image.setRGB(j,i,nc.getRGB());
			}
			else
			{
				Color nc=new Color(0,0,0);
				image.setRGB(j,i,nc.getRGB());
			}
		}
	}
    }

    public BufferedImage getImg(){
        return image;
    }

    private gradient convolution(int x, int y){
        int px_x = 0;
        int px_y = 0;

        for (int i=-1; i<=1; i++){
            for (int j=-1; j<=1; j++){

		Color c=null;
                c = new Color(src.getRGB(j+x,i+y));
			
                int red = (int)(c.getRed() * 0.3);
                int green = (int)(c.getGreen() * 0.3);
                int blue = (int)(c.getBlue() * 0.3);
                int rgb = red+green+blue;
                px_x += mx[j+1][i+1] * rgb;
                px_y += my[j+1][i+1] * rgb;
            }
        }

    	gradient g=new gradient(px_x,px_y);
        return g;
    }


	private double roundedAngle(double angle)
	{
		if(angle>=0 && angle<45)
		{
			return 0;
		}
		else if(angle>=45 && angle<90)
		{
			return 45;
		}
		else if(angle>=90 && angle<135)
		{
			return 90;
		}
		else if(angle>=135 && angle<180)
		{
			return 135;
		}
		else if(angle<=0 && angle>-45)
		{
			return 135;
		}
		else if(angle<=-45 && angle>-90)
		{
			return 90;
		}
		else if(angle<=-90 && angle>-135)
		{
			return 45;
		}
		else if(angle<=-135 && angle>-180)
		{
			return 0;
		}
		else
			return 0;
	}


	/* Si un pixel se trouvant dans le masque
	de convolution a une norme supérieur au pixel
	que l'on traite alors cela veut dire que le
	pixel courant n'est pas le maximum local et
	n'est donc pas le bord*/
	private void nonMaxima(int x,int y)
	{
		boolean tmp=true;

		for(int i=-1;i<=1;i++)
		{
			for(int j=-1;j<=1;j++)
			{
				if(carte[j+x][i+y].Norme>carte[x][y].Norme)
				{
					tmp=false;
				}
			}
		}

		carte[x][y].etat=tmp;
	}

}
