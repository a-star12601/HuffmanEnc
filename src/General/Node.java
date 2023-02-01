package General;

/**
 * General.Node Data Structure for Tree.
 */
public class Node {
    /**
     * Character in the node.
     */
    public char Char;
    /**
     * frequency of the node.
     */
    public int Freq;
    /**
     * left link.
     */
    public Node Left;
    /**
     * right link.
     */
    public Node Right;
    /**
     * Current height of node.
     */
    public int Height;

    /**
     * Constructor for empty node.
     */
    Node(){
        Char='\0';
        Freq=0;
        Left=null;
        Right=null;
        Height=0;
    }

    /**
     * Constructor for leaf node.
     *
     * @param c the character
     * @param f the frequency
     */
    public Node(char c,int f){
        Char=c;
        Freq=f;
        Left=null;
        Right=null;
        Height=0;
    }

    /**
     * Constructor for an intermediate node.
     *
     * @param f     the frequency
     * @param left  the left link
     * @param right the right link
     * @param h     the height
     */
    public Node(int f,Node left,Node right,int h){
        Freq=f;
        Char='\0';
        Left=left;
        Right=right;
        Height=h;
    }
}
