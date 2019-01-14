package debug.textProcesser;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedList;

public class Node {

  //Trie that the current node belongs to
  Trie t;

  //Linked list of characters
  LinkedList<Character> c = new LinkedList<>();

  //All of the nodes who are children of this node
  LinkedList<Node> children = new LinkedList<>();

  //Boolean storing whether or not this node is a leaf
  boolean isLeaf = false;

  //Value of the node if it is a leaf
  // 此处由于不明确Node(boolean b, int lv)是否可以删除 所以不能删掉此处未用代码
  // 而findbugs会将未使用的代码作为错误 故将其忽略
  @SuppressFBWarnings("URF_UNREAD_FIELD")
  int leafValue = 0;


  public Node(boolean b, int lv) {
    isLeaf = b;
    leafValue = lv;
  }

  public Node(Trie tv) {
    t = tv;
  }

  public Node(char cv, Trie tv) {
    c.add(cv);
    t = tv;
  }

  public void addChild(char c) {
    if (t.pointer.childValues().contains(c)) {
      //Check all the children of the currently pointed at node (root at start)
      for (Node n : children) {
        //Check if currently pointed to node already has a child whose value is c
        //if so, then shift the pointer to that node
        if (n.c.contains(c)) {
          t.pointer = n;
        }
      }
    } else {
      //Otherwise add a new child to that node and set the pointer to that node
      Node n = new Node(c, t.pointer.getTrie());
      t.pointer.children.add(n);
      t.nodeSet.add(n);
      t.pointer = n;
    }
  }

  //Method to add a leaf to the tree when a leaf node is required
  public void addLeafChild(Node n) {
    children.add(n);
  }

  //Getter method for the trie this node currently belongs to
  public Trie getTrie() {
    return this.t;
  }

  //Method that returns a linked list of characters containing all the values of
  //of the children of the node
  public LinkedList<Character> childValues() {
    LinkedList<Character> l = new LinkedList<>();
    for (Node n : children) {
      if (n.isLeaf) {
        l.add('*');
      } else {
        l.add(n.c.getFirst());
      }
    }
    return l;
  }

}
