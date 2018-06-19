package pavanasahithi.justbake;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 29-05-2018.
 */

public class StepsPojo implements Parcelable {
    public static final Creator<StepsPojo> CREATOR = new Creator<StepsPojo>() {
        @Override
        public StepsPojo createFromParcel(Parcel in) {
            return new StepsPojo(in);
        }

        @Override
        public StepsPojo[] newArray(int size) {
            return new StepsPojo[size];
        }
    };
    @SerializedName("id")
    int id;
    @SerializedName("shortDescription")
    String shortDescription;
    @SerializedName("description")
    String description;
    @SerializedName("videoURL")
    String videoUrl;
    @SerializedName("thumbnailURL")
    String thumbnailURl;

    protected StepsPojo(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailURl = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailURl() {
        return thumbnailURl;
    }

    public void setThumbnailURl(String thumbnailURl) {
        this.thumbnailURl = thumbnailURl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getShortDescription());
        dest.writeString(getDescription());
        dest.writeString(getVideoUrl());
        dest.writeString(getThumbnailURl());
    }
}
