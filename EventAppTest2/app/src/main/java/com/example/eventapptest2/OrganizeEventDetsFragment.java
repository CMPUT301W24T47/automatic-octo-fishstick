package com.example.eventapptest2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class OrganizeEventDetsFragment extends Fragment {
    Event event;

    public OrganizeEventDetsFragment(Event events){
        event = events;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_detail, container, false);
        ImageView detimage = v.findViewById(R.id.EventImageOrgdet);
        TextView detname = v.findViewById(R.id.EventDetailNameOrgdet);
        TextView detdate = v.findViewById(R.id.CalenderDateOrgdet);
        TextView detloc = v.findViewById(R.id.LocationNameOrgdet);
        TextView detdesc = v.findViewById(R.id.EventDescriptionOrgdet);
        TextView detlim = v.findViewById(R.id.AttendeeLimitNumOrgdet);
        detlim.setText(event.getEventLimit());
        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        Picasso.get().load(event.getEventPoster()).into(detimage);
        //set the share button make it functional
        ImageView checkinsharebut = v.findViewById(R.id.ShareQRIconOrgdet);

        checkinsharebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//// Example: content://com.google.android.apps.photos.contentprovider/...
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(event.getSignINQR()));
//                shareIntent.setType("image/jpeg");
//                startActivity(Intent.createChooser(shareIntent, null));


                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, event.getSignINQR());

// (Optional) Here you're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Introducing content previews");

// (Optional) Here you're passing a content URI to an image to be displayed
                sendIntent.setData(Uri.parse(event.getSignINQR()));
                sendIntent.setType("image/jpeg");
                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Show the Sharesheet
                startActivity(Intent.createChooser(sendIntent, null));



            }
        });

        ImageView postersharebut = v.findViewById(R.id.ShareIconOrgdet);

        postersharebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//// Example: content://com.google.android.apps.photos.contentprovider/...
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(event.getSignINQR()));
//                shareIntent.setType("image/jpeg");
//                startActivity(Intent.createChooser(shareIntent, null));


                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, event.getQrUrl());

// (Optional) Here you're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Introducing content previews");

// (Optional) Here you're passing a content URI to an image to be displayed
                sendIntent.setData(Uri.parse(event.getQrUrl()));
                sendIntent.setType("image/jpeg");
                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Show the Sharesheet
                startActivity(Intent.createChooser(sendIntent, null));



            }
        });





        return v;
    }
}
