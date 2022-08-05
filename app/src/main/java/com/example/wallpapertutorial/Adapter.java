package com.example.wallpapertutorial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHodel> {
    Context context;

    ArrayList<ImageModel> wallpaperList;

    public Adapter(Context context, ArrayList<ImageModel> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
    }

    public Adapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_layout,null,false);
       return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHodel holder, int position) {

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView.setMovementMethod(LinkMovementMethod.getInstance());
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.pexels.com/"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        });

        Glide.with(context).load(wallpaperList.get(position).getSrc().getPortrait()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(context,setwallpaper.class);
                intent2.putExtra("image",wallpaperList.get(position).getSrc().getPortrait());
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        });


    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textView;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
