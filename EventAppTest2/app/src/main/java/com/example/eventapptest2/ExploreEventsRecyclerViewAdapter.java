package com.example.eventapptest2;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.eventapptest2.databinding.FragmentExploreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExploreEventsRecyclerViewAdapter extends RecyclerView.Adapter<ExploreEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;
    List<Event> sevents;
    String did;
    //private OnExploreButtonClickListener mListener;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  CollectionReference saveevent;
    private static FragmentManager frag;
    private BottomNavigationView bottomnav;

    /**
     * a constructor to display the explore events array adapter
     * @param items
     * @param saveEvents
     * @param id
     * @param freg
     * @param bottomNavigationview
     */
    public ExploreEventsRecyclerViewAdapter(List<Event> items,List<Event> saveEvents,String id,FragmentManager freg,BottomNavigationView bottomNavigationview) {
        events = items;
        sevents = saveEvents;
        did = id;
        saveevent = db.collection("SavedEvents"+id);
        frag = freg;
        bottomnav = bottomNavigationview;

    }

    /**
     * inflates the fragment of the event in the recyler view
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentExploreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
        int postini = position;
        holder.EventForView = events.get(position);
        holder.ExploreEventName.setText(events.get(position).getEventName());
        holder.Eventdate.setText(events.get(position).getEventDate());
        holder.Eventlocation.setText(events.get(position).getEventLocation());
        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2F7aa31d9e-1539-49f9-bc21-4ec823cdbfdb?alt=media&token=7afc1c14-11f4-48c7-8ab1-99ee1e96eaa4";
        Picasso.get().load(events.get(position).getEventPoster()).into(holder.Imageing);
        String imageUrl = events.get(position).getEventPoster();
         /////////might wanna delete picasso or glide casue it redundant to display twice

        // display image url
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
//                    final CollectionReference attendeeRef = db.collection("Attendee" + events.get(postini).getEventid());
//                    ArrayList<User> attendees = new ArrayList<>();
//                attendeeRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                            if (error != null) {
//                                return;
//                            }
//
//                            attendees.clear(); // Clear the old list
//                            for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
////                    String eventId = doc.getId();
//                                if (doc.exists()) {
//                                    attendees.add(new User());
//                                }// Assuming "karan" is a placeholder for organizer
//                            }
//
//
//                        }
//                    });
//                //so theses ifs did nothing also doesnt check if user has already added it to the list so be careful
//                if (events.get(postini).getEventLimit() == ""){saveevent.add(events.get(postini));
//                                                                        attendeeRef.add(new User());}
//
//                else if (attendees.size() <= Integer.parseInt(events.get(postini).getEventLimit())) {saveevent.add(events.get(postini));
//                    attendeeRef.add(new User());}



                //^^ all the code above is to try to save an event


//                if (mListener != null) {
//                    if (postini != RecyclerView.NO_POSITION) {
//                        mListener.onExploreButtonClick();
//                    }
//                }

                // now we try to hide bottom nav to show a detials page with a back arrow



               // BottomNavigationView bottomnav = v.findViewById(R.id.bottomNavView);
//                //bottomnav.setVisibility(View.INVISIBLE);
//
//
                FragmentTransaction fragmentTransaction = frag.beginTransaction();
                //System.out.println("testtttttttttt " + testuser.getCreatedEvents());
                fragmentTransaction.replace(R.id.framelayout, new ExploreEventDetsFragment(events.get(position),frag)); //explore is temp
                fragmentTransaction.commit();
                if (bottomnav != null) {
                    bottomnav.getMenu().clear();
                    bottomnav.inflateMenu(R.menu.go_back_menu);
                    v.postDelayed(() -> bottomnav.setSelectedItemId(R.id.invisibleforgoingback), 100);
                }




            }
        });

    }
//    public interface OnExploreButtonClickListener {
//        void onExploreButtonClick();
//    }
//
//
//
//    public void setOnExploreButtonClickListener(OnExploreButtonClickListener listener) {
//        mListener = listener;
//    }

    /**
     * gets the events item
     * @return the number of items in the event
     */
    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * creates a new view to display the event as a fragment
     * in the ReyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView ExploreEventName;

        public final TextView Eventlocation;
        public final TextView Eventdate;
        public final ImageView Imageing;
        public Event EventForView;
        public Button button;


        /**
         * binds the XML ids to the events info
         * @param binding
         */
        public ViewHolder(FragmentExploreBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.ExploreEventTitle;
            Eventlocation = binding.ExploreEventlocation;
            Eventdate = binding.ExploreEventDate;
            Imageing = binding.ExploreuserImage;
            button = binding.ExploreEventDetialsButton;

        }


        /**
         *
         * @return the date
         */
        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}