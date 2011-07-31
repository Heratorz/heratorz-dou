import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Abstract flying unit's wrapper  
 */
abstract class FlyObject
{
   Pt p;
   int id;
   int side;
   int size;
   boolean selected;
   private final int dx[] = {-1, 0, 1, 0};
   private final int dy[] = {0, 1, 0, -1};
   private BufferedImage selectImg;
   
   public FlyObject(Pt P, int Side, int Size, int Id) {
      p = P;
      side = Side;
      size = Size;
      id = Id;
      selected = false;
      try { selectImg = ImageIO.read(new File("img/selection.png")); }
	   catch (Exception e) { KissMyAsser.errorFound(); }
   }
   
   public void drawSelection(Graphics2D g2) {
	   if (selected)
	      g2.drawImage(selectImg, WC.LX+p.x*WC.SZ-WC.SZ*3/2, WC.LY+p.y*WC.SZ-WC.SZ*3/2, (size+3)*WC.SZ, (size+3)*WC.SZ, null);
   }
   
   public void drawInfo(Graphics2D g2) {
       g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
       g2.drawString("id: "+ id + " side: " + side + " size: " + size + " corner: " + p.toString() + " center: " + getCenter().toString(), WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ);
   }
   
   public void paint(Graphics2D g2) {
      Color c = side == 0 ? Color.BLUE : side == 1 ? Color.RED : Color.DARK_GRAY;
      g2.setColor(c);
      g2.drawOval(WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ);
      drawSelection(g2);
      drawInfo(g2);
   }
   
   public void setSelected(boolean state) {
      if (state == selected)
         KissMyAsser.errorFound();
      selected = state;
   }
   
   public Pt getCenter() {
      return new Pt(p.x+size/2, p.y+size/2);
   }
   
   public boolean havePoint(Pt p2) {
      return Utils.getDistance(getCenter(), p2) <= size/2+1e-9;
   }
   
   public String toString() {
      KissMyAsser.errorFound();
      return "Abstract";
   }
   
   public void makeTurn() {
      // Some objects (e.g. planets) do nothing automatically...
   }
   
   public boolean touchObject(FlyObject o2) {
      return Utils.getDistance(this, o2) <= 1e-7;
   }
   
   public boolean moveToObject(FlyObject o2) {
      if (o2 == null)
         KissMyAsser.errorFound();
      Pt oldp = this.p;
      int k = -1;
      double dist = Double.MAX_VALUE;
      Cycle: for (int i = 0; i < 4; i++) {
         this.p = new Pt(oldp.x+dx[i], oldp.y+dy[i]);
         if (p.x < 0 || p.x >= WC.N || p.y < 0 || p.y >= WC.M) continue;
         for (FlyObject obj : WorldEnv.all) if (obj.id != id) {
            double dObj = Utils.getDistance(this, obj);
            if (touchObject(obj)) {
               if (obj.id != o2.id) continue Cycle;
               // our goal - make sure to select
               else { k = i; dist = Double.MIN_VALUE; } 
            }
         }
         double d = Utils.getDistance(this, o2);
         if (d < dist-1e-9) { k = i; dist = d; }
      }
      if (k == -1)
         KissMyAsser.errorFound();
      this.p = new Pt(oldp.x+dx[k], oldp.y+dy[k]);
      return false;
   }
}
