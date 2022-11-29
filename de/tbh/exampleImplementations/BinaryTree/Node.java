/**
 *
 * Class of Object Type node
 *
 * @version 1.0 vom 21.10.2021
 * @author Tom Hopf
 */
public class Node {
  Object content;
  Node left;
  Node right;
  //initially create a Node without left or right
  Node(Object val) {
      this.content = val;
      right = null;
      left = null;
  }
  //sets current Node's content to the value with which the method has been called
  public void setContent(Object val){
    this.content = val;
  }
  //returns the current Node's content
  public Object getContent(){
    return this.content;
  }
  //sets current Node's left neighbour to the Node with which the method has been called
  public void setLeft(Node node){
    left = node;
  }
  //sets current Node's right neighbour to the Node with which the method has been called
  public void setRight(Node node){
    right = node;
  }
  //return the current Node's left neighbour
  public Node getLeft(){
    return left;
  }
  //return the current Node's right neighbour
  public Node getRight(){
    return right;
  }
}
