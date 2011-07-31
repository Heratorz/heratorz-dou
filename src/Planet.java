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
   public Planet(Pt P, int Side, int Size, int Id) {
      super(P, Side, Size, Id);
      image = null;
      selection = null;
	  imageFileName = 
			   "img/planet_" + 
				((new Random()).nextInt(4) + 1);
      loadImage(imageFileName);
   }

   public String toString() {
      return "planet";
   }

   
   @Override
   public void paint(Graphics2D g2) {
	   g2.drawImage(image, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
	   drawSelection(g2);
	   drawInfo(g2);
	   super.paint(g2);
   }
   
   public Harvester generateHarvester() {
	   return null;
   }
   
   public Fighter generateFigher() {
	   return null;
   }
   
   private void loadImage(String baseFileName) {
	   // TODO: reinitialize on side changed
	   baseFileName += side == 0? "" : side == 1? "_r" : "_g";
	   try {
		   image = ImageIO.read(new File(baseFileName + ".png"));
	   }
	   catch (Exception e) { e.printStackTrace(); KissMyAsser.errorFound(); }
   }
   
   private String imageFileName;
   private BufferedImage image;
   private BufferedImage selection;
}
