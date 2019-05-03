package com.tiagofarinha.inmezzoapp.AdminTools;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class AdminLogic extends Fragment {

    public static final String DEFAULT_PASSWORD = "pass123";

    private EditText email, name, birth, voice, phone, mode;

    private ArrayList<EditText> fields;

    private static AdminLogic INSTANCE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment, container, false);

        getComps(view);

        INSTANCE = this;

        return view;
    }

    private void getComps(View view){

        fields = new ArrayList<>();

        email = view.findViewById(R.id.admin_email);
        name = view.findViewById(R.id.admin_name);
        birth = view.findViewById(R.id.admin_birth);
        voice = view.findViewById(R.id.admin_voice);
        phone = view.findViewById(R.id.admin_phone);
        mode = view.findViewById(R.id.admin_mode);

        fields.add(email);
        fields.add(name);
        fields.add(birth);
        fields.add(voice);
        fields.add(phone);
        fields.add(mode);


        Button add_button = view.findViewById(R.id.admin_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().closeKeyboard();
                createUser(fields);
            }
        });
    }

    private void createUser(ArrayList<EditText> fields) {
        String email_text = email.getText().toString();
        String name_text = name.getText().toString();
        String birth_text = birth.getText().toString();
        String voice_text = voice.getText().toString();
        String phone_text = phone.getText().toString();
        String mode_text = mode.getText().toString();

        if(!(email_text.isEmpty() && name_text.isEmpty() && birth_text.isEmpty() && voice_text.isEmpty() && phone_text.isEmpty() && mode_text.isEmpty())){
            Utils.showMessage(getContext(), "A criar user...");
            new UserCreator(email_text, DEFAULT_PASSWORD, name_text, birth_text, voice_text, phone_text, mode_text).start();
        } else Utils.showMessage(getContext(), "Campos Vazios");
    }

    public void onSucess(){
        Utils.showMessage(getContext(), "User Created");

        for(EditText x : fields)
            x.setText("");
    }

    public static AdminLogic getInstance(){
        return INSTANCE;
    }

}
