import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;



public class ConverterSVG {

    public ConverterSVG(ArrayList<Path> paths, int h, int w){

        System.out.println(getHeader(h,w));

        for (Path path : paths){
            System.out.println(convertPath(path));
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

    String convertPath(Path pa){
        Point po = pa.points.get(0);
        String svg_path = "M "+po.x+" "+po.y+" ";

        for (int i=1; i<pa.points.size(); i += 5){
            po = pa.points.get(i);
            svg_path = svg_path + "L "+po.x+" "+po.y+" ";
        }

        return "<path d=\""+svg_path+"\" fill=\"rgb("      +
               pa.color.getRed() +","+pa.color.getGreen()+","+
               pa.color.getBlue()+")\"/>";
    }
}
