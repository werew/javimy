import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Tracer {
    
    ArrayList<Path> paths; 
    ArrayList<Point> jn_points;

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

    // Label visit 0
    static final int LAB_VS = -1;
    static final int LAB_JN = -2;
    

    public Tracer (BufferedImage img){
        // Init object
        int w = img.getWidth();
        int h = img.getHeight();

        src = img;
        labels = new int[w*2-1][h*2-1];
        paths  = new ArrayList<Path>();
        jn_points = new ArrayList<Point>();


         
        
    }
    private void trace(){
        int w = src.getWidth();
        int h = src.getHeight();
        int wl = w*2-1;
        int hl = h*2-1;

        int c0, c1;

        // Fill edges
        for (int i = 0; i < wl; i++){
            for (int j = 0; j < hl; i++){
                if (i&0x1 && j^0x1){
                    // i odd j even
                    c0 = src.getRGB((i+1)/2,j/2);
                    c1 = src.getRGB((i-1)/2,j/2);
                    if (c0 != c1){
                        labels[i][j] = LAB_VS;
                    }
                } else if (i^0x1 && j&0x1){
                    // i even j odd
                    c0 = src.getRGB(i/2,(j+1)/2);
                    c1 = src.getRGB(i/2,(j-1)/2);
                    if (c0 != c1){
                        labels[i][j] = LAB_VS;
                    }
                }
            }
        }

        // Fill gaps junction points
        for (int i = 1; i < wl-2; i++){
            for (int j = 1; j < hl-2; i++){
                int sum_v = labels[i+1][j] + labels[i-1][j];
                int sum_h = labels[i][j+1] + labels[i][j-1];

                if (sum_v + sum_h < LAB_VS*2){
                    labels[i][j] = LAB_JN;
                    jn_points.add(new Point(i,j); 
                } else if (sum_v == LAB_VS*2 || 
                           sum_h == LAB_VS*2 ){
                    labels[i][j] = LAB_VS;
                }
            }
        }


    }
   
    private Point follow_path(Point p){
        Point[] edgp = new Point[4];
        adgp[0] = new Point(p.x+1,p.y);
        adgp[1] = new Point(p.x-1,p.y);
        adgp[2] = new Point(p.x,p.y+1);
        adgp[3] = new Point(p.x,p.y-1);
        // Priority to 4-connected links
        // and junction points
        for (Point n : edgp){
            if (labels[n.x][n.y] == LAB_JN)
                return n;
        }

        for (Point n : edgp){
            if (labels[n.x][n.y] == LAB_VS)
                return n;
        }

        
    } 
    private void get_jn_path(Point jn, Point next){
         
        Path pa = new Path(jn);
        next.x + (next.x-jn.x), next.y + (next.y-jn.y)
         

    }

    
    private void collect_paths(){

        for (Point p : jn_points){
            if (labels[p.x+1][p.y] == LAB_VS){
                get_jn_path(p, new Point(p.x+1,p.y))
            }

            if (labels[p.x-1][p.y] == LAB_VS){
                get_jn_path(p, new Point(p.x-1,p.y))
            }

            if (labels[p.x][p.y+1] == LAB_VS){
                get_jn_path(p, new Point(p.x,p.y+1))
            }

            if (labels[p.x][p.y-1] == LAB_VS){
                get_jn_path(p, new Point(p.x+p.y-1))
            }
        }
     
    



    }

}
