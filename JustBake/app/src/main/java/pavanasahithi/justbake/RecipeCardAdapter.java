package pavanasahithi.justbake;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.transform.Templates;

/**
 * Created by Lenovo on 30-05-2018.
 */

public class RecipeCardAdapter extends  RecyclerView.Adapter<RecipeCardAdapter.RecyclerViewHolder>{
    Context context;
    List<JsonPojo> jsonPojos;
    RecipeCardAdapter(Context context,List<JsonPojo> jsonPojos){
        this.context=context;
        this.jsonPojos=jsonPojos;
    }

    @Override
    public RecipeCardAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCardAdapter.RecyclerViewHolder holder, final int position) {
        holder.recipe_card_servings.setText("Servings: "+jsonPojos.get(position).getServings());
        holder.recipe_card_name.setText(jsonPojos.get(position).getName());
        String imageUrl=jsonPojos.get(position).getImageUrl();
        Uri uri=Uri.parse(imageUrl).buildUpon().build();
        switch (position)
        {
            case 0:
                Picasso.with(context).load(uri).placeholder(R.drawable.nutella_pie)
                .error(R.drawable.nutella_pie).into(holder.recipe_card_image);
            break;
            case 1:
                Picasso.with(context).load(uri).placeholder(R.drawable.brownies)
                        .error(R.drawable.brownies).into(holder.recipe_card_image);
                break;
            case 2:
                Picasso.with(context).load(uri).placeholder(R.drawable.yellow_cake)
                        .error(R.drawable.yellow_cake).into(holder.recipe_card_image);
                break;
            case 3:
                Picasso.with(context).load(uri).placeholder(R.drawable.cheese_cake)
                        .error(R.drawable.cheese_cake).into(holder.recipe_card_image);
                break;
        }


        holder.recipe_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ItemListActivity.class);
                i.putExtra("recipe",jsonPojos.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(jsonPojos==null){
            return 0;
        }
        return jsonPojos.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView recipe_card_image;
        TextView recipe_card_name,recipe_card_servings;
        CardView recipe_card_view;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            recipe_card_image=(ImageView)itemView.findViewById(R.id.id_image);
            recipe_card_name=(TextView)itemView.findViewById(R.id.id_recipe_name);
            recipe_card_servings=(TextView)itemView.findViewById(R.id.id_recipe_servings);
            recipe_card_view=(CardView)itemView.findViewById(R.id.id_card_view_recipe_card);
        }
    }
}
