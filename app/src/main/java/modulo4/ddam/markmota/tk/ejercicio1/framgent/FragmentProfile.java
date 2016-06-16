package modulo4.ddam.markmota.tk.ejercicio1.framgent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import modulo4.ddam.markmota.tk.ejercicio1.R;

/**
 * Created by markmota on 6/15/16.
 */
public class FragmentProfile extends Fragment {
    private ImageView imgProfile;

    public static FragmentProfile newInstance(String name)
    {
        FragmentProfile f = new FragmentProfile();
        Bundle user = new Bundle();
        user.putString("user_key",name);
        f.setArguments(user);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        char firstLetter;
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        imgProfile = (ImageView) view.findViewById(R.id.fragment_profile_imgProfile);
        TextView txt = (TextView) view.findViewById(R.id.fragment_profile_txtFragment);
        Bundle bundle=getArguments();
        String user ;
        if(bundle!=null){
            user=bundle.getString("user_key");
            // obtenemos el primer caracter de la letra del usuario
            firstLetter=user.charAt(0);
            // Segun el caso ponemos la imagen correspondiente en el imageView del fragmento
            if((firstLetter >= 'A' && firstLetter <='N') || (firstLetter >= 'a' && firstLetter <='m') ){
                imgProfile.setImageResource(R.drawable.ic_user_a_m);
            }
            else{
                imgProfile.setImageResource(R.drawable.ic_user_n_z);
            }
        }

        else
            user= String.valueOf(R.string.fragment_profile_message_null_user);

        txt.setText(user);

        return view;
    }
}
