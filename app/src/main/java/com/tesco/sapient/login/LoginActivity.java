package com.tesco.sapient.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tesco.sapient.MyApplication;
import com.tesco.sapient.R;
import com.tesco.sapient.di.component.ActivityComponent;
import com.tesco.sapient.di.component.DaggerActivityComponent;
import com.tesco.sapient.di.module.ActivityModule;
import com.tesco.sapient.db.DataManager;
import com.tesco.sapient.main.MainActivity;
import com.tesco.sapient.dto.UseDTO;
import com.tesco.sapient.util.KeyboardUtil;
import com.tesco.sapient.util.Validation;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;
    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.editTextUserName)
    EditText editTextUserName;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @Inject
    DataManager mDataManager;
    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MyApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

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

    @OnClick(R.id.buttonLogin)
    public void loginButtonClick(View view) {
        // Hide keyboard after login button click
        KeyboardUtil.hideSoftKeyboard(context);
        String userName = editTextUserName.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();
        if (!Validation.isEmpty(userName)) {
            editTextUserName.setError(context.getText(R.string.login_validation_error_message_username));
            editTextUserName.requestFocus();
        } else if (!Validation.isEmpty(userPassword)) {
            editTextPassword.setError(context.getText(R.string.login_validation_error_message_password));
            editTextPassword.requestFocus();
        } else {
            UseDTO user = new UseDTO();
            user.setUserName(userName);
            user.setPassword(userPassword);
            presenter.login(user);
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        Snackbar.make(coordinatorLayout, context.getText(R.string.login_validation_error_invalid_user_or_password), Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
