package me.geekymind.bakingapp.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

  @SerializedName("image")
  private String image;

  @SerializedName("servings")
  private double servings;

  @SerializedName("name")
  private String name;

  @SerializedName("ingredients")
  private List<Ingredient> ingredients;

  @SerializedName("id")
  private double id;

  @SerializedName("steps")
  private List<Step> steps;

  public void setImage(String image) {
    this.image = image;
  }

  public String getImage() {
    return image;
  }

  public void setServings(int servings) {
    this.servings = servings;
  }

  public double getServings() {
    return servings;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getId() {
    return id;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public List<Step> getSteps() {
    return steps;
  }

  @Override
  public String toString() {
    return "Response{"
        + "image = '"
        + image
        + '\''
        + ",servings = '"
        + servings
        + '\''
        + ",name = '"
        + name
        + '\''
        + ",ingredients = '"
        + ingredients
        + '\''
        + ",id = '"
        + id
        + '\''
        + ",steps = '"
        + steps
        + '\''
        + "}";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.image);
    dest.writeDouble(this.servings);
    dest.writeString(this.name);
    dest.writeTypedList(this.ingredients);
    dest.writeDouble(this.id);
    dest.writeList(this.steps);
  }

  public Recipe() {
  }

  protected Recipe(Parcel in) {
    this.image = in.readString();
    this.servings = in.readDouble();
    this.name = in.readString();
    this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    this.id = in.readDouble();
    this.steps = new ArrayList<Step>();
    in.readList(this.steps, Step.class.getClassLoader());
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