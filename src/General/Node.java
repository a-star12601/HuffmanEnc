/**
 * Node Data Structure for Tree.
 */
public class Node {
    /**
     * Character in the node.
     */
    char Char;
    /**
     * frequency of the node.
     */
    int Freq;
    /**
     * left link.
     */
    Node Left;
    /**
     * right link.
     */
    Node Right;
    /**
     * Current height of node.
     */
    int Height;

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
    Node(char c,int f){
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
    Node(int f,Node left,Node right,int h){
        Freq=f;
        Char='\0';
        Left=left;
        Right=right;
        Height=h;
    }
}
