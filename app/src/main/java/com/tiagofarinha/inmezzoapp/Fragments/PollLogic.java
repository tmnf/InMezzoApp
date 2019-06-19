package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Adapter.VoteAdapter;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.Models.Vote;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;

public class PollLogic extends Fragment {

    public static final int GO = 0, DONT = 1;

    private Adaptable event;
    private DatabaseReference votes_ref;

    private int go, dont;

    private DataSnapshot voteSnap;
    private Vote vote;

    private Button goButt, dontButt;
    private TextView goNum, dontNum;
    private ProgressBar progGo, progDont;

    private ListView goList, dontList;

    private ArrayList<Vote> votes;

    private VoteAdapter goAdap, dontAdap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poll_fragment, container, false);

        event = (Adaptable) getArguments().getSerializable("event");
        votes_ref = FirebaseDatabase.getInstance().getReference().child("votes");

        votes = new ArrayList<>();

        goNum = view.findViewById(R.id.poll_vou_num);
        dontNum = view.findViewById(R.id.poll_nao_num);
        goButt = view.findViewById(R.id.vou_button);
        dontButt = view.findViewById(R.id.nao_button);

        progGo = view.findViewById(R.id.poll_vou);
        progDont = view.findViewById(R.id.poll_nao);

        goList = view.findViewById(R.id.poll_go_list);
        dontList = view.findViewById(R.id.poll_dont_list);

        goButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVote(GO);
            }
        });
        dontButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVote(DONT);
            }
        });

        getVotes();

        return view;
    }

    private void getComps() {
        goNum.setText(go + "");
        dontNum.setText(dont + "");

        progGo.setMax(go + dont);
        progDont.setMax(go + dont);

        progGo.setProgress(go);
        progDont.setProgress(dont);

        generateLists();

        checkTextures();
    }

    private void generateLists() {
        ArrayList<Adaptable> go = new ArrayList<>();
        ArrayList<Adaptable> dont = new ArrayList<>();

        for (Vote x : votes) {
            if (x.getValue() == GO)
                go.add(x.getUser());
            else
                dont.add(x.getUser());
        }

        goAdap = new VoteAdapter(MainMethods.getInstance().getContext(), go, R.layout.vote_row);
        dontAdap = new VoteAdapter(MainMethods.getInstance().getContext(), dont, R.layout.vote_row);

        goList.setAdapter(goAdap);
        dontList.setAdapter(dontAdap);
    }

    private void checkTextures() {
        if (vote == null) {
            goButt.setBackgroundResource(R.drawable.login_button);
            dontButt.setBackgroundResource(R.drawable.login_button);
        } else {
            if (vote.getValue() == GO) {
                goButt.setBackgroundResource(R.drawable.login_pressed);
                dontButt.setBackgroundResource(R.drawable.login_button);
            } else {
                goButt.setBackgroundResource(R.drawable.login_button);
                dontButt.setBackgroundResource(R.drawable.login_pressed);
            }
        }
    }

    private void getVotes() {
        votes_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                go = 0;
                dont = 0;
                votes.clear();

                Adaptable localEvent;

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Vote vote = x.getValue(Vote.class);

                    if (event instanceof Ensaio && vote.getEnsaio() != null) {
                        localEvent = vote.getEnsaio();
                        handleVote(localEvent, vote, x);
                    } else if ((event instanceof Concert && vote.getConcert() != null)) {
                        localEvent = vote.getConcert();
                        handleVote(localEvent, vote, x);
                    }
                }

                getComps();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handleVote(Adaptable localEvent, Vote vote, DataSnapshot x) {
        if (localEvent.equals(PollLogic.this.event)) {
            if (vote.getValue() == GO)
                go++;
            else
                dont++;

            votes.add(vote);

            if (vote.getUser().equals(MainMethods.getInstance().getAuxUser())) {
                PollLogic.this.voteSnap = x;
                PollLogic.this.vote = vote;
            }
        }
    }

    private void addVote(int value) {
        if (voteSnap != null) {
            int voteValue = vote.getValue();
            removeVote();
            if (voteValue == value)
                return;
        }

        votes_ref.push().setValue(new Vote(event, MainMethods.getInstance().getAuxUser(), value));
    }

    private void removeVote() {
        votes_ref.child(voteSnap.getKey()).removeValue();
        voteSnap = null;
        vote = null;
    }
}
