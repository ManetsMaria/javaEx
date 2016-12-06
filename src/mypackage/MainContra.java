package mypackage;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.File;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;
public class MainContra{
	public static MyContainer <Student> readStudent (String filename)throws IOException, InputMismatchException, NoSuchElementException{
		MyContainer <Student> mc = new MyContainer <Student>();
		Scanner in = new Scanner(new File(filename));
		while (in.hasNext()){
			mc.add(new Student(in.nextInt(), in.nextDouble(), in.next()));
		}
		return mc;
	}
	public static MyContainer <Double> readDouble (String filename)throws IOException, InputMismatchException{
		MyContainer <Double> mc = new MyContainer <Double>();
		Scanner in = new Scanner(new File(filename));
		while (in.hasNext()){
			mc.add(in.nextDouble());
		}
		return mc;
	}
    public static void main(String args[]){
    	try{
    		MyContainer <Double> dc=readDouble("doubleNumber.txt");
    		MyContainer <Student> sc=readStudent("studentElement.txt");
    		System.out.println(dc.average());
    		System.out.println(dc.min().toString());
    		System.out.println(sc.average());
    		System.out.println(sc.max().toString());
    		dc.remove(-1);
    	}
    	catch(IOException a){
    		System.err.println("can't find file ");
    	}
    	catch (InputMismatchException b){
    		System.err.println("check input elements ");
    	}
    	catch (NoSuchElementException c){
    		System.err.println("no full data");
    	}
    	catch(ArrayIndexOutOfBoundsException d){
    		System.err.println("check your indexes");
    	}
    	catch (Exception e){
    		System.out.println ("something wrong, but system can't understand error");
    	}
    }
}