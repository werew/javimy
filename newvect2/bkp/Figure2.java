import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


class Figure {
    public final Color color;

    private LinkedList<Point> angles;
    public LinkedList<Path> borders;

    public Figure(int rgb){
        color = new Color(rgb);
        borders = new LinkedList<Path>();
        angles  = new LinkedList<Point>();
    }

    public boolean addBorder(Path p){
        if (borders.size() == 0) borders.add(p);

        for (Path b : borders){
            if (p.id == b.id) return true;
        }

        Point start = borders.getFirst().getFirst();
        Point end   = borders.getLast().getLast();

        Point a = p.getFirst();
        Point b = p.getLast();

                System.out.println("S: "+start.x+" "+start.y+" E: "+end.x+" "+end.y);
                System.out.println("a: "+a.x+" "+a.y+" b: "+b.x+" "+b.y);

        if (end.equals(a)) {
            borders.addLast(p);
            System.out.println("Add bottop");
            return true;
        } else if (start.equals(b)){
            borders.addFirst(p);
            System.out.println("Add top");
            return true;
        } else if (end.equals(b)){
            p.reverse();
            borders.addLast(p);
            System.out.println("Add bottom rev");
            return true;
        } else if (start.equals(a)){
            p.reverse();
            borders.addFirst(p);
            System.out.println("Add top rev");
            return true;
        }

        return false;
    }

    public void addBorders(ArrayList<Path> paths){

        while (paths.size() > 0){
        System.out.println("Adding "+paths.size());
            for (int i=0; i < paths.size(); i++) {
                if (addBorder(paths.get(i))) paths.remove(i--);
            }
        }
    } 

    public void addAngle(Point a){
        angles.add(a);
    }


    boolean isClosed(){
        Point start = borders.getFirst().getFirst();
        Point end   = borders.getLast().getLast();

        return start.equals(end);
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
