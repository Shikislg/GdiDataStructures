import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 *
 * Implements the GUI for the Application, with integrated visualization
 *
 * @version 1.5 vom 29.10.2021
 * @author Tom Hopf
 */

public class BinTreeGUI extends JFrame implements ActionListener, KeyListener, ItemListener {
  // Anfang Attribute
  JPanel cp = new JPanel(null);

  BinarySearchTree tree = new BinarySearchTree();
  Label header = new Label("Binary Tree Creator");
  Label visualizationHeader = new Label("Visualization of Binary Tree");
  Label outputLabel = new Label("");
  Label animationLabel = new Label("Enable Animations");

  JButton inOrderbtn = new RoundButton("Print in Order");
  JButton preOrderbtn = new RoundButton("Print pre Order");
  JButton postOrderbtn = new RoundButton("Print post Order");
  JButton addbtn = new RoundButton("Add Node");
  JButton delbtn = new RoundButton("Delete Node");
  JButton resetbtn = new RoundButton("Reset Tree");
  JButton undobtn = new RoundButton("Undo Last Action");

  JTextField inputtf = new JTextField("");

  JCheckBox enableAnimation = new JCheckBox();

  int frameWidth = 1000;
  int frameHeight = 1050;

  Color darkgrey = new Color(20, 20, 20);
  public Font font;
  // Ende Attribute

