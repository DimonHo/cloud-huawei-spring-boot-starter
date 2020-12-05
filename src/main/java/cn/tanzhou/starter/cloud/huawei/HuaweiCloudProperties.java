package cn.tanzhou.starter.cloud.huawei;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参数配置
 *
 * @author 敖癸
 * @date 2020/12/4 - 18:47
 */
@Data
@ConfigurationProperties(prefix = "cloud.huawei")
public class HuaweiCloudProperties {

    /**
     * 账户名
     */
    private final String accountName;

    /**
     * IAM用户名
     */
    private final String userName;

    /**
     * 用户密码
     */
    private final String userPass;

    private String domainId;

    private String projectId;

    @Getter(value = AccessLevel.NONE)
    private String endpoint = ".cn-north-4.myhuaweicloud.com";

    private String ak;

    private String sk;

    private String bucketName;

    private String location;

    public String getEndpoint(Type type) {
        return type.name() + endpoint;
    }

    public enum Type {
        obs, iam, mpc
    }
}
