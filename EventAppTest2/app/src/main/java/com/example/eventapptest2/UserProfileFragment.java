//package com.example.eventapptest2;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//
//import org.w3c.dom.Text;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link UserProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class UserProfileFragment extends Fragment {
//
////    // TODO: Rename parameter arguments, choose names that match
////    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
////    private static final String ARG_PARAM1 = "param1";
////    private static final String ARG_PARAM2 = "param2";
////
////    // TODO: Rename and change types of parameters
////    private String mParam1;
////    private String mParam2;
//    User user;
//
//    public UserProfileFragment(User testuser) {
//        // Required empty public constructor
//        this.user = testuser;
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment UserProfileFragment.
//     */
//    // TODO: Rename and change types and number of parameters
////    public static UserProfileFragment newInstance(String param1, String param2) {
////        UserProfileFragment fragment = new UserProfileFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
////        return fragment;
////    }
////
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
////        }
////    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
//
//        TextView test = v.findViewById(R.id.usernameTextViewProfile);
//
//        test.setText(user.getUserName());
//        //settext,settext
//        //editing --> new window --> confirm --> usersref.document(id).set(User edituser)
//        // user.setposter(string imageurl)
//
//
//
//        return v;
//    }
//}

package com.example.eventapptest2;


import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link
 * UserProfileFragment#
 * newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment{
    private ImageView profilePic;
    private TextView textOnProfilePic;
    private User user;
    private Button editProfileButton;
    private TextView userNameTextView;
    private TextView userHomePageTextView;
    private TextView userEmailTextView;
    private TextView userPhoneNumTextView;
    // Geolocation switch
    private SwitchCompat geolocation;

    private static FragmentManager frag;
    private BottomNavigationView bottomnav;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // CollectionReference will store the collection of Users
    //  - Which then each user will store different information
    private final CollectionReference usersRef = db.collection("users");
//    private User user;
    public UserProfileFragment(User useri,FragmentManager freg,BottomNavigationView bottomNavigationview) {
        // Required empty public constructor
        user = useri;
        frag = freg;
        bottomnav = bottomNavigationview;

    }

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData()!= null){
                    Uri picture = result.getData().getData();

                    //getting successful image
                    assert picture != null;
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images/" + UUID.randomUUID().toString());
                    storageRef.putFile(picture) .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get download URL
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            //  adding imageURL to the db-
                            String imageURL = uri.toString();
                            user.setUserProfileImage(imageURL);
                            //update the user
                            usersRef.document(user.getDeviceId()).set(user);
                        });
                    });

                    profilePic.setVisibility(View.VISIBLE);

                    profilePic.setImageURI(picture);


                    //profilePic.setImageURI(Uri.parse(user.getUserProfileImage()));
                    /*updates image user uses in firebase*/
                    textOnProfilePic.setText(null);
//                    usersRef.document(user.getUserName()).set(user);

                }

            });

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null){
                    String updatedUserName = result.getData().getStringExtra("updatedUserName");
                    String updatedUserHomepage = result.getData().getStringExtra("updatedUserHomepage");
                    String updatedUserEmail = result.getData().getStringExtra("updatedUserEmail");
                    String updatedUserPhoneNum = result.getData().getStringExtra("updatedUserPhoneNum");

                    userNameTextView.setText(updatedUserName);
                    userHomePageTextView.setText(updatedUserHomepage);
                    userEmailTextView.setText(updatedUserEmail);
                    userPhoneNumTextView.setText(updatedUserPhoneNum);

                    user.setUserName(updatedUserName);
                    user.setUserHomepage(updatedUserHomepage);
                    user.setUserEmail(updatedUserEmail);
                    user.setUserPhoneNumber(updatedUserPhoneNum);

                    usersRef.document(user.getDeviceId()).set(user);

                }
            }
    );



//    public User CreateUser(){
//        User user = new User("abdimare25","abdimare25", "thatdudeinblue","aamare@gmail.com"
//                ,"7807088263", null, null, null,null);
//        return user;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);


        geolocation = v.findViewById(R.id.UsergeolocationSwitch);


        if (user.getTracking() instanceof String){
        if (user.getTracking().equals("on")){
            geolocation.setChecked(true);
        }
        }



        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (geolocation.isChecked()){
                    user.setTracking("on");
                    usersRef.document(user.getDeviceId()).set(user);
                }
                else {user.setTracking("off");
                    usersRef.document(user.getDeviceId()).set(user);}
            }
        });


        ArrayList<String> admin_ids = new ArrayList<>();
        admin_ids.add("fae6f715c0a59b56"); //karans at home phone
        admin_ids.add("019120a48899b3ec"); //abdimac




        if( admin_ids.contains(user.getDeviceId()) ){
            Button adminbut = v.findViewById(R.id.admin_open_button);

            adminbut.setVisibility(View.VISIBLE);
            adminbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bottomnav != null) {
                        bottomnav.getMenu().clear();
                        bottomnav.inflateMenu(R.menu.admin_nav_menu);
                        v.postDelayed(() -> bottomnav.setSelectedItemId(R.id.AdminEvents), 100);
                    }



                }
            });




        }





















