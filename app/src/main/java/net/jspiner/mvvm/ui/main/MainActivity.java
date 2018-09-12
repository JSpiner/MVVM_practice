package net.jspiner.mvvm.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.jspiner.mvvm.R;
import net.jspiner.mvvm.util.SimpleTextWatcher;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel = new MainViewModel();

    private EditText editTextId;
    private EditText editTextPw;
    private Button buttonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        editTextId = findViewById(R.id.id);
        editTextPw = findViewById(R.id.pw);
        buttonLogin = findViewById(R.id.login);

        setUpViews();
        bindViewModel();
    }

    private void setUpViews() {
        editTextId.addTextChangedListener((SimpleTextWatcher) viewModel::onInputIdChanged);
        editTextPw.addTextChangedListener((SimpleTextWatcher) viewModel::onInputPwChanged);
        buttonLogin.setOnClickListener(__ -> viewModel.onLoginButtonClicked());
    }

    private void bindViewModel() {
        viewModel.getLoginButtonEnabled()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(buttonLogin::setEnabled);

        viewModel.getLoginResult()
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(isLoginSuccess -> {
                    if (isLoginSuccess) {
                        Toast.makeText(getBaseContext(), "로그인 성공", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
