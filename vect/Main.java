import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Main {
    public static void main(String args[]){

        BufferedImage image;
        try {
            File input = new File("test.jpg");
            image = ImageIO.read(input);
            Tracer f = new Tracer(image); 
            image = f.getImg();
            File output = new File("out.jpg");
            ImageIO.write(image,"jpg",output); 
        } catch (IOException e){
        };

    }
}
