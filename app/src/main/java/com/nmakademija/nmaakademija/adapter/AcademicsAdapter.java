package com.nmakademija.nmaakademija.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Academic;

import java.util.ArrayList;
import java.util.List;


public class AcademicsAdapter extends RecyclerView.Adapter<AcademicsAdapter.UserViewHolder> implements Filterable {

    private List<Academic> academics;
    public List<Academic> allAcademics;
    private OnAcademicSelectedListener onAcademicSelectedListener;

    public AcademicsAdapter(List<Academic> academics, OnAcademicSelectedListener onAcademicSelectedListener) {
        this.academics = academics;
        this.allAcademics = academics;
        this.onAcademicSelectedListener = onAcademicSelectedListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final Academic academic = academics.get(position);
        holder.bindData(academic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAcademicSelectedListener.onAcademicSelected(academic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return academics.size();
    }

    @Override
    public long getItemId(int position) {
        return academics.get(position).getId();
    }

    @Override
    public Filter getFilter() {
        return academicsFilter;
    }

    private Filter academicsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Academic> filtered = new ArrayList<>();

            int sectionId = Integer.parseInt(charSequence.toString());

            for (Academic a : allAcademics) {
                if (sectionId < 0 || a.getSection() == sectionId) {
                    filtered.add(a);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            academics = (List<Academic>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public interface OnAcademicSelectedListener {
        void onAcademicSelected(Academic academic);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        UserViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.user_name);
            image = (ImageView) view.findViewById(R.id.user_image);
        }

        void bindData(final Academic academic) {
            name.setText(academic.getName());

            if (academic.getImage() != null) {
                Glide.with(itemView.getContext())
                        .load(academic.getImage())
                        .error(R.drawable.profile)
                        .into(image);

            }
        }
    }
}
