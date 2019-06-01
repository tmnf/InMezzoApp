package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
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
    protected void fillView(View view, Adaptable obj) {
        Post post = (Post) obj;
        TextView post_name, post_pub_date, post_text, hidden_url;
        /* REFERENCES */

        ImageView pic = view.findViewById(R.id.post_pic);
        post_name = view.findViewById(R.id.post_name);
        post_pub_date = view.findViewById(R.id.post_pub_date);
        post_text = view.findViewById(R.id.post_message);

        // Contains url associated to the video
        hidden_url = view.findViewById(R.id.hidden_url);

        /* Data Handle */

        hidden_url.setVisibility(View.INVISIBLE);
        youtubeViewHandle(view, post, hidden_url);

        LoginUtils.putInto(pic, post.getOwner());
        post_name.setText(post.getOwner().getUser_name());
        post_pub_date.setText(post.getDate_pub());
        post_text.setText(post.getPost_text());
    }

    private void youtubeViewHandle(View postListView, Post post, TextView hidden_url) {
        ImageView thumb = postListView.findViewById(R.id.youtube_tumbnail);

        if (post.getUrl().isEmpty()) {
            ConstraintLayout container = postListView.findViewById(R.id.post_container);
            ImageView play = postListView.findViewById(R.id.play_button);
            container.removeView(play);
            container.removeView(thumb);
        } else {
            YoutubeVideo video = ResourceLoader.findVideoWithUrl(post.getUrl());
            if (video != null && thumb != null) {
                hidden_url.setText(video.getId());
                thumb.setImageBitmap(video.getThumbnail());
            }
        }
    }
}
