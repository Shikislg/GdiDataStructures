package de.tbh.exampleImplementations.queue;
import de.tbh.exampleImplementations.list.List;
public class Queue extends List{
    public Queue(int pContent){
        insert(pContent);
    }

    public int pop()
    {
        int content = getStartNode().getContent();
        remove(0);
        return content;
    }
 
    // Utility function to return the front element of the queue
    public int peek()
    {
        return get(0);
    }
    public void enqueue(int pContent)
    {
        insert(pContent);
    }
    // Utility function to check if the queue is empty or not
    public boolean isEmpty() {
        return (getSize() == 0);
    }
}
