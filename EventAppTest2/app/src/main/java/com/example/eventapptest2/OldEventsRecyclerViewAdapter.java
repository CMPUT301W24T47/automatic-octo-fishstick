package com.example.eventapptest2;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.FragmentOldQrsBinding;
import com.example.eventapptest2.databinding.FragmentOrgainzersEventBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OldEventsRecyclerViewAdapter extends RecyclerView.Adapter<OldEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String id;

    private static FragmentManager frag;
    public OldEventsRecyclerViewAdapter(List<Event> items,String did, FragmentManager freg) {
        events = items;
        id = did;
        frag = freg;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOldQrsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.EventForView = events.get(position);
        holder.ExploreEventName.setText(events.get(position).getEventName());
        holder.Eventdate.setText(events.get(position).getEventDate());
        holder.Eventlocation.setText(events.get(position).getEventLocation());
        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2F7aa31d9e-1539-49f9-bc21-4ec823cdbfdb?alt=media&token=7afc1c14-11f4-48c7-8ab1-99ee1e96eaa4";
       // Picasso.get().load(events.get(position).getEventPoster()).into(holder.Imageing);
        String imageUrl = events.get(position).getEventPoster();
        System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);



        holder.date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //        https://techprogrammingideas.blogspot.com/2020/05/android-edit-text-to-show-dd-mm-yyyy.html
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    holder.date.setText(current);
                    holder.date.setSelection(sel < current.length() ? sel : current.length());


                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need a way to get a new date... rn our code in main will just not care abt this so we need change the date







                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();

                        Date date1 = null;
                        try {
                            date1 = simpleDateFormat.parse( holder.date.getText().toString());
                            //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + date.getText().toString() + "   >=   " + date1);
                        } catch (ParseException e) {
                            e.printStackTrace(); // Handle parsing exception appropriately
                        }

                        if (date1 != null && date1.compareTo(currentDate) >= 0){
                        holder.EventForView.setEventDate( holder.date.getText().toString());



                        final CollectionReference createeventsRef = db.collection("CreateEvents" + id);
                        final CollectionReference OldQreventsRef = db.collection("OldQrsList" + id);
                        // well need to do something like we did in main to get data for the attende list and check if length is < the length of limit
                        createeventsRef.document(holder.EventForView.getEventid()).set(holder.EventForView);
                        OldQreventsRef.document(holder.EventForView.getEventid()).delete();
                        }






                //events.remove(position);
            }
        });

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
        public Button button;
        EditText date;


        public ViewHolder(@NonNull FragmentOldQrsBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.ExploreEventTitlee;
            Eventlocation = binding.ExploreEventlocatione;
            Eventdate = binding.ExploreEventDatee;
            Imageing = binding.ExploreuserImagee;
            button = binding.ExploreEventDetialsButtone;
            date = binding.newdate;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}