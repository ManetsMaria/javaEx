package mypackage;
public class Car extends Vehicale{
	private String material;
	public Car(String n, String c, String t, String m)throws ContEx{
		super(n, c, t);
		material=m;
	}
	public String toString(){
        StringBuffer s=new StringBuffer();
        s.append(super.toString());
        s.append(", ");
        s.append(material);
        s.append(";");
        return s.toString();
    }
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Car t = (Car) o;

        if (!material.equals(t.material) && t.material!=null)
			return false;
		return true;
    }
}