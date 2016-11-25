import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Main {
    public static void main(String args[]){

        BufferedImage image;
        try {
            File input = new File("test.jpg");
            image = ImageIO.read(input);
            PathsCollector pc = new PathsCollector(image); 
            //ConverterSVG c = new ConverterSVG(f.paths,image.getHeight(),image.getWidth()); 
            image = pc.getImg();
            File output = new File("out.jpg");
            ImageIO.write(image,"jpg",output); 

        } catch (IOException e){
        };

    }
}
