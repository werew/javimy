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

    void generateKmeans(int K){
        kmeans = new Color[K];
        for (int i=0;i < K; i++){
            int x = (int) (Math.random()*src.getWidth());
            int y = (int) (Math.random()*src.getHeight());
            kmeans[i] = new Color(src.getRGB(x,y));
            System.out.println(kmeans[i]+" "+x+" "+y);
        }
        
    }

    void createLabels(){
        int w = src.getWidth();
        int h = src.getHeight();

        for (int i=0; i<w; i++){
            for (int j=0; j<h; j++){
                int distance = 255*255; // Max distance
                //System.out.println("Distance "+distance);
                Color c = new Color(src.getRGB(i,j));
                for (int k=0; k < kmeans.length; k++){
                    int newdist = RGBdistance(kmeans[k],c);
               //     System.out.println("Compare "+newdist+" "+distance);
                    if (newdist < distance){
                        distance = newdist;
                        // Set label TODO for now set the color directly
                        image.setRGB(i,j,kmeans[k].getRGB());
              //          System.out.println("Setting "+k);
                    }
                }
            }
        }
    }

    int RGBdistance(Color c1, Color c2){
        int r1 = c1.getRed();   int r2 = c2.getRed(); 
        int g1 = c1.getGreen(); int g2 = c2.getGreen(); 
        int b1 = c1.getBlue();  int b2 = c2.getBlue(); 
        int r = (int) Math.sqrt((r1-r2)*(r1-r2) + 
                                (g1-g2)*(g1-g2) + 
                                (b1-b2)*(b1-b2));
       // System.out.println(r1+" "+g1+" "+b1+" "+r2+" "+g2+" "+b2);
        return r;
    }
    
    BufferedImage getImg(){ return image;}

}
