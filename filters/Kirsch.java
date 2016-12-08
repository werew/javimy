package filters;
import java.awt.image.BufferedImage;
import java.awt.*;

// Here is a cool post about this filter:
// https://blog.saush.com/2011/04/20/edge-detection-
// with-the-sobel-operator-in-ruby/
public class Kirsch extends Filter {

    private int max = 1;
    private int thr_max = 0;
    private int thr_min = 0;

    private int[][][] g = {{{5,5,5},{-3,0,-3},{-3,-3,-3}},
			   {{5,5,-3},{5,0,-3},{-3,-3,-3}},
			   {{5,-3,-3},{5,0,-3},{5,-3,-3}},
			   {{-3,-3,-3,},{5,0,-3},{5,5,-3}},
			   {{-3,-3,-3},{-3,0,-3},{5,5,5}},
			   {{-3,-3,-3},{-3,0,5},{-3,5,5}},
			   {{-3,-3,5},{-3,0,5},{-3,-3,5}},
			   {{-3,5,5},{-3,0,5},{-3,-3,-3}}};
    BufferedImage src;


    public Kirsch(BufferedImage img, int threshold_min, int threshold_max ){
        src = img;
	thr_min = threshold_min;
	thr_max = threshold_max;
        int w = img.getWidth();
        int h = img.getHeight();
        image = new BufferedImage(w-2,h-2,img.getType());

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
                int val = convolution(i,j);

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
    }

    public BufferedImage getImg(){
        return image;
    }

    private int convolution(int x, int y){
        int px_x = 0;
	int max_g =0;

	for(int k=0;k<8;k++)
	{
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				Color c = new Color(src.getRGB(i+x-1,j+y-1));
				int red = (int)(c.getRed() * 0.3);
				int green = (int)(c.getGreen() * 0.3);
				int blue = (int)(c.getBlue() * 0.3);
				int rgb = red+green+blue;
				px_x += g[k][j][i] * rgb;

				
			}
		}
		px_x=Math.abs(px_x);
		if(px_x > max_g)
		{
			max_g=px_x;
		}
	}

        return max_g;
    }

}
