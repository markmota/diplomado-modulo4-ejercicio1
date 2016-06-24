package modulo4.ddam.markmota.tk.ejercicio1.framgent;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import modulo4.ddam.markmota.tk.ejercicio1.DetailActivity;
import modulo4.ddam.markmota.tk.ejercicio1.R;
import modulo4.ddam.markmota.tk.ejercicio1.model.ModelItem;
import modulo4.ddam.markmota.tk.ejercicio1.adapters.AdapterItemList;
import modulo4.ddam.markmota.tk.ejercicio1.sql.ItemDataSource;

/**
 * Created by markmota on 6/15/16.
 */
public class FragmentList extends Fragment {
    private ListView listView;
    private List<ModelItem> array = new ArrayList<>();
    private int counter;
    private boolean evenItem=true;
    private ItemDataSource itemDataSource;
    private int numberOfElements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDataSource = new ItemDataSource(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        // Configuracion de la lista de items
        listView = (ListView) view.findViewById(R.id.fragment_list_listItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItemList adapter= (AdapterItemList) parent.getAdapter();
                ModelItem modelItem =adapter.getItem(position);
                ModelItem modelItem2 = array.get(position);
                //Toast.makeText(getActivity(),modelItem2.item,Toast.LENGTH_SHORT).show();
                // Para cargar la activity detail
                Intent intent= new Intent(parent.getContext(),DetailActivity.class);
                intent.putExtra("key_detail",modelItem2.item);
                startActivity(intent);


            }
        });
        // Getting all the elements on the database
        List<ModelItem> modelItemList = itemDataSource.getAllItems();
        numberOfElements= modelItemList.size();
        // Setting the correct image even or pair
        evenItem=!(modelItemList.size()%2==0);
        // For the fist time populating the list with the elements in de modelItemList
        listView.setAdapter(new AdapterItemList(getActivity(),modelItemList));

        // Configuration of the message box when the user click and hold a list element
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItemList adapter =(AdapterItemList) parent.getAdapter();
                final ModelItem modelItem =adapter.getItem(position);

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.fragment_list_delete_title)
                        .setMessage(String.format(getString(R.string.fragment_list_delete_message),modelItem.item))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemDataSource.deleteItem(modelItem);
                                listView.setAdapter(new AdapterItemList(getActivity(),
                                        itemDataSource.getAllItems()));
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).create().show();
                return true;
            }
        });



        // Configuracion de editText y boton para agregar a la lista
        final EditText mItemsText = (EditText) view.findViewById(R.id.fragment_list_inputText);
        view.findViewById(R.id.fragment_list_btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemData = mItemsText.getText().toString();
                if(!TextUtils.isEmpty(itemData))
                {
                    // Creamos el objeto (que es un modelo de datos solamente) para que tenga los elementos de la lista
                    ModelItem item =new ModelItem(itemData,getString(R.string.fragment_list_message_listidTitle)+" "+counter,evenItem?R.drawable.ic_even_item:R.drawable.ic_odd_item);

                    // Agregamos el modelItem al array
                    //array.add(item);
                    // Adding the information to the database
                    itemDataSource.saveItem(item);
                    // Refreshing the list content whit the database information
                    listView.setAdapter(new AdapterItemList(getActivity(),itemDataSource.getAllItems()));

                    //listView.setAdapter(new AdapterItemList(getActivity(),array));
                    evenItem=!evenItem;
                    counter++;
                    mItemsText.setText("");

                }

            }
        });
        return view;
    }
}
