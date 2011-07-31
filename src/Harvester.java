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
   
   public Harvester(Pt P, int Side, int Id, Planet home) {
      super(P, Side, 4, Id);
      cargoGold = cargoWood = 0;
      targetMine = null;
      targetPlanet = home;
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
         cargoGold += 10;
         cargoWood += 10;
         //JOptionPane.showMessageDialog(null, "Harvester found resources!");
      }
      else {
         moveToObject(targetMine);
         //JOptionPane.showMessageDialog(null, "Harvester moves at work!");
      }
   }
}
