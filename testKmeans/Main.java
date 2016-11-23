import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.util.Iterator;

public class Main {
    public static void main(String args[]){

        BufferedImage image;
        try {
            File input = new File("img.jpg");
            image = ImageIO.read(input);
            Clusterizator f = new Clusterizator(image, 2); 
            image = f.getImg();
            File output = new File("out.jpg");
            //ImageIO.write(image,"",output); 
            writeJPG(image, new FileOutputStream(output), 1f);


        } catch (IOException e){
        };

    }

public static void writeJPG(
    BufferedImage bufferedImage,
    OutputStream outputStream,
    float quality) throws IOException
{
    Iterator<ImageWriter> iterator =
        ImageIO.getImageWritersByFormatName("jpg");
    ImageWriter imageWriter = iterator.next();
    ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
    imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    imageWriteParam.setCompressionQuality(quality);
    ImageOutputStream imageOutputStream =
        new MemoryCacheImageOutputStream(outputStream);
    imageWriter.setOutput(imageOutputStream);
    IIOImage iioimage = new IIOImage(bufferedImage, null, null);
    imageWriter.write(null, iioimage, imageWriteParam);
    imageOutputStream.flush();
}
}
