import javax.swing.*;

/**
 * Application starts here...
 */
public class Main
{
   public static void main(String[] args) throws Exception {
      System.out.println("Hello hackaton!");
      HackFrame frame = new HackFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
