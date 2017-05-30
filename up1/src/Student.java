/**
 * Created by mariya on 14.2.17.
 */
public class Student extends Person implements Notifiable {
    private String login;
    private String email;
    public Student (String name, String l, String e){
        super(name);
        login=l;
        email=e;
    }
    public String getLogin(){
        return login;
    }
    public void setLogin(String l){
        login=l;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String e){
        email=e;
    }
    @Override
    public void notify(String message){
        System.out.println(message);
    }
}
