package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    public static final int GO = 0, DONT = 1, MAYBE = 2;

    private Adaptable event;
    private DatabaseReference votes_ref;

    private DataSnapshot voteSnap;
    private Vote vote;

    private Button goButt, dontButt, maybeButt;
    private TextView goNum, dontNum, maybeNum;
    private ProgressBar progGo, progDont, progMaybe;

    private ListView goList, dontList, maybeList;

    private ArrayList<Vote> votes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poll_fragment, container, false);

        event = (Adaptable) getArguments().getSerializable("event");
        votes_ref = FirebaseDatabase.getInstance().getReference().child("votes");

        votes = new ArrayList<>();

        goNum = view.findViewById(R.id.poll_vou_num);
        dontNum = view.findViewById(R.id.poll_nao_num);
        maybeNum = view.findViewById(R.id.poll_maybe_num);

        goButt = view.findViewById(R.id.vou_button);
        dontButt = view.findViewById(R.id.nao_button);
        maybeButt = view.findViewById(R.id.maybe_button);

        progGo = view.findViewById(R.id.poll_vou);
        progDont = view.findViewById(R.id.poll_nao);
        progMaybe = view.findViewById(R.id.poll_talvez);

        goList = view.findViewById(R.id.poll_go_list);
        dontList = view.findViewById(R.id.poll_dont_list);
        maybeList = view.findViewById(R.id.poll_maybe_list);

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
        maybeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVote(MAYBE);
            }
        });

        getVotes();

        return view;
    }

    private void getComps() {
        generateLists();
        checkTextures();
    }

    private void setNumbers(int go, int dont, int maybe) {
        int max = go + dont + maybe;

        progGo.setMax(max);
        progDont.setMax(max);
        progMaybe.setMax(max);

        progGo.setProgress(go);
        progDont.setProgress(dont);
        progMaybe.setProgress(maybe);

        goNum.setText(go + "");
        maybeNum.setText(maybe + "");
        dontNum.setText(dont + "");
    }

    private void generateLists() {
        ArrayList<Adaptable> go = new ArrayList<>();
        ArrayList<Adaptable> maybe = new ArrayList<>();
        ArrayList<Adaptable> dont = new ArrayList<>();

        for (Vote x : votes) {
            switch (x.getValue()) {
                case GO:
                    go.add(x.getUser());
                    break;
                case DONT:
                    dont.add(x.getUser());
                    break;
                case MAYBE:
                    maybe.add(x.getUser());
                    break;
            }
        }

        setNumbers(go.size(), dont.size(), maybe.size());

        VoteAdapter adapt = null;

        // GO
        VoteAdapter ad1 = new VoteAdapter(go, R.layout.vote_row);
        goList.setAdapter(ad1);

        // DONT
        VoteAdapter ad2 = new VoteAdapter(dont, R.layout.vote_row);
        dontList.setAdapter(ad2);

        //Maybe
        VoteAdapter ad3 = new VoteAdapter(maybe, R.layout.vote_row);
        maybeList.setAdapter(ad3);
    }

    private void checkTextures() {
        if (vote == null) {
            changeTexture(goButt, true);
            changeTexture(dontButt, true);
            changeTexture(maybeButt, true);
        } else {
            switch (vote.getValue()) {
                case GO:
                    changeTexture(goButt, false);
                    changeTexture(dontButt, true);
                    changeTexture(maybeButt, true);
                    break;
                case DONT:
                    changeTexture(goButt, true);
                    changeTexture(dontButt, false);
                    changeTexture(maybeButt, true);
                    break;
                case MAYBE:
                    changeTexture(goButt, true);
                    changeTexture(dontButt, true);
                    changeTexture(maybeButt, false);
                    break;
            }
        }
    }

    private void changeTexture(Button bt, boolean active) {
        int id;

        if (active)
            id = R.drawable.login_button;
        else
            id = R.drawable.login_pressed;

        bt.setBackgroundResource(id);
    }

    private void getVotes() {
        votes_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();

                Adaptable localEvent = null;

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Vote vote = x.getValue(Vote.class);

                    localEvent = null;

                    if (event instanceof Ensaio && vote.getEnsaio() != null)
                        localEvent = vote.getEnsaio();
                    else if (event instanceof Concert && vote.getConcert() != null)
                        localEvent = vote.getConcert();

                    if (localEvent != null)
                        handleVote(localEvent, vote, x);
                }

                getComps();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void handleVote(Adaptable localEvent, Vote vote, DataSnapshot x) {
        if (localEvent.equals(event)) {
            votes.add(vote);

            if (vote.getUser().equals(MainMethods.getInstance().getAuxUser())) {
                this.voteSnap = x;
                this.vote = vote;
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
