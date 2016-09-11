package com.kitsyambochcka.loginpage.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kitsyambochcka.loginpage.App;
import com.kitsyambochcka.loginpage.Constants;
import com.kitsyambochcka.loginpage.R;
import com.kitsyambochcka.loginpage.models.User;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

import butterknife.BindView;

public class EditActivity extends BaseActivity {

    @BindView(R.id.editTextFirstNam)EditText etFirstName;
    @BindView(R.id.editTextLastName)EditText etLastName;
    @BindView(R.id.editTextEmail)EditText etEmail;
    @BindView(R.id.editTextBitrhday)EditText etBirthday;
    @BindView(R.id.buttonSave)Button bSave;
    @BindView(R.id.buttonCancel)Button bCancel;

    private String network;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit);
        super.onCreate(savedInstanceState);

        network = getIntent().getStringExtra(Constants.SOCIAL_NETWORKS);
        User user = App.getDataManager().getAccount(network);
        etFirstName.setText(user.getName().substring(0, user.getName().indexOf(" ")));
        etLastName.setText(user.getName().substring(user.getName().indexOf(" "), user.getName().length()));
        etEmail.setText(user.getEmail());
        etBirthday.setText(user.getDateOfBirthday());
        setViews(network);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllFields()){
                    sendRequest(network);
                }else {
                    showToast(Constants.CHECK_FIELDS);
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setViews(String network) {

        switch (network){
            case Constants.VK:
                etEmail.setEnabled(false);
                break;
            case Constants.FACEBOOK:
                //TODO:delete fields if it will be needed - facebook
                etFirstName.setEnabled(false);
                etLastName.setEnabled(false);
                etEmail.setEnabled(false);
                etBirthday.setEnabled(false);
                break;
            case Constants.GPlus:
                //TODO:delete fields if it will be needed - G+
                etFirstName.setEnabled(false);
                etLastName.setEnabled(false);
                etEmail.setEnabled(false);
                etBirthday.setEnabled(false);
                break;
        }
    }

    private void sendRequest(String network) {
        switch (network){
            case Constants.VK:
                sendVKRequest();
                break;
            case Constants.FACEBOOK:
                //TODO:Edit facebook account if it possible
                break;
            case Constants.GPlus:
                //TODO:Edit G+ account if it possible
                break;
        }
    }

    private void sendVKRequest() {
        VKRequest requestName = new VKRequest(Constants.VK_SAVE_PROFILE, VKParameters.from(Constants.FIRST_NAME,
                etFirstName.getText().toString(), Constants.LAST_NAME, etLastName.getText().toString(),
                Constants.VK_BIRTHDATE, etBirthday.getText().toString()));


        requestName.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {

                    String massage=response.json.getJSONObject(Constants.RESPONSE).getJSONObject(Constants.VK_NAME_REQUEST).getString(Constants.VK_LANG);

                    showToast(massage);

                } catch (JSONException e) {
                    showToast(Constants.SUCCESSFUL);
                }finally {
                    onBackPressed();
                }
                
            }
        });
    }



    private boolean checkAllFields() {
        if(etFirstName.getText().toString().equals(Constants.EMPTY_STRING)){
            return false;
        }
        if(etLastName.getText().toString().equals(Constants.EMPTY_STRING)){
            return false;
        }
        if(etEmail.getText().toString().equals(Constants.EMPTY_STRING)){
            return false;
        }
        if(etBirthday.getText().toString().equals(Constants.EMPTY_STRING)){
            return false;
        }
        return true;
    }

    public void showToast(String massage){
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }
}
