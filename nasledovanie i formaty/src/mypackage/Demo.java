package mypackage;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.File;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Exception;
public class Demo{
	/*public static boolean type(String type) throws ContEx{
		if (type.toUpperCase().compareTo("BENZIN")==0)
			return false;
		if (type.toUpperCase().compareTo("DIZEL")==0)
			return true;
		throw new ContEx("unknown type");
	}*/
	public static MyContainer <Car> readCar (String filename)throws IOException, NoSuchElementException, ContEx {
		MyContainer <Car> cc = new MyContainer <Car>();
		Scanner in = new Scanner(new File(filename));
		while (in.hasNext()){
			cc.add(new Car(in.next(), in.next(),in.next(),in.next()));
		}
		return cc;
	}
	public static MyContainer <Bus> readBus (String filename)throws IOException, InputMismatchException, NoSuchElementException, ContEx {
		MyContainer <Bus> bc = new MyContainer <Bus>();
		Scanner in = new Scanner(new File(filename));
		while (in.hasNext()){
			bc.add(new Bus(in.next(),in.next(),in.next(), in.nextInt(),in.nextInt()));
		}
		return bc;
	}
    public static void main(String args[]){
    	try{
    		MyContainer <Car> carCont=readCar("input1.txt");
            System.out.println(carCont.toString());
            System.out.println(carCont.countVehicale(new Car("car4", "green", "benzin", "mat")));
            System.out.println(carCont.countVehicale(new Car("car4", "green", "benzin", "mater")));
            System.out.println(carCont.binarySearch(new Car("car4", "green", "benzin", "mat")));
            System.out.println(carCont.binarySearch(new Car("car5", "green", "benzin", "mat")));
            System.out.println(carCont.max().toString());
    		MyContainer <Bus> busCont=readBus("input2.txt");
            System.out.println(busCont.toString());
            System.out.println(busCont.countVehicale(new Bus("car2", "red", "benzin", 23, 5)));
            System.out.println(busCont.countVehicale(new Bus("car2", "red", "benzin", 20, 5)));
            System.out.println(busCont.binarySearch(new Bus("car2", "red", "benzin", 23, 5)));
            System.out.println(busCont.binarySearch(new Bus("car3", "red", "benzin", 23, 5)));
            System.out.println(busCont.max());
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
    	catch(ContEx f){
    		System.out.println(f.getMessage());
    	}
    	catch (Exception e){
    		System.out.println ("something wrong, but system can't understand error");
    	}
    }
}