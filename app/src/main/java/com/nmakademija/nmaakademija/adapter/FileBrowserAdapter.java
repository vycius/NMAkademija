package com.nmakademija.nmaakademija.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Academic;
import com.nmakademija.nmaakademija.entity.FileItem;

import java.util.List;

public class FileBrowserAdapter extends RecyclerView.Adapter<FileBrowserAdapter.ViewHolder> {

    private List<FileItem> files;

    public FileBrowserAdapter(List<FileItem> files) {
        this.files = files;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_file, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FileItem fileItem = files.get(i);
        viewHolder.title.setText(fileItem.name);
        viewHolder.date.setText(fileItem.date.toString());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.file_name);
            date = view.findViewById(R.id.file_date);
        }
    }
}
