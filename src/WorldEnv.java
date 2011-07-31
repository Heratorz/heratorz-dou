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
   public static ArrayList<FlyObject> all;
   public static ArrayList<Planet> planets;
   LinkedList<Source> sources;
   public static ArrayList<Harvester> harvesters;
   public static ArrayList<Fighter> fighters;
   LinkedList<Pt> randomPoints; // random-shuffled cords
   Random randomizer = new Random();
   static int gold1, gold2; // TODO: Little WORKAROUND (remove static later...)
   static int wood1, wood2; // TODO: Little WORKAROUND (remove static later...)
   FlyObject selected;
   int idCounter;
   
   private BufferedImage selection;
   
   public WorldEnv() {
	  selection = null;
	  
      all = new ArrayList<FlyObject>();
      planets = new ArrayList<Planet>();
      sources = new LinkedList<Source>();
      harvesters = new ArrayList<Harvester>();
      fighters = new ArrayList<Fighter>();
      randomPoints = new LinkedList<Pt>();
      for (int i = 0; i < WC.N; i++)
         for (int j = 0; j < WC.M; j++)
            randomPoints.add(new Pt(i, j));
      generateWorld();
      try {
		  selection = ImageIO.read(new File("img/selection.gif"));
   	  }
	  catch (Exception e) { e.printStackTrace(); KissMyAsser.errorFound(); }
   }
   
   public static void killObject(FlyObject obj) {
      int n = all.size();
      for (int i = 0; i < n; i++) if (all.get(i).id == obj.id) {
         all.set(i, all.get(n-1));
         all.remove(n-1);
         break;
      }
      if (obj.toString().equals("fighter")) {
         n = fighters.size();
         for (int i = 0; i < n; i++) if (fighters.get(i).id == obj.id) {
            fighters.set(i, fighters.get(n-1));
            fighters.remove(n-1);
            break;
         }
      }
      else if (obj.toString().equals("harvester")) {
         n = harvesters.size();
         for (int i = 0; i < n; i++) if (harvesters.get(i).id == obj.id) {
            harvesters.set(i, harvesters.get(n-1));
            harvesters.remove(n-1);
            break;
         }
      }
   }
   
   public void buildHarvester() {
      if (!canBuildHarvester())
         KissMyAsser.errorFound();
      //JOptionPane.showMessageDialog(null, "Build Harvester!");
      Pt p = (selected.getCenter().y < WC.M/2)
         ? new Pt(selected.getCenter().x-2, selected.p.y+selected.size+2)
         : new Pt(selected.getCenter().x-2, selected.p.y-6); // Fix: WORKAROUND :)
      Harvester unit = new Harvester(p, 0, idCounter++, (Planet)selected);
      all.add(unit);
      harvesters.add(unit);
      gold1 -= Harvester.priceGold;
      wood1 -= Harvester.priceWood;
   }
   
   // TODO: fix code duplication
   public void buildFighter() {
      //if (!canBuildFighter())
      //   KissMyAsser.errorFound();
      //JOptionPane.showMessageDialog(null, "Build Fighter!");
      Pt p = (selected.getCenter().y < WC.M/2)
         ? new Pt(selected.getCenter().x-2, selected.p.y+selected.size+2)
         : new Pt(selected.getCenter().x-2, selected.p.y-6); // Fix: WORKAROUND :)
      Fighter unit = new Fighter(p, 0, idCounter++);
      all.add(unit);
      fighters.add(unit);
      gold1 -= Fighter.priceGold;
      wood1 -= Fighter.priceWood;
   }
   
   // TODO: fix code duplication
   public boolean canBuildHarvester() {
      if (selected == null ||
         !selected.toString().equals("planet") ||
         selected.side != 0 || 
         gold1 < Harvester.priceGold ||
         wood1 < Harvester.priceWood)
         return false;
      Pt p = (selected.getCenter().y < WC.M/2)
         ? new Pt(selected.getCenter().x-2, selected.p.y+selected.size+2)
         : new Pt(selected.getCenter().x-2, selected.p.y-6); // Fix: WORKAROUND :)
      Harvester unit = new Harvester(p, 0, -1, (Planet)selected);
      for (FlyObject obj : all)
         if (unit.touchObject(obj))
            return false;
      return true;
   }
   
   public boolean canBuildFighter() {
      if (selected == null ||
            !selected.toString().equals("planet") ||
            selected.side != 0 || 
            gold1 < Fighter.priceGold ||
            wood1 < Fighter.priceWood)
            return false;
      Pt p = (selected.getCenter().y < WC.M/2)
         ? new Pt(selected.getCenter().x-2, selected.p.y+selected.size+2)
         : new Pt(selected.getCenter().x-2, selected.p.y-6); // Fix: WORKAROUND :)
      Fighter unit = new Fighter(p, 0, -1);
      for (FlyObject obj : all)
         if (unit.touchObject(obj))
            return false;
      return true;
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
      if (newSelect != null && selected != null && selected.side == 0) {
         String Old = selected.toString();
         String New = newSelect.toString();
         if (Old.equals("harvester")) {
            if (New.equals("source")) {
               ((Harvester)selected).targetMine = (Source)newSelect;
               //JOptionPane.showMessageDialog(null, "Harvester selected source!");
            }
            else if (New.equals("planet") && newSelect.side == 0) {
               ((Harvester)selected).targetPlanet = (Planet)newSelect;
               //JOptionPane.showMessageDialog(null, "Harvester selected planet!");
            }
            newSelect.setSelected(false);
            newSelect = null;
         }
         else if (Old.equals("fighter")) {
            if (New.equals("planet") || New.equals("harvester") || New.equals("fighter")) {
               ((Fighter)selected).killTarget = newSelect;
               //JOptionPane.showMessageDialog(null, "Harvester selected source!");
            }
            newSelect.setSelected(false);
            newSelect = null;
         }
      }
      selected = newSelect;
   }
   
   // Next user's turn 
   public void nextTurn() {
      for (Planet pl : planets)
         if (pl.health == 0) {
            JOptionPane.showMessageDialog(null, (pl.side == 1 ? "BLUE" : "RED") + " PLAYER WIN!!!");
            return;
         }
      //JOptionPane.showMessageDialog(null, "Next turn!");
      if (selected != null) {
         selected.setSelected(false);
         selected = null;
      }
      for (FlyObject obj : all)
         obj.makeTurn();
      enemyTurn();
   }
   
   private void enemyTurn() {
      Planet pl = null;
      for (Planet p : planets)
         if (p.side == 1)
            pl = p;
      if (pl == null)
         KissMyAsser.errorFound();
      boolean canBuildSomeShit = true;
      for (FlyObject obj : all)
         if (obj.side == 1 && obj.id != pl.id)
            if (Utils.getDistance(pl, obj) < 10)
               canBuildSomeShit = false;
      if (canBuildSomeShit) {
         // Try build harvester
         if (randomizer.nextBoolean()) {
            if (gold2 >= Harvester.priceGold && wood2 >= Fighter.priceWood) {
               Pt p = (pl.getCenter().y < WC.M/2)
                  ? new Pt(pl.getCenter().x-2, pl.p.y+pl.size+2)
                  : new Pt(pl.getCenter().x-2, pl.p.y-6); // Fix: WORKAROUND :)
               Harvester unit = new Harvester(p, 1, idCounter++, pl);
               all.add(unit);
               harvesters.add(unit);
               gold2 -= Harvester.priceGold;
               wood2 -= Harvester.priceWood;
               unit.targetMine = sources.get((new Random()).nextInt(sources.size()));
            }
         }
         // Try build fighter
         else {
            if (gold2 >= Fighter.priceGold && wood2 >= Fighter.priceWood) {
               Pt p = (pl.getCenter().y < WC.M/2)
                  ? new Pt(pl.getCenter().x-2, pl.p.y+pl.size+2)
                  : new Pt(pl.getCenter().x-2, pl.p.y-6); // Fix: WORKAROUND :)
               Fighter unit = new Fighter(p, 1, idCounter++);
               all.add(unit);
               fighters.add(unit);
               gold2 -= Fighter.priceGold;
               wood2 -= Fighter.priceWood;
               ArrayList<FlyObject> player1Units = new ArrayList<FlyObject>();
               for (FlyObject fo : all) if (fo.side == 0)
                  player1Units.add(fo);
               unit.killTarget = player1Units.get((new Random()).nextInt(player1Units.size()));
               //unit.targetMine = sources.get((new Random()).nextInt(sources.size()));
            }
         }
      }
   }
   
   public void drawWorld(Graphics2D g2) {
      for (FlyObject fo : all) {
         fo.paint(g2);
    	 if (fo.selected)
    		 g2.drawImage(selection, WC.LX+fo.p.x*WC.SZ-WC.SZ*3/2, WC.LY+fo.p.y*WC.SZ-WC.SZ*3/2, (fo.size+3)*WC.SZ, (fo.size+3)*WC.SZ, null);
      }
      g2.setColor(Color.BLACK);
      // Blue player (#1)
      g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
      g2.drawString("[ Blue player ]", WC.LX+WC.W+20, 280);
      g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
      g2.drawString("Gold: " + gold1, WC.LX+WC.W+35, 310);
      g2.drawString("Wood: " + wood1, WC.LX+WC.W+35, 335);
      // Red player (#2)
      g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
      g2.drawString("[ Red player ]", WC.LX+WC.W+20, 380);
      g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
      g2.drawString("Gold: " + gold2, WC.LX+WC.W+35, 410);
      g2.drawString("Wood: " + wood2, WC.LX+WC.W+35, 435);
      drawArrows(g2);
   }
   
   public void drawArrows(Graphics2D g2) {
      g2.setColor(new Color(0, 235, 0, 180));
      for (Harvester h : harvesters)
         if (h.targetPlanet != null && h.targetMine != null) {
            g2.drawLine(WC.LX+h.targetPlanet.getCenter().x*WC.SZ, WC.LY+h.targetPlanet.getCenter().y*WC.SZ,
                        WC.LX+h.getCenter().x*WC.SZ, WC.LY+h.getCenter().y*WC.SZ);
            g2.drawLine(WC.LX+h.getCenter().x*WC.SZ, WC.LY+h.getCenter().y*WC.SZ,
                        WC.LX+h.targetMine.getCenter().x*WC.SZ, WC.LY+h.targetMine.getCenter().y*WC.SZ);
            g2.drawLine(WC.LX+h.targetPlanet.getCenter().x*WC.SZ, WC.LY+h.targetPlanet.getCenter().y*WC.SZ,
                        WC.LX+h.targetMine.getCenter().x*WC.SZ, WC.LY+h.targetMine.getCenter().y*WC.SZ);
         }
      g2.setColor(new Color(235, 0, 0, 180));
      for (Fighter f : fighters)
         if (f.killTarget != null) {
            g2.drawLine(WC.LX+f.killTarget.getCenter().x*WC.SZ, WC.LY+f.killTarget.getCenter().y*WC.SZ,
                        WC.LX+f.getCenter().x*WC.SZ, WC.LY+f.getCenter().y*WC.SZ);
         }
   }
   
   public void generateWorld() {
      // Probability for continue is about 0
      Gen: for ( ;; ) {
      idCounter = 0;
      all.clear();
      planets.clear();
      sources.clear();
      harvesters.clear();
      fighters.clear();
      gold1 = gold2 = wood1 = wood2 = 50;
      Utils.randomShuffle(randomPoints);
         if (!generatePlanet(0, 14)) continue Gen;
         if (!generatePlanet(1, 14)) continue Gen;
      int countSource = 4;
      for (int i = 0; i < countSource; i++){
    	  if(i >= countSource/2)
    		  if (!generateSource(SourceType.WOOD)) 
    			  break;    	  
		  else if (!generateSource(SourceType.GOLD)) 
				  break;
      }
      selected = null;
         break;
      }
   }
   
   // Generate size randomly if corresponding parameter size == -1
   public boolean generatePlanet(int side, int size) {
      final int mn = 10, mx = 20, closest = 20;
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
   
   public boolean generateSource(SourceType type) {
      final int mn = 10, mx = 20, closest = 20;
      int size = mn + randomizer.nextInt(mx-mn+1);
      size -= size&1;
      Pt p = getPossiblePlace(size, closest);
      if (p != null) {
    	 int rnd = (new Random()).nextInt(1);
         Source cur = new Source(p, size, idCounter++, type, type == SourceType.GOLD ? WC.RGold * size : WC.RWood * size);
         all.add(cur);
         sources.add(cur);
         //System.out.println("Source generated: " + cur.p.toString());
         return true;
      }
      return false;
   }
   
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
      return null;
   }
}
