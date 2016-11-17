package filters;
import java.awt.image.BufferedImage;
import java.awt.*;

// Here is a cool post about this filter:
// https://blog.saush.com/2011/04/20/edge-detection-
// with-the-sobel-operator-in-ruby/
public class Sobel extends Filter {

    private int[][] mx = {{1,0,-1},{2,0,-2},{1,0,-1}};
    private int[][] my = {{-1,-2,-1},{0,0,0},{1,2,1}};

    public Sobel(BufferedImage img){
        System.out.println("Make Sobel");
        image = img;
    }

    public void apply(){
        System.out.println("Apply Sobel");
        int w = image.getWidth();
        int h = image.getHeight();
        for (int i=1; i<w-1; i++){
            for (int j=1; j<h-1; j++){
                convolution(i,j);
            }
        }
        
    }
    
    public BufferedImage getImg(){
        return image;
    }

    private void convolution(int x, int y){
        int px_x = 0;
        int px_y = 0;

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                Color c = new Color(image.getRGB(i+x-1,j+y-1));
                int red = (int)(c.getRed() * 0.2);
                int green = (int)(c.getGreen() * 0.2);
                int blue = (int)(c.getBlue() * 0.2);
                int rgb = red+green+blue;
               // System.out.println("---> "+mx[j][i]);
               // System.out.println("rgb "+rgb);
                px_x += mx[j][i] * rgb;
                px_y += my[j][i] * rgb;
                //System.out.println("px "+px_x+" "+px_y);
            }
        }

    
        int val = (int) Math.ceil(Math.sqrt((px_x*px_x) + (px_y*px_y)));
        if (val > 255) val = 255;
        //System.out.println("final "+val);
        Color nc = new Color(val,val,val);
        image.setRGB(x,y,nc.getRGB());
    }

}
