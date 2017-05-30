package mypackage;
public class Bus extends Vehicale{
	private int place;
	private int doors;
	public Bus(String n, String c, String t, int p, int d)throws ContEx{
		super(n, c, t);
		place=p;
		doors=d;
	}
	public String toString(){
        StringBuffer s=new StringBuffer();
        s.append(super.toString());
        s.append(", ");
        s.append(Integer.toString(place));
        s.append(", ");
        s.append(Integer.toString(doors));
        s.append(";");
        return s.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Bus t = (Bus) o;

        if (place!=t.place)
			return false;
		if (doors!=t.doors)
			return false;
		return true;
    }
}