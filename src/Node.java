public class Node {
    char Char;
    int Freq;
    Node Left;
    Node Right;
    int Height;

    Node(){
        Char='\0';
        Freq=0;
        Left=null;
        Right=null;
        Height=0;
    }
    Node(char c,int f){
        Char=c;
        Freq=f;
        Left=null;
        Right=null;
        Height=0;
    }
    Node(int f,Node left,Node right,int h){
        Freq=f;
        Char='\0';
        Left=left;
        Right=right;
        Height=h;
    }
}
