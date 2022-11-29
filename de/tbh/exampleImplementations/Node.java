package de.tbh.exampleImplementations;
public class Node
{
    int mContent;
    Node mNext;

    public Node()
    {
        mContent = 0;
    }
    public Node(int pContent)
    {
        mContent = pContent;
    }
    public int getContent(){
        return mContent;
    }
    public void setContent(int pContent){
        mContent = pContent;
    }
    public Node getNext(){
        return mNext;
    }
    public void setNext(Node pNext){
        mNext = pNext;
    }
}