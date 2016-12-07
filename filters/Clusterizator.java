package filters;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Clusterizator extends Filter {
    
    Color[] kmeans;      // k-means
    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel

    public Clusterizator (BufferedImage img, int K){
        int w = img.getWidth();
        int h = img.getHeight();
        image = new BufferedImage(w,h,img.getType());
        src = img;
        labels = new int[w][h];
        generateKmeans(K);

        while (createLabels() == false){
            mv2centroids();
        }

        for (int i=0; i<w; i++){
            for (int j=0; j<h; j++){
                image.setRGB(i,j,kmeans[labels[i][j]].getRGB());
            }
        }
    }

    void generateKmeans(int K){
        kmeans = new Color[K];
        for (int i=0;i < K; i++){
            int x = (int) (Math.random()*src.getWidth());
            int y = (int) (Math.random()*src.getHeight());
            kmeans[i] = new Color(src.getRGB(x,y));
        }
        
    }

    void mv2centroids(){
        int w = src.getWidth();
        int h = src.getHeight();

        for (int k=0; k < kmeans.length ; k++){
            int r = 0; int g = 0; int b = 0;
            int cnt = 1;
            for (int i=0; i<w; i++){
                for (int j=0; j<h; j++){
                    if (labels[i][j] == k){
                        cnt++;
                        Color px = new Color(src.getRGB(i,j));
                        r += px.getRed();
                        g += px.getGreen();
                        b += px.getBlue();
                    }
                }
            }
            r = r/cnt; g = g/cnt; b = b/cnt;
            kmeans[k] = new Color(r,g,b);
        }
    }

    boolean createLabels(){
        boolean converged = true;
        int w = src.getWidth();
        int h = src.getHeight();

        for (int i=0; i<w; i++){
            for (int j=0; j<h; j++){

                Color c = new Color(src.getRGB(i,j));
                double distance = RGBdistance(kmeans[labels[i][j]],c);

                for (int k=0; k < kmeans.length; k++){
                    double newdist = RGBdistance(kmeans[k],c);
                    if (newdist < distance){
                        distance = newdist;
                        // Set label 
                        labels[i][j] = k;
                        converged = false;
                    }
                }
            }
        }
        return converged;
    }

    double RGBdistance(Color c1, Color c2){
        int r1 = c1.getRed();   int r2 = c2.getRed(); 
        int g1 = c1.getGreen(); int g2 = c2.getGreen(); 
        int b1 = c1.getBlue();  int b2 = c2.getBlue(); 
        double r = Math.sqrt((r1-r2)*(r1-r2) + 
                             (g1-g2)*(g1-g2) + 
                             (b1-b2)*(b1-b2));
        return r;
    }
    
    public BufferedImage getImg(){ return image;}

}
