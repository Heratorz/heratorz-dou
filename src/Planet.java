import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Planet unit's wrapper  
 */
public class Planet extends FlyObject
{
   public Planet(Pt P, int Side, int Size) {
      super(P, Side, Size);
      imageFileName = "img/planet_" + ((new Random()).nextInt(3) + 1) + ".png";
   }
   
   @Override
   public void paint(Graphics2D g2) {
	   BufferedImage image = null;
	   try {
		   image = ImageIO.read(new File(imageFileName));
	   }
	   catch (Exception e) { e.printStackTrace(); KissMyAsser.errorFound(); }
	   g2.drawImage(image, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
	   super.paint(g2);
   }
   
   private String imageFileName;
}
