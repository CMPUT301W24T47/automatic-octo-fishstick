// OpenEventsRecyclerViewAdapter.java

package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OpenEventsRecyclerViewAdapter extends RecyclerView.Adapter<OpenEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mEvents;

    public OpenEventsRecyclerViewAdapter(List<Event> events) {
        mEvents = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orgainzers_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = mEvents.get(position);
        holder.mEventName.setText(event.getEventName());
        holder.mEventDate.setText(event.getEventDate());
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mEventName;
        public final TextView mEventDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mEventName = itemView.findViewById(R.id.OpenEventTitle);
            mEventDate = itemView.findViewById(R.id.OpenEventlocation);
        }
    }
}
