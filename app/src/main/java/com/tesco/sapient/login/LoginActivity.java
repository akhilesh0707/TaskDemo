package com.tesco.sapient.login;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tesco.sapient.MyApplication;
import com.tesco.sapient.R;
import com.tesco.sapient.di.component.ActivityComponent;
import com.tesco.sapient.di.component.DaggerActivityComponent;
import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.main.MainActivity;
import com.tesco.sapient.dto.UserDTO;
import com.tesco.sapient.util.KeyboardUtil;
import com.tesco.sapient.util.Validation;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * LoginActivity or UI
 *
 * @author Akhilesh Patil
 * @version 1.0
 * @since 2018-03-17
 */
public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    private LoginPresenter presenter;
    private Context context;
    private Unbinder unbinder;
    private static final int REQUEST_CODE = 1234;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.editTextUserName)
    EditText editTextUserName;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @Inject
    DataManager mDataManager;
    private ActivityComponent activityComponent;

    /**
     * Get component for dependency injection - dagger
     *
     * @return ActivityComponent
     */
    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MyApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    /**
     * LifeCycle method to handle ui and other elements
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initializing ButterKnife
        unbinder = ButterKnife.bind(this);
        // Initializing login activity context
        context = this;
        //Initialize DataBase
        getActivityComponent().inject(this);
        // Initializing Presenter
        presenter = new LoginPresenter(this, mDataManager);
    }

    /**
     * Login button click
     *
     * @param view
     */
    @OnClick(R.id.buttonLogin)
    public void loginButtonClick(View view) {
        // Hide keyboard after login button click
        KeyboardUtil.hideSoftKeyboard(context);
        presenter.login();
    }

    /**
     * Get username from EditText and pass to presenter
     *
     * @return
     */
    @Override
    public String getUsername() {
        return editTextUserName.getText().toString().trim();
    }

    /**
     * Show error message is username is empty
     *
     * @param resId
     */
    @Override
    public void showUsernameErrorMessage(int resId) {
        editTextUserName.setError(getString(resId));
        editTextUserName.requestFocus();
    }

    /**
     * Get password string and pass to presenter
     *
     * @return
     */
    @Override
    public String getPassword() {
        return editTextPassword.getText().toString().trim();
    }

    /**
     * Show error message if password is empty
     *
     * @param resId
     */
    @Override
    public void showPasswordErrorMessage(int resId) {
        editTextPassword.setError(getString(resId));
        editTextPassword.requestFocus();
    }

    /**
     * Login success redirect to HomeActivity
     *
     * @param userDTO
     */
    @Override
    public void loginSuccess(UserDTO userDTO) {
        MyApplication.get(context).setUser(userDTO);
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Login failed error message
     */
    @Override
    public void loginFailed() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.login_validation_error_invalid_user_or_password), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Activity lifecycle on destroy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    /**
     * Handle the action of the button being clicked
     */
    @OnClick(R.id.imageViewRecord)
    public void speakButtonClicked(View v) {
        startVoiceRecognitionActivity();
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                Log.d("LoginActivity", matches.toString());
                String query = matches.get(0);
                String queryArray[] = query.split("\\s+");
                if (queryArray.length > 0) {
                    editTextUserName.setText(queryArray[0].toLowerCase());
                }
                if (queryArray.length > 1) {
                    editTextPassword.setText(queryArray[1].toLowerCase());
                }
                if (queryArray.length > 2) {
                    buttonLogin.performClick();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
