package me.geekymind.bakingapp.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Recipe {

  @SerializedName("image")
  private String image;

  @SerializedName("servings")
  private double servings;

  @SerializedName("name")
  private String name;

  @SerializedName("ingredients")
  private List<IngredientsItem> ingredients;

  @SerializedName("id")
  private double id;

  @SerializedName("steps")
  private List<StepsItem> steps;

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

  public void setIngredients(List<IngredientsItem> ingredients) {
    this.ingredients = ingredients;
  }

  public List<IngredientsItem> getIngredients() {
    return ingredients;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getId() {
    return id;
  }

  public void setSteps(List<StepsItem> steps) {
    this.steps = steps;
  }

  public List<StepsItem> getSteps() {
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
}