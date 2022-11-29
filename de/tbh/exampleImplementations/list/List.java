package de.tbh.exampleImplementations.list;

import de.tbh.exampleImplementations.Node;
public class List
{
    int mSize;
	Node mStartNode;
	public List()
	{
        mSize = 0;
		mStartNode = null;
	}
	public List(int pContent)
    {
        mSize = 1;
		mStartNode = new Node(pContent); 
    }
    public int get(int position) throws NullPointerException{
        Node curNode = mStartNode;
        while(position > 0){
            try{
                curNode = curNode.getNext();
            }catch(NullPointerException ne){
                throw ne;
            }
            position--;
        }
        return curNode.getContent();
    }
    public Node getNode(int position) throws NullPointerException{
        Node curNode = mStartNode;
        while(position > 0){
            try{
                curNode = curNode.getNext();
            }catch(NullPointerException ne){
                throw ne;
            }
            position--;
        }
        return curNode;
    }
	public void insert(int pContent)
    {
		
        if (mStartNode == null) {
            mStartNode = new Node(pContent);
        }
        else {
            Node currNode = mStartNode;
            while (currNode.getNext() != null) {
                currNode = currNode.getNext();
            }
   
            currNode.setNext(new Node(pContent));
        }
        mSize++;
    }
	public void insert(int pContent, int position)
    {
		
        if (mStartNode == null) {
            mStartNode = new Node(pContent);
        }
        else {
            Node predecessor = getNode(position-1);
            Node insertedNode = new Node(pContent);
            Node successor = predecessor.getNext();
            predecessor.setNext(insertedNode);
            insertedNode.setNext(successor);

        }
        mSize++;
    }
    public void remove(int position){
        if(position == 0){
            mStartNode = mStartNode.getNext();
        }else if(position >= mSize){
            throw new IllegalArgumentException("Position cannot be greater than the List Size");
        }else{
            Node predecessor = getNode(position-1);
            predecessor.setNext(predecessor.getNext().getNext());
        }
        mSize--;
    }
    // Method to print the LinkedList.
    public void printList()
    {
        Node currNode = mStartNode;
    
        while (currNode != null) {
            System.out.print(currNode.getContent() + " ");
    
            currNode = currNode.getNext();
        }
    }
    
    public Node getStartNode(){
        return mStartNode;
    }
    public int getSize(){
        return mSize;
    }
}

