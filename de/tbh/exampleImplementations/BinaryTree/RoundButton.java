import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
 
public class RoundButton extends JButton {
 
  public RoundButton(String label) {
    super(label);
 
    setBackground(Color.lightGray);
    setFocusable(false);
 
    /*
     These statements enlarge the button so that it 
     becomes a circle rather than an oval.
    */
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
 
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
    setContentAreaFilled(false);
  }
 
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    if (getModel().isArmed()) {
      g2d.setColor(Color.gray);
    } else {
      g2d.setColor(getBackground());
    }
    g2d.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
 
    super.paintComponent(g);
  }
 
  protected void paintBorder(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g.setColor(Color.white);
    g2d.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
  }
 
  // Hit detection.
  Shape shape;
 
  public boolean contains(int x, int y) {
    // If the button has changed size,  make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Rectangle2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
}