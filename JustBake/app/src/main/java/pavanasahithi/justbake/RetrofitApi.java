package pavanasahithi.justbake;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lenovo on 30-05-2018.
 */

public interface RetrofitApi {
    String BASE_URL_BAKING
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @GET("baking.json")
    Call<List<JsonPojo>> loadData();
}
