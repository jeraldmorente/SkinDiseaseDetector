package cict.thesis.iskinclinic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomDialogClass2 extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CustomDialogClass2(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog2);

        yes = (Button) findViewById(R.id.btn_yes2);
        no = (Button) findViewById(R.id.btn_no2);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes2:
                Intent b= new Intent(c, SkinApp.class );
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(b);
                c.finish();

                break;
            case R.id.btn_no2:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}