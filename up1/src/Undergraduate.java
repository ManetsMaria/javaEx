/**
 * Created by mariya on 14.2.17.
 */
public class Undergraduate extends Student {
    private Academic tutor;
    public Undergraduate(String n, String l, String e, Academic t){
        super(n, l, e);
        tutor=t;
    }
    public Academic getTutor(){
        return tutor;
    }
    public void setTutor(Academic t){
        tutor=t;
    }
}
