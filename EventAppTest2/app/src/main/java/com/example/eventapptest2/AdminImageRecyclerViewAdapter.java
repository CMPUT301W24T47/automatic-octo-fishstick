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
 * A RecyclerView Adapter for displaying images related to event images and user profile images
 * the Admin can view.
 * The Adapter binds the event information to the corresponding views in the RecyclerView.
 */
public class AdminImageRecyclerViewAdapter extends RecyclerView.Adapter<AdminImageRecyclerViewAdapter.ViewHolder> {
    private  ArrayList<Event> allImages; // List of all images within the app
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Constructor for the AdminImageRecyclerViewAdapter.
     *
     * @param allImageInput List of all images within the app
     */
    public AdminImageRecyclerViewAdapter(ArrayList<Event> allImageInput) {
        allImages = allImageInput;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(AdminImageFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the image associated with the current position
        holder.imageForView = allImages.get(position);
        String imageURL = holder.imageForView.getEventPoster();

        // Load the image using Glide
        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.image);

        // Set the name and background colour if a user profile image has no image (empty string)
        if ((!Objects.equals(holder.imageForView.getOwner(),"")&&(!Objects.equals(holder.imageForView.getOwner(),"")&&((Objects.equals(imageURL, ""))||(Objects.equals(imageURL, null)))))){
            holder.name.setText(holder.imageForView.getOwner());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.name.setBackgroundColor(color);
        }

        // Delete button onClickListener
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the image position in the Adapter
                int imagePosition = holder.getAbsoluteAdapterPosition();

                if (imagePosition != RecyclerView.NO_POSITION){
                    // Get all the images
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

                    // Remove image from the list and notify the adapter
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

    /**
     * ViewHolder class for holding the views associated with each image.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final ImageView deleteButton;
        public final TextView name;
        public Event imageForView;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param binding The binding object containing the views for the item
         */
        public ViewHolder(AdminImageFragmentBinding binding) {
            super(binding.getRoot());
            image = binding.AdminImageView;
            deleteButton = binding.ImageDeleteButton;
            name = binding.nameOnImageView;
        }



    }
}