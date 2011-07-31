import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.*;

/**
 * Abstract flying unit's wrapper  
 */
abstract class FlyObject
{
   Pt p;
   int side;
   int size;
   
   public FlyObject(Pt P, int Side, int Size) {
      p = P;
      side = Side;
      size = Size;
   }
   
   public void paint(Graphics2D g2) {
      Color c = side == 0 ? Color.BLUE : side == 1 ? Color.RED : Color.GRAY;
      g2.setColor(c);
//      g2.drawOval(WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ);
      g2.drawString("side: " + side + " size: " + size + " corner: " + p.toString() + " center: " + getCenter().toString(), WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ);
      //System.out.println(p.toString());
   }
   
   public boolean canMove(int Dx, int Dy, int N, int M) {
      int x2 = p.x+Dx;
      int y2 = p.y+Dy;
      return x2 >= 0 && x2 < N && y2 >= 0 && y2 < M;
   }
   
   public void move(Pt P) {
      p = P;
   }
   
   public Pt getCenter() {
      return new Pt(p.x+size/2, p.y+size/2);
   }
}
