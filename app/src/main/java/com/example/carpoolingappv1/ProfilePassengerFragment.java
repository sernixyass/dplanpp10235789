package com.example.carpoolingappv1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class ProfilePassengerFragment extends Fragment implements View.OnClickListener{


    private FloatingActionButton buttonPassEditProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String userID;
    private TextView fullNameGrand,phone,willaya;


    public StorageReference userProfileImageRef;
    Uri imageUri;

    final static int Gallery_Pick = 1;

    ImageButton profilPicP;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
       // return inflater.inflate(R.layout.fragment_profile_passenger,container,false);
        final View view = inflater.inflate(R.layout.fragment_profile_passenger,container,false);



        //view.findViewById(R.id.add_friend).setOnClickListener(this);
        buttonPassEditProfile = view.findViewById(R.id.edit_pass_Pprofile);
        buttonPassEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Passenger.class));
            }
        });

        //link

        fullNameGrand = view.findViewById(R.id.user_big_profile_name);
        phone = view.findViewById(R.id.pass_phone);
        willaya = view.findViewById(R.id.pass_wilaya);


        mAuth = FirebaseAuth.getInstance();
        //retrieve data
        userID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(MainActivity.currentUserID);

        profilPicP = view.findViewById(R.id.pass_profile_pic);

        profilPicP.setOnClickListener(this);
        return view ;
    }


    private void showData(DataSnapshot dataSnapshot) {
        //fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        fullNameGrand.setText(dataSnapshot.child("fullName").getValue().toString());
        //email.setText(dataSnapshot.child("email").getValue().toString());
        phone.setText(dataSnapshot.child("phone").getValue().toString());
        //birthDate.setText(dataSnapshot.child("bDate").getValue().toString());
        willaya.setText(dataSnapshot.child("wilaya").getValue().toString());

        if (!Objects.equals(dataSnapshot.child("profilePic").getValue(), "") ){
            //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
            if (getActivity()!=null){
                Glide.with(getContext()).load(dataSnapshot.child("profilePic").getValue())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilPicP);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_P:
                logout();
                break;
            case R.id.pass_profile_pic:
                chooseImg();
                break;
        }
    }

    public void chooseImg(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(android.content.Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        //galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivityForResult(galleryIntent, Gallery_Pick);
        //Toast.makeText(getContext(), "55555", Toast.LENGTH_SHORT).show();

        //startActivityForResult(Intent.createChooser(galleryIntent,"select Picture"),Gallery_Pick);
    }

    public void uploadImg(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading . . .");
        pd.show();


        if(imageUri!=null){
            userProfileImageRef.putFile(imageUri)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }
                            return userProfileImageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downUri = task.getResult();
                        MainActivity.databaseReference.child("profilePic").setValue(downUri.toString());
                        pd.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Error downloading", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == Activity.RESULT_OK && data != null ) {
            imageUri = data.getData();

            uploadImg();

        }
    }


    private void logout() {
        MainActivity.mAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}