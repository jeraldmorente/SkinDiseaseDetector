package cict.thesis.iskinclinic;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DiseaseOutput extends AppCompatActivity {
    private static String URL_READ = "http://192.168.43.61/iskinclinic/result.php";
    private static final String TAG = DiseaseOutput.class.getSimpleName();
    AdapterOutput adapter;
    private onClickInterface onclickInterface;
    List<DashboardOutput> dashboardOutputs;
    private RecyclerView outputRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_app_output);
        dashboardOutputs = new ArrayList<>();
        outputRecycler = findViewById(R.id.output_recycler);
        outputRecycler.setHasFixedSize(true);
        outputRecycler.setLayoutManager(new LinearLayoutManager(this));
        getResult();
    }

    private void getResult() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dashresult = new JSONArray(response);
                    for (int i = 0; i < dashresult.length(); i++) {
                        JSONObject dashobject = dashresult.getJSONObject(i);
                        int outputID = dashobject.getInt("outputID");
                        String output = dashobject.getString("output");
                        DashboardOutput dashboardOutput = new DashboardOutput(outputID, output);
                        dashboardOutputs.add(dashboardOutput);
                    }
                    adapter = new AdapterOutput(DiseaseOutput.this, dashboardOutputs, onclickInterface);
                    outputRecycler.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DiseaseOutput.this, "Error Reading Detail " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DiseaseOutput.this, "Error Reading Detail " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        CustomDialogClass2 cdd = new CustomDialogClass2(DiseaseOutput.this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(cdd.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        cdd.show();
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.getWindow().setAttributes(lp);
    }
}
