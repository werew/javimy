package filters;
import java.awt.image.BufferedImage;
import java.awt.*;

// Here is a cool post about this filter:
// https://blog.saush.com/2011/04/20/edge-detection-
// with-the-sobel-operator-in-ruby/
public class Prewitt extends Filter {

    private int max = 1;
    private int thr_min = 0;
    private int thr_max = 0;

    private int[][] mx = {{-1,0,1},{-1,0,1},{-1,0,1}};
    private int[][] my = {{-1,-1,-1},{0,0,0},{1,1,1}};
    BufferedImage src;


    // XXX if threshold < 0 ?? exception ??
    public Prewitt(BufferedImage img, int threshold_min, int threshold_max ){
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

    
        int val = (int) Math.ceil(Math.sqrt((px_x*px_x) + (px_y*px_y)));
        return val;
    }

}
