package cict.thesis.iskinclinic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.os.Bundle;

public class SkinApp extends AppCompatActivity {
    private Button button_SkinDetect, button_SkinDiseases, button_About, button_Contact;
    String appEmail = "iskinclinicapp@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_app_dashboard);

        button_SkinDetect = findViewById(R.id.button1);
        button_SkinDiseases = findViewById(R.id.button2);
        button_About = findViewById(R.id.button3);
        button_Contact = findViewById(R.id.button4);


        button_SkinDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skinDetect = new Intent(SkinApp.this, SkinDetect.class);
                startActivity(skinDetect);
            }
        });

        button_SkinDiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skinDiseases = new Intent(SkinApp.this, SkinAppDiseases.class);
                startActivity(skinDiseases);
            }
        });

        button_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(SkinApp.this, SkinAppAbout.class);
                startActivity(about);
            }
        });

        button_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={appEmail};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"iSkin Application concern");
                intent.putExtra(Intent.EXTRA_TEXT,"Developers.. ");
                intent.putExtra(Intent.EXTRA_CC,appEmail);
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });


    }
    public void onBackPressed(){
        CustomDialogClass cdd1 = new CustomDialogClass(SkinApp.this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(cdd1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        cdd1.show();
        cdd1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd1.getWindow().setAttributes(lp);
    }


}
