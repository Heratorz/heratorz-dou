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
   BufferedImage bgImage;
   
   private BufferedImage image;
   
   public Harvester(Pt P, int Side, int Id, Planet home) {
      super(P, Side, 4, Id);
      cargoGold = cargoWood = 0;
      targetMine = null;
      targetPlanet = home;
      try { bgImage = ImageIO.read(new File("img/harvester" + (Side == 0? "" : "_r") + ".png")); }
      catch(Exception e) { KissMyAsser.errorFound(); }
   }

   @Override
	public void paint(Graphics2D g2) {
	    g2.drawImage(bgImage, WC.LX+p.x*WC.SZ, WC.LY+p.y*WC.SZ, size*WC.SZ, size*WC.SZ, null);
        drawInfo(g2);
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
    	 {
    		 if (targetMine.amount <= 0)
    			 return;
    		 else if(targetMine.amount - WC.HarvCanTakeGold < 0){
    			 cargoGold += targetMine.amount;
    			 targetMine.amount = 0;
    		 }
    		 else{
    			 cargoGold += WC.HarvCanTakeGold;
    			 targetMine.amount -= WC.HarvCanTakeGold;
    		 }
    	 }
    	 else
    	 {
    		 if (targetMine.amount <= 0)
    			 return;
    		 else if(targetMine.amount - WC.HarvCanTakeWood < 0){
    			 cargoWood += targetMine.amount;
    			 targetMine.amount = 0;
    		 }
    		 else{
    			 cargoWood += WC.HarvCanTakeWood;
    			 targetMine.amount -= WC.HarvCanTakeWood;
    		 }
    	 }
         //JOptionPane.showMessageDialog(null, "Harvester found resources!");
      }
      else {
         moveToObject(targetMine);
         //JOptionPane.showMessageDialog(null, "Harvester moves at work!");
      }
   }

}
