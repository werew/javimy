package vectorization;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class PathsCollector {
    
    public ArrayList<Path> paths; 
    ArrayList<Point> jn_points;

    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel

    final int w,h,wl,hl; // Widths and heights of the
                         // src image and the labels

    // Labels
    static final int LAB_VS = -1;
    static final int LAB_JN = -2;
    static final int LAB_NOT_FOLLOW = -3;
    

    
    public PathsCollector (BufferedImage img, float precision){
        // Init object
        w = img.getWidth();
        h = img.getHeight();
        wl = w*2-1;
        hl = h*2-1;

        src = img;
        labels = new int[wl][hl];
        paths  = new ArrayList<Path>();
        jn_points = new ArrayList<Point>();

        trace();
        collect_paths();

        if (precision > 100) precision = 100;

        if (precision < 100) {
            for (Path p : paths) p.reduce(100-precision);
        }
    
    }

    /**
     * Check if a point can move to a given direction 
     * @param origin Point to test for
     * @param delts Direction
     */
    public boolean checkDirection(Point origin, Point delta){

        Point p = new Point(origin.x*2+delta.x,origin.y*2+delta.y);

            if (pointOutOfBounds(p) == true) return false;

            if (labels[p.x][p.y] != 0) return false;

            // Direction is valid if there is no path
            // and it's not out of bounds

            return true;
    }

    /**
     * Get the border passing from a given edge
     * of a pixel.
     * @param pixel Start pixel
     * @param delta Direction of the edge
     * @return null or the border found
     */
    public Path getBorder(Point origin, Point delta){

        Point p = new Point(origin.x*2+delta.x,origin.y*2+delta.y);

        if (pointOutOfBounds(p) == true || 
            labels[p.x][p.y]    <= 0    ) return null;

        return paths.get(labels[p.x][p.y]-1);
    }


    /**
     * Get an image where all the paths are represented
     * using random colors
     */
    public BufferedImage getImg(){
        int imgtype = src.getType();
        if (imgtype == 0 ) imgtype = 5;
        BufferedImage img = new BufferedImage(wl,hl,imgtype);
        int r,g,b; 
        for (Path pa : paths){
            r = 50 + (int)(Math.random() * ((255 - 50) + 1));
            g = 50 + (int)(Math.random() * ((255 - 50) + 1));
            b = 50 + (int)(Math.random() * ((255 - 50) + 1));
            Color c = new Color(r,g,b);
            for (Point p : pa.points) {
                img.setRGB(p.x,p.y,c.getRGB());
            }
        }
        return img;
    }
        


    /**
     * Label all the points between two different
     * sections as traced. Junction points are labeled
     * as junction point and stored separatly 
     */
    private void trace(){
        int c0, c1;
        // Fill edges
        for (int i = 0; i < wl; i++){
            for (int j = 0; j < hl; j++){

                if ((i%2) == 1 && (j%2) == 0){
                    // i odd j even
                    c0 = src.getRGB((i+1)/2,j/2);
                    c1 = src.getRGB((i-1)/2,j/2);
                    if (c0 != c1){
                        labels[i][j] = LAB_VS;
                    }
                } else if ((i%2) == 0 && (j%2) == 1){
                    // i even j odd
                    c0 = src.getRGB(i/2,(j+1)/2);
                    c1 = src.getRGB(i/2,(j-1)/2);
                    if (c0 != c1){
                        labels[i][j] = LAB_VS;
                    }
                }
            }
        }

        // Fill gaps and junction points
        for (int i = 1; i < wl-1; i += 2){
            for (int j = 1; j < hl-1; j += 2){
                int sum_v = labels[i+1][j] + labels[i-1][j];
                int sum_h = labels[i][j+1] + labels[i][j-1];

                if (sum_v + sum_h < LAB_VS*2){
                    labels[i][j] = LAB_JN;
                    jn_points.add(new Point(i,j));
                } else if (sum_v == LAB_VS*2 || 
                           sum_h == LAB_VS*2 ){
                    labels[i][j] = LAB_VS;
                }
            }
        }

    }







    /**
     * Collects all the paths of the image
     */
    private void collect_paths(){
        // Paths from junction points
        for (Point p : jn_points){
            for (Point edge : get_edges(p)){

                if (labels[edge.x][edge.y] == LAB_VS){
                    paths.add(get_jn_path(p, edge));
                }
            }
        }

        // Paths from the borders
        int label = paths.size() + 1;
        for (int i = 0; i < wl; i++){
            if (labels[i][0] == LAB_VS){
                Point p = new Point(i,0);
                Path pa = take_path(p, label++);
                paths.add(pa);    
            }

            if (labels[i][hl-1] == LAB_VS){
                Point p = new Point(i,hl-1);
                Path pa = take_path(p, label++);
                paths.add(pa);    
            }
        }

        for (int i = 0; i < hl; i++){
            if (labels[0][i] == LAB_VS){
                Point p = new Point(i,0);
                Path pa = take_path(p, label++);
                paths.add(pa);    
            }

            if (labels[wl-1][i] == LAB_VS){
                Point p = new Point(wl-1,i);
                Path pa = take_path(p, label++);
                paths.add(pa);    
            }
        }

        // Remaining paths
        for (int i = 0; i < wl; i++){
            for (int j = 0; j < hl; j++){

                if (labels[i][j] == LAB_VS){
                    Point p = new Point(i,j);
                    Path pa = take_path(p, label++);
                    paths.add(pa);    
                }
            }
        }

    }




    /**
     * Takes the path starting from a junction point 'jn'
     * which consecutive point is 'next'. Note that 'next' must
     * be an edge of 'jn'.
     * @param jn Junction point, beginning of the path
     * @param next Second point of the path
     * @return The path passing from those two points
     */
    private Path get_jn_path(Point jn, Point next){
        // Label of the current path
        int label = paths.size() + 1;

        // Visit next
        labels[next.x][next.y] = label;
        
        // The junction point and next are part of the path
        Path pa = new Path(label);
        pa.points.add(jn);
        pa.points.add(next);

        // Find next point
        int dx = next.x-jn.x;
        int dy = next.y-jn.y;
        Point[] ng = new Point[3];
        ng[0] = new Point(next.x+dx,next.y+dy);
        ng[1] = new Point(ng[0].x+dy,ng[0].y+dx);
        ng[2] = new Point(ng[0].x-dy,ng[0].y-dx);
        Point p = find_next(ng);
        
        // Get the path
        pa = take_path2(p, label, pa);

        return pa;

    }




    /* Caller for take_path2 */
    private Path take_path(Point start, int label){
        Path pa = new Path(label);
        return take_path2(start, label, pa);
    }
     
    /**
     * Given a starting point this method follows all
     * adjacent points in order to get the complete path.
     * All points of the path ('start' within them) are
     * marked with 'label'
     * @param start Beginning of the path
     * @param label Label used to mark the point of this path
     * @return The whished path
     */
    private Path take_path2(Point start, int label, Path pa){
            
        Point current = start;

        while (current != null && labels[current.x][current.y] != LAB_JN){

            // Add and mark as visited
            pa.points.add(current);
            labels[current.x][current.y] = label;

            // Priority to 4-connected links
            // and junction points
            Point next = find_next(get_edges(current));
            if (next == null){
                next = find_next(get_angles(current));
            }

            current = next;
        }

        if (current != null) {
            // Path ends with a junction point
            pa.points.add(current);
        } 

        // Test for closed paths
        Point a = pa.getFirst();
        Point b = pa.getLast();
        int dx = Math.abs(a.x-b.x);
        int dy = Math.abs(a.y-b.y);
        if (dx <= 1 && dy <= 1){
            pa.circular = true;
        }

        return pa;
    } 





    /**
     * Finds a suitable, valid, not visited point of
     * a path between an set of points
     * @param neighbors An array of the points to test
     * @return The first suitable point of 'neighbors' or
     *         null if no suitable point was found
     */
    private Point find_next(Point[] neighbors){

        for (Point p : neighbors){
            // Is it a valid point ?
            if (pointOutOfBounds(p)) continue;
            
            int l = labels[p.x][p.y];
            if (l == LAB_JN || l == LAB_VS) return p;
                
        }

        // Not found 
        return null;

    }


    boolean pointOutOfBounds(Point p){
            return (p.x < 0 || p.x >= wl || 
                    p.y < 0 || p.y >= hl );
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

}


