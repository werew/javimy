import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class Main {
    public static void main(String args[]){

        BufferedImage image;
        try {
            File input = new File("test.jpg");
            image = ImageIO.read(input);
           ConverterSVG c = new ConverterSVG(image); 
        c.printSVG();
//            PathsCollector pc = new PathsCollector(image);
 //           image = pc.getImg();
  //          File output = new File("out.bmp");
   //         ImageIO.write(image,"bmp",output); 


        } catch (IOException e){
            e.printStackTrace();
        };

    }
}
