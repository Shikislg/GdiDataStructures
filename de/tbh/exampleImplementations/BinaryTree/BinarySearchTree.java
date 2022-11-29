import java.awt.*;
import java.awt.geom.*;
import java.text.DecimalFormat;

/**
 *
 * Implements add method and generally creates the Tree
 *
 * @version 1.5 vom 29.10.2021
 * @author Tom Hopf
 */

public class BinarySearchTree extends BinTree {
  public Node curr;

  public TreeCanvas canvas;

  public indexedNode[] indexedNodes = new indexedNode[100];

  public int x, y, depth, factor;

  public boolean isEmpty = false;
  public boolean animationEnabled = true;
  public boolean animationEnabledDefault = true;
  public boolean hasPassedThrough = false;
  public boolean isOrdering = false;

  public DecimalFormat formatter = new java.text.DecimalFormat("#");

  /*
   * Add method called from other Classes such as a GUI The Method sets default
   * values for x, y and the depth before calling the real Add-Method with a
   * float-cast of the value passed to it
   */
  @Override
  public void add(Object value) {
    x = 460;
    y = 5;
    depth = 0;
    float fValue = Float.parseFloat((String) value);
    root = addRecursive(root, fValue);
  }

  /*
   * Adds the value passed by the above method. The method gets called until the
   * Node passed onto it is empty, which means it has found its spot, before then
   * it is compared to the value of the Node in its place, moving further right
   * for smaller and further left for bigger values (compared to itself)
   */
  public Node addRecursive(Node current, float fVal) {
    if (current == null) {
      curr = new Node(fVal);
      indexNode(curr);
      return curr;
    }

    y += 125;
    depth += 1;
    factor = (int) Math.pow(2, depth);
    if (depth >= 3) {
      throw new UnsupportedOperationException("Error: Cannot add more than 3 Layers to the Binary tree");
    }
    if (fVal < (float) current.getContent()) {
      x -= 460 / factor;
      current.setLeft(addRecursive(current.getLeft(), fVal));
    } else if (fVal > (float) current.getContent()) {
      x += 460 / factor;
      current.setRight(addRecursive(current.getRight(), fVal));
    } else {
      return current;
    }
    return current;
  }

