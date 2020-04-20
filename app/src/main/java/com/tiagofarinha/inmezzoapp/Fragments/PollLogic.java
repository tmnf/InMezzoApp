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

import com.google.firebase.auth.FirebaseAuth;
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
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.Models.Vote;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class PollLogic extends Fragment {

    // Different Types of Vote
    public static final int GO = 0, DONT = 1, MAYBE = 2;

    // Main View
    private View view;

    // All Votes From This Event
    private ArrayList<Vote> votes = new ArrayList<>();

    // Reference to User's Vote
    private DataSnapshot voteSnap;

    // Event Key
    private String eventKey = "";

    // Reference to Votes Table
    private DatabaseReference votesRef;

    // Components
    private Button goBt, dontBt, maybeBt;
    private ListView goList, dontList, maybeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.poll_fragment, container, false);

        getComps(view);

        votesRef = FirebaseDatabase.getInstance().getReference().child("votes");
        getEventKey(((Adaptable) getArguments().getSerializable("event")));

        addListeners();

        return view;
    }

    private void addListeners() {
        goBt.setOnClickListener(v -> vote(GO));
        dontBt.setOnClickListener(v -> vote(DONT));
        maybeBt.setOnClickListener(v -> vote(MAYBE));
    }

    // Handles User Input Vote
    private void vote(int mode) {

        if (voteSnap != null) {
            int vote = voteSnap.getValue(Vote.class).getValue();

            votesRef.child(voteSnap.getKey()).removeValue();
            voteSnap = null;

            if (vote == mode) {
                updateLeaderBoard(-1);

                Utils.showMessage("Voto Removido");
                return;
            }
        } else
            updateLeaderBoard(1);

        votesRef.push().setValue(new Vote(eventKey, MainMethods.getInstance().getAuxUser(), mode));
        Utils.showMessage("Voto Adicionado");
    }

    // Updates Current Votes on Current User
    private void updateLeaderBoard(int value) {
        User currUser = MainMethods.getInstance().getAuxUser();
        currUser.addVote(value);
        Utils.changeVotesOfUser(FirebaseAuth.getInstance().getUid(), currUser.getVotes());
    }

    // Updates Button Textures
    private void updateButtons() {
        boolean[] states = new boolean[3];

        if (voteSnap != null)
            states[voteSnap.getValue(Vote.class).getValue()] = true;

        goBt.setPressed(states[0]);
        dontBt.setPressed(states[1]);
        maybeBt.setPressed(states[2]);
    }

    // Updates Vote Count and Progress
    private void updateInfo(int mode, int max, int value) {
        int num_id = 0, prog_id = 0;

        switch (mode) {
            case GO:
                num_id = R.id.poll_vou_num;
                prog_id = R.id.poll_vou;
                break;
            case DONT:
                num_id = R.id.poll_nao_num;
                prog_id = R.id.poll_nao;
                break;
            case MAYBE:
                num_id = R.id.poll_maybe_num;
                prog_id = R.id.poll_talvez;
                break;
            default:
                break;
        }

        ((TextView) view.findViewById(num_id)).setText(value + "");
        ((ProgressBar) view.findViewById(prog_id)).setMax(max);
        ((ProgressBar) view.findViewById(prog_id)).setProgress(value);
    }

    // Gets a Reference to the Components
    private void getComps(View view) {
        goBt = view.findViewById(R.id.vou_button);
        dontBt = view.findViewById(R.id.nao_button);
        maybeBt = view.findViewById(R.id.maybe_button);

        goList = view.findViewById(R.id.poll_go_list);
        dontList = view.findViewById(R.id.poll_dont_list);
        maybeList = view.findViewById(R.id.poll_maybe_list);
    }

    // Gets the Event Key
    private void getEventKey(Adaptable event) {
        String eventType = "ensaios";

        if (event instanceof Concert)
            eventType = "concerts";

        FirebaseDatabase.getInstance().getReference().child(eventType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Adaptable aux_event = null;

                    if (event instanceof Concert)
                        aux_event = x.getValue(Concert.class);
                    else
                        aux_event = x.getValue(Ensaio.class);

                    if (aux_event.equals(event)) {
                        eventKey = x.getKey();
                        break;
                    }
                }
                generateLists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Gets All Votes
    private void generateLists() {
        votesRef.orderByChild("eventKey").equalTo(eventKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Vote v = x.getValue(Vote.class);
                    votes.add(x.getValue(Vote.class));

                    if (v.getUser().equals(MainMethods.getInstance().getAuxUser()))
                        voteSnap = x;
                }

                goList.setAdapter(new VoteAdapter(getAllVotesOf(votes, GO), R.layout.vote_row));
                dontList.setAdapter(new VoteAdapter(getAllVotesOf(votes, DONT), R.layout.vote_row));
                maybeList.setAdapter(new VoteAdapter(getAllVotesOf(votes, MAYBE), R.layout.vote_row));

                updateButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Gets Votes From a Certain Type
    public ArrayList<Adaptable> getAllVotesOf(ArrayList<Vote> votes, int mode) {
        ArrayList<Adaptable> res = new ArrayList<>();

        for (Vote x : votes)
            if (x.getValue() == mode)
                res.add(x.getUser());

        updateInfo(mode, votes.size(), res.size());

        return res;
    }
}
