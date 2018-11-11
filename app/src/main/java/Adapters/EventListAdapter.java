package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opensource.schedular.R;

import org.w3c.dom.Text;

import java.util.List;

import Model.EventModel;



public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {



    public EventListListener eventListListener;
    private List<EventModel> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View layout;
        //custom view variables
        public LinearLayout linearLayoutPostItemCard;

        public TextView time;
        public TextView description;


        public ImageButton imageButtonDelete;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            //custom view variable initialize
            linearLayoutPostItemCard = (LinearLayout) v.findViewById(R.id.EventItemCard);

            time = (TextView) v.findViewById(R.id.time);
            description = (TextView) v.findViewById(R.id.description);
            imageButtonDelete = (ImageButton) v.findViewById(R.id.deleteBtn);
        }
    }



    public void add(int position, EventModel item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventListAdapter(List<EventModel> myDataset) {
        values = myDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.recycler_event_item, parent, false);



        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    private int lastPosition = -1;
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.time.setText(values.get(position).Raw_Hour+":"+values.get(position).Raw_Min);
        holder.description.setText(values.get(position).description);



        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;


        holder.linearLayoutPostItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.EventItemCard:
                        System.out.println(values.get(position).description.toString());
                        eventListListener.valuePass(values.get(position));
                        break;
                    default:
                        return;
                }
            }
        });

        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.deleteBtn:
                        System.out.println(values.get(position).description.toString());
                        eventListListener.deleteRecord(values.get(position));
                        break;
                    default:
                        return;
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try{
            eventListListener = (EventListListener) recyclerView.getContext();
        }catch (ClassCastException e){
            System.out.println(e.getMessage());
        }
    }

    public interface EventListListener{
        void valuePass(EventModel eventDesc);
        void deleteRecord(EventModel eventModel);
    }
}
