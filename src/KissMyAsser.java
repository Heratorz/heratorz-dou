import javax.swing.*;

/**
 * Losers come here...
 */
public class KissMyAsser
{
   public static void errorFound() {
      JOptionPane.showMessageDialog(null, "GPF!");
      (new Exception()).printStackTrace();
   }
}
