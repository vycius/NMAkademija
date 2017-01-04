package com.nmakademija.nmaakademija.adapter;

import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.viewholder.UserViewHolder;
import com.nmakademija.nmaakademija.entity.User;


public class AcademicsAdapter extends FirebaseRecyclerAdapter<User, UserViewHolder> {

    private OnAcademicSelectedListener onAcademicSelectedListener;

    public AcademicsAdapter(Query academicsQuery, OnAcademicSelectedListener onAcademicSelectedListener) {
        super(User.class,
                R.layout.list_item_user,
                UserViewHolder.class,
                academicsQuery);

        this.onAcademicSelectedListener = onAcademicSelectedListener;
    }

    @Override
    protected void populateViewHolder(UserViewHolder viewHolder, final User user, int position) {
        viewHolder.bindData(user);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAcademicSelectedListener.onAcademicSelected(user);
            }
        });
    }


    public interface OnAcademicSelectedListener {
        void onAcademicSelected(User user);
    }
}
