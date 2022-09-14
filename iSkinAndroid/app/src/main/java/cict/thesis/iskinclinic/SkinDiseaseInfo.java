package cict.thesis.iskinclinic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SkinDiseaseInfo extends AppCompatActivity {
    TextView sdname, sddesc, sdcauses, sdsymptoms, sdprevention, sdtreatment, labelname, labelcauses, labeltreatment, labelsymptoms, labelprevention ;
    ImageView sdimage;
    String string_sdname, string_sddesc, string_sdcauses, string_sdsymptoms, string_sdprevention, string_sdtreatment, string_sdimage;
    int sdID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_disease_info);
        sdname = findViewById(R.id.sdinfoname);
        sdimage = findViewById(R.id.sdinfoimage);
        sddesc = findViewById(R.id.sdinfodesc);
        sdcauses = findViewById(R.id.sdinfocauses);
        sdsymptoms = findViewById(R.id.sdinfosymptoms);
        sdprevention = findViewById(R.id.sdinfoprevention);
        sdtreatment = findViewById(R.id.sdinfotreatment);
        labelname = findViewById(R.id.labeldesc);
        labelcauses = findViewById(R.id.labelcauses);
        labeltreatment = findViewById(R.id.labeltreatment);
        labelsymptoms = findViewById(R.id.labelsymptoms);
        labelprevention = findViewById(R.id.labelprevention);

        Intent intent = getIntent();
        sdID = intent.getIntExtra("skinID", 0);
        string_sdname = intent.getStringExtra("skinDiseaseName");
        string_sdimage = intent.getStringExtra("skinDiseaseImage");
        string_sddesc = intent.getStringExtra("skinDiseaseDesc");
        string_sdcauses = intent.getStringExtra("skinDiseaseCauses");
        string_sdsymptoms = intent.getStringExtra("skinDiseaseSymptoms");
        string_sdprevention = intent.getStringExtra("skinDiseasePrevention");
        string_sdtreatment = intent.getStringExtra("skinDiseaseTreatment");

        labelname.setText("What is "+string_sdname+"?");
        labelcauses.setText("What causes "+string_sdname+"?");
        labeltreatment.setText("How is "+string_sdname+" treated?");
        labelsymptoms.setText("What are the symptoms of "+string_sdname+"?");
        labelprevention.setText("Can I prevent "+string_sdname+"?");
        sdname.setText(string_sdname);
        sdcauses.setText(string_sdcauses);
        sddesc.setText(string_sddesc);
        sdsymptoms.setText(string_sdsymptoms);
        sdprevention.setText(string_sdprevention);
        sdtreatment.setText(string_sdtreatment);

        Glide.with(this)
                .load(string_sdimage).error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(sdimage);
    }
}