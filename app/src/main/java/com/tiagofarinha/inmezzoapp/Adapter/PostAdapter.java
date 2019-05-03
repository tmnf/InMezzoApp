package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;
import com.tiagofarinha.inmezzoapp.Youtube.YoutubeListener;
import com.tiagofarinha.inmezzoapp.Youtube.YoutubeMain;

import java.util.ArrayList;


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
        View postList = convertView;

        if(postList == null)
            postList = LayoutInflater.from(mContext).inflate(R.layout.post_row,parent,false);

        Post post = posts.get(position);

        /*Enviar dados para Layout*/

        ImageView pic = postList.findViewById(R.id.post_pic);
        TextView post_name = postList.findViewById(R.id.post_name);
        TextView post_pub_date = postList.findViewById(R.id.post_pub_date);
        TextView post_text = postList.findViewById(R.id.post_message);

        // YOUTUBE HANDLING \\

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        MainActivity.getInstance().getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, youTubePlayerFragment).commit();

        if (post.getUrl().isEmpty()) {
            ConstraintLayout cons = postList.findViewById(R.id.post_container);
            FrameLayout fl = postList.findViewById(R.id.frame_fragment);

            cons.removeView(fl);
        } else {
            String[] video = post.getUrl().split("=");
            youTubePlayerFragment.initialize(YoutubeMain.getApiKey(), new YoutubeListener("gjp0EVM4oHI"));
        }


        LoginUtils.putInto(pic, post.getOwner());

        post_name.setText(post.getOwner().getUser_name());
        post_pub_date.setText(post.getDate_pub());
        post_text.setText(post.getPost_text());


        return postList;
    }
}
