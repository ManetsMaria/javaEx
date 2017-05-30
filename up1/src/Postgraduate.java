/**
 * Created by mariya on 14.2.17.
 */
public class Postgraduate extends Student {
    private Academic supervisor;
    public Postgraduate(String n, String l, String e, Academic s){
        super(n,l,e);
        supervisor=s;
    }
    public Academic getSupervisor(){
        return supervisor;
    }
    public void setSupervisor(Academic s){
        supervisor=s;
    }
}
