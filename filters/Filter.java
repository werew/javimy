package filters;
import java.awt.image.BufferedImage;


public abstract class Filter {
    protected BufferedImage image;
    public abstract BufferedImage getImg();
  //  public abstract void apply();
}
