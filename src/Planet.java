/**
 * Planet unit's wrapper  
 */
public class Planet extends FlyObject
{
   public Planet(Pt P, int Side, int Size, int Id) {
      super(P, Side, Size, Id);
   }
   
   public String toString() {
      return "planet";
   }
}
