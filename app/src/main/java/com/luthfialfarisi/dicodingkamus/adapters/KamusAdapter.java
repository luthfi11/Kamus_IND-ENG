package com.luthfialfarisi.dicodingkamus.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luthfialfarisi.dicodingkamus.R;
import com.luthfialfarisi.dicodingkamus.activities.DetailActivity;
import com.luthfialfarisi.dicodingkamus.models.KamusItems;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.ViewHolder> {

    private ArrayList<KamusItems> kamusData = new ArrayList<>();
    private Context context;

    public KamusAdapter(ArrayList<KamusItems> kamusData, Context context) {
        this.context = context;
        this.kamusData = kamusData;
    }

    public void setData(ArrayList<KamusItems> items) {
        kamusData.clear();
        kamusData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public KamusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kamus_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KamusAdapter.ViewHolder holder, final int position) {
        holder.tvWord.setText(kamusData.get(position).getWord());
        holder.tvDesc.setText(kamusData.get(position).getDesc());

        holder.kamusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KamusItems item = kamusData.get(position);

                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_WORD, item.getWord());
                intent.putExtra(DetailActivity.EXTRA_DESC, item.getDesc());
                
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kamusData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord, tvDesc;
        LinearLayout kamusItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            kamusItem = itemView.findViewById(R.id.kamusItem);
        }
    }
}
