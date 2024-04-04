package com.example.eventapptest2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AdminImageFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminImageRecyclerViewAdapter extends RecyclerView.Adapter<AdminImageRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Event> allImages;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdminImageRecyclerViewAdapter(ArrayList<Event> allImageInput) {
        allImages = allImageInput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminImageFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imageForView = allImages.get(position);
        String imageURL = holder.imageForView.getEventPoster();

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.image);

        if ((!Objects.equals(holder.imageForView.getOwner(),"")&&(!Objects.equals(holder.imageForView.getOwner(),"")&&((Objects.equals(imageURL, ""))||(Objects.equals(imageURL, null)))))){
            holder.name.setText(holder.imageForView.getOwner());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.name.setBackgroundColor(color);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imagePosition = holder.getAbsoluteAdapterPosition();

                if (imagePosition != RecyclerView.NO_POSITION){
                    Event deleteImage = allImages.get(imagePosition);

                    // Firebase updating set images to empty string in ExploreEvents and Users
                    if(Objects.equals(deleteImage.getEventid(),"")){
                        db.collection("users")
                                .document(deleteImage.getOwner())
                                .update("userProfileImage","");
                    }

                    if((Objects.equals(deleteImage.getOwner(),"")) || Objects.equals(deleteImage.getEventPoster(),null)){
                        db.collection("ExploreEvents")
                                .document(deleteImage.getEventid())
                                .update("eventPoster","");
                    }

                    allImages.remove(deleteImage);
                    notifyItemRemoved(imagePosition);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return allImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final ImageView deleteButton;
        public final TextView name;
        public Event imageForView;



        public ViewHolder(AdminImageFragmentBinding binding) {
            super(binding.getRoot());
            image = binding.AdminImageView;
            deleteButton = binding.ImageDeleteButton;
            name = binding.nameOnImageView;
        }



    }
}