package cn.tanzhou.starter.cloud.huawei;

import com.obs.services.IObsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * OBS服务
 *
 * @author 敖癸
 * @date 2020/12/4 - 18:53
 */
@Service
@RequiredArgsConstructor
public class ObsService {

    final IObsClient obsClient;

    /**
     * 获取对象MD5摘要
     *
     * @param bucketName 桶名
     * @param objectKey 对象
     * @return java.lang.String
     * @author 敖癸 2020-12-04 - 20:23
     **/
    public String getObjectMd5(String bucketName, String objectKey) {
        return obsClient.getObjectMetadata(bucketName, objectKey).getEtag();
    }
}
