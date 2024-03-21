package com.example.eventapptest2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneratedQRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneratedQRFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private Button generate_qr_button;
    public GeneratedQRFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneratedQRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneratedQRFragment newInstance(String param1, String param2) {
        GeneratedQRFragment fragment = new GeneratedQRFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generated_q_r, container, false);
        String examplestring = "Hello";
        generate_qr_button = (Button) rootView.findViewById(R.id.generate_qr_button);
        imageView = (ImageView) rootView.findViewById(R.id.qr_code_image);
        generate_qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(examplestring,BarcodeFormat.QR_CODE,300,300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);
                    BitMatrix bitMatrix1 = 
                }catch (WriterException e){
                    throw new RuntimeException(e);
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

}
