import java.awt.image.BufferedImage;
import java.awt.*;

public class Clusterizator {
    
    Color[] kmeans;      // k-means
    BufferedImage image; // Segmented image
    BufferedImage src;   // Image source
    int[][] labels;      // Segment label of each pixel

    public Clusterizator (BufferedImage img, int K){
        int w = img.getWidth();
        int h = img.getHeight();
        image = new BufferedImage(w,h,img.getType());
        src = img;
        labels = new int[w][h];
        generateKmeans(K);
        createLabels();
        
    }

    /**
     * Devide the 255 range in K sections
     */
    void generateKmeans(int K){
        kmeans = new Color[K];
        for (int i=0;i < K; i++){
            int c = (255/K * (i+1));
            kmeans[i] = new Color(c,c,c);
           System.out.println("Km "+c);
        }
        
    }

    void createLabels(){
        int w = src.getWidth();
        int h = src.getHeight();

        for (int i=0; i<w; i++){
            for (int j=0; j<h; j++){
                int distance = 255; // Max distance
                //System.out.println("Distance "+distance);
                Color c = new Color(src.getRGB(i,j));
                for (int k=0; k < kmeans.length; k++){
                    int newdist = RGBdistance(kmeans[k],c);
                    if (newdist < distance){
                        distance = newdist;
                        // Set label TODO for now set the color directly
                        image.setRGB(i,j,kmeans[k].getRGB());
                        //System.out.println("Setting "+k);
                    }
                }
            }
        }
    }

    int RGBdistance(Color c1, Color c2){
        int r1 = c1.getRed();   int r2 = c2.getRed(); 
        int g1 = c1.getGreen(); int g2 = c2.getGreen(); 
        int b1 = c1.getBlue();  int b2 = c2.getBlue(); 
        int r = Math.abs(r1+b1+g1-(r2+g2+b2));
        return r;
    }
    
    BufferedImage getImg(){ return image;}

}
