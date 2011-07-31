import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.lang.Math.*;

import sun.misc.Cleaner;

/**
 * Basic GUI panel
 */
class HackPanel extends JPanel implements MouseListener
{
   WorldEnv we;
   FlyObject selectedObj = null;
   
   public HackPanel() {
      repaint();
      we = new WorldEnv();    
      addMouseListener(this);
   }
   
   public void paintComponent(Graphics g) {	   
      super.paintComponent(g);    
      Graphics2D g2 = (Graphics2D)g;
      setBackGroundImage(g2);
      drawGrid(g2);
      we.drawWorld(g2);  
      
      if(selectedObj == null)
      {
    	  add(new Label("Selected obj: none"));
      }
      else
      {
    	  add(new Label("Selected obj: " + selectedObj.p.toString()));
      }
      //Component d = getComponent(0);
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
         g2.setColor((i == 0 || i == WC.W) ? Color.BLACK : Color.LIGHT_GRAY);
         g2.drawLine(WC.LX+i, WC.LY, WC.LX+i, WC.LY+WC.H);
      }
      // draw horizontals
      for (int i = 0; i <= WC.H; i+=WC.SZ) {
         g2.setColor((i == 0 || i == WC.H) ? Color.BLACK : Color.LIGHT_GRAY);
         g2.drawLine(WC.LX, WC.LY+i, WC.LX+WC.W, WC.LY+i);
      }
   }

	@Override
	public void mouseClicked(MouseEvent e) {
		selectedObj = null;
		for (FlyObject obj : this.we.all) {
			int r = obj.size * WC.SZ;
			int x = WC.LX + obj.p.x * WC.SZ + r;
			int y = WC.LY + obj.p.y * WC.SZ + r;
			//Sqrt((Xc-Xp)^2+(Yc-Yp)^2)<=R
			if(Math.sqrt(Math.pow((x - e.getX()), 2) + Math.pow((y - e.getY()), 2)) < r)
				selectedObj = obj;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}
}
