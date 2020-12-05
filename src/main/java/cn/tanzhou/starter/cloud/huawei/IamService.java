package cn.tanzhou.starter.cloud.huawei;

import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.iam.v3.IamClient;
import com.huaweicloud.sdk.iam.v3.model.KeystoneCreateScopedTokenRequest;
import com.huaweicloud.sdk.iam.v3.model.KeystoneCreateScopedTokenResponse;
import com.huaweicloud.sdk.iam.v3.model.ScopeTokenResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 认证服务
 *
 * @author 敖癸
 * @date 2020/12/4 - 20:39
 */
@Service
@RequiredArgsConstructor
public class IamService {

    final IamClient iamClient;

    final HuaweiCloudProperties huaweiCloudProperties;

    public ScopeTokenResult getToken() {
        KeystoneCreateScopedTokenRequest request = new KeystoneCreateScopedTokenRequest();
        try {
            KeystoneCreateScopedTokenResponse response = iamClient.keystoneCreateScopedToken(request);
            ScopeTokenResult token = response.getToken();
            return token;
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
        return null;
    }
}
