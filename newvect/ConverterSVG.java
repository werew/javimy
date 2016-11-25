import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;



public class ConverterSVG {

    int h,w;
    ArrayList<Figure> figures;

    public ConverterSVG(BufferedImage img){
        src = img;
        this.w = img.getHeight()*2-1; 
        this.h = img.getWidth()*2-1; 

        FigureBuilder fb = new FigureBuilder(img);
        figures = fb.getFigures();
        
    }

    public void printSVG(){

        System.out.println(getHeader(h,w));

        for (Figure f : figures){
            System.out.println(f.toSVG());
        }
        
        System.out.println(getFooter());
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