  // creates a new Canvas
  public void createCanvas(boolean Order) {
    for (int i = 0; i < allowedToPrint.length; i++) {
      allowedToPrint[i] = null;
    }
    isOrdering = Order;
    for (int i = 0; i < indexedNodes.length; i++) {
      if (indexedNodes[i] != null) {
        indexedNodes[i].leftAnimated = false;
        indexedNodes[i].rightAnimated = false;
        indexedNodes[i].hasBeenOrdered = false;
      }
    }
    canvas = new TreeCanvas();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /*
   * Creates a new entry in the array of indexed Nodes, which are Nodes with 3
   * extra parameters: x, y and depth. Indexing Nodes allows for all Methods
   * following this one and also drawing the Nodes, as only an indexed Node can be
   * connected directly to it's coordinates and depth
   */
  public void indexNode(Node node) {
    for (int i = 0; i <= indexedNodes.length - 1; i++) {
      if (indexedNodes[i] == null) {
        indexedNodes[i] = new indexedNode(node, this.x, this.y, this.depth);
        break;
      }
    }
  }

  /*
   * Deletes the last Node. This is a little more complicated than one might
   * think, as you not only need to delete the Node itself, but find the parent of
   * the Node and delete the Entry of the Node to be deleted in it.
   */
  public void Undo() {
    // Deletes the Node itself
    curr = null;
    for (int i = indexedNodes.length - 1; i >= 0; i--) {
      // deletes the reference to the Node in it's parent
      if (indexedNodes[i] != null) {
        if (getParent(root, (float) indexedNodes[i].getNode().getContent()) != null) {
          if ((float) indexedNodes[i].getNode()
              .getContent() > (float) getParent(root, (float) indexedNodes[i].getNode().getContent()).getContent()) {
            Node parent = getParent(root, (float) indexedNodes[i].getNode().getContent());
            parent.setRight(null);
          } else {
            Node parent = getParent(root, (float) indexedNodes[i].getNode().getContent());
            parent.setLeft(null);
          }
        } else {
          isEmpty = true;
        }
        // deletes it's indexed reference in the Array
        indexedNodes[i] = null;
        break;
      }
    }
  }

  public void delete(Object key) {
    delete(key, root);
  }

  private void delete(Object key, Node currentNode) {
    Node node = searchInOrder(key, root);
    if (node != null) {
      if (node.getContent() == root.getContent()) {
        if (node.getLeft() == null) {
          for (int i = 0; i < indexedNodes.length; i++) {
            if (indexedNodes[i] != null) {
              if (indexedNodes[i].getNode().getContent() == node.getContent()) {
                indexedNode oldChild = null;
                for (int e = 0; e < indexedNodes.length; e++) {
                  if (indexedNodes[e] != null) {
                    if ((float) indexedNodes[e].getNode().getContent() == (float) node.getRight().getContent()) {
                      oldChild = indexedNodes[e];
                      break;
                    }
                  }
                }
                // Adjust x y and depth values
                int oldX = indexedNodes[i].x;
                int oldY = indexedNodes[i].y;
                int oldDepth = indexedNodes[i].depth;
                indexedNodes[i] = oldChild;
                indexedNodes[i].x = oldX;
                indexedNodes[i].y = oldY;
                indexedNodes[i].depth = oldDepth;
                Node testNode = indexedNodes[i].getNode();
                for (int e = 0; e < indexedNodes.length; e++) {
                  if (indexedNodes[e] != null) {
                    if (indexedNodes[e].getNode().getContent() == testNode.getRight().getContent()) {
                      indexedNodes[e].depth -= 1;
                      indexedNodes[e].y -= 125;
                      indexedNodes[e].x -= 460 / Math.pow(2, indexedNodes[e].depth + 1);
                    }
                  }
                }
                break;
              }
            }
          }
        } else if (node.getRight() == null) {
          for (int i = 0; i < indexedNodes.length; i++) {
            if (indexedNodes[i] != null) {
              if (indexedNodes[i].getNode().getContent() == node.getContent()) {
                indexedNode oldChild = null;
                for (int e = 0; e < indexedNodes.length; e++) {
                  if (indexedNodes[e] != null) {
                    if ((float) indexedNodes[e].getNode().getContent() == (float) node.getLeft().getContent()) {
                      oldChild = indexedNodes[e];
                      break;
                    }
                  }
                }
                // Adjust x y and depth values
                int oldX = indexedNodes[i].x;
                int oldY = indexedNodes[i].y;
                int oldDepth = indexedNodes[i].depth;
                indexedNodes[i] = oldChild;
                indexedNodes[i].x = oldX;
                indexedNodes[i].y = oldY;
                indexedNodes[i].depth = oldDepth;
                Node testNode = indexedNodes[i].getNode();
                for (int e = 0; e < indexedNodes.length; e++) {
                  if (indexedNodes[e] != null) {
                    if (indexedNodes[e].getNode().getContent() == testNode.getLeft().getContent()) {
                      indexedNodes[e].depth -= 1;
                      indexedNodes[e].y -= 125;
                      indexedNodes[e].x += 460 / Math.pow(2, indexedNodes[e].depth + 1);
                    }
                  }
                }
                break;
              }
            }
          }
        } else {
          searchInOrder(key, root);
          for (int e = 0; e < indexedNodes.length; e++) {
            if (indexedNodes[e] != null) {
              if (indexedNodes[e].getNode().getContent() == currentLookedAt[foundAt + 1].getContent()) {
                indexedNodes[e] = null;
              }
            }
          }
          Node oldLeft = indexedNodes[0].getNode().getLeft();
          indexedNodes[0].corrNode = currentLookedAt[foundAt + 1];
          indexedNodes[0].corrNode.setLeft(oldLeft);
          Node parent = getParent(root, (float)currentLookedAt[foundAt+1].getContent());
          indexedNodes[0].corrNode.setRight(parent);
          parent.setLeft(null);
          node.setContent(indexedNodes[0].getNode().getContent());
          node.setLeft(indexedNodes[0].getNode().getLeft());
          node.setRight(indexedNodes[0].getNode().getRight());
        }

      } else if (node.getLeft() == null && node.getRight() == null) {
        // Deletes all references of Node
        for (int i = 0; i < indexedNodes.length; i++) {
          if (indexedNodes[i] != null) {
            if (indexedNodes[i].getNode().getContent() == node.getContent()) {
              if (getParent(root, (float) indexedNodes[i].getNode().getContent()) != null) {
                if ((float) indexedNodes[i].getNode()
                    .getContent() > (float) getParent(root, (float) indexedNodes[i].getNode().getContent())
                        .getContent()) {
                  Node parent = getParent(root, (float) indexedNodes[i].getNode().getContent());
                  parent.setRight(null);
                } else {
                  Node parent = getParent(root, (float) indexedNodes[i].getNode().getContent());
                  parent.setLeft(null);
                }
              } else {
                isEmpty = true;
              }
              // deletes it's indexed reference in the Array
              indexedNodes[i] = null;
              break;
            }
          }
        }
        node = null;
      } else if (node.getLeft() == null && node.getRight() != null
          || node.getLeft() != null && node.getRight() == null) {
        Node parent = getParent(root, (float) node.getContent());
        if (node.getLeft() == null) {
          parent.setRight(node.getRight());
          // Deletes all references of Node
          for (int i = 0; i < indexedNodes.length; i++) {
            if (indexedNodes[i] != null) {
              if (indexedNodes[i].getNode().getContent() == node.getContent()) {
                if (getParent(root, (float) indexedNodes[i].getNode().getContent()) != null) {
                  Node nodeParent = getParent(root, (float) indexedNodes[i].getNode().getContent());
                  nodeParent.setRight(node.getRight());
                } else {
                  isEmpty = true;
                }
                // deletes it's indexed reference in the Array
                indexedNode oldChild = null;
                for (int e = 0; e < indexedNodes.length; e++) {
                  if (indexedNodes[e] != null) {
                    if ((float) indexedNodes[e].getNode().getContent() == (float) node.getRight().getContent()) {
                      oldChild = indexedNodes[e];
                      break;
                    }
                  }
                }
                int oldX = indexedNodes[i].x;
                int oldY = indexedNodes[i].y;
                int oldDepth = indexedNodes[i].depth;
                indexedNodes[i] = oldChild;
                indexedNodes[i].x = oldX;
                indexedNodes[i].y = oldY;
                indexedNodes[i].depth = oldDepth;
                System.out.println(indexedNodes[i].getNode().getContent());
                break;
              }
            }
          }
        } else {
          parent.setLeft(node.getLeft());
          // Deletes all references of Node
          for (int i = 0; i < indexedNodes.length; i++) {
            if (indexedNodes[i] != null) {
              if (indexedNodes[i].getNode().getContent() == node.getContent()) {
                if (getParent(root, (float) indexedNodes[i].getNode().getContent()) != null) {
                  Node nodeParent = getParent(root, (float) indexedNodes[i].getNode().getContent());
                  nodeParent.setLeft(node.getRight());
                } else {
                  isEmpty = true;
                }
                // deletes it's indexed reference in the Array
                indexedNode oldChild = null;
                for (int e = 0; e < indexedNodes.length; e++) {
                  if (indexedNodes[e] != null) {
                    if ((float) indexedNodes[e].getNode().getContent() == (float) node.getLeft().getContent()) {
                      oldChild = indexedNodes[e];
                      break;
                    }
                  }
                }
                int oldX = indexedNodes[i].x;
                int oldY = indexedNodes[i].y;
                int oldDepth = indexedNodes[i].depth;
                indexedNodes[i] = oldChild;
                indexedNodes[i].x = oldX;
                indexedNodes[i].y = oldY;
                indexedNodes[i].depth = oldDepth;
                break;
              }
            }
          }
        }
      }
    }
  }

  public int arrayLength(Object[] array) {
    int length = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] != null) {
        length++;
      }
    }
    return length;
  }

  /*
   * Defines the type indexedNode.
   */
  public class indexedNode {
    Node corrNode;
    int x;
    int y;
    int depth;
    boolean leftAnimated = false;
    boolean rightAnimated = false;
    boolean hasBeenOrdered = false;

    public indexedNode(Node node, int x, int y, int depth) {
      this.corrNode = node;
      this.x = x;
      this.y = y;
      this.depth = depth;
    }

    public Node getNode() {
      return corrNode;
    }
  }

  /*
   * Creates Canvas
   */
  public class TreeCanvas extends Canvas {
    @Override
    public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      // Set font
      Font font = new Font("Arial Black", Font.BOLD, 20);
      g.setFont(font);

      // The Graphical representation of the Node has to be drawn seperately for each
      // Node
      if (isOrdering && !indexedNodes[0].hasBeenOrdered) {
        for (int e = 0; e < currentLookedAt.length; e++) {
          if (currentLookedAt[e] != null) {
            for (int i = 0; i <= indexedNodes.length - 1; i++) {
              if (indexedNodes[i] != null && !indexedNodes[i].hasBeenOrdered) {
                // Draw outline
                if (indexedNodes[i].getNode().getContent() == currentLookedAt[e].getContent()) {
                  for (int o = 0; o < allowedToPrint.length; o++) {
                    if (allowedToPrint[o] == null) {
                      allowedToPrint[o] = indexedNodes[i].getNode().getContent();

                      break;
                    }
                  }
                  new ScriptedOval(indexedNodes[i], g, Color.red);
                } else {
                  new ScriptedOval(indexedNodes[i], g, Color.white);
                }

                g2d.setColor(Color.white);
                if (indexedNodes[i].leftAnimated && indexedNodes[i].rightAnimated || !animationEnabledDefault
                    || isOrdering) {
                  animationEnabled = false;
                } else {
                  animationEnabled = animationEnabledDefault;
                }
                if (animationEnabled && !isOrdering && !indexedNodes[i].leftAnimated
                    || animationEnabled && !indexedNodes[i].rightAnimated) {
                  animateLines(i, g2d, 2);
                } else {
                  drawLines(i, g2d);
                }
              }
            }
            try {
              Thread.sleep(1000);
            } catch (Exception exce) {
              System.out.println(exce);
            }
          }
        }
        for (int i = 0; i < indexedNodes.length; i++) {
          if (indexedNodes[i] != null) {
            indexedNodes[i].hasBeenOrdered = true;
          }
        }
      } else {
        for (int i = 0; i <= indexedNodes.length - 1; i++) {
          if (indexedNodes[i] != null) {
            // Draw outline
            new ScriptedOval(indexedNodes[i], g, Color.white);

            if (indexedNodes[i].leftAnimated && indexedNodes[i].rightAnimated || !animationEnabledDefault
                || isOrdering) {
              animationEnabled = false;
            } else {
              animationEnabled = animationEnabledDefault;
            }
            if (animationEnabled && !isOrdering && !indexedNodes[i].leftAnimated
                || animationEnabled && !indexedNodes[i].rightAnimated) {
              animateLines(i, g2d, 2);
            } else {
              drawLines(i, g2d);
            }
          }
        }
      }
    }

    public void animateLines(int i, Graphics2D g2d, int speed) {
      if (indexedNodes[i].getNode().getRight() != null && !indexedNodes[i].rightAnimated) {
        indexedNodes[i].rightAnimated = true;
        double yIncrease = 0;
        int difference = (indexedNodes[i].x + (int) (460 / Math.pow(2, indexedNodes[i].depth + 1)) + 5)
            - indexedNodes[i].x + 45;
        for (double e = indexedNodes[i].x + 47.5; e <= indexedNodes[i].x
            + (int) (460 / Math.pow(2, indexedNodes[i].depth + 1))
            + 5; e += 2 * (speed / (indexedNodes[i].depth + 1))) {

          if (indexedNodes[i].depth + 1 >= 2) {
            yIncrease += (((double) 117 / (double) difference) * 4) * (speed / (indexedNodes[i].depth + 1));
          } else {
            yIncrease += (((double) 77 / (double) difference) * 4) * (speed / (indexedNodes[i].depth + 1));
          }
          Line2D line = new Line2D.Double((double) indexedNodes[i].x + 47.5, (double) indexedNodes[i].y + 37.5,
              (double) e, (double) indexedNodes[i].y + 35 + yIncrease);
          g2d.draw(line);
          try {
            Thread.sleep(10);
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          }
        }
      }
      if (indexedNodes[i].getNode().getLeft() != null && !indexedNodes[i].leftAnimated) {
        indexedNodes[i].leftAnimated = true;
        double yIncrease = 0;
        int difference = (indexedNodes[i].x + (int) (460 / Math.pow(2, indexedNodes[i].depth + 1)) + 5)
            - indexedNodes[i].x + 45;
        for (double e = indexedNodes[i].x + 2.5; e >= indexedNodes[i].x
            - (int) (460 / Math.pow(2, indexedNodes[i].depth + 1))
            + 45; e -= 2 * (speed / (indexedNodes[i].depth + 1))) {

          if (indexedNodes[i].depth + 1 >= 2) {
            yIncrease += (((double) 117 / (double) difference) * 4) * (speed / (indexedNodes[i].depth + 1));
          } else {
            yIncrease += (((double) 77 / (double) difference) * 4) * (speed / (indexedNodes[i].depth + 1));
          }

          Line2D line = new Line2D.Double((double) indexedNodes[i].x + 2.5, (double) indexedNodes[i].y + 37.5,
              (double) e, (double) indexedNodes[i].y + 35 + yIncrease);
          g2d.draw(line);
          try {
            Thread.sleep(10);
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          }
        }
      }
      if (indexedNodes[i].rightAnimated && indexedNodes[i].leftAnimated
          || !indexedNodes[i].rightAnimated && indexedNodes[i].getNode().getRight() == null
          || !indexedNodes[i].leftAnimated && indexedNodes[i].getNode().getLeft() == null) {
        drawLines(i, g2d);
      }
    }

    public void drawLines(int i, Graphics2D g2d) {
      if (indexedNodes[i].getNode().getRight() != null) {
        Line2D line = new Line2D.Double((double) indexedNodes[i].x + 47.5, (double) indexedNodes[i].y + 37.5,
            indexedNodes[i].x + (int) (460 / Math.pow(2, indexedNodes[i].depth + 1)) + 5, indexedNodes[i].y + 140);
        g2d.draw(line);
      }
      if (indexedNodes[i].getNode().getLeft() != null) {
        Line2D line = new Line2D.Double((double) indexedNodes[i].x + 2.5, (double) indexedNodes[i].y + 37.5,
            indexedNodes[i].x - (int) (460 / Math.pow(2, indexedNodes[i].depth + 1)) + 45, indexedNodes[i].y + 140);
        g2d.draw(line);
      }
    }
  }
} // end of class BinarySearchTree