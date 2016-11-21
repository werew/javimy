import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

class Path {
    public ArrayList<Point> points;
    public Color color;

    public Path(Color c){
        color = c;
        points = new ArrayList<Point>();
    }

}

public class Tracer {
    
    ArrayList<Path> paths; 

    BufferedImage image; // Segmented image
    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel


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
                System.out.println("Process "+i+" "+j+" Rgb "+prev_rgb+" "+new_rgb+" label "+labels[i][j]);
                if (new_rgb != prev_rgb && labels[i][j] == 0) { 
                    // Add a new path 
                    System.out.println("---> Start path");
                    paths.add(get_path(new Point(i,j)));
                    System.out.println("---> End path");
                }
                prev_rgb = new_rgb;
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
        System.out.println("Color "+rgb);

        do {
            // Visit point
            labels[current.x][current.y] = l;
            path.points.add(current);
            System.out.println("Adding point: "+current);
            Color c = new Color(255,255,255);
            image.setRGB(current.x,current.y, c.getRGB());

            // Look to 4 directions (clockwise order)
            for (int i=0; i<4; i++){

                // next_direction is the i-th rotation starting from 
                // to the left of the current direction
                next_direction = (direction+3+i) % 4;
                Point next = getNeighbour(current, next_direction);

                // Check boundaries
                if (next.x < 0 || next.x >= w ||
                    next.y < 0 || next.y >= h  ) continue;

                System.out.println("Look at "+next_direction+" is "+src.getRGB(next.x,next.y));

                if (src.getRGB(next.x,next.y) == rgb) {
                    System.out.println("Found it!");
                    // Found it ! Go on !
                    current = next;
                    break;
                }
            }

            // Update direction
            direction = next_direction;

                    System.out.println("I moved to: "+current);
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
