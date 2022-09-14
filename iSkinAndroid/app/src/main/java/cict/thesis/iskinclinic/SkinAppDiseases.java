package cict.thesis.iskinclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkinAppDiseases extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<SkinAppDiseasesList> skinAppDiseasesLists;
    private AdapterSkinDiseasesList adapterSkinDiseasesList;
    private ApiInterface apiInterface;
    private onClickInterface onclickInterface;
    ProgressBar progressBar;
    TextView search;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_app_diseases);
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int abc) {
            }
        };

        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchContact("skindiseases", "");
    }
    public void fetchContact(String type, String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<SkinAppDiseasesList>> call = apiInterface.getContact(type, key);
        call.enqueue(new Callback<List<SkinAppDiseasesList>>() {
            @Override
            public void onResponse(Call<List<SkinAppDiseasesList>> call, Response<List<SkinAppDiseasesList>> response) {
                progressBar.setVisibility(View.GONE);
                skinAppDiseasesLists = response.body();
                adapterSkinDiseasesList = new AdapterSkinDiseasesList(skinAppDiseasesLists, SkinAppDiseases.this, onclickInterface);
                recyclerView.setAdapter(adapterSkinDiseasesList);
                adapterSkinDiseasesList.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SkinAppDiseasesList>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SkinAppDiseases.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchContact("skindiseases", query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("skindiseases", newText);
                return false;
            }
        });
        return true;
    }
}