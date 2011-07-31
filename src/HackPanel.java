import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import sun.misc.Cleaner;

/**
 * Basic GUI panel
 */
class HackPanel extends JPanel implements MouseListener
{
   WorldEnv we;
   Label lbl = new Label("");
   Component selectedElement = null;
   
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

      if(selectedElement == null){
    	  lbl = new Label("Selected item: none");    	  
      }
      else{
    	  lbl = new Label("Selected item: somewhere");
      }      
      //Component[] d = this.getComponents();
      
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
		// TODO Auto-generated method stub
		Component d = findComponentAt(e.getX(), e.getY());
		if (d instanceof JPanel) {
			selectedElement = d;
		}   		
		repaint();
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
