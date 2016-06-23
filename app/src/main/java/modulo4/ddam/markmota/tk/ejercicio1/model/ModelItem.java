package modulo4.ddam.markmota.tk.ejercicio1.model;

/**
 * Created by markmota on 6/15/16.
 */
public class ModelItem {
    public int id;
    public  String item;
    public String description;
    public int resourceId;

    public ModelItem(int id, String item, String description, int resourceId) {
        this.id = id;
        this.item = item;
        this.description = description;
        this.resourceId = resourceId;
    }
}
