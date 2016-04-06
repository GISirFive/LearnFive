package singer.five.ms.login;

import android.os.Bundle;

import org.json.JSONObject;

import com.telchina.pub.construction.AbsBaseModel;
import com.telchina.pub.construction.base.IModelCallback;
import com.telchina.pub.utils.RequestUtils;

/**
 * Created by GISirFive on 2016-3-22.
 */
public class ILoginModelImp extends AbsBaseModel implements ILoginModel {

    public ILoginModelImp(IModelCallback callback) {
        super(callback);
    }

    /**
     * 请求成功，返回请求成果
     * @param code
     * @param response 结果是json形式
     * @return 返回结果封装成
     */
    @Override
    protected Bundle onRequestSuccess(RequestUtils.RequestCode code, JSONObject response) {
        return null;
    }

    /**
     * 请求失败
     * @param code
     * @param response 结果是json形式
     * @return 返回结果封装成
     */
    @Override
    protected Bundle onRequestFailure(RequestUtils.RequestCode code, JSONObject response) {
        return null;
    }

    @Override
    public void login(LoginParams params) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("name",params.userName);
//        requestParams.put("password", params.password);
//        post(RequestUtils.RequestCode.USER_LOGIN, requestParams);
    }
}
