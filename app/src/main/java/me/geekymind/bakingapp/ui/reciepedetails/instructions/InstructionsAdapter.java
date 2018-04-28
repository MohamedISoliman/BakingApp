package me.geekymind.bakingapp.ui.reciepedetails.instructions;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.base.AppBaseAdapter;
import me.geekymind.bakingapp.data.entity.Step;
import me.geekymind.bakingapp.databinding.ItemStepBinding;

/**
 * Created by Mohamed Ibrahim on 4/28/18.
 */
public class InstructionsAdapter
    extends AppBaseAdapter<InstructionsAdapter.InstructionViewHolder, Step> {

  @NonNull
  @Override
  public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_step, parent, false);
    return new InstructionViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
    Step step = getDataItem(position);
    holder.bindData(step);
  }

  class InstructionViewHolder extends RecyclerView.ViewHolder {

    private ItemStepBinding binding;

    InstructionViewHolder(ItemStepBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bindData(Step step) {
      binding.stepTitle.setText(step.getShortDescription());
    }
  }
}
