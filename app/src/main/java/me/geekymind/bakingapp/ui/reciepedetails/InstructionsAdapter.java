package me.geekymind.bakingapp.ui.reciepedetails;

import android.content.Context;
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

  private final StepClickListener stepClickListener;
  private int currentSelected;

  public InstructionsAdapter(StepClickListener stepClickListener, Step selected) {
    this.stepClickListener = stepClickListener;
    setSelected(selected);
  }

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

  private void setSelected(Step selected) {
    int itemCount = getItemCount();
    for (int i = 0; i < itemCount; i++) {
      Step dataItem = getDataItem(i);
      if (selected.getId() == dataItem.getId()) {
        currentSelected = i;
        dataItem.setSelected(true);
        break;
      }
    }
  }

  class InstructionViewHolder extends RecyclerView.ViewHolder {

    private ItemStepBinding binding;
    private Context context;
    private boolean isTabletMode;

    InstructionViewHolder(ItemStepBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      this.context = binding.getRoot().getContext();
      isTabletMode = context.getResources().getBoolean(R.bool.tablet_mode);
    }

    void bindData(Step step) {
      binding.backgroundLayout.setBackgroundColor(
          step.isSelected ? context.getResources().getColor(R.color.blue_grey_300) : 0);
      binding.stepTitle.setText(step.getShortDescription());
      binding.stepLayoutContainer.setOnClickListener(v -> {
        if (isTabletMode) updateBackground();
        stepClickListener.onStepSelected(step);
      });
    }

    private void updateBackground() {
      getDataItem(getAdapterPosition()).setSelected(true);
      notifyItemChanged(getAdapterPosition());
      getDataItem(currentSelected).setSelected(false);
      notifyItemChanged(currentSelected);
      currentSelected = getAdapterPosition();
    }
  }
}
