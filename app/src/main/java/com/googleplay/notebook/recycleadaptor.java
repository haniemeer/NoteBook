package com.googleplay.notebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



    public class recycleadaptor extends RecyclerView.Adapter<recycleadaptor.ViewHolder> {
        List<notedata> localDataSet = new ArrayList<>();

        public recycleadaptor(List<notedata> localDataSet) {
            this.localDataSet = localDataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            notedata text = localDataSet.get(position);
            holder.textView.setText(text.getText());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.textView);
            }
        }
    }






