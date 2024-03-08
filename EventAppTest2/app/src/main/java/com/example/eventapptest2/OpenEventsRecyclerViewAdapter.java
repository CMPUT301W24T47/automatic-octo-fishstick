package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.FragmentOrgainzersEventBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an adapter to display the organizers events
 */
public class OpenEventsRecyclerViewAdapter extends RecyclerView.Adapter<OpenEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;
    private static FragmentManager frag;
    private BottomNavigationView bottomnav;
    private User inte;

    /**
     * constructor
     * @param items
     * @param freg
     * @param bottomNavigationview
     * @param inting
     */
    public OpenEventsRecyclerViewAdapter(List<Event> items,FragmentManager freg,BottomNavigationView bottomNavigationview,User inting) {
        events = items;
        frag = freg;
        bottomnav = bottomNavigationview;
        inte = inting;
    }

    /**
     * creates the view of event recycler for the organizer
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return the view of the recycler adapter
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOrgainzersEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * binds the events items to the xml ids
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // well need to do something like we did in main to get data for the attende list and check if length is < the length of limit
                //as well we should do else elif b/c the code will be faster for no limit then just check getlimit == ""
                //to do the attende chcek in the just add a optinal boolean to the user init clause and only use it for create user as attendee here
                FragmentTransaction fragmentTransaction = frag.beginTransaction();
                //System.out.println("testtttttttttt " + testuser.getCreatedEvents());

                //inte = position;
                inte.setLastsaved(position);
                fragmentTransaction.replace(R.id.framelayout, new OrganizeEventDetsFragment(events.get(position))); //explore is temp -----------------------------change


                fragmentTransaction.commit();
                if (bottomnav != null) {
                    bottomnav.getMenu().clear();
                    bottomnav.inflateMenu(R.menu.event_nav_menu);
                    v.postDelayed(() -> bottomnav.setSelectedItemId(R.id.EventDetailsNav), 100);
                }



            }
        });

    }


    @Override
    public int getItemCount() {

        return events.size();
    }

    /**
     * creates the fragment shown in the Recyler View of the OrganizersEventFragment
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView ExploreEventName;
        public final TextView Eventlocation;
        public final TextView Eventdate;
        public final ImageView Imageing;
        public Event EventForView;
        public Button button;


        public ViewHolder(@NonNull FragmentOrgainzersEventBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.OpenEventTitle;
            Eventlocation = binding.OpenEventlocation;
            Eventdate = binding.OpenEventDate;
            Imageing = binding.OpenUserImage;
            button = binding.OpenEventButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}