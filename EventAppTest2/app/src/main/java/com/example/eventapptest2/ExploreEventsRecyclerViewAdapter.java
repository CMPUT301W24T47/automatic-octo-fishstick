package com.example.eventapptest2;

import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.eventapptest2.databinding.FragmentExploreBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExploreEventsRecyclerViewAdapter extends RecyclerView.Adapter<ExploreEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;

    public ExploreEventsRecyclerViewAdapter(List<Event> items) {
        events = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentExploreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
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
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView ExploreEventName;

        public final TextView Eventlocation;
        public final TextView Eventdate;
        public final ImageView Imageing;
        public Event EventForView;


        public ViewHolder(FragmentExploreBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.ExploreEventTitle;
            Eventlocation = binding.ExploreEventlocation;
            Eventdate = binding.ExploreEventDate;
            Imageing = binding.ExploreuserImage;

        }


        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}