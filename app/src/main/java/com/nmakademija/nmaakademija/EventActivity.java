package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nmakademija.nmaakademija.adapter.FileBrowserAdapter;
import com.nmakademija.nmaakademija.entity.FileItem;
import com.nmakademija.nmaakademija.entity.ScheduleEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends BaseActivity {
    public static final String EXTRA_EVENT = "event";

    private RatingBar rating;
    private TextView lecturer;
    private TextView eventTime;
    private TextView description;
    private RecyclerView fileRecycler;
    private FloatingActionButton attachFAB;

    private ScheduleEvent event;

    private List<FileItem> files;
    private RecyclerView recyclerView;
    private FileBrowserAdapter fileBrowserAdapter;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private Uri imgURI;

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static  final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        event = getIntent().getParcelableExtra(EXTRA_EVENT);

        rating = findViewById(R.id.rating);
        lecturer = findViewById(R.id.lecturer);
        eventTime = findViewById(R.id.event_time);
        description = findViewById(R.id.description);
        fileRecycler = findViewById(R.id.recycler);
        attachFAB = findViewById(R.id.attach_fab);

        lecturer.setText(event.getLecturer());
        eventTime.setText(event.getStartDate().toString());
        description.setText("fix me");

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        files = new ArrayList<>();
        fileBrowserAdapter = new FileBrowserAdapter(files);
        fileRecycler.setLayoutManager(new LinearLayoutManager(this));
        fileRecycler.setAdapter(fileBrowserAdapter);
    }

        public void btnBrowse_Click(View v){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
            imgURI = data.getData();

        try{
            Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgURI);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
