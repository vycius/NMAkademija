package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nmakademija.nmaakademija.entity.User;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText nameEdit;
    private EditText emailEdit;
    private EditText phoneEdit;
    private EditText bioEdit;
    private Button saveButton;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = new User();

        imageView = (ImageView) findViewById(R.id.profile_pic_view);

        nameEdit = (EditText) findViewById(R.id.name_edit);
        nameEdit.setText(user.getName());

        emailEdit = (EditText) findViewById(R.id.email_edit);
        emailEdit.setText(user.getEmail());

        phoneEdit = (EditText) findViewById(R.id.phone_edit);
        phoneEdit.setText(user.getPhone());

        bioEdit = (EditText) findViewById(R.id.bio_edit);
        bioEdit.setText(user.getBio());

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
    }
}
