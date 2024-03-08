package com.example.eventapptest2;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.eventapptest2.databinding.FragmentSavedEventsBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SavedEventsRecyclerViewAdapter extends RecyclerView.Adapter<SavedEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;

    /**
     * constructor
     * @param saveEvents
     */
    public SavedEventsRecyclerViewAdapter(List<Event> saveEvents) {
        events = saveEvents;
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSavedEventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int postini = position;
        holder.EventForView = events.get(position);
        holder.ExploreEventName.setText(events.get(position).getEventName());
        holder.Eventdate.setText(events.get(position).getEventDate());
        holder.Eventlocation.setText(events.get(position).getEventLocation());
        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2F7aa31d9e-1539-49f9-bc21-4ec823cdbfdb?alt=media&token=7afc1c14-11f4-48c7-8ab1-99ee1e96eaa4";
        Picasso.get().load(events.get(position).getEventPoster()).into(holder.Imageing);
        String imageUrl = events.get(position).getEventPoster();
        System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                saveevent.add(events.get(postini));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * creates the view of the fragment of the event
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView ExploreEventName;

        public final TextView Eventlocation;
        public final TextView Eventdate;
        public final ImageView Imageing;
        public Event EventForView;
        //public Button button;

        public ViewHolder(FragmentSavedEventsBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.SavedEventTitle;
            Eventlocation = binding.SavedEventlocation;
            Eventdate = binding.SavedEventDate;
            Imageing = binding.SaveduserImage;
           // button = binding.ExploreEventDetialsButton;
        }


        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}