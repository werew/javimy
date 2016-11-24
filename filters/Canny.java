package filters;
import java.awt.image.BufferedImage;
import java.awt.*;

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
        src = img;
	thr_min = threshold_min;
	thr_min = threshold_max;
	

        int w = img.getWidth();
        int h = img.getHeight();
        image = new BufferedImage(w-2,h-2,img.getType());
	carte=new gradient[image.getWidth()][image.getHeight()];


	//TODO appliquer gauss

        // Set max
        for (int i=1; i<w-1; i++){
            for (int j=1; j<h-1; j++){
                int val = convolution(i,j);
                if (val>max) max = val;
            }
        }

        // Apply filter 
        for (int i=1; i<w-1; i++){
            for (int j=1; j<h-1; j++){
                gradient val = convolution(i,j);

		//TODO
                // Truncate values
                val = (int) Math.floor((255*val)/max);
                // Normalize
                if (val > 255 - thr_max) val = 255;
                else if (val < thr_min) val = 0;
    
                // Set pixel
                Color nc = new Color(val,val,val);
                image.setRGB(i-1,j-1,nc.getRGB());
            }
        }

	for(int i=0;i<h-2;i++)
	{
		for(int j=0;j<w-2;j++)
		{
			//TODO
			int dx,dy;
			if(carte[j][i].getAngle()==0)
			{
				dx=;
				dy=;
			}
			if(carte[j][i].getAngle()==0)
			{
				dx=;
				dy=;
			}
			if(carte[j][i].getAngle()==0)
			{
				dx=;
				dy=;
			}
			if(carte[j][i].getAngle()==0)
			{
				dx=;
				dy=;
			}

			if(carte[j+dx][i+dy]>carte[j][i].getNorme() || carte[j-dx][i-dy]>carte[j][i])
			{
				//XXX Mise a 0?
				carte[j][i].etat=false;
			}
		}
	}

	for(int i=0;i<h-2;i++)
	{
		for(int j=0;j<w-2;j++)
		{
			if(carte[j][i]<thr_min)
			{
				//XXX rejeté?
				carte[j][i]=false;
			}
			else if(carte[j][i]>thr_max)
			{
				//XXX accepter
				carte[j][i]=true;
			}
			else
			{
				//TODO que faire?
			}


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

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                Color c = new Color(src.getRGB(i+x-1,j+y-1));
                int red = (int)(c.getRed() * 0.3);
                int green = (int)(c.getGreen() * 0.3);
                int blue = (int)(c.getBlue() * 0.3);
                int rgb = red+green+blue;
                px_x += mx[j][i] * rgb;
                px_y += my[j][i] * rgb;
            }
        }

    	gradient g=new gradient(px_x,px_y);
        return g;
    }

}
