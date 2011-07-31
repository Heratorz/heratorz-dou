import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Harvester unit's wrapper  
 */
public class Harvester extends FlyObject
{
	Planet basePlanet;
	Pt sourcePt = null;
	Source source = null;
	BufferedImage bgImage = null;
	
   public Harvester(Pt P, int Side, int Size, Planet BasePlanet) {
      super(P, Side, Size);
	  
      basePlanet = BasePlanet;      	   
      try { bgImage = ImageIO.read(new File("img/harvester.png")); }
      catch(Exception e) { e.printStackTrace(); }
   }
      
   @Override
	public void paint(Graphics2D g2) {
		// TODO Auto-generated method stub
	   g2.drawImage(bgImage, WC.LX + basePlanet.p.x * WC.SZ, WC.LY + basePlanet.p.y * WC.SZ, size * WC.SZ, size * WC.SZ, null);
	}
}
