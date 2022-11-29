public class indexedNumber{
    public int value, index;
    public indexedNumber(int value, int index){
        this.value = value;
        this.index = index;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
}