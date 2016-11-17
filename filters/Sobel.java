package filters;
import java.awt.image.BufferedImage;

public class Sobel extends Filter {

    public Sobel(BufferedImage img){
        System.out.println("Make Sobel");
        image = img;
    }

    public void apply(){
        System.out.println("Apply Sobel");
    }

}
