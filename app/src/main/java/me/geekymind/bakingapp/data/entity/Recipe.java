package me.geekymind.bakingapp.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "recipes")
public class Recipe implements Parcelable {

  @PrimaryKey(autoGenerate = true)
  private int primaryKey;

  @SerializedName("image")
  private String image;

  @SerializedName("servings")
  private double servings;

  @SerializedName("name")
  private String name;

  @SerializedName("ingredients")
  @Ignore
  private List<Ingredient> ingredients;

  @SerializedName("id")
  @Ignore
  private double id;

  @SerializedName("steps")
  @Ignore
  private List<Step> steps;

  public int getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(int primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public double getServings() {
    return servings;
  }

  public void setServings(double servings) {
    this.servings = servings;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public double getId() {
    return id;
  }

  public void setId(double id) {
    this.id = id;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.primaryKey);
    dest.writeString(this.image);
    dest.writeDouble(this.servings);
    dest.writeString(this.name);
    dest.writeTypedList(this.ingredients);
    dest.writeDouble(this.id);
    dest.writeTypedList(this.steps);
  }

  public Recipe() {
  }

  protected Recipe(Parcel in) {
    this.primaryKey = in.readInt();
    this.image = in.readString();
    this.servings = in.readDouble();
    this.name = in.readString();
    this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    this.id = in.readDouble();
    this.steps = in.createTypedArrayList(Step.CREATOR);
  }

  public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
    @Override
    public Recipe createFromParcel(Parcel source) {
      return new Recipe(source);
    }

    @Override
    public Recipe[] newArray(int size) {
      return new Recipe[size];
    }
  };
}