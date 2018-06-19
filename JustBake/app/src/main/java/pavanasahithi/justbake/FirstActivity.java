package pavanasahithi.justbake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstActivity extends AppCompatActivity {
    RecyclerView recyclerView_recipe_card;
    RecipeCardAdapter recipeCardAdapter;
    LinearLayoutManager linearLayoutManager;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RetrofitApi.BASE_URL_BAKING).
                addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<List<JsonPojo>> call = retrofitApi.loadData();
        call.enqueue(new Callback<List<JsonPojo>>() {
            @Override
            public void onResponse(Call<List<JsonPojo>> call, Response<List<JsonPojo>> response) {
                List<JsonPojo> recipe_card = response.body();
                for (JsonPojo jsonPojo : recipe_card) {
                    recipeCardAdapter = new RecipeCardAdapter(FirstActivity.this, recipe_card);
                    linearLayoutManager = new LinearLayoutManager(FirstActivity.this);
                    recyclerView_recipe_card = (RecyclerView) findViewById(R.id.id_recycler_view_recipe_card);
                    recyclerView_recipe_card.setLayoutManager(linearLayoutManager);
                    recyclerView_recipe_card.setAdapter(recipeCardAdapter);
                    recyclerView_recipe_card.scrollToPosition(position);
                }
            }

            @Override
            public void onFailure(Call<List<JsonPojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("error", t.getMessage());
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("position");
    }

}
