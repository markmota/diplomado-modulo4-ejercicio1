package modulo4.ddam.markmota.tk.ejercicio1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import modulo4.ddam.markmota.tk.ejercicio1.R;
import modulo4.ddam.markmota.tk.ejercicio1.model.ModelItem;

/**
 * Created by markmota on 6/15/16.
 */
public class AdapterItemList extends ArrayAdapter<ModelItem> {
    public AdapterItemList(Context context, List<ModelItem> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list,parent,false);
        }
        TextView txtItemDescription= (TextView) convertView.findViewById(R.id.row_list_txtItemDescription);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.row_list_txtItemTitle);
        ImageView img = (ImageView) convertView.findViewById(R.id.row_list_row_image_view );

        ModelItem modelItem=getItem(position);
        txtTitle.setText(modelItem.item);
        txtItemDescription.setText(modelItem.id);
        img.setImageResource(modelItem.imgResourceId);
        return convertView;
    }
}
