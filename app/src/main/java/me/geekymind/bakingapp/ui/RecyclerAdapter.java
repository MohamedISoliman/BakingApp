package me.geekymind.bakingapp.ui;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mohamed Ibrahim on 4/14/18.
 */
public abstract class RecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

  private List<T> data = Collections.emptyList();

  public void setData(List<T> data) {
    if (this.data.isEmpty()) {
      this.data = new ArrayList<>();
    }
    this.data = data;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  protected List<T> getData(){
    return data;
  }
}
