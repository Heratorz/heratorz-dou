import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Source unit's wrapper  
 */
public class Source extends FlyObject
{
   public Source(Pt P, int Size, int Id) {
      super(P, -1, Size, Id);
   }
   
   public void paint(Graphics2D g2) {
	   Color c = side == 0 ? Color.BLUE : side == 1 ? Color.RED : Color.LIGHT_GRAY;
	   g2.setColor(c);
      g2.fillOval(WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ);
      drawSelection(g2);
      drawInfo(g2);
   }
   
   public String toString() {
      return "source";
   }
}
