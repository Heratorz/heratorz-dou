import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Fighter unit's wrapper  
 */
public class Fighter extends FlyObject
{
   FlyObject killTarget;
   public static final int priceGold = 10;
   public static final int priceWood = 10;
   
   public Fighter(Pt P, int Side, int Id) {
      super(P, Side, 4, Id);
      killTarget = null;
   }
   
   public String toString() {
      return "fighter";
   }
   
   public void makeTurn() {
      if (killTarget == null) return;
      if (Utils.getDistance(this, killTarget) <= 1.5) {
         if (killTarget.toString().equals("fighter")) {
            WorldEnv.killObject(this);
            WorldEnv.killObject(killTarget);
         }
         else if (killTarget.toString().equals("harvester")) {
            WorldEnv.killObject(killTarget);
            if (side == 0) killTarget = null;
            else {
               ArrayList<FlyObject> player1Units = new ArrayList<FlyObject>();
               for (FlyObject fo : WorldEnv.all) if (fo.side == 0)
                  player1Units.add(fo);
               killTarget = player1Units.get((new Random()).nextInt(player1Units.size()));
            }
         }
         else if (killTarget.toString().equals("planet")) {
            WorldEnv.killObject(this);
            ((Planet)killTarget).health--;
            //JOptionPane.showMessageDialog(null, "Planet is under attack!");
         }
         else
            KissMyAsser.errorFound();
      }
      else
         moveToObject(killTarget);
   }
}
