package pavanasahithi.justbake;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lenovo on 29-05-2018.
 */

public class JsonPojo implements Parcelable {
    public static final Creator<JsonPojo> CREATOR = new Creator<JsonPojo>() {
        @Override
        public JsonPojo createFromParcel(Parcel in) {
            return new JsonPojo(in);
        }

        @Override
        public JsonPojo[] newArray(int size) {
            return new JsonPojo[size];
        }
    };
    @SerializedName("id")
    int id;
    @SerializedName("servings")
    int servings;
    @SerializedName("image")
    String imageUrl;
    @SerializedName("name")
    String name;
    @SerializedName("ingredients")
    List<IngredientsPojo> ingredientsPojos;
    @SerializedName("steps")
    List<StepsPojo> stepsPojos;

    protected JsonPojo(Parcel in) {
        id = in.readInt();
        servings = in.readInt();
        imageUrl = in.readString();
        name = in.readString();
        ingredientsPojos = in.createTypedArrayList(IngredientsPojo.CREATOR);
        stepsPojos = in.createTypedArrayList(StepsPojo.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsPojo> getIngredientsPojos() {
        return ingredientsPojos;
    }

    public void setIngredientsPojos(List<IngredientsPojo> ingredientsPojos) {
        this.ingredientsPojos = ingredientsPojos;
    }

    public List<StepsPojo> getStepsPojos() {
        return stepsPojos;
    }

    public void setStepsPojos(List<StepsPojo> stepsPojos) {
        this.stepsPojos = stepsPojos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getServings());
        dest.writeString(getImageUrl());
        dest.writeString(getName());
        dest.writeTypedList(getIngredientsPojos());
        dest.writeTypedList(getStepsPojos());
    }
}
