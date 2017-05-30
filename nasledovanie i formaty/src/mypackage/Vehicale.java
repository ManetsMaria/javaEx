package mypackage;
abstract public class Vehicale  implements Comparable <Vehicale>{
    private boolean inputType(String type) throws ContEx{
        if (type.toUpperCase().compareTo("BENZIN")==0)
            return false;
        if (type.toUpperCase().compareTo("DIZEL")==0)
            return true;
        throw new ContEx("unknown type");
    }
    private String returnType(boolean type){
        if (type)
            return "DIZEL";
        return "BENZIN";
    }
	private String name;
	private String color;
    private boolean type;
    public Vehicale(String n, String c, String t)throws ContEx{
    	name=n;
    	color=c;
    	type=inputType(t);
    }
    @Override
    public int compareTo(Vehicale t) {
        int result;
        result = name.compareTo(t.name);
        if(result != 0) 
            return result;
        if (t.type==type)
            return 0;
        if (t.type==true)
            return 1;
        return -1;
    }
    public String toString(){
    	StringBuffer s=new StringBuffer();
        s.append(name);
        s.append(", ");
        s.append(color);
        s.append(", ");
        s.append(returnType(type));
        return s.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicale t = (Vehicale) o;
        if (!name.equals(t.name) && t.name!= null)
    		return false;
    	if (!color.equals(t.color) && t.color!=null)
    		return false;
    	if (type!=t.type)
    		return false;
    	return true;
    }
    /*@Override
    public double doubleValue(){
        return ball;
    }
    ///
    @Override
    public float floatValue(){
        return (float)ball;
    }
    @Override
    public  long longValue(){
        return (long)group;
    }
    @Override
    public int intValue(){
        return group;
    }*/
}