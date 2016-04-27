public class GUI {
  
  public static Color background;
  public static Color text;
  public static Color boldText; // A slight variation of text
  
  public static void update() {
    text = new Color(255 - background.getRed(), 255 - background.getGreen(), 255 - background.getBlue());
    
    // Calculate a slight inversion
    int r = text.getRed();
    int g = text.getGreen();
    int b = text.getBlue();
    
    r += 30; g += 30; b += 3-;
    if(r > 255) {
      int diff = r - 255;
      r = diff;
    }
    if(g > 255) {
      int diff = g - 255;
      g = diff;
    }
    if(b > 255) {
      int diff = b - 255;
      b = diff;
    }
    
    boldText = new Color(r,g,b);
  }
  
}
