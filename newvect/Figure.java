import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


class Figure {
    private boolean sorted = true;
    public final Color color;

    public LinkedList<Path> borders;

    public Figure(int rgb){
        color = new Color(rgb);
        borders = new LinkedList<Path>();
    }

    public void addBorder(Path p){
        for (Path b : borders){
            if (p.id == b.id) return;
        }
        borders.add(p);
        sorted = false;
    }

    public void addBorders(Path[] paths){
        for (Path p : paths) {
            addBorder(p);            
        }
    } 

    private void sortBorders(){

        if (sorted == true) return;

        LinkedList<Path> sortedBorders = new LinkedList<Path>();

        Path current = borders.pollFirst();
        Point start = current.getFirst();
        Point end   = current.getLast();

        sortedBorders.add(current);

        while (borders.size() > 0){
            
            for (Path p : borders ){
                Point a = p.getFirst();
                Point b = p.getLast();
                if (start.equals(b)){
                   sortedBorders.addFirst(p);
                    start = a;
                } else if (end.equals(a)){
                    sortedBorders.addLast(p);
                    end = b;
                }
            }
        }

    }

    boolean isClosed(){
        sortedBorders();
        Path first = borders.getFirst();
        Point a1 = first.getFirst();
        Point b1 = first.getLast();

        Path last = borders.getLast();
        Point a2 = last.getFirst();
        Point b2 = last.getLast();
        
        return (a1.equals(a2) || a1.equals(b2) ||
                b1.equals(a2) || b1.equals(b2)  );

    }


    public String toSVG(){
        String svg_path = "";

        for (Path b : borders){
            svg_path += b.toSVG();
        }

        return "<path d=\""+svg_path+"\" fill=\"rgb("   +
               color.getRed() +","+color.getGreen()+"," +
               color.getBlue()+")\"/>";
    }

}
