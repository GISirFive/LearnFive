package singer.five.ms.login;

import android.os.Bundle;
import android.widget.Toast;

import com.telchina.pub.construction.AbsBasePresenter;
import com.telchina.pub.utils.RequestUtils;

/**
 * Created by GISirFive on 2016-3-23.
 */
public class LoginPresenter extends AbsBasePresenter {

    private ILoginView mLoginView;

    private ILoginModel mLoginModel;

    public LoginPresenter(ILoginView loginView) {
        super(loginView);
        mLoginView = loginView;

        mLoginModel = new ILoginModelImp(this);
    }

    /**
     * 请求成功，返回请求成果
     *
     * @param code
     * @param bundle
     */
    @Override
    public void onSuccess(RequestUtils.RequestCode code, Bundle bundle) {

    }

    /**
     * 请求失败
     *
     * @param code
     * @param bundle
     */
    @Override
    public void onFailure(RequestUtils.RequestCode code, Bundle bundle) {

    }

    public void login() {
        mLoginView.getMessager().showToast("asdasdas", Toast.LENGTH_LONG);
    }
}
