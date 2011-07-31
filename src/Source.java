import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Source unit's wrapper  
 */
public class Source extends FlyObject
{
	SourceType type;
	int amount = 0;
	
   public Source(Pt P, int Size, int Id, SourceType Type, int Amount) {
      super(P, -1, Size, Id);
      type = Type;
      amount = Amount;
   }
   
   public void paint(Graphics2D g2) {
	 Color c = side == 0 ? Color.BLUE : side == 1 ? Color.RED : Color.LIGHT_GRAY;
	 g2.setColor(c);
     g2.fillOval(WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ);
     drawInfo(g2);
     if(selected)
     {
    	 g2.setColor(Color.BLACK);
    	 g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
         g2.drawString(type.toString(), WC.LX+WC.W+30, 470);
         g2.drawString("Amount: ", WC.LX+WC.W+15, 490);
         g2.drawString(Integer.toString(amount), WC.LX+WC.W+85, 490);
     }
   }
   
   public String toString() {
      return "source";
   }
}

