package cict.thesis.iskinclinic;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("searchdiseases.php")
    Call<List<SkinAppDiseasesList>> getContact(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
}
