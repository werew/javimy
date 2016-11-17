package filters;
import java.awt.image.BufferedImage;


public abstract class Filter {
    protected BufferedImage image;
    public abstract void apply();
}
