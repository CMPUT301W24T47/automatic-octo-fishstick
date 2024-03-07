package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.FragmentOrgainzersEventBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OpenEventsRecyclerViewAdapter extends RecyclerView.Adapter<OpenEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;

    public OpenEventsRecyclerViewAdapter(List<Event> items) {
        events = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOrgainzersEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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


        public ViewHolder(@NonNull FragmentOrgainzersEventBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.OpenEventTitle;
            Eventlocation = binding.OpenEventlocation;
            Eventdate = binding.OpenEventDate;
            Imageing = binding.OpenUserImage;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}