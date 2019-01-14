package debug.textProcesser;

import java.util.LinkedList;

public class Trie {

  Node root = new Node(this);
  Node pointer;
  LinkedList<Node> nodeSet = new LinkedList<Node>();

  // Adds a word to the Trie
  public void addWord(String s) {
    char[] arr = s.toCharArray();
    if (pointer == null) {
      pointer = root;
    }
    for (char c : arr) {
      pointer.addChild(c);
    }

    Node leaf = new Node(true, s.hashCode());
    pointer.addLeafChild(leaf);
    nodeSet.add(leaf);

  }

  // Displays the current state of the Trie
  public void display() {
    pointer = root;
    // ?
    System.out.println("Node Value: " + pointer + ", Children: " + pointer.childValues().get(0));
    for (Node n : nodeSet) {
      System.out.println();
      System.out.print("Node Value: " + n + ", Children: ");

      for (int i = 0; i < n.childValues().size(); i++) {
        System.out.println(n.childValues().get(i));
      }

      if (n.isLeaf) {
        System.out.print(", isLeaf: " + n.isLeaf + ", hashcode: " + n);
      }
    }
  }

  // Prints all the nodes in a nodeSet and displays whether or not they have a
  // child that is a leaf
  public void hasLeaf() {
    for (Node n : nodeSet) {
      if (n.isLeaf) {
        System.out.println("true");
      }
    }
  }
}
