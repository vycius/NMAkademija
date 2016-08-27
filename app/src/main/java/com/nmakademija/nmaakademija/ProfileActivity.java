package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.entity.User;

public class ProfileActivity extends AppCompatActivity {
    public static final String EXTRA_ALLOW_EDIT = "com.nmakademija.nmaakademija.allow_edit";
    public static final String EXTRA_USER = "com.nmakademija.nmaakademija.user";

    private ImageView imageView;
    private EditText nameEdit;
    private EditText emailEdit;
    private EditText phoneEdit;
    private EditText bioEdit;
    private Button saveButton;
    private User user;
    private boolean allowEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        allowEdit = getIntent().getBooleanExtra(EXTRA_ALLOW_EDIT, false);
        user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        if (user == null) user = new User();

        imageView = (ImageView) findViewById(R.id.profile_pic_view);
        Glide.with(this).load(user.getImage()).into(imageView);

        nameEdit = (EditText) findViewById(R.id.name_edit);
        nameEdit.setText(user.getName());
        nameEdit.setEnabled(allowEdit);

        emailEdit = (EditText) findViewById(R.id.email_edit);
        emailEdit.setText(user.getEmail());
        emailEdit.setEnabled(allowEdit);

        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        phoneEdit.setText(user.getPhone());
        phoneEdit.setEnabled(allowEdit);

        bioEdit = (EditText) findViewById(R.id.bio_edit);
        bioEdit.setText(user.getBio());
        bioEdit.setEnabled(allowEdit);

        saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(nameEdit.getText().toString());
                user.setEmail(emailEdit.getText().toString());
                user.setPhone(phoneEdit.getText().toString());
                user.setBio(bioEdit.getText().toString());
                finish();
            }
        });
        saveButton.setVisibility(allowEdit ? View.VISIBLE : View.INVISIBLE);
    }
}
