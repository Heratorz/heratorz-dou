import java.util.*;
import java.awt.*;

/**
 * Playing world environment's wrapper   
 */
public class WorldEnv 
{
   LinkedList<FlyObject> all;
   LinkedList<Planet> planets;
   LinkedList<Pt> randomPoints; // random shuffled coords
   Random randomizer = new Random();
   
   public WorldEnv() {
      all = new LinkedList<FlyObject>();
      planets = new LinkedList<Planet>();
      randomPoints = new LinkedList<Pt>();
      for (int i = 0; i < WC.N; i++)
         for (int j = 0; j < WC.M; j++)
            randomPoints.add(new Pt(i, j));
      System.out.println(randomPoints.size());
      if (!generateWorld())
         KissMyAsser.errorFound();
   }
   
   public void drawWorld(Graphics2D g2) {
      for (FlyObject fo : all)
         fo.paint(g2);
   }
   
   public boolean generateWorld() {
      all.clear();
      planets.clear();
      Utils.randomShuffle(randomPoints);
      for (int i = 0; i < 4; i++)
         if (!generatePlanet(i%3))
            return false;
      System.out.println("=====================");
      //TODO full shit
      generateX(0);
      return true;
   }
   
   public boolean generatePlanet(int side) {
      final int mn = 10, mx = 20, closest = 10;
      int size = mn + randomizer.nextInt(mx-mn+1);
      size -= size&1;
      Pt p = getPossiblePlace(size, closest);
      if (p != null) {
         Planet cur = new Planet(p, side, size);
         all.add(cur);         
         planets.add(cur);
         System.out.println("Planet generated: " + cur.p.toString());
         return true;
      }
      return false;
   }
   
   //TODO full shit
   public boolean generateX(int side) {
	   Harvester f = new Harvester(new Pt(5, 5), side, 2, (Planet)all.get(0));
	   all.add(f);		   
	   return true;
   }
   
   public Pt getPossiblePlace(int size, double minDistPossible) {
      for (Pt p : randomPoints) {
         if (p.x+size >= WC.N || p.y+size >= WC.M) continue;
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
      Pt p = new Pt(0, 0);
      Pt center = new Pt(p.x+size/2, p.y+size/2);
      System.out.println(size);
      System.out.println(center.toString());
      for (FlyObject obj : all)
         System.out.println("-> " + (Utils.getDistance(center, obj.getCenter())-size/2.-obj.size/2.));
      KissMyAsser.errorFound();
      return null;
   }
}
