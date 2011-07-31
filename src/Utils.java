import java.util.*;

public class Utils
{
   static Random randomizer = new Random(); 
   
   public static void randomShuffle(LinkedList<Pt> o) {
      int n = o.size();
      for (int i = 0; i < n-1; i++) {
         int j = i+1+randomizer.nextInt(n-i-1);
         Pt nxt = o.get(j);
         o.set(j, o.get(i));
         o.set(i, nxt);
      }
   }
   
   // Returns Euclidean point-point distance  
   public static double getDistance(Pt a, Pt b) {
      return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
      //return Math.hypot(a.x-b.x, a.y-b.y);
   }
   
// Returns Euclidean point-point distance  
   public static double getDistance(FlyObject a, FlyObject b) {
      return getDistance(a.getCenter(), b.getCenter()) - a.size/2. - b.size/2.;
   }
}
