package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Adapter.ChatAdapter;
import com.tiagofarinha.inmezzoapp.Adapter.DefaultAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.ChatMessage;
import com.tiagofarinha.inmezzoapp.R;

public class ChatLogic extends Fragment {

    private ListView listView;
    private DefaultAdapter adapter;

    private DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        listView = view.findViewById(R.id.chat_list);

        ref = FirebaseDatabase.getInstance().getReference().child("messages");

        inflateList();
        monitorMessageInput(view);

        return view;
    }

    private void monitorMessageInput(View view) {
        final EditText msgInput = view.findViewById(R.id.chat_input_msg);

        msgInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (msgInput.getRight() - msgInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        saveMessage(msgInput);
                        return true;
                    }
                }
                return false;
            }
        });

        msgInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    saveMessage(msgInput);
                    return true;
                }
                return false;
            }
        });
    }

    private void saveMessage(final EditText msgInput) {
        String msg = msgInput.getText().toString();

        if (!msg.isEmpty()) {
            ChatMessage toSend = new ChatMessage(MainMethods.getInstance().getAuxUser(), msg);
            ref.push().setValue(toSend).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    msgInput.setText("");
                    MainMethods.getInstance().closeKeyboard();
                }
            });
        }
    }

    private void inflateList() {
        adapter = new ChatAdapter(ResourceLoader.getInstance().getChat_messages(), R.layout.chat_row);
        listView.setAdapter(adapter);

        listView.setSelection(adapter.getCount() - 1);
    }

    public void refreshList() {
        adapter.notifyDataSetChanged();
        listView.setSelection(adapter.getCount() - 1);
    }

}
