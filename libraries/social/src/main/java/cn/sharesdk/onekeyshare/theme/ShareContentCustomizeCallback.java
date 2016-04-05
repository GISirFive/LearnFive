package cn.sharesdk.onekeyshare.theme;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;

public interface ShareContentCustomizeCallback {

	public void onShare(Platform platform, ShareParams paramsToShare);

}
