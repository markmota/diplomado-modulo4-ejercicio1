package modulo4.ddam.markmota.tk.ejercicio1.model;

/**
 * Created by markmota on 6/22/16.
 */
public class ModelUser {
    public int id;
    public String username;
    public String password;


    public ModelUser(int id, String userName, String password) {
        this.id=id;
        this.username = userName;
        this.password = password;
    }
    public ModelUser(String userName, String password) {

        this.username = userName;
        this.password = password;
    }
}
