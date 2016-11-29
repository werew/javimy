import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


class Figure {
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
    }

    boolean isClosed(){
        Point start = borders.getFirst().getFirst();
        Point end   = borders.getLast().getLast();
        return start.equals(end);
    }


    void sortBorders(){
        //System.out.println("-------- Start sort -------"+borders.size());

        if (borders.size() <= 1) return;

        if (_sortBorders(1)) return;
        borders.get(0).reverse();
        _sortBorders(1); 
    }

    // Sort borders starting from i > 0
    // NB: this is an function auxiliary to sortBorders
    // shouldn't be used on it's own
    boolean _sortBorders(int i){

        //System.out.println("---- Sort "+i);
        // Check the last border against the first one
        if ( i >= borders.size() - 1){
            if (matchb(i-1, i) && matchb(i, 0)) {
                //System.out.println("\tlAST: Sort "+i+" worked");
                return true;
            } 

            borders.get(i).reverse();
            //System.out.println("\tlAST: Sort "+i+" reverse");
            if ((matchb(i-1,i) && matchb(i,0))) { 
                //System.out.println("\tlAST: Sort "+i+" worked");
                return true;
            }
                //System.out.println("\tlAST: Sort "+i+" BAD");
                return false;
        }

        // Try the current orientation
        if (matchb(i-1,i) && _sortBorders(i+1)){
            //System.out.println("\tSort "+i+" worked");
            return true;
        }
       
        // Don't need to reverse if the current
        // border is a circular path
        if ( borders.get(i).getFirst()
                .equals(borders.get(i).getLast())
           ) return false;
               
        // Current orietation doesn't work
        // try to reverse the border
        borders.get(i).reverse();
        //System.out.println("\tSort "+i+" reverse");
        if (matchb(i-1,i) && _sortBorders(i+1)) {
            //System.out.println("\tSort "+i+" worked");
            return true;
        } else {
            //System.out.println("\tSort "+i+" BAD");
            return false;
        }
    }

    boolean matchb(int i, int j){
        Point a = borders.get(i).getLast();
        Point b = borders.get(j).getFirst();

        //System.out.println("\t\tmatchb "+i+" "+j+" "+a+" "+b);
        if (a.equals(b)) return true;

        if (a.x == b.x || a.y == b.y ){
            Point c = borders.get(j).getLast();
            if (a.x != c.x && a.y != c.y) return true;

            double d_abx = a.x-b.x; double d_aby = a.y-b.y;
            double d_acx = a.x-c.x; double d_acy = a.y-c.y;
            double dist_ab = Math.sqrt(d_abx*d_abx + d_aby*d_aby); 
            double dist_ac = Math.sqrt(d_acx*d_acx + d_acy*d_acy); 
            if (dist_ab <= dist_ac) return true;
        }

        //System.out.println("\t\tfail matchb");
        return false;
    }
        

    public String toSVG(){
        sortBorders();
        String svg_path = "";

        for (Path b : borders){
            svg_path += "\n"+b.toSVG();
        }

        return "<path d=\""+svg_path+" Z\" fill=\"rgb("   +
               color.getRed() +","+color.getGreen()+"," +
               color.getBlue()+")\"/>";
    }

}
