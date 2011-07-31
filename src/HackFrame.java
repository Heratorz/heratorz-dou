import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Basic GUI frame
 */
public class HackFrame extends JFrame
{
   public HackFrame() {
      installHackPanel();
      this.setVisible(true);
      this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
      
   }
   
   public void installHackPanel() {
      JPanel warField = new HackPanel();
      this.add(warField);
   }
}
