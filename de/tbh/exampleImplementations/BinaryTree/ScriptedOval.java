import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;

public class ScriptedOval extends BinarySearchTree {
    public ScriptedOval(indexedNode indexedNode, Graphics g, Color prefCol) {
        // Outline
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D outer = new Ellipse2D.Double(indexedNode.x, indexedNode.y, 50, 50);
        Ellipse2D inner = new Ellipse2D.Double(indexedNode.x + 2.5, indexedNode.y + 2.5, 45, 45);

        Area area = new Area(outer);
        area.subtract(new Area(inner));
        g2d.setColor(prefCol);
        g2d.fill(area);
        g2d.draw(area);

        // Text
        int length = 0;
        String toBePrinted = "";

        if (!isDecimal((float) indexedNode.getNode().getContent())) {
            length = String.valueOf(formatter.format(indexedNode.getNode().getContent())).length();
            toBePrinted = formatter.format(indexedNode.getNode().getContent());
        } else {
            length = String.valueOf(indexedNode.getNode().getContent()).length();
            toBePrinted = "" + (float) indexedNode.getNode().getContent();
        }
        // Draw the text accordingly
        if (length == 1) {
            g.drawString(toBePrinted, indexedNode.x + 18, indexedNode.y + 33);
        } else if (length == 2) {
            g.drawString(toBePrinted, indexedNode.x + 11, indexedNode.y + 33);
        } else if (length >= 2 && toBePrinted.contains(".")) {
            g.drawString(toBePrinted, indexedNode.x + 8, indexedNode.y + 33);
        } else {
            g.drawString(toBePrinted, indexedNode.x + 5, indexedNode.y + 33);
        }
    }
}
