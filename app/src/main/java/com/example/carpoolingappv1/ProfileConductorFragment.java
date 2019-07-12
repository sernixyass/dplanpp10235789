package com.example.carpoolingappv1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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

import java.io.IOException;
import java.util.Objects;

import Adapter.PagerAdapterChatNotification;
import Adapter.PagerAdapteurRatingsTrips;

public class ProfileConductorFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton buttonConducEditProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String userID;
    TextView fullNameGrand,phone,willaya,carModel,carNumber;
    ImageButton profilPicC;


    private TabLayout tabLayoutProfileConductor;
    private ViewPager mViewPager ;

    public StorageReference userProfileImageRef;
    Uri imageUri;
    StorageTask uploadTask;


    final static int Gallery_Pick = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_profile_conductor,container,false);
        View view = inflater.inflate(R.layout.fragment_profile_conductor,container,false);


        //link

        fullNameGrand = view.findViewById(R.id.con_big_profile_name);
        //fullName = view.findViewById(R.id.con_full_name);
        //email = view.findViewById(R.id.con_email);
        //birthDate = view.findViewById(R.id.con_birth_date);
        phone = view.findViewById(R.id.con_phone);
        willaya = view.findViewById(R.id.con_wilaya);
        carModel = view.findViewById(R.id.con_car_model);
        carNumber = view.findViewById(R.id.con_car_unique_nbe);

        profilPicC = view.findViewById(R.id.con_profile_pic);

        //ViewPager
        /*tabLayoutProfileConductor = view.findViewById(R.id.tab_layout_profile_conductor);
        mViewPager=view.findViewById(R.id.view_pager_profile_conductor_tab);


        setUpViewPageAdapter(mViewPager);
        tabLayoutProfileConductor.setupWithViewPager(mViewPager);
*/

       // view.findViewById(R.id.add_friend).setOnClickListener(this);
         mAuth = FirebaseAuth.getInstance();

        //retrieve data
        userID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);


        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(MainActivity.currentUserID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        view.findViewById(R.id.logout_C).setOnClickListener(this);

        //view.findViewById(R.id.drop_down_option_menu).setOnClickListener(this);

        buttonConducEditProfile = view.findViewById(R.id.edit_con_profile);
        buttonConducEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), Edit_profile_Conductor.class));
            }
        });

        profilPicC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });


        return view ;

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

    /*public String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/


    public void uploadImg(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading . . .");
        pd.show();


        if(imageUri!=null){
            //Toast.makeText(getContext(), "22222", Toast.LENGTH_SHORT).show();

            /*userProfileImageRef.putFile(imageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(getContext(), "uploaded", Toast.LENGTH_SHORT).show();

                            MainActivity.databaseReference.child("profilePic").setValue(userProfileImageRef.getDownloadUrl().toString());
                        }
                    });*/
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
            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(carpoolingappv1.getApplication().getContentResolver(),imageUri);
            }catch (IOException e){
                e.printStackTrace();
            }*/

            uploadImg();

        }
    }




        private void showData(DataSnapshot dataSnapshot) {
        //fullName.setText(dataSnapshot.child("fullName").getValue().toString());
        fullNameGrand.setText(dataSnapshot.child("fullName").getValue().toString());
        //email.setText(dataSnapshot.child("email").getValue().toString());
        phone.setText(dataSnapshot.child("phone").getValue().toString());
        //birthDate.setText(dataSnapshot.child("bDate").getValue().toString());
        willaya.setText(dataSnapshot.child("wilaya").getValue().toString());
        carModel.setText(dataSnapshot.child("carModel").getValue().toString());
        carNumber.setText(dataSnapshot.child("carKey").getValue().toString());

        if (!Objects.equals(dataSnapshot.child("profilePic").getValue(), "") ){
            //Glide.with(getContext().load(dataSnapshot.child("profilPic").getValue().into(profilPicC));
            if (getActivity()!=null){
                Glide.with(getContext()).load(dataSnapshot.child("profilePic").getValue())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilPicC);
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_C:
                logout();
                break;
        }
    }



    private void logout() {
        MainActivity.mAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }



    private void setUpViewPageAdapter(ViewPager viewPager) {
        PagerAdapteurRatingsTrips Adapter = new PagerAdapteurRatingsTrips(getActivity().getSupportFragmentManager(),tabLayoutProfileConductor.getTabCount());
        viewPager.setAdapter(Adapter);
    }
}
