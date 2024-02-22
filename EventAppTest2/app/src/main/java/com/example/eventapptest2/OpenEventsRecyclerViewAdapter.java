package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapptest2.databinding.FragmentOrgainzersEventBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

public class OpenEventsRecyclerViewAdapter extends RecyclerView.Adapter<OpenEventsRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public OpenEventsRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOrgainzersEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;


        public ViewHolder(@NonNull FragmentOrgainzersEventBinding binding) {
            super(binding.getRoot());
            mIdView = binding.OpenEventTitle;
            mContentView = binding.OpenEventlocation;
        }

        @Override
        public String toString() {

            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}