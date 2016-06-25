package modulo4.ddam.markmota.tk.ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;


/**
 * Created by markmota on 6/17/16.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public String detail;
    Random rand = new Random();
    public int rndNumber=rand.nextInt(3) + 1;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        // Getting the info sended from the precious activity
        detail=getIntent().getExtras().getString("key_detail");

        // Linking layout elements with objects
        TextView txtDetail= (TextView) findViewById(R.id.activity_detail_txtDetail);
        ImageView imgPicture= (ImageView) findViewById(R.id.activity_detail_imgPicture);

        // Getting a random image
        int idImage;
        String imgName="ic_random_"+rndNumber;
        idImage=this.getResources().getIdentifier(imgName,"drawable", this.getPackageName());
        if(idImage==0){
            // DEFAULT IMAGE
            idImage= R.drawable.ic_random_1;
        }
        // Setting data in the layput
        imgPicture.setImageResource(idImage);
        txtDetail.setText(detail);
        // Setting click listener to buttons
        findViewById(R.id.activity_detail_btnBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_detail_btnBack:
                finish();
                break;
        }
    }


}
