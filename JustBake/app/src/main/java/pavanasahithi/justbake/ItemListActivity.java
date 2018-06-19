package pavanasahithi.justbake;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    JsonPojo jsonPojo;
    TextView ingredients_tv;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ingredients_tv = (TextView) findViewById(R.id.id_text_view_ingredients);
        jsonPojo = getIntent().getParcelableExtra("recipe");
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        ingredients_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemListActivity.this, R.string.ingredients, Toast.LENGTH_SHORT).show();
                if (mTwoPane) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("recipe", jsonPojo);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                    intent.putExtra("recipe", jsonPojo);
                    ItemListActivity.this.startActivity(intent);
                }
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, jsonPojo, mTwoPane));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final JsonPojo mValues;
        private final boolean mTwoPane;
        List<StepsPojo> stepsPojos;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      JsonPojo items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(stepsPojos.get(position).getShortDescription());
        }

        @Override
        public int getItemCount() {
            stepsPojos = mValues.getStepsPojos();
            if (stepsPojos != null)
                return stepsPojos.size();
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView = (TextView) view.findViewById(R.id.content);
                mContentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Toast.makeText(mParentActivity,
                                stepsPojos.get(position).getShortDescription(),
                                Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("steps", stepsPojos.get(position));
                        if (mTwoPane) {
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                            intent.putExtra("steps", stepsPojos.get(position));
                            ItemListActivity.this.startActivity(intent);
                        }
                    }
                });
            }
        }
    }
}
