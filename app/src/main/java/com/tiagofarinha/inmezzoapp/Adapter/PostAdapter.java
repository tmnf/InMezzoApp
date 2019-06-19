package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.MainLogic.YoutubeActivity;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

import java.util.ArrayList;

/* This class handles post row fulfilling */

public class PostAdapter extends DefaultAdapter {

    public PostAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
        super(mContext, objects, layoutId);
    }

    @Override
    protected View fillView(View convertView, ViewGroup parent, Adaptable obj) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView, (Post) obj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.post = (Post) obj;

        LoginUtils.putInto(holder.pic, holder.post.getOwner());
        holder.post_name.setText(holder.post.getOwner().getUser_name());
        holder.post_pub_date.setText(holder.post.getDate_pub());
        holder.post_text.setText(holder.post.getPost_text());

        addUserListener(holder);
        youtubeViewHandle(holder);

        return convertView;
    }

    private void youtubeViewHandle(ViewHolder h) {
        YoutubeVideo video = ResourceLoader.getInstance().findVideoWithUrl(h.post.getUrl());
        if (video == null) {
            h.thumb.setVisibility(View.GONE);
            h.play.setVisibility(View.GONE);
        } else {
            LoginUtils.fillView(h.thumb, video.getThumbnail());
            setListener(video.getId(), h);

            h.thumb.setVisibility(View.VISIBLE);
            h.play.setVisibility(View.VISIBLE);
        }
    }

    private void setListener(final String id, ViewHolder h) {
        h.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), YoutubeActivity.class);
                intent.putExtra("url", id);
                getContext().startActivity(intent);
            }
        });
    }

    private void addUserListener(final ViewHolder h) {
        h.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMethods.getInstance().goToProfilePage(h.post.getOwner());
            }
        });
    }

    /* Caching Views */
    private class ViewHolder {
        TextView post_name, post_pub_date, post_text;
        ImageView pic, thumb, play;
        ConstraintLayout container;
        Post post;

        ViewHolder(View convertView, Post post) {
            this.post = post;

            pic = convertView.findViewById(R.id.post_pic);
            post_name = convertView.findViewById(R.id.post_name);
            post_pub_date = convertView.findViewById(R.id.post_pub_date);
            post_text = convertView.findViewById(R.id.post_message);
            thumb = convertView.findViewById(R.id.youtube_tumbnail);
            play = convertView.findViewById(R.id.play_button);

            container = convertView.findViewById(R.id.post_container);
        }
    }

}
