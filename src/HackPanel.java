import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * Basic GUI panel
 */
class HackPanel extends JPanel
{
   WorldEnv we;
   
   private class TestMouseAdapter extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         we.generateWorld();
         repaint();
      }
   }
   
   public HackPanel() {
      repaint();
      we = new WorldEnv();
      this.addMouseListener(new TestMouseAdapter());
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      setBackGroundImage(g2);
      drawGrid(g2);
      we.drawWorld(g2);
   }
   
   void setBackGroundImage(Graphics2D g2) {
      BufferedImage bgImage = null;
      try { bgImage = ImageIO.read(new File("img/bck.jpg")); }
      catch(Exception e) { e.printStackTrace(); }
      g2.drawImage(bgImage, 0, 0, null);
   }
   
   void drawGrid(Graphics2D g2) {
      // draw verticals
      for (int i = 0; i <= WC.W; i+=WC.SZ) {
         g2.setColor((i == 0 || i == WC.W) ? Color.BLACK : Color.GRAY);
         g2.drawLine(WC.LX+i, WC.LY, WC.LX+i, WC.LY+WC.H);
      }
      // draw horizontals
      for (int i = 0; i <= WC.H; i+=WC.SZ) {
         g2.setColor((i == 0 || i == WC.H) ? Color.BLACK : Color.GRAY);
         g2.drawLine(WC.LX, WC.LY+i, WC.LX+WC.W, WC.LY+i);
      }
   }
}
