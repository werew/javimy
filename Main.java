import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import filters.*;

public class Main {
    public static void main(String args[]){

        BufferedImage image;
        try {
            File input = new File("img.jpg");
            image = ImageIO.read(input);
            Filter f = new Sobel(image); 
            image = f.getImg();
            File output = new File("out.jpg");
            ImageIO.write(image,"jpg",output); 
        } catch (IOException e){
        };

    }
}
