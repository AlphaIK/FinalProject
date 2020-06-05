package com.example.final_project;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class videoAdapt extends RecyclerView.Adapter<videoAdapt.NumberViewHolder> {

    private int item_number;
    private List<videoResponse> data_list;
    private Context context;

    public videoAdapt(List<videoResponse> messages, Context context){
        data_list = messages;
        item_number = messages.size();
        this.context = context;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttachToParentImmediately);

        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return item_number;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nickname;
        TextView description;
        TextView like_count;
        ImageView avatar;
        ImageView poster;
        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nickname);
            description = itemView.findViewById(R.id.description);
            like_count = itemView.findViewById(R.id.like_count);
            avatar = itemView.findViewById(R.id.avatar);
            poster = itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            videoResponse message = data_list.get(position);
            //设置头像
            Glide.with(context)
                    .load(message.avatar)
                    .into(avatar);
            //截取视频第一帧作为封面图
            Glide.with(context)
                    .setDefaultRequestOptions(
                            new RequestOptions()
                                    .frame(0)
                                    .centerCrop()
                    )
                    .load(message.feedurl)
                    .into(poster);
            nickname.setText("@" + message.nickname);
            description.setText(message.description);
            like_count.setText(message.likecount);
        }

        @Override
        public void onClick(View v){
            int clickedPosition = getAdapterPosition();
            Bundle bundle = new Bundle();
            videoResponse message = data_list.get(clickedPosition);
            Intent intent = new Intent(context, videoPlayer.class);
            bundle.putString("description",message.get_description());
            bundle.putString("feedurl",message.get_url());
            bundle.putString("avatar",message.get_avatar());
            bundle.putString("nickname",message.get_nickname());
            bundle.putString("likecount",message.get_like_count());
            bundle.putString("_id",message.get_id());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }
}
