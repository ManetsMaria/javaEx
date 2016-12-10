package mypackage;
import java.util.ArrayList;
import java.util.Collections;
public class MyContainer <T extends Vehicale> extends ArrayList<T> {
    public MyContainer (int c){
    	super(c);
    }
    public MyContainer (){
    }
    public T max() {
    	return Collections.max(this);
    }
    public int countVehicale(T t){
    	return  Collections.frequency(this, t);
    }
    public T binarySearch(T t){
        MyContainer <T> copy=this;
        Collections.sort(copy);
        try{
    	   return copy.get(Collections.binarySearch(copy, t));
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public String toString(){
    	StringBuffer s=new StringBuffer();
    	for (int i=0; i<this.size(); i++){
			s.append(this.get(i).toString());
			s.append(" ");
    	}
    	return s.toString();
    }
}