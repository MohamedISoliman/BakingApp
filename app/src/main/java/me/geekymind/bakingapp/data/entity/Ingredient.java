package me.geekymind.bakingapp.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ingredients")
public class Ingredient implements Parcelable {

  @PrimaryKey(autoGenerate = true)
  private int ingredientId;

  private double recipeId;

  @SerializedName("quantity")
  private double quantity;

  @SerializedName("measure")
  private String measure;

  @SerializedName("ingredient")
  private String ingredientDescription;

  public int getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(int ingredientId) {
    this.ingredientId = ingredientId;
  }

  public double getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(double recipeId) {
    this.recipeId = recipeId;
  }

  public double getQuantity() {
    return quantity;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public String getIngredientDescription() {
    return ingredientDescription;
  }

  public void setIngredientDescription(String ingredientDescription) {
    this.ingredientDescription = ingredientDescription;
  }

  public static Creator<Ingredient> getCREATOR() {
    return CREATOR;
  }

  public Ingredient() {
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "IngredientsItem{"
        + "quantity = '"
        + quantity
        + '\''
        + ",measure = '"
        + measure
        + '\''
        + ",ingredientDescription = '"
        + ingredientDescription
        + '\''
        + "}";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.ingredientId);
    dest.writeDouble(this.recipeId);
    dest.writeDouble(this.quantity);
    dest.writeString(this.measure);
    dest.writeString(this.ingredientDescription);
  }

  protected Ingredient(Parcel in) {
    this.ingredientId = in.readInt();
    this.recipeId = in.readDouble();
    this.quantity = in.readDouble();
    this.measure = in.readString();
    this.ingredientDescription = in.readString();
  }

  public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
    @Override
    public Ingredient createFromParcel(Parcel source) {
      return new Ingredient(source);
    }

    @Override
    public Ingredient[] newArray(int size) {
      return new Ingredient[size];
    }
  };
}