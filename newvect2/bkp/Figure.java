import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


class Figure {
    private boolean sorted = true;
    public final Color color;

    private LinkedList<Point> angles;
    public LinkedList<Path> borders;

    public Figure(int rgb){
        color = new Color(rgb);
        borders = new LinkedList<Path>();
        angles  = new LinkedList<Point>();
    }

    public void addBorder(Path p){
        for (Path b : borders){
            if (p.id == b.id) return;
        }
        //System.out.println("Add path "+p.id);
        borders.add(p);
        sorted = false;
    }

    public void addBorders(ArrayList<Path> paths){
        for (Path p : paths) {
            addBorder(p);            
        }
    } 

    public void addAngle(Point a){
        angles.add(a);
    }

    private void sortBorders(){

        if (sorted == true) return;
        System.out.println("Sort");

        LinkedList<Path> sortedBorders = new LinkedList<Path>();


        Path current = borders.pollFirst();
        Point start = current.getFirst();
        Point end   = current.getLast();


        sortedBorders.add(current);

        while (borders.size() > 0){
            
        System.out.println("Sorted: "+sortedBorders.size()+" old: "+borders.size());
            for (int i=0; i < borders.size(); i++){
                Path p = borders.get(i);
                Point a = p.getFirst();
                Point b = p.getLast();
                System.out.println("S: "+start.x+" "+start.y+" E: "+end.x+" "+end.y);
                System.out.println("a: "+a.x+" "+a.y+" b: "+b.x+" "+b.y);
                if (start.equals(b)){
                    sortedBorders.addFirst(p);
                    start = a;
                    borders.remove(i--);
                    System.out.println("-------> Add");
                } else if (end.equals(a)){
                    sortedBorders.addLast(p);
                    end = b;
                    borders.remove(i--);
                    System.out.println("-------> Add");
                } else if (start.equals(a)){
                    p.reverse();
                    sortedBorders.addFirst(p);
                    start = b;
                    borders.remove(i--);
                    System.out.println("-------> Add rev");
                } else if (end.equals(b)){
                    p.reverse();
                    sortedBorders.addLast(p);
                    end = a;
                    borders.remove(i--);
                    System.out.println("-------> Add rev");
                }
            }
        }

        borders = sortedBorders;

    }

    boolean isClosed(){
        sortBorders();
        Path first = borders.getFirst();
        Point a1 = first.getFirst();
        Point b1 = first.getLast();

        Path last = borders.getLast();
        Point a2 = last.getFirst();
        Point b2 = last.getLast();
        
        return (a1.equals(a2) || a1.equals(b2) ||
                b1.equals(a2) || b1.equals(b2)  );

    }

    void sortAngles(){

        LinkedList<Point> sortedAngles = new LinkedList<Point>();

        Path last = borders.getLast();

        Point end;
        if (last == null) {
            end = angles.pollFirst();
            sortedAngles.add(end);
        } else {
             end = last.getLast();
        }

        while (angles.size() > 0){
            for (int i=0; i < angles.size(); i++){
                Point a = angles.get(i);
                if (end.x == a.x || end.y == a.y) {
                    sortedAngles.add(a);
                    angles.remove(i);
                    end = a;
                }
            }
        }
    
        angles = sortedAngles;
    }


    public String toSVG(){
        sortBorders();
        sortAngles();
        String svg_path = "";

        for (Path b : borders){
            svg_path += b.toSVG();
        }

        String svg_angles = "";
        for (Point a : angles){
            svg_angles += "L "+a.x+" "+a.y+" ";
        }

        return "<path d=\""+svg_path+svg_angles+" Z\" fill=\"rgb("   +
               color.getRed() +","+color.getGreen()+"," +
               color.getBlue()+")\"/>";
    }

}
