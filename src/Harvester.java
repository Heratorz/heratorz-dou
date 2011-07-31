import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 * Harvester unit's wrapper  
 */
public class Harvester extends FlyObject
{
   Source targetMine;
   Planet targetPlanet;
   int cargoGold;
   int cargoWood;
   public static final int priceGold = 5;
   public static final int priceWood = 5;
   
   private BufferedImage image;
   
   public Harvester(Pt P, int Side, int Id, Planet home) {
      super(P, Side, 4, Id);
      cargoGold = cargoWood = 0;
      targetMine = null;
      targetPlanet = home;
      image = null;
      try { image = ImageIO.read(new File("img/harvester.gif")); }
      catch(Exception e) { e.printStackTrace(); }
   }

   @Override
	public void paint(Graphics2D g2) {
		// TODO Auto-generated method stub
		//super.paint(g2);
        g2.drawImage(image, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
        drawInfo(g2);
	      drawSelection(g2);
	} 

   public String toString() {
      return "harvester";
   }

   public void makeTurn() {
      if (cargoGold != 0 || cargoWood != 0) goHome();
      else goMine();
   }
   
   private void goHome() {
      if (targetMine == null) return;
      if (this.touchObject(targetPlanet)) {
         WorldEnv.gold1 += cargoGold;
         WorldEnv.wood1 += cargoWood;
         cargoGold = cargoWood = 0;
         //JOptionPane.showMessageDialog(null, "Harvester found resources!");
      }
      else {
         moveToObject(targetPlanet);
         //JOptionPane.showMessageDialog(null, "Harvester moves at work!");
      }
      //JOptionPane.showMessageDialog(null, "Harvester moves home!");
   }
   
   private void goMine() {
      if (targetMine == null) return;
      if (this.touchObject(targetMine)) {
    	 if(targetMine.type == SourceType.GOLD)
    		 cargoGold += 10;
    	 else
    		 cargoWood += 6;
         //JOptionPane.showMessageDialog(null, "Harvester found resources!");
      }
      else {
         moveToObject(targetMine);
         //JOptionPane.showMessageDialog(null, "Harvester moves at work!");
      }
   }

}
