import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class FigureBuilder {
    
    private ArrayList<Figure> figures; 

    PathsCollector pc;
    BufferedImage src;   // Image source
    int w,h;
    int[][] labels;      // Segment label of each pixel

    /**
     *  Direction (ATTENTION: directions must be
     *  clockwise enumerated)
     */
    static final int UP    = 0;
    static final int RIGHT = 1;
    static final int DOWN  = 2;
    static final int LEFT  = 3;

    public FigureBuilder (BufferedImage img){
        // Init object
        w = img.getWidth();
        h = img.getHeight();
        System.out.println(w+" "+h);
        src = img;
        labels = new int[w][h];
        figures = new ArrayList<Figure>();

        // Run paths collector
        pc = new PathsCollector(img);

        // Start tracing
        for (int j=0; j<h; j++){
            // prev_rgb is always different of new_rgb 
            // at the beginning of each new line
            int prev_rgb = src.getRGB(0,j) + 1;

            for (int i=0; i<w; i++){
                int new_rgb = src.getRGB(i,j);

                // If a new color has been detected and the
                // figure has not been yet taken (label == 0)
                if (new_rgb != prev_rgb && labels[i][j] == 0) { 

                    Point p = new Point(i,j);
                    // Add a new figure
                    figures.add(collect_figure(p));
                    // Fill the figure to avoid re-detections
                    fill_figure(p, figures.size()-1);
                }
                prev_rgb = new_rgb;
            }
        }
    }

    void fill_figure(Point start, int label){

        // Color of the figure
        int rgb = src.getRGB(start.x, start.y);
        
        LinkedList<Point> fifo = new LinkedList<Point>();
        fifo.add(start);
        
        Point p;
        while ((p = fifo.pollFirst()) != null){
            // If visited skip it
            if (labels[p.x][p.y] == label) continue;

            // Visit
            labels[p.x][p.y] = label;

            // Look to 4 directions
            for (int direction=0; direction<4; direction++){

                Point next = getNeighbour(p, direction);

                // Check boundaries
                if (next.x < 0 || next.x >= w ||
                    next.y < 0 || next.y >= h  ) continue;

                // Is part of the figure ?
                if (src.getRGB(next.x,next.y) == rgb &&
                    labels[next.x][next.y] != label) {
                    fifo.add(next);
                }
            }
        }
    }

    /**
     * Get a figure scanning the borders starting
     * from the point 'start'
     */
    Figure collect_figure(Point start){

        // Color and label of the figure
        int rgb = src.getRGB(start.x, start.y);
        int l = figures.size()+1;
        
        Figure f = new Figure(rgb);

        // Current point 
        Point current = start;

        // Backtrack direction
        int bk = 0; // Left

        System.out.println("----> Figure "+l);


        do {
            // Visit point
            labels[current.x][current.y] = l;

        System.out.println("---pick--> "+ current);

            // If current == angle, add a path
            Path pa = null;
            if (current.x == 0) {
                if (current.y == 0) pa = new Path(-1);
                else if (current.y == h-1) pa = new Path(-2);
            } else if (current.x ==  w -1) {
                if (current.y == 0) pa = new Path(-3);
                else if (current.y == h-1) pa = new Path(-4);
            }

            if (pa != null) {
                    System.out.println("---ADD ANGLE--> "+ current);
                    pa.points.add(new Point(current.x*2,current.y*2));
                    f.addBorder(pa);
            }
    

            boolean found = false; 
            Point[] nb = getNeighbours(current);

            for (int i = 0; i < 8 && found == false; i++){
               
                bk = (bk + 1) % 8;
                Point next = nb[bk];
                Point delta = new Point(next.x-current.x,next.y-current.y);
                //System.out.println("considering "+ next);

                // Check boundaries and color
                // and if its separate by a path
                if (next.x < 0 || next.x >= w ||
                    next.y < 0 || next.y >= h ||
                    src.getRGB(next.x,next.y) != rgb ||
                    pc.checkDirection(current, delta) == false ){
                
                    Path p = pc.getBorder(current, delta);
                    if (p != null) f.addBorder(p);
                    
                    continue;
                }

                 // Found it
                 found = true;
                 current = next;

                 // Update backtrak direction
                 switch (bk){
                     case 0:
                     case 1: bk = 6;
                         break;
                     case 2:
                     case 3: bk = 0;
                         break;
                     case 4:
                     case 5: bk = 2;
                         break;
                     case 6:
                     case 7: bk = 4;
                         break;
                 }
            }

        } while (current.equals(start) == false /*&& bk == 0 */);


        return f;
    }

    Point[] getNeighbours(Point p){
        Point[] n = new Point[8];

        // Clockwise from the left
        n[0] = new Point(p.x-1,p.y);
        n[1] = new Point(p.x-1,p.y-1);
        n[2] = new Point(p.x,p.y-1);
        n[3] = new Point(p.x+1,p.y-1);
        n[4] = new Point(p.x+1,p.y);
        n[5] = new Point(p.x+1,p.y+1);
        n[6] = new Point(p.x,p.y+1);
        n[7] = new Point(p.x-1,p.y+1);

        return n;
    }


    // Assure they are auto-contained from left to right
    ArrayList<Figure> getFigures(){
        ArrayList<Figure> figs = new ArrayList<Figure>();

        for (Figure f : figures){
            System.out.println("Check fig "+figures.indexOf(f));
            if (f.isClosed() == false) figs.add(f);
        }

        for (Figure f : figures){
            System.out.println("Check fig "+figures.indexOf(f));
            if (f.isClosed()) figs.add(f);
        }
        System.out.println("DONE");
        return figs;
    }


    Point getNeighbour(Point p, int direction){
        switch (direction){
            case UP:    return new Point(p.x,p.y-1);
            case RIGHT: return new Point(p.x+1,p.y);
            case DOWN:  return new Point(p.x,p.y+1);
            default:    return new Point(p.x-1,p.y);
        }
    }
}
