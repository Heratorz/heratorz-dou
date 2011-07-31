import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Playing world environment's wrapper   
 */
public class WorldEnv 
{
   //TODO: Little WORKAROUND (remove static later...)
   public static LinkedList<FlyObject> all;
   LinkedList<Planet> planets;
   LinkedList<Source> sources;
   LinkedList<Harvester> harvesters;
   LinkedList<Fighter> fighters;
   LinkedList<Pt> randomPoints; // random-shuffled cords
   Random randomizer = new Random();
   static int gold1, gold2; // TODO: Little WORKAROUND (remove static later...)
   static int wood1, wood2; // TODO: Little WORKAROUND (remove static later...)
   FlyObject selected;
   int idCounter;
   
   private BufferedImage selection;
   
   public WorldEnv() {
	  selection = null;
	  
      all = new LinkedList<FlyObject>();
      planets = new LinkedList<Planet>();
      sources = new LinkedList<Source>();
      harvesters = new LinkedList<Harvester>();
      fighters = new LinkedList<Fighter>();
      randomPoints = new LinkedList<Pt>();
      for (int i = 0; i < WC.N; i++)
         for (int j = 0; j < WC.M; j++)
            randomPoints.add(new Pt(i, j));
      if (!generateWorld())
         KissMyAsser.errorFound();
      
      try {
		  selection = ImageIO.read(new File("img/selection.gif"));
   	  }
	  catch (Exception e) { e.printStackTrace(); KissMyAsser.errorFound(); }
   }
   
   public void buildHarvester() {
      if (!canBuildHarvester())
         KissMyAsser.errorFound();
      //JOptionPane.showMessageDialog(null, "Build Harvester!");
      Pt p = (selected.getCenter().y < WC.M/2)
         ? new Pt(selected.getCenter().x-2, selected.p.y+selected.size)
         : new Pt(selected.getCenter().x-2, selected.p.y-4); // Fix: WORKAROUND :)
      Harvester unit = new Harvester(p, 0, idCounter++, (Planet)selected);
      all.add(unit);
      harvesters.add(unit);
      gold1 -= Harvester.priceGold;
      wood1 -= Harvester.priceWood;
   }
   
   public void buildFighter() {
      if (!canBuildFighter())
         KissMyAsser.errorFound();
      //JOptionPane.showMessageDialog(null, "Build Fighter!");
      Pt p = (selected.getCenter().y < WC.M/2)
         ? new Pt(selected.getCenter().x-2, selected.p.y+selected.size)
         : new Pt(selected.getCenter().x-2, selected.p.y-4); // Fix: WORKAROUND :)
      Fighter unit = new Fighter(p, 0, idCounter++);
      all.add(unit);
      fighters.add(unit);
      gold1 -= Fighter.priceGold;
      wood1 -= Fighter.priceWood;
   }
   
   public boolean canBuildHarvester() {
      return selected != null &&
      selected.toString().equals("planet") &&
      selected.side == 0 &&
      gold1 > Harvester.priceGold &&
      wood1 > Harvester.priceWood;
   }
   
   public boolean canBuildFighter() {
      return selected != null &&
      selected.toString().equals("planet") &&
      selected.side == 0 &&
      gold1 > Fighter.priceGold &&
      wood1 > Fighter.priceWood;
   }
   
   public FlyObject findSelectedObject(Pt p) {
      for (FlyObject obj : all)
         if (obj.havePoint(p))
            return obj;
      return null;
   }
   
   // User have just clicked here...
   public void userClicked(Pt p) {
      p = new Pt((p.x-WC.LX)/WC.SZ, (p.y-WC.LY)/WC.SZ);
      FlyObject newSelect = findSelectedObject(p);
      if (selected != null)
         selected.setSelected(false);
      //TODO: WORKAROUND: use smth. like instanceof... (fix later)
      if (newSelect != null) newSelect.setSelected(true);
      if (newSelect != null && selected != null) {
         String Old = selected.toString();
         String New = newSelect.toString();
         if (Old.equals("harvester")) {
            if (New.equals("source")) {
               ((Harvester)selected).targetMine = (Source)newSelect;
               JOptionPane.showMessageDialog(null, "Harvester selected source!");
            }
            else if (New.equals("planet") && newSelect.side == 0) {
               ((Harvester)selected).targetPlanet = (Planet)newSelect;
               JOptionPane.showMessageDialog(null, "Harvester selected planet!");
            }
         }
      }
      selected = newSelect;
   }
   
