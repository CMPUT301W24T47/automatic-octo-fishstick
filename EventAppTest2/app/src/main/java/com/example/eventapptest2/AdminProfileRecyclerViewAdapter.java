package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapptest2.databinding.AdminImageFragmentBinding;
import com.example.eventapptest2.databinding.AdminProfilesFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminProfileRecyclerViewAdapter extends RecyclerView.Adapter<AdminProfileRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<User> allusers;

    public AdminProfileRecyclerViewAdapter(ArrayList<User> allusersinput) {
        allusers = allusersinput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminProfilesFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
//        int postini = position;
//
//        System.out.println("testtttttttttt " + allusers.get(postini));
//        holder.userName.setText(allusers.get(postini));


    }


    @Override
    public int getItemCount() {
        return allusers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        public final ImageView Imageing;
        public final ImageView deletebut;
        public final TextView Textname;
        public Event EventForView;



        public ViewHolder(AdminProfilesFragmentBinding binding) {
            super(binding.getRoot());
            
            Imageing = binding.adminProfileIcon;
            deletebut = binding.adminDeletebtnProfiles;
            Textname = binding.adminProfiles;


        }


      
    }
}