package com.khalil.qrscanner.ui.home.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.khalil.qrscanner.R;
import com.khalil.qrscanner.domain.module.ScannedItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<ScannedItem> items = new ArrayList<>();
    private static Context context;
    private OnFavClickListener favClickListener;

    public HistoryAdapter(Context context, OnFavClickListener favClickListener){
        this.context = context;
        this.favClickListener = favClickListener;
    }

    public void setItems(List<ScannedItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvTimestamp;
        ImageView imgFav;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            imgFav = itemView.findViewById(R.id.imgFav);
        }

        public void bind(ScannedItem item, int position, OnFavClickListener listener) {
            tvContent.setText(item.getContent());

            String formattedTime = DateFormat.getDateTimeInstance()
                    .format(new Date(item.getTimestamp()));
            tvTimestamp.setText(formattedTime);
            if(item.isFavorite()){
                imgFav.setColorFilter(
                        ContextCompat.getColor(
                                context,
                                R.color.red
                        ), PorterDuff.Mode.SRC_IN);
            }else{
                imgFav.setColorFilter(
                        ContextCompat.getColor(
                                context,
                                R.color.gray
                        ), PorterDuff.Mode.SRC_IN);
            }


            imgFav.setOnClickListener(v -> {
                listener.onFavClick(item, position);
            });
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scanned, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(items.get(position), position, favClickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnFavClickListener {
        void onFavClick(ScannedItem item, int position);
    }

}

