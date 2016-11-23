import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Tracer {
    
    ArrayList<Path> paths; 

    BufferedImage image; // Segmented image
    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel


    static final int VISITED = -1;
    /**
     *  Direction (ATTENTION: directions must be
     *  clockwise enumerated)
     */
    static final int UP    = 0;
    static final int RIGHT = 1;
    static final int DOWN  = 2;
    static final int LEFT  = 3;

    public Tracer (BufferedImage img){
        // Init object
        int w = img.getWidth();
        int h = img.getHeight();
        src = img;
        image = new BufferedImage(w,h,img.getType());
        labels = new int[w][h];
        paths = new ArrayList<Path>();

        // Start tracing
        for (int j=0; j<h; j++){
            // prev_rgb is always different of new_rgb 
            // at the beginning of each new line
            int prev_rgb = src.getRGB(0,j) + 1;
            for (int i=0; i<w; i++){
                int new_rgb = src.getRGB(i,j);
                if (new_rgb != prev_rgb && labels[i][j] == 0) { 
                    // Add a new path 
                    Path p = get_path(new Point(i,j));
                    paths.add(p);
                    fill_figure(p);
                }
                prev_rgb = new_rgb;
            }
        }
    }

    void fill_figure(Path path){

        // Color and label of the path
        Point start = path.points.get(0);
        int rgb = src.getRGB(start.x, start.y);
        
        // Boundaries of the image
        int w = src.getWidth(); 
        int h = src.getHeight();

        LinkedList<Point> fifo = new LinkedList<Point>();
        fifo.add(start);
        
        Point p;
        while ((p = fifo.pollFirst()) != null){
            // If visited skip it
            if (labels[p.x][p.y] == VISITED) continue;

            // Visit
            labels[p.x][p.y] = VISITED;

            // Look to 4 directions
            for (int direction=0; direction<4; direction++){

                Point next = getNeighbour(p, direction);

                // Check boundaries
                if (next.x < 0 || next.x >= w ||
                    next.y < 0 || next.y >= h  ) continue;

                // Is part of the figure ?
                if (src.getRGB(next.x,next.y) == rgb &&
                    labels[next.x][next.y] != VISITED) {
                    fifo.add(next);
                }
            }
        }

    }

    Path get_path(Point start){
        // Color and label of the path
        int rgb = src.getRGB(start.x, start.y);
        int l = paths.size()+1;
        
        // Boundaries of the image
        int w = src.getWidth(); 
        int h = src.getHeight();

        // Store all the points inside an object Path
        Path path = new Path(new Color(rgb));

        // Current point 
        Point current = start;

        // When starting direction is always RIGHT
        int direction = RIGHT;
        int next_direction = 0;

        do {
            // Visit point
            labels[current.x][current.y] = l;
            path.points.add(current);
            image.setRGB(current.x,current.y, rgb);

            // Look to 4 directions (clockwise order)
            for (int i=0; i<4; i++){

                // next_direction is the i-th rotation starting from 
                // to the left of the current direction
                next_direction = (direction+3+i) % 4;
                Point next = getNeighbour(current, next_direction);

                // Check boundaries
                if (next.x < 0 || next.x >= w ||
                    next.y < 0 || next.y >= h  ) continue;


                if (src.getRGB(next.x,next.y) == rgb) {
                    // Found it ! Go on !
                    current = next;
                    break;
                }
            }

            // Update direction
            direction = next_direction;

        } while (current.x != start.x || current.y != start.y /*&& END CONDITION */);

        return path;

    }

    Point getNeighbour(Point p, int direction){
        switch (direction){
            case UP:    return new Point(p.x,p.y-1);
            case RIGHT: return new Point(p.x+1,p.y);
            case DOWN:  return new Point(p.x,p.y+1);
            default:    return new Point(p.x-1,p.y);
        }
    }

    

    BufferedImage getImg(){ return image;}

}
