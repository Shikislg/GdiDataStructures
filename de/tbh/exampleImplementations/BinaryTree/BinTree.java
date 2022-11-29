/**
 *
 * Sets the return methods for the Binary Tree
 *
 * @version 1.5 vom 29.10.2021
 * @author Tom Hopf
 */
public abstract class BinTree {
  public static Node root;
  public String lastOrder;
  public boolean animateOrderOutput;
  public Node[] currentLookedAt = new Node[20];
  public int foundAt;
  public Object[] allowedToPrint = new Object[20];

  public Node searchInOrder(Object key, Node curr) {
    inOrder();
    for (int i = 0; i < currentLookedAt.length; i++) {
      if (currentLookedAt[i] != null) {
        if ((float) currentLookedAt[i].getContent() == Float.parseFloat((String) key)) {
          foundAt = i;
          return currentLookedAt[i];
        }
      }
    }
    return null;
  }

  public String inOrder() {
    lastOrder = "inOrder";
    for (int i = 0; i < currentLookedAt.length; i++) {
      currentLookedAt[i] = null;
    }
    return inOrder(BinTree.root);
  }

  // Returns binary tree via the InOrder method
  public String inOrder(Node node) {
    String s = "";
    if (node != null) {
      s += inOrder(node.getLeft()) + "";
      s += round((float) node.getContent()) + " ";
      for (int i = 0; i < currentLookedAt.length; i++) {
        if (currentLookedAt[i] == null) {
          currentLookedAt[i] = node;
          break;
        }
      }
      s += inOrder(node.getRight()) + "";
    }
    return s;
  }

  public String preOrder() {
    lastOrder = "preOrder";
    for (int i = 0; i < currentLookedAt.length; i++) {
      currentLookedAt[i] = null;
    }
    return preOrder(BinTree.root);
  }

  // Returns binary tree via the PreOrder method
  public String preOrder(Node node) {
    String s = "";
    if (node != null) {
      s += round((float) node.getContent()) + " ";
      for (int i = 0; i < currentLookedAt.length; i++) {
        if (currentLookedAt[i] == null) {
          currentLookedAt[i] = node;
          break;
        }
      }
      s += preOrder(node.getLeft()) + "";
      s += preOrder(node.getRight()) + "";
    }
    return s;
  }

  public String postOrder() {
    lastOrder = "postOrder";
    for (int i = 0; i < currentLookedAt.length; i++) {
      currentLookedAt[i] = null;
    }
    return postOrder(BinTree.root);
  }

  // Returns binary tree via the PostOrder method
  public String postOrder(Node node) {
    String s = "";
    if (node != null) {
      s += postOrder(node.getLeft()) + "";
      s += postOrder(node.getRight()) + "";
      s += round((float) node.getContent()) + " ";
      for (int i = 0; i < currentLookedAt.length; i++) {
        if (currentLookedAt[i] == null) {
          currentLookedAt[i] = node;
          break;
        }
      }
    }
    return s;
  }

  // Activates animation for the Order output
  public void setAnimationActive(boolean bl) {
    animateOrderOutput = bl;
  }

  // Returns the parentNode of whichever Node's content corresponds to the given
  // key
  public Node getParent(Node node, float key) {
    if (node == null || (float) node.getContent() == key) {
      return null;
    }
    if ((node.getLeft() != null && (float) node.getLeft().getContent() == key)
        || (node.getRight() != null && (float) node.getRight().getContent() == key)) {
      return node;
    }
    Node p = getParent(node.getLeft(), key);

    if (p != null) {
      return p;
    }
    p = getParent(node.getRight(), key);
    return p;
  }

  // rounds the given float value if possible. e.g 1.0f would be rounded to 1
  public Object round(float value) {
    if (isDecimal(value)) {
      return value;
    } else {
      int returnVal = Integer.parseInt(new java.text.DecimalFormat("#").format(value));
      return returnVal;
    }
  }

  // Returns true if given Value is a decimal (such as 1.5), and false if it is
  // not (such as 1 or 1.0)
  public boolean isDecimal(float value) {
    if ((int) value == value) {
      return false;
    }
    return true;
  }

  public abstract void add(Object value);
}
