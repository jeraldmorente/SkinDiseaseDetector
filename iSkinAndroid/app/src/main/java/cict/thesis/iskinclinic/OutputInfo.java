package cict.thesis.iskinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class OutputInfo extends AppCompatActivity {
    TextView sdname, sddesc, sdcauses, sdsymptoms, sdprevention, sdtreatment, labelname, labelcauses, labeltreatment, labelsymptoms, labelprevention;
    ImageView sdimage;
    private static String URL_READ = "https://iskinclinic.000webhostapp.com/read_result.php";
    private static final String TAG = OutputInfo.class.getSimpleName();
    String string_output;
    int outputID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_app_outputinfo);
        sdname = findViewById(R.id.sdinfoname1);
        sdimage = findViewById(R.id.sdinfoimage1);
        sddesc = findViewById(R.id.sdinfodesc1);
        sdcauses = findViewById(R.id.sdinfocauses1);
        sdsymptoms = findViewById(R.id.sdinfosymptoms1);
        sdprevention = findViewById(R.id.sdinfoprevention1);
        sdtreatment = findViewById(R.id.sdinfotreatment1);
        labelname = findViewById(R.id.labeldesc1);
        labelcauses = findViewById(R.id.labelcauses1);
        labeltreatment = findViewById(R.id.labeltreatment1);
        labelsymptoms = findViewById(R.id.labelsymptoms1);
        labelprevention = findViewById(R.id.labelprevention1);
    }


    private void getResultDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG, response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");
                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String sd_name = object.getString("skinDiseaseName").trim();
                            String sd_image = object.getString("skinDiseaseImage");
                            String sd_desc = object.getString("skinDiseaseDesc");
                            String sd_causes = object.getString("skinDiseaseCauses");
                            String sd_symptoms = object.getString("skinDiseaseSymptoms");
                            String sd_prevention = object.getString("skinDiseasePrevention");
                            String sd_treatment = object.getString("skinDiseaseTreatment");
                            String sd_photo = "https://iskinclinic.000webhostapp.com/pages/skindiseases/image/" + sd_image;

                            if (sd_photo != null) {
                                Glide.with(OutputInfo.this)
                                        .load(sd_photo).error(R.mipmap.ic_launcher)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into(sdimage);
                            }

                            labelname.setText("What is " + sd_name + "?");
                            labelcauses.setText("What causes " + sd_name + "?");
                            labeltreatment.setText("How is " + sd_name + " treated?");
                            labelsymptoms.setText("What are the symptoms of " + sd_name + "?");
                            labelprevention.setText("Can I prevent " + sd_name + "?");
                            sdname.setText(sd_name);
                            sdcauses.setText(sd_causes);
                            sddesc.setText(sd_desc);
                            sdsymptoms.setText(sd_symptoms);
                            sdprevention.setText(sd_prevention);
                            sdtreatment.setText(sd_treatment);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(OutputInfo.this, "Error Reading Detail " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(OutputInfo.this, "Error Reading Detail " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent intent = getIntent();
                outputID = intent.getIntExtra("outputID", 0);
                string_output = intent.getStringExtra("output");

                Map<String, String> params = new HashMap<>();
                params.put("output", string_output);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getResultDetails();
    }

    public void onBackPressed() {
        CustomDialogClass2 cdd = new CustomDialogClass2(OutputInfo.this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(cdd.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        cdd.show();
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.getWindow().setAttributes(lp);
    }
}