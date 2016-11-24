import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Tracer {
    
    ArrayList<Path> paths; 
    ArrayList<Point> jn_points;

    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel

    final int w,h,wl,hl; // Widths and heights of the
                         // src image and the labels

    // Label visit 0
    static final int LAB_VS = -1;
    static final int LAB_JN = -2;
    

    public Tracer (BufferedImage img){
        // Init object
        w = img.getWidth();
        h = img.getHeight();
        wl = w*2-1;
        hl = h*2-1;

        src = img;
        labels = new int[w*2-1][h*2-1];
        paths  = new ArrayList<Path>();
        jn_points = new ArrayList<Point>();


         
        
    }
    private void trace(){
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
    private Point find_next(Point current, Point[] neighbors){

        for (Point p : neighbors){
            // Is it a valid point ?
            if (p.x < 0 || p.x >= wl || 
                p.y < 0 || p.y >= hl ) continue;
            
            int l = labels[p.x][p.y];
            if (l == LAB_JN || l == LAB_VS) return p;
                
        }

        // Not found 
        return null;

    }
   
    private Path take_path(Point start, int label){
        Path pa = new Path();
        Point current = start;

        while (current != null && labels[current.x][current.y] != LAB_JN){

            // Add and mark as visited
            pa.add(current);
            labels[current.x][current.y] == label;

            // Priority to 4-connected links
            // and junction points
            Point next = find_next(current, get_edges(current));
            if (next == null){
                next = find_next(current, get_angles(current));
            }

            current = next;
        }

        if (current != null) {
            // Path ends with a junction point
            pa.add(current);
        } else {
            // Test for closed paths
            Point last = pa.points.get(pa.points.size()-1);
            int dx = Math.abs(start.x-last.x);
            int dy = Math.abs(start.y-last.y);
            if (dx <= 1 && dy <= 1){
                pa.circular = true;
            }
        }

        return pa;
    } 

    private void get_jn_path(Point jn, Point next){
        // Label of the current path
        int label = paths.size() + 1;

        // Hide all the edges of the junction point
        // marking them as not to be used for the path
        int i = 0;
        int[] bkp_labels = new int[4];
        for (Point e : get_edges(jn)){
            bkp_labels[i++] = labels[e.x][e.y]; // Backup old values
            labels[e.x][e.y] = LAB_NOT_FOLLOW;  // Mark as not valid
        }

        // Junction point and next are part of the path
        // they must be marked as not visited
        labels[jn.x][jn.y]     = LAB_VS;
        labels[next.x][next.y] = LAB_VS;

        // Get the path
        Path pa = take_path(jn, label);
        paths.add(pa);    

        // Restore backup values
        i = 0;
        for (Point e : get_edges(jn)){
            labels[e.x][e.y] = bkp_labels[i++];
        }
        // Restore junction point and mark
        // next as part of the path
        labels[jn.x][jn.y]     = LAB_JN;
        labels[next.x][next.y] = label;

    }

    /**
     * Get the 4 edges at the sides of a point p 
     * in clockwise order
     */
    private Point[] get_edges(Point p){
        Point[] edges = new Point[4];
        edges[0] = new Point(p.x,p.y-1);    
        edges[1] = new Point(p.x+1,p.y);    
        edges[2] = new Point(p.x,p.y+1);    
        edges[3] = new Point(p.x-1,p.y);    
        return edges;
    }

    /**
     * Get the 4 points at the angles of a point p
     * in clockwise order
     */
    private Point[] get_angles(Point p){
        Point[] angles = new Point[4];
        angles[0] = new Point(p.x+1,p.y-1);    
        angles[1] = new Point(p.x+1,p.y+1);    
        angles[2] = new Point(p.x-1,p.y+1);    
        angles[3] = new Point(p.x-1,p.y-1);    
        return angles;
    }

    private void collect_paths(){

        for (Point p : jn_points){
            for (Point edge : get_edges(p)){

                if (labels[edge.x][edge.y] == LAB_VS){
                    get_jn_path(p, edge);
                }
            }
        }
     
    



    }

}