//        user = CreateUser();





        profilePic = v.findViewById(R.id.userImage);
        textOnProfilePic = v.findViewById(R.id.textOnImage);
        Button replaceButton = v.findViewById(R.id.replaceImageButton);
        Button deleteButton = v.findViewById(R.id.deleteImageButton);

        Drawable originalPic = profilePic.getDrawable();

        //Picasso.get().load(user.getUserProfileImage()).into(profilePic);
        String imageUrl = user.getUserProfileImage();
        /////////might wanna delete picasso or glide casue it redundant to display twice

        // display image url
        System.out.println("Image URL: " + imageUrl);
        Glide.with(profilePic.getContext())
                .load(imageUrl)
                .into(profilePic);
        if(user.getUserProfileImage() == "" || user.getUserProfileImage() == null){

            generateImage(originalPic);}
//        usersRef.document(user.getUserName()).set(user);
//

        deleteButton.setOnClickListener(v1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to delete?");
            builder.setTitle("Alert");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which)-> {
                    user.setUserProfileImage("");
//                  user.setUserProfileImage(originalPic.toString());
//                    profilePic.setImageDrawable(originalPic);
                //needed to delete image
                //usersRef.document(user.setUserProfileImage())
                setImageDel(originalPic);
                //usersRef.document(user.getUserName()).set(user);
                dialog.dismiss();
            });

            builder.setNegativeButton("No", (dialog, which)-> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });
        // replace images
        replaceButton.setOnClickListener(v12 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to replace?");
            builder.setTitle("Alert");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which)-> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);
                dialog.dismiss();
            });

            builder.setNegativeButton("No", (dialog, which)-> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });

        // Edit users profile
        editProfileButton = v.findViewById(R.id.editUsernameButton);
        // Inputed user information
        userNameTextView = v.findViewById(R.id.usernameTextViewProfile);
        userHomePageTextView = v.findViewById(R.id.homepageEditText);
        userEmailTextView = v.findViewById(R.id.emailEditText);
        userPhoneNumTextView = v.findViewById(R.id.phoneEditText);








        // Store user information
        // Testing profile information
        // Set the text of all user information
        userNameTextView.setText(user.getUserName());
        userHomePageTextView.setText(user.getUserHomepage());
        userEmailTextView.setText(user.getUserEmail());
        userPhoneNumTextView.setText(user.getUserPhoneNumber());

        String userName = userNameTextView.getText().toString();
        String userHomepage = userHomePageTextView.getText().toString();
        String userEmail = userEmailTextView.getText().toString();
        String userPhoneNum = userPhoneNumTextView.getText().toString();


        editProfileButton.setOnClickListener(v1 -> {
            // Get text of user information
//            String userName = userNameTextView.getText().toString();
//            String userHomepage = userHomePageTextView.getText().toString();
//            String userEmail = userEmailTextView.getText().toString();
//            String userPhoneNum = userPhoneNumTextView.getText().toString();
            // Carry the current information of the user and show it on the EdutUserProfile Activity
            Intent intent = new Intent(getActivity(), EditUserProfileActivity.class);
            intent.putExtra("userName", user.getUserName());
            intent.putExtra("homepage", user.getUserHomepage());
            intent.putExtra("email", user.getUserEmail());
            intent.putExtra("phoneNum", user.getUserPhoneNumber());


            editProfileLauncher.launch(intent);

        });
        return v;
    }

    public void generateImage(Drawable originalPic){
        if((user.getUserName() != null) && (user.getUserProfileImage()==null) || (user.getUserProfileImage() == "")){
            textOnProfilePic.setText(user.getUserName());
            profilePic.setVisibility(View.INVISIBLE);
        }
        if((user.getUserName() == null) && (user.getUserProfileImage()==null) || (user.getUserProfileImage() == "")){
            profilePic.setImageDrawable(originalPic);
        }

    }


    public void setImageDel(Drawable originalPic){
        //either delete the image on firestore so everything in app "updates" with the image deleted
        // on text edits and profile image edits we need to update these db collections:
        // for eventid in savedevents of current user:
        // attendeelist = firbase collection AttendeeList + eventid
        //attendeelist.document(userid).set(current user)
        // we can just move the generate image code to the attendeeList so we aint gotta figure out how to make it a URL
        //also to make life easier since we have a "attendee" User from the class lets add a field for checkin count str ofc
        //check in count updated on a sucsseful scan

        profilePic.setVisibility(View.INVISIBLE);
        textOnProfilePic.setText(user.getUserName());
        profilePic.setImageDrawable(originalPic);
        user.setUserProfileImage(null); // alway make it null when deleting images its just easir that way
        usersRef.document(user.getDeviceId()).set(user);

    }

}