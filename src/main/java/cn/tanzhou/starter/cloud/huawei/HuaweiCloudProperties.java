package cn.tanzhou.starter.cloud.huawei;

import lombok.Data;
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
    private String account;

    /**
     * IAM用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    private String domainId;

    private String projectId;

    private String endpoint = "cn-north-4.myhuaweicloud.com";

    private String ak;

    private String sk;

    private String bucket;

    private String location;

    public String getEndpoint(Type type) {
        return "https://" + type.name() + "." + endpoint;
    }

    public enum Type {
        obs, iam, mpc
    }
}
