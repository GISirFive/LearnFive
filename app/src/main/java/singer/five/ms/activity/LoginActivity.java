package singer.five.ms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.telchina.image.activity.ImagePicker;
import com.telchina.pub.construction.AbsBaseActivity;
import singer.five.ms.R;
import singer.five.ms.login.ILoginView;
import singer.five.ms.login.LoginPresenter;

public class LoginActivity extends AbsBaseActivity implements ILoginView {

    @Bind(R.id.mEtUserName)
    EditText mEtUserName;
    @Bind(R.id.mEtPassword)
    EditText mEtPassword;
    @Bind(R.id.mBtnLogin)
    Button mBtnLogin;
    @Bind(R.id.mBtnRegister)
    Button mBtnRegister;
    @Bind(R.id.mPbLoading)
    ProgressBar mPbLoading;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(this);

    }

    @OnClick({R.id.mBtnLogin, R.id.mBtnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtnLogin:
                mLoginPresenter.login();
                break;
            case R.id.mBtnRegister:
                Intent intent = new Intent(this, ImagePicker.class);
                intent.putExtra(ImagePicker.FLAG_INPUT_SELECTNUM, 4);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    @Override
    public String getUserName() {
        return mEtUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void clearPassword() {
        mEtPassword.setText("");
    }

    @Override
    public void toMainActivity() {
        Logger.i("跳转到首页");
    }
}
