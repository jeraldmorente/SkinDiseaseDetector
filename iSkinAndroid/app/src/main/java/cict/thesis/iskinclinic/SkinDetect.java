package cict.thesis.iskinclinic;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class SkinDetect extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = SkinDetect.class.getSimpleName();
    private static final int REQUEST_PERMISSION = 1000;
    private Bitmap bitmap;
    Button btnUpload;
    ImageView imageView;
    Button btnconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_app_detect);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);
        }

        btnUpload = (Button)findViewById(R.id.btn_upload);
        imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPicture(getStringImage(bitmap));
            }
        });

    }



    private void UploadPicture(final String photo)
    {
        String URL_UPLOAD = "http://192.168.43.61/iskinclinic/upload.php";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Uploading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(SkinDetect.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                Handler h  = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        btnconfirm = (Button)findViewById(R.id.btn_confirm);
                                        btnconfirm.setEnabled(true);
                                    }
                                },10000);

                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(SkinDetect.this, "Error Reading Detail "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        progressDialog.dismiss();
                        Toast.makeText(SkinDetect.this, "Error Reading Detail "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sdphoto",photo);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a File"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();

            try {
                //Bitmap bitmapd = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //float aspectratio = bitmap.getWidth() / (float) bitmap.getHeight();
                //int width = 293;
                //int height = Math.round(width/aspectratio);
                //bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                imageView.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private File getBitmapFile(Bitmap reduced) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "reduce_file");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        reduced.compress(Bitmap.CompressFormat.JPEG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte []imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodeImage;
    }


    public void onConfirm(View view){
        if (view.getId() == R.id.btn_confirm){
            Intent result = new Intent(SkinDetect.this, DiseaseOutput.class);
            startActivity(result);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
