package util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    //informs itemtouchadapter that drag gestures are enabled
    @Override
    public boolean isLongPressDragEnabled(){
        return true;
    }

    //informs itemtouchadapter that swipe gestures are enabled
    @Override
    public boolean isItemViewSwipeEnabled(){
        return true;
    }

    //informs itemtouchhelper which movement directions are supported
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
      final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
      final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
      return makeMovementFlags(dragFlags, swipeFlags);
    }

    //  The method below notifies the adapter that an item has moved.
    //  This triggers the onItemMove override in our Firebase adapter,
    //  which will eventually handle updating the restaurants ArrayList to reflect the item's new position.
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
       if(source.getItemViewType() != target.getItemViewType()){
           return false;
       }
       mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
       return true;
    }


    //  The method below notifies the adapter that an item was dismissed.
    //  This triggers the onItemDismiss override in our Firebase adapter which will eventually handle deleting this item from the user's "Saved Restaurants" in Firebase.
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());

    }

    //   The method below triggers the callback in ItemTouchHelperViewHolder which is then sent to our RestaurantListViewHolder where we will later add animations.
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState){
        //  This conditional ensures we only change appearance of active items:
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if (viewHolder instanceof ItemTouchHelperViewHolder){
                //  This tells the viewHolder that an item is being moved or dragged:
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //  This triggers the callback in the ItemTouchHelperViewHolder which will be sent to our RestaurantListViewHolder.
    //  Then, in the clearView override in RestaurantListViewHolder, we will remove the animations attached to 'selected' items, since this item will no longer be actively selected.
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof ItemTouchHelperViewHolder){

            //  Tells the view holder to return the item back to its normal appearance:
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }

}
