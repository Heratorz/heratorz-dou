import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Harvester unit's wrapper  
 */
public class Harvester extends FlyObject
{
   public Harvester(Pt P, int Side, int Size) {
      super(P, Side, Size);
   }
   
   
   @Override
	public void paint(Graphics2D g2) {
		// TODO Auto-generated method stub
		//super.paint(g2);
	   BufferedImage bgImage = null;
	      try { bgImage = ImageIO.read(new File("img/harvester.png")); }
	      catch(Exception e) { e.printStackTrace(); }
	      g2.drawImage(bgImage, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
	} 
}
