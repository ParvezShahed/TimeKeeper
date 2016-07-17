package com.time.code.timekeeper.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.time.code.timekeeper.R;

/**
 * Created by shahed on 16/07/2016.
 */
public class LoginFragment extends Fragment{
    private Button mOkButton;
    private Button mCancelButton;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private static String USERNAME = "userA";
    private static String PASSWARD = "pass";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mOkButton = (Button) view.findViewById(R.id.ok);
        mCancelButton = (Button) view.findViewById(R.id.cancel);;
        mUsernameEditText = (EditText) view.findViewById(R.id.username);
        mPasswordEditText = (EditText) view.findViewById(R.id.password);;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onOkButtonAction();
        onCancelButtonAction();
    }

    private void onOkButtonAction(){
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String pass = mPasswordEditText.getText().toString();
                if(username.equals(USERNAME) && pass.equals(PASSWARD)){
                    TimeCardsFragment timeCardsFragment = new TimeCardsFragment();
                    timeCardsFragment.setUsername(username);
                    FragmentTransaction transaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, timeCardsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else{
                    mUsernameEditText.setText("");
                    mPasswordEditText.setText("");
                }
            }
        });
    }

    private void onCancelButtonAction(){
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}