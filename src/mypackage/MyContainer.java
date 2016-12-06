package mypackage;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Number;
public class MyContainer <T extends Number & Comparable<T> > extends ArrayList<T> {
    public MyContainer (int c){
    	super(c);
    }
    public MyContainer (){
    }
    public T min() {
        return Collections.min(this);
    }
    public T max() {
    	return Collections.max(this);
    }
    public double average() { 
    	double sum = 0.0;
    	for(int i=0; i < this.size(); i++) 
    		sum += this.get(i).doubleValue(); 
    	return sum / this.size(); 
    }
}