import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * Fighter unit's wrapper  
 */
public class Fighter extends FlyObject
{
   public static final int priceGold = 10;
   public static final int priceWood = 10;
 
   int damage = 1;
   int defence = 1;
   private BufferedImage bgImage;
   
   public Fighter(Pt P, int Side, int Id) {
      super(P, Side, 4, Id);
      bgImage = null;
      
      try { bgImage = ImageIO.read(new File("img/fighter" + (Side == 0? "" : Side == 1? "_r" : "_g") + ".png")); }
      catch (Exception e) { KissMyAsser.errorFound(); }
   }
   
   public String toString() {
      return "fighter";
   }
   
   @Override
   public void paint(Graphics2D g2) {
	   g2.drawImage(bgImage, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
	   drawInfo(g2);
	   if(selected)
	   {		   
		   g2.setColor(Color.BLACK);
		   g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
		   g2.drawString("Damage: " + damage, WC.LX+WC.W+15, 450);
		   g2.drawString("Defence: " + defence, WC.LX+WC.W+15, 470);
	   }
   }
}
