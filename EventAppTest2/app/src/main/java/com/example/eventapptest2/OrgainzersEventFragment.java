// OrgainzersEventFragment.java

package com.example.eventapptest2;

import android.os.Bundle;
<<<<<<< Updated upstream

import androidx.fragment.app.Fragment;

=======
import android.util.Log;
>>>>>>> Stashed changes
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< Updated upstream
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrgainzersEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrgainzersEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
=======
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapptest2.placeholder.PlaceholderContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrgainzersEventFragment extends Fragment {

    private static final String TAG = "OrgainzersEventFragment";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
>>>>>>> Stashed changes

    public OrgainzersEventFragment() {
        // Required empty public constructor
    }

<<<<<<< Updated upstream
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrgainzersEventFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static OrgainzersEventFragment newInstance(String param1, String param2) {
//        OrgainzersEventFragment fragment = new OrgainzersEventFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
=======
    public static OrgainzersEventFragment newInstance(int columnCount) {
        OrgainzersEventFragment fragment = new OrgainzersEventFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
>>>>>>> Stashed changes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< Updated upstream
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orgainzers_event, container, false);
    }
}
=======
        View view = inflater.inflate(R.layout.open_events_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            // Fetch Firestore data
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("events")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Event> events = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Event event = document.toObject(Event.class);
                                    events.add(event);
                                }
                                recyclerView.setAdapter(new OpenEventsRecyclerViewAdapter(events));
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        return view;
    }
}
>>>>>>> Stashed changes
