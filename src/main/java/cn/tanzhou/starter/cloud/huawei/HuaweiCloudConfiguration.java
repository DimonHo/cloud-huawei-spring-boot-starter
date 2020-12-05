package cn.tanzhou.starter.cloud.huawei;

import cn.tanzhou.starter.cloud.huawei.HuaweiCloudProperties.Type;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.GlobalCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.iam.v3.IamClient;
import com.huaweicloud.sdk.mpc.v1.MpcClient;
import com.obs.services.ObsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 * @author 敖癸
 * @date 2020/12/4 - 18:45
 */
@Configuration
@EnableConfigurationProperties(HuaweiCloudProperties.class)
public class HuaweiCloudConfiguration {

    @Bean("globalCredentials")
    @ConditionalOnClass(GlobalCredentials.class)
    public GlobalCredentials globalCredentials(HuaweiCloudProperties huaweiCloudProperties) {
        return new GlobalCredentials()
            .withDomainId(huaweiCloudProperties.getDomainId())
            .withAk(huaweiCloudProperties.getAk())
            .withSk(huaweiCloudProperties.getSk());
    }

    @Bean("basicCredentials")
    @ConditionalOnClass(ICredential.class)
    public BasicCredentials basicCredentials(HuaweiCloudProperties huaweiCloudProperties) {
        return new BasicCredentials()
            .withProjectId(huaweiCloudProperties.getProjectId())
            .withAk(huaweiCloudProperties.getAk())
            .withSk(huaweiCloudProperties.getSk());
    }

    @Bean
    @ConditionalOnClass(IamClient.class)
    public IamClient iamClient(GlobalCredentials globalCredentials, HuaweiCloudProperties huaweiCloudProperties) {
        return IamClient.newBuilder()
            .withCredential(globalCredentials)
            .withEndpoint(huaweiCloudProperties.getEndpoint(Type.iam))
            .build();
    }

    @Bean
    @ConditionalOnClass(MpcClient.class)
    public MpcClient mpcClient(BasicCredentials basicCredentials, HuaweiCloudProperties huaweiCloudProperties) {
        return MpcClient.newBuilder()
            .withCredential(basicCredentials)
            .withEndpoint(huaweiCloudProperties.getEndpoint(Type.mpc))
            .build();
    }

    @Bean
    @ConditionalOnClass(ObsClient.class)
    public ObsClient obsClient(HuaweiCloudProperties huaweiCloudProperties) {
        return new ObsClient(huaweiCloudProperties.getAk(), huaweiCloudProperties.getSk(),
            huaweiCloudProperties.getEndpoint(Type.obs));
    }

}