   // Next user's turn 
   public void nextTurn() {
      //JOptionPane.showMessageDialog(null, "Next turn!");
      if (selected != null) {
         selected.setSelected(false);
         selected = null;
      }
      for (FlyObject obj : all)
         obj.makeTurn();
   }
   
   public void drawWorld(Graphics2D g2) {
      for (FlyObject fo : all) {
         fo.paint(g2);
    	 if (fo.selected)
    		 g2.drawImage(selection, WC.LX+fo.p.x*WC.SZ-WC.SZ*3/2, WC.LY+fo.p.y*WC.SZ-WC.SZ*3/2, (fo.size+3)*WC.SZ, (fo.size+3)*WC.SZ, null);
      }
      g2.setColor(Color.BLACK);
      // Blue player (#1)
      g2.setFont(new Font("SansSerif", Font.PLAIN, 22));
      g2.drawString("[ Blue player ]", WC.LX+WC.W+20, 320);
      g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
      g2.drawString("Gold: " + gold1, WC.LX+WC.W+35, 350);
      g2.drawString("Wood: " + wood1, WC.LX+WC.W+35, 375);
      // Red player (#2)
      g2.setFont(new Font("SansSerif", Font.PLAIN, 22));
      g2.drawString("[ Red player ]", WC.LX+WC.W+20, 420);
      g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
      g2.drawString("Gold: " + gold1, WC.LX+WC.W+35, 450);
      g2.drawString("Wood: " + wood1, WC.LX+WC.W+35, 475);
   }
   
   public boolean generateWorld() {
      idCounter = 0;
      all.clear();
      planets.clear();
      sources.clear();
      harvesters.clear();
      fighters.clear();
      gold1 = gold2 = wood1 = wood2 = 50;
      Utils.randomShuffle(randomPoints);
      if (!generatePlanet(0, 14)) return false;
      if (!generatePlanet(1, 14)) return false;
      for (int i = 0; i < 4; i++)
         if (!generateSource()) return false;
      selected = null;
      return true;
   }
   
   // Generate size randomly if corresponding parameter size == -1
   public boolean generatePlanet(int side, int size) {
      final int mn = 10, mx = 20, closest = 10;
      if (size == -1)
         size = mn + randomizer.nextInt(mx-mn+1);
      size -= size&1;
      Pt p = getPossiblePlace(size, closest);
      if (p != null) {
         Planet cur = new Planet(p, side, size, idCounter++);
         all.add(cur);
         planets.add(cur);
         //System.out.println("Planet generated: " + cur.p.toString());
         return true;
      }
      return false;
   }
   
   public boolean generateSource() {
      final int mn = 10, mx = 20, closest = 10;
      int size = mn + randomizer.nextInt(mx-mn+1);
      size -= size&1;
      Pt p = getPossiblePlace(size, closest);
      if (p != null) {
         Source cur = new Source(p, size, idCounter++);
         all.add(cur);
         sources.add(cur);
         //System.out.println("Source generated: " + cur.p.toString());
         return true;
      }
      return false;
   }
   
   //TODO full shit
//   public boolean generateX(int side) {
//	   Harvester f =new Harvester(new Pt(5, 5), side, 2);
//	   all.add(f);
//	   return true;
//   }
   
   public Pt getPossiblePlace(int size, double minDistPossible) {
      // No generated object can be closer
      // to the map's border than this value
      final int border = 7;  
      for (Pt p : randomPoints) {
         if (p.x-border < 0 || p.y-border < 0 ||
             p.x+border+size >= WC.N || p.y+border+size >= WC.M)
            continue;
         Pt center = new Pt(p.x+size/2, p.y+size/2);
         boolean good = true;
         for (FlyObject obj : all) {
            if (Utils.getDistance(center, obj.getCenter())-size/2.-obj.size/2. < minDistPossible+1e-9) {
               good = false;
               break;
            }
         }
         if (good)
            return p;
      }
      KissMyAsser.errorFound();
      return null;
   }
}
