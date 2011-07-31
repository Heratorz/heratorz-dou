import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Fighter unit's wrapper  
 */
public class Fighter extends FlyObject
{
   public static final int priceGold = 10;
   public static final int priceWood = 10;
   
   public Fighter(Pt P, int Side, int Id) {
      super(P, Side, 4, Id);
   }
   
   public String toString() {
      return "fighter";
   }
}
