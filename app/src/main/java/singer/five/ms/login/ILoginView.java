package singer.five.ms.login;

import com.telchina.pub.construction.base.IControllerCenter;

/**
 * Created by GISirFive on 2016-3-22.
 */
public interface ILoginView extends IControllerCenter{

    String getUserName();

    String getPassword();

    void clearPassword();

    void toMainActivity();

}
