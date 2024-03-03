package com.example.eventapptest2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.app.AlertDialog;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link
 * UserProfileFragment#
 * newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment{
    private Button replaceButton;
    private Button deleteButton;

    private ImageView profilePic;


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference attendeeRef = db.collection("users");

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public User CreateUser(){
        User user = new User("abdimare25", "thatdudeinblue","aamare@gmail.com"
        ,"7807088263", null, null, null,null);
        return user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param {@linkparam1 Parameter 1.
     * @param {@linkparam2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static UserProfileFragment newInstance(String param1, String param2) {
//        UserProfileFragment fragment = new UserProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        User user = CreateUser();

        // delete images
        profilePic = v.findViewById(R.id.userImage);
        replaceButton = v.findViewById(R.id.replaceImageButton);
        deleteButton = v.findViewById(R.id.deleteImageButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to delete?");
                builder.setTitle("Alert");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes",(DialogInterface.OnClickListener)(dialog,which)-> {
                    user.setUserProfileImage(null);
                    attendeeRef.document(user.getUserName()).set(user);
                    dialog.dismiss();
                });

                builder.setNegativeButton("No",(DialogInterface.OnClickListener)(dialog,which)-> {
                    dialog.cancel();
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        // replace images
        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to replace?");
                builder.setTitle("Alert");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes",(DialogInterface.OnClickListener)(dialog,which)-> {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    launcher.launch(intent);

                    dialog.dismiss();
                });

                builder.setNegativeButton("No",(DialogInterface.OnClickListener)(dialog,which)-> {
                    dialog.cancel();
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        return v;
    }
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData()!= null){
                    Uri picture = result.getData().getData();
                    profilePic.setImageURI(picture);
                }
            }
    );

}