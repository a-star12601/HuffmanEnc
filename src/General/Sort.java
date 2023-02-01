package General;

import General.Node;

import java.util.Comparator;

/**
 * Comparator to sort the tree nodes.
 */
public class Sort implements Comparator<Node> {
    public int compare(Node x, Node y){
        if(x.Freq-y.Freq!=0)
            return x.Freq-y.Freq;
        else if(x.Height-y.Height!=0)
            return x.Height-y.Height;
        else return x.Char-y.Char;
    }
}