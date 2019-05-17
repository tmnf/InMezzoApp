package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

import java.util.ArrayList;

/* This class handles post row fulfilling */

public class PostAdapter extends ArrayAdapter<Post> {

    private Context mContext;
    private ArrayList<Post> posts;

    public PostAdapter(@NonNull Context mContext, ArrayList<Post> posts) {
        super(mContext, 0, posts);

        this.mContext = mContext;
        this.posts = posts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View postListView = convertView;

        if (postListView == null)
            postListView = LayoutInflater.from(mContext).inflate(R.layout.post_row, parent, false);

        Post post = posts.get(position);

        /* REFERENCES */

        ImageView pic = postListView.findViewById(R.id.post_pic);
        TextView post_name = postListView.findViewById(R.id.post_name);
        TextView post_pub_date = postListView.findViewById(R.id.post_pub_date);
        TextView post_text = postListView.findViewById(R.id.post_message);

        // Contains url associated to the video
        TextView hidden_url = postListView.findViewById(R.id.hidden_url);

        /* Data Handle */

        hidden_url.setVisibility(View.INVISIBLE);
        youtubeViewHandle(postListView, post, hidden_url);

        LoginUtils.putInto(pic, post.getOwner());
        post_name.setText(post.getOwner().getUser_name());
        post_pub_date.setText(post.getDate_pub());
        post_text.setText(post.getPost_text());

        return postListView;
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
