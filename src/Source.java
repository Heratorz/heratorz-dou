import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Source unit's wrapper  
 */
public class Source extends FlyObject
{
	SourceType type;
	int amount = 0;
	
	private BufferedImage imageBack;
	
   public Source(Pt P, int Size, int Id, SourceType Type, int Amount) {
      super(P, -1, Size, Id);
      type = Type;
      amount = Amount;
      imageBack = null;
      
      try { imageBack = ImageIO.read(new File("img/source_" + (Type == SourceType.GOLD? "1" : "2") + ".png")); }
      catch (Exception e) { KissMyAsser.errorFound(); }
   }
   
   public void paint(Graphics2D g2) {
     g2.drawImage(imageBack, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
     drawInfo(g2);
     if(selected) {
    	   g2.setColor(Color.BLACK);
    	   g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
         g2.drawString(type.toString() + " MINE:", WC.LX+WC.W+30, 490);
         g2.drawString("Amount: ", WC.LX+WC.W+30, 510);
         g2.drawString(Integer.toString(amount), WC.LX+WC.W+100, 510);
     }
   }
   
   public String toString() {
      return "source";
   }
}