  public BinTreeGUI() {
    // Frame-Initialisierung
    super();
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        dispose();
      }
    });
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Binary Tree Visualization");
    setResizable(false);

    add(cp);
    cp.setBackground(darkgrey);
    setFonts();
    setBounds(2);
    addToFrame();
    // Anfang Komponenten

    // Set Limit for inputfield
    inputtf.setDocument(new LimitedTextField(3));

    // Set Default for Checkbox to checked
    enableAnimation.setSelected(true);
    enableAnimation.setOpaque(false);
    enableAnimation.setBackground(darkgrey);
    enableAnimation.setForeground(Color.white);
    addListeners();
    // Ende Komponenten

    setVisible(true);
  } // end of public BinTreeGUI

  // Init methods
  /*
   * Sets Fonts
   */
  private void setFonts() {
    Font font = new Font("Arial Black", Font.BOLD, 16);
    Font largeFont = new Font("Arial Black", Font.BOLD, 24);
    cp.setForeground(Color.white);
    header.setFont(largeFont);
    visualizationHeader.setFont(largeFont);
    outputLabel.setFont(largeFont);
    animationLabel.setFont(font);

    inputtf.setFont(largeFont);
    inputtf.setForeground(Color.black);

    inOrderbtn.setFont(font);
    inOrderbtn.setForeground(Color.white);
    preOrderbtn.setFont(font);
    preOrderbtn.setForeground(Color.white);
    postOrderbtn.setFont(font);
    postOrderbtn.setForeground(Color.white);
    addbtn.setFont(font);
    addbtn.setForeground(Color.white);
    delbtn.setFont(font);
    delbtn.setForeground(Color.white);
    resetbtn.setFont(font);
    resetbtn.setForeground(Color.white);
    undobtn.setFont(font);
    undobtn.setForeground(Color.white);

  }

  /*
   * Sets Bounds
   */
  private void setBounds(int scale) {
    header.setBounds(195 * scale, 0 * scale, 150 * scale, 30 * scale);
    visualizationHeader.setBounds(165 * scale, 265 * scale, 225 * scale, 30 * scale);
    inputtf.setBounds(30 * scale, 50 * scale, 125 * scale, 30 * scale);
    addbtn.setBounds(180 * scale, 50 * scale, 125 * scale, 30 * scale);
    delbtn.setBounds(330 * scale, 50 * scale, 125 * scale, 30 * scale);
    inOrderbtn.setBounds(5 * scale, 100 * scale, 150 * scale, 30 * scale);
    preOrderbtn.setBounds(172 * scale, 100 * scale, 150 * scale, 30 * scale);
    postOrderbtn.setBounds(339 * scale, 100 * scale, 150 * scale, 30 * scale);
    outputLabel.setBounds(0 * scale, 150 * scale, 500 * scale, 30 * scale);
    resetbtn.setBounds(60 * scale, 200 * scale, 150 * scale, 30 * scale);
    undobtn.setBounds(260 * scale, 200 * scale, 150 * scale, 30 * scale);
    animationLabel.setBounds(15 * scale, 485 * scale, 150 * scale, 30 * scale);
    enableAnimation.setBounds(5 * scale, 494 * scale, 11 * scale, 11 * scale);
    enableAnimation.setBackground(darkgrey);
  }

  /*
   * Adds Key and Button-Press-listeners
   */
  private void addListeners() {
    inputtf.addKeyListener(this);
    addbtn.addActionListener(this);
    delbtn.addActionListener(this);
    inOrderbtn.addActionListener(this);
    preOrderbtn.addActionListener(this);
    postOrderbtn.addActionListener(this);
    resetbtn.addActionListener(this);
    undobtn.addActionListener(this);
    enableAnimation.addItemListener(this);
  }

  /*
   * Adds the Objects to the Main Frame
   */
  private void addToFrame() {
    cp.add(header);
    cp.add(visualizationHeader);
    cp.add(inputtf);
    cp.add(addbtn);
    cp.add(delbtn);
    cp.add(inOrderbtn);
    cp.add(preOrderbtn);
    cp.add(postOrderbtn);
    cp.add(outputLabel);
    cp.add(resetbtn);
    cp.add(undobtn);
    cp.add(animationLabel);
    cp.add(enableAnimation);
  }

  // Other methods
  /*
   * adds a value to the binary Tree
   */
  public void add() {
    String input = inputtf.getText();
    if (input.isEmpty() == true) {
      throw new UnsupportedOperationException("Error: Cannot add empty Object");
    } else {
      try {
        float fInput = Float.parseFloat(input);
        System.out.print(fInput+" ");
      } catch (Exception e) {
        inputtf.setText("");
        throw new UnsupportedOperationException(
            "Error: Cannot add specified Input. Check for Letters and other special Characacters");
      }
      tree.add(input);
      inputtf.setText("");
    }
  }

  /*
   * resets all values of the tree
   */
  public void resetTree() {
    // sets all values to null
    BinTree.root = null;
    for (int i = 0; i <= tree.indexedNodes.length - 1; i++) {
      tree.indexedNodes[i] = null;
    }
    outputLabel.setText(null);
    // draws the Tree again, since all values are empty this clears out the canvas
    redrawTree(tree.animationEnabledDefault);
  }

  /*
   * draws the tree from scratch
   */
  private void redrawTree(boolean Order) {
    if (tree.canvas != null) {
      cp.remove(tree.canvas);
    }
    tree.createCanvas(Order);
    tree.canvas.setBounds(0, 600, 1000, 375);
    cp.add(tree.canvas);
  }
  /*
   * private void fakeTree(){ tree.add(40); tree.add(20); tree.add(60);
   * tree.add(10); tree.add(30); tree.add(50); tree.add(70); tree.add(5);
   * tree.add(15); tree.add(25); tree.add(35); tree.add(45); tree.add(55);
   * tree.add(65); tree.add(75); }
   */

  /*
   * All Events happening on KeyPress
   */
  public void keyPressed(KeyEvent e) {
    // Enter pressed
    if (e.getKeyChar() == 10) {
      add();
      redrawTree(false);
    }
    // Espace pressed
    if (e.getKeyChar() == 27) {
      resetTree();
    }
    // Delete Pressed
    if (e.getKeyChar() == 127) {
      tree.Undo();
      if (tree.isEmpty) {
        resetTree();
        tree.isEmpty = false;
      }
      if (outputLabel.getText() != "") {
        if (tree.lastOrder == "inOrder") {
          outputLabel.setText(tree.inOrder());
        } else if (tree.lastOrder == "preOrder") {
          outputLabel.setText(tree.preOrder());
        } else {
          outputLabel.setText(tree.postOrder());
        }
      }
      redrawTree(false);
    }
  }

  /*
   * All Events happening on ItemEvent change
   */
  public void itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == ItemEvent.DESELECTED) {
      tree.animationEnabledDefault = false;
      tree.animationEnabled = tree.animationEnabledDefault;
    } else {
      tree.animationEnabledDefault = true;
      tree.animationEnabled = tree.animationEnabledDefault;
    }
  }

  /*
   * All Events happening on Buttonpress
   */
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == addbtn) {
      add();
      redrawTree(false);
    }

    if(ae.getSource() == delbtn){
      try{
        tree.delete(inputtf.getText());
      }catch(Exception e){
        System.out.println(e);
      }
      redrawTree(false);
    }

    if (ae.getSource() == inOrderbtn) {
      if (tree.indexedNodes[0] == null) {
        throw new UnsupportedOperationException("Error: Cannot output Binary Tree at this time. Is it empty?");
      }
      try {
        for (int i = 0; i < tree.allowedToPrint.length; i++) {
          if (tree.allowedToPrint[i] != null) {
            tree.allowedToPrint[i] = null;
          }
          if (tree.currentLookedAt[i] != null) {
            tree.currentLookedAt[i] = null;
          }
        }
        outputLabel.setText("");
        tree.inOrder();
        DrawingThread drawing = new DrawingThread();
        drawing.start();
        LabelingThread labeling = new LabelingThread();
        labeling.start();
      } catch (Exception e) {
        throw new UnsupportedOperationException("Error: Cannot output Binary Tree at this time. Is it empty?");
      }
    }

    if (ae.getSource() == preOrderbtn) {
      if (tree.indexedNodes[0] == null) {
        throw new UnsupportedOperationException("Error: Cannot output Binary Tree at this time. Is it empty?");
      }
      for (int i = 0; i < tree.allowedToPrint.length; i++) {
        if (tree.allowedToPrint[i] != null) {
          tree.allowedToPrint[i] = null;
        }
        if (tree.currentLookedAt[i] != null) {
          tree.currentLookedAt[i] = null;
        }
      }
      outputLabel.setText("");
      tree.preOrder();
      DrawingThread drawing = new DrawingThread();
      drawing.start();
      LabelingThread labeling = new LabelingThread();
      labeling.start();

    }

    if (ae.getSource() == postOrderbtn) {
      if (tree.indexedNodes[0] == null) {
        throw new UnsupportedOperationException("Error: Cannot output Binary Tree at this time. Is it empty?");
      }
      for (int i = 0; i < tree.allowedToPrint.length; i++) {
        if (tree.allowedToPrint[i] != null) {
          tree.allowedToPrint[i] = null;
        }
        if (tree.currentLookedAt[i] != null) {
          tree.currentLookedAt[i] = null;
        }
      }
      outputLabel.setText("");
      tree.postOrder();
      DrawingThread drawing = new DrawingThread();
      drawing.start();
      LabelingThread labeling = new LabelingThread();
      labeling.start();
    }
    if (ae.getSource() == resetbtn) {
      resetTree();
      redrawTree(false);
    }
    if (ae.getSource() == undobtn) {
      tree.Undo();
      if (tree.isEmpty) {
        resetTree();
        tree.isEmpty = false;
      }
      if (outputLabel.getText() != "") {
        if (tree.lastOrder == "inOrder") {
          outputLabel.setText(tree.inOrder());
        } else if (tree.lastOrder == "preOrder") {
          outputLabel.setText(tree.preOrder());
        } else {
          outputLabel.setText(tree.postOrder());
        }
      }
      redrawTree(false);
    }
  }

  /*
   * Only so java doesn't get mad at me (They have to be existent as the GUI is
   * implementing KeyListener, which comes with these abstract methods)
   */
  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }

  public static void main(String[] args) {
    new BinTreeGUI();
  }

  public class LabelingThread extends Thread {
    @Override
    public void run() {
      while (tree.arrayLength(tree.allowedToPrint) < tree.arrayLength(tree.currentLookedAt)) {
        String testText = outputLabel.getText();
        outputLabel.setText("");
        for (int i = 0; i < tree.allowedToPrint.length; i++) {
          if (tree.allowedToPrint[i] != null) {
            outputLabel.setText(outputLabel.getText() + " " + tree.round((float) tree.allowedToPrint[i]));
          }
        }
        if (testText != outputLabel.getText()) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      outputLabel.setText("");
      for(int i =0;i<tree.allowedToPrint.length;i++){
        if(tree.allowedToPrint[i]!=null){
          outputLabel.setText(outputLabel.getText()+" "+tree.round((float)tree.allowedToPrint[i]));
        }
      }
    }
  }

  public class DrawingThread extends Thread {
    @Override
    public void run() {
      redrawTree(true);
    }
  }
} // end of class BinTreeGUI