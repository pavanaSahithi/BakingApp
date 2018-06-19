package pavanasahithi.justbake;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 29-05-2018.
 */

public class IngredientsPojo implements Parcelable{
    @SerializedName("quantity")
    float quantity;
    @SerializedName("measure")
    String measure;
    @SerializedName("ingredient")
    String ingredient;

    protected IngredientsPojo(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientsPojo> CREATOR = new Creator<IngredientsPojo>() {
        @Override
        public IngredientsPojo createFromParcel(Parcel in) {
            return new IngredientsPojo(in);
        }

        @Override
        public IngredientsPojo[] newArray(int size) {
            return new IngredientsPojo[size];
        }
    };

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(getQuantity());
        dest.writeString(getMeasure());
        dest.writeString(getIngredient());
    }
}
