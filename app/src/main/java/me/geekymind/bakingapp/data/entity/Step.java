package me.geekymind.bakingapp.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

  @SerializedName("videoURL")
  private String videoURL;

  @SerializedName("description")
  private String description;

  @SerializedName("id")
  private double id;

  @SerializedName("shortDescription")
  private String shortDescription;

  @SerializedName("thumbnailURL")
  private String thumbnailURL;
  public boolean isSelected;

  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }

  public String getVideoURL() {
    return videoURL;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getId() {
    return id;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
  }

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  @Override
  public String toString() {
    return "StepsItem{"
        + "videoURL = '"
        + videoURL
        + '\''
        + ",description = '"
        + description
        + '\''
        + ",id = '"
        + id
        + '\''
        + ",shortDescription = '"
        + shortDescription
        + '\''
        + ",thumbnailURL = '"
        + thumbnailURL
        + '\''
        + "}";
  }

  public Step() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.videoURL);
    dest.writeString(this.description);
    dest.writeDouble(this.id);
    dest.writeString(this.shortDescription);
    dest.writeString(this.thumbnailURL);
    dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
  }

  protected Step(Parcel in) {
    this.videoURL = in.readString();
    this.description = in.readString();
    this.id = in.readDouble();
    this.shortDescription = in.readString();
    this.thumbnailURL = in.readString();
    this.isSelected = in.readByte() != 0;
  }

  public static final Creator<Step> CREATOR = new Creator<Step>() {
    @Override
    public Step createFromParcel(Parcel source) {
      return new Step(source);
    }

    @Override
    public Step[] newArray(int size) {
      return new Step[size];
    }
  };
}