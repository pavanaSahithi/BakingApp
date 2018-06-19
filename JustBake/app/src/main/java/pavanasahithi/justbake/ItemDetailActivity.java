package pavanasahithi.justbake;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ItemDetailActivity extends AppCompatActivity {
    JsonPojo jsonPojo = null;
    StepsPojo stepsPojo = null;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            jsonPojo = getIntent().getParcelableExtra("recipe");
            stepsPojo = getIntent().getParcelableExtra("steps");
            if (jsonPojo != null) {
                bundle = new Bundle();
                bundle.putParcelable("recipe", jsonPojo);
            } else {
                bundle = new Bundle();
                bundle.putParcelable("steps", stepsPojo);
            }
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

}
