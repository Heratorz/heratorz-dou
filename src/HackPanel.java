import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.border.*;

/**
 * Basic GUI panel
 */
class HackPanel extends JPanel
{
   WorldEnv we;
   JButton newGameButton;
   JButton nextTurnButton;
   JButton buildHarvesterButton;
   JButton buildFighterButton;
   MouseAdapter userClickListener;
   
   private class NewGameButtonMouseAdapter extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         we.generateWorld();
         repaint();
      }
   }
   
   private class NextTurnButtonMouseAdapter extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         we.nextTurn();
         updateInterface();
         repaint();
      }
   }
   
   private class BuildHarvesterButtonMouseAdapter extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         if (!buildHarvesterButton.isEnabled())
            return;
         we.buildHarvester();
         repaint();
      }
   }
   
   private class BuildFighterButtonMouseAdapter extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         if (!buildFighterButton.isEnabled())
            return;
         we.buildFighter();
         repaint();
      }
   }
   
   public HackPanel() {
      we = new WorldEnv();
      this.setLayout(null);
      installUserInterface();
      installMouseListener();
   }
   
   public void installMouseListener() {
      userClickListener = new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            Pt point = new Pt(e.getX(), e.getY());
            we.userClicked(point);
            repaint();
         }
      };
      this.addMouseListener(userClickListener);
   }
   
   public void installUserInterface() {
      // Add "New game" button
      newGameButton = new JButton("New game");
      newGameButton.setBounds(WC.LX, WC.LY+WC.H+20, 100, 40);
      newGameButton.addMouseListener(new NewGameButtonMouseAdapter());
      newGameButton.setBackground(new Color(160, 209, 223));
      newGameButton.setForeground(Color.BLACK);
      newGameButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
      this.add(newGameButton);
      // Add "Next turn" button
      nextTurnButton = new JButton("Next turn");
      nextTurnButton.setBounds(WC.LX+WC.W-100, WC.LY+WC.H+20, 100, 40);
      nextTurnButton.addMouseListener(new NextTurnButtonMouseAdapter());
      nextTurnButton.setBackground(new Color(160, 209, 223));
      nextTurnButton.setForeground(Color.BLACK);
      nextTurnButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
      this.add(nextTurnButton);
      // Add "Build harvester" button
      buildHarvesterButton = new JButton("Build harvester");
      buildHarvesterButton.setBounds(WC.LX+450, WC.LY+WC.H+20, 200, 40);
      buildHarvesterButton.addMouseListener(new BuildHarvesterButtonMouseAdapter());
      buildHarvesterButton.setBackground(new Color(160, 209, 223));
      buildHarvesterButton.setForeground(Color.BLACK);
      buildHarvesterButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
      this.add(buildHarvesterButton);
      // Add "Build fighter" button
      buildFighterButton = new JButton("Build fighter");
      buildFighterButton.setBounds(WC.LX+660, WC.LY+WC.H+20, 200, 40);
      buildFighterButton.addMouseListener(new BuildFighterButtonMouseAdapter());
      buildFighterButton.setBackground(new Color(160, 209, 223));
      buildFighterButton.setForeground(Color.BLACK);
      buildFighterButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
      this.add(buildFighterButton);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      setBackGroundImage(g2);
      drawGrid(g2);
      updateInterface();
      we.drawWorld(g2);
   }
   
   void updateInterface() {
      buildHarvesterButton.setEnabled(we.canBuildHarvester());
      buildFighterButton.setEnabled(we.canBuildFighter());
   }
   
   void setBackGroundImage(Graphics2D g2) {
      BufferedImage bgImage = null;
      try { bgImage = ImageIO.read(new File("img/bck.jpg")); }
      catch(Exception e) { e.printStackTrace(); }
      g2.drawImage(bgImage, 0, 0, null);
   }
   
   void drawGrid(Graphics2D g2) {
      // draw verticals
      g2.setColor(Color.LIGHT_GRAY);
      for (int i = 0; i <= WC.W; i+=WC.SZ)
         g2.drawLine(WC.LX+i, WC.LY, WC.LX+i, WC.LY+WC.H);
      // draw horizontals
      for (int i = 0; i <= WC.H; i+=WC.SZ)
         g2.drawLine(WC.LX, WC.LY+i, WC.LX+WC.W, WC.LY+i);
      g2.setColor(Color.BLACK);
      g2.drawLine(WC.LX, WC.LY, WC.LX, WC.LY+WC.H);
      g2.drawLine(WC.LX+WC.W, WC.LY, WC.LX+WC.W, WC.LY+WC.H);
      g2.drawLine(WC.LX, WC.LY+WC.H, WC.LX+WC.W, WC.LY+WC.H);
      g2.drawLine(WC.LX, WC.LY, WC.LX+WC.W, WC.LY);
   }
}
