package filters;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;

// Here is a cool post about this filter:
// https://blog.saush.com/2011/04/20/edge-detection-
// with-the-sobel-operator-in-ruby/
public class Canny extends Filter {

    private int max = 1;
    private int thr = 0;

    private int[][] mx = {{-1,0,1},{-2,0,2},{-1,0,1}};
    private int[][] my = {{-1,-2,-1},{0,0,0},{1,2,1}};
    private gradient carte[][];
    private int thr_min;
    private int thr_max;

    BufferedImage src;

    public Canny(BufferedImage img){
        this(img,0,0);
    }

    // XXX if threshold < 0 ?? exception ??
    public Canny(BufferedImage img, int threshold_min,int threshold_max ){
	src=new Gauss(img,1,0.8).getImg();	//TODO parametre libre
	thr_min = threshold_min;
	thr_min = threshold_max;
	

        int w = src.getWidth();
        int h = src.getHeight();
        image = new BufferedImage(w-2,h-2,src.getType());
	carte = new gradient[image.getWidth()][image.getHeight()];

	System.out.println("w "+w+" h "+h);

        // Set max
        for (int i=1; i<h-1; i++){
            for (int j=1; j<w-1; j++){
                gradient val = convolution(j,i);
//		System.out.println("===========> i "+i+" j "+j);
                if (val.Norme>max) max = val.Norme;
            }
        }

        // Apply filter 
        for (int i=1; i<h-1; i++){
            for (int j=1; j<w-1; j++){
                gradient val = convolution(j,i);

		//TODO
                // Truncate values
                val.Norme = (int) Math.floor((255*val.Norme)/max);
                // Normalize
                if (val.Norme > 255 - thr_max) val.Norme = 255;
                else if (val.Norme < thr_min) val.Norme = 0;
    
                // Set pixel
                //Color nc = new Color(val.Norme,val.Norme,val.Norme);
                //image.setRGB(i-1,j-1,nc.getRGB());
		carte[j-1][i-1]=val;
            }
        }

	for(int i=1;i<h-3;i++)
	{
		for(int j=1;j<w-3;j++)
		{
			//TODO arrondir angle
			int dx=0,dy=0;
			if(carte[j][i].Angle>=0 && carte[j][i].Angle<45)
			{
				dx=1;
				dy=0;
			}
			if(carte[j][i].Angle>=45 && carte[j][i].Angle<90)
			{
				dx=1;
				dy=1;
			}
			if(carte[j][i].Angle>=90 && carte[j][i].Angle<135)
			{
				dx=0;
				dy=1;
			}
			if(carte[j][i].Angle>=135 && carte[j][i].Angle<180)
			{
				dx=-1;
				dy=1;
			}

			if(carte[j+dx][i+dy].Norme>carte[j][i].Norme || carte[j-dx][i-dy].Norme>carte[j][i].Norme)
			{
				//XXX Mise a 0?
				carte[j][i].etat=false;
			}
		}
	}




	ArrayList<Point> incertain=new ArrayList<Point>();

	for(int i=0;i<h-2;i++)
	{
		for(int j=0;j<w-2;j++)
		{
			if(carte[j][i].Norme<thr_min)
			{
				//XXX rejeté?
				carte[j][i].etat=false;
			}
			else if(carte[j][i].Norme>thr_max)
			{
				//XXX accepter
				carte[j][i].etat=true;
			}
			else
			{
				incertain.add(new Point(j,i));
			}


		}
	}
	//TODO prend les point au bord: attention
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
		try{
                c = new Color(src.getRGB(j+x,i+y));
		} catch(Exception e){
			
		System.out.println("i "+i+" j "+j+" w "+src.getWidth()+" h "+src.getHeight());
		};
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

}
