package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AdminEventsFragmentsBinding;
import com.example.eventapptest2.databinding.AdminImageFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminImageRecyclerViewAdapter extends RecyclerView.Adapter<AdminImageRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Event> allImages;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    //CollectionReference eventListRef = db.collection("ExploreEvents");
    //CollectionReference userListRef = db.collection("users");

    public AdminImageRecyclerViewAdapter(ArrayList<Event> allImagesInput) {
        allImages= allImagesInput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminImageFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.eventForView = allImages.get(position);



        String imageUrl = holder.eventForView.getEventPoster();
        /////////might wanna delete picasso or glide casue it redundant to display twice

        // display image url
        System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);


        holder.deletebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eventPosition = holder.getAbsoluteAdapterPosition();
                if(eventPosition != RecyclerView.NO_POSITION){
                    // When we click delete it gets those event detatils
                    Event deleteImg = allImages.get(eventPosition);


                    // Firebase deleting, delete from "ExploreEvents" and "CreateEvents
                    if(Objects.equals(deleteImg.getEventid(),"")){
                        db.collection("users")
                                .document(deleteImg.getOwner())
                                .update("userProfileImage","");

                    }

                    if(Objects.equals(deleteImg.getOwner(),"")){
                        db.collection("ExploreEvents")
                                .document(deleteImg.getEventid())
                                .update("eventPoster","");

                    }
                    notifyItemRemoved(eventPosition);

                    // From data base remove from ExploreEvents and CreatedEvents"deviceId" getting the owner ID
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return allImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView Imageing;
        public final ImageView deletebut;
        public Event eventForView;



        public ViewHolder(AdminImageFragmentBinding binding) {
            super(binding.getRoot());

            Imageing = binding.AdminImageView;
            deletebut = binding.ImageDeleteButton;

        }



    }
}