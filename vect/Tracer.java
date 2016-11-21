import java.awt.image.BufferedImage;
import java.awt.*;

class Path {
    public ArrayList<Point> points;
    public Color color;

    public Path(Color c){
        color = c;
        points = new ArrayList<Point>();
    }

}

public class Tracer {
    
    BufferedImage image; // Segmented image
    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel
    ArrayList<Path> paths;

    public Tracer (BufferedImage img, int K){
        int w = img.getWidth();
        int h = img.getHeight();
        image = new BufferedImage(w,h,img.getType());
        src = img;
        labels = new int[w][h];
        paths = new ArrayList<Path>();

        int prev_rgb = src.getRGB(0,0);
        for (int j=0; j<h; j++){
            for (int i=0; i<w; i++){
                int new_rgb = src.getRGB(i,j);
                if (new_rgb != prev_rgb && labels[i][j] != 0) { 
                    // Get path
                    get_path(i,j);
                }
                prev_rgb = new_rgb;
            }
        }
    }

    void get_path(int x, int y){
        int rgb = src.getRGB(x,y);  // Current color
        int l = paths.size()+1;     // Current label
        labels[x][y] = l;

        Path p = new Path(new Color(rgb));


        

    }

    int getDirection(Point prev, Point current){
        if (prev.x < current.x) return RIGHT;
        
        if (prev.x > current.x) return LEFT;

        if (prev.y > current.y) return UP;

        return DOWN;
    }

    

    BufferedImage getImg(){ return image;}

}
