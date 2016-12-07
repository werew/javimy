package vectorization;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;


public class ConverterSVG {

    int h,w;
    ArrayList<Figure> figures;

    public ConverterSVG(BufferedImage img){
        this.w = img.getWidth()*2-2; 
        this.h = img.getHeight()*2-2; 

        FigureBuilder fb = new FigureBuilder(img);
        figures = fb.getFigures();
        
    }

    public void export(String filename) throws IOException {

        PrintWriter writer = new PrintWriter(filename);

        writer.println(getHeader(h,w));

        for (Figure f : figures){
            writer.println(f.toSVG());
            writer.flush();
        }
        
        writer.println(getFooter());
        writer.close();
        
    }

    String getHeader(int h, int w){
        return "<svg version=\"1.1\" "             +
               "width=\""+w+"\" height=\""+h+"\" " +
               "xmlns=\"http://www.w3.org/2000/svg\">";
    }

    String getFooter(){
        return "</svg>";
    }

}
