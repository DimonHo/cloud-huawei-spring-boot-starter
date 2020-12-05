package cn.tanzhou.starter.cloud.huawei;

import com.huaweicloud.sdk.mpc.v1.MpcClient;
import com.huaweicloud.sdk.mpc.v1.model.CreateThumbReq;
import com.huaweicloud.sdk.mpc.v1.model.CreateThumbnailsTaskRequest;
import com.huaweicloud.sdk.mpc.v1.model.CreateThumbnailsTaskResponse;
import com.huaweicloud.sdk.mpc.v1.model.ObsObjInfo;
import com.huaweicloud.sdk.mpc.v1.model.ThumbnailPara;
import com.huaweicloud.sdk.mpc.v1.model.ThumbnailPara.TypeEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * MPC服务
 *
 * @author 敖癸
 * @date 2020/12/4 - 18:52
 */
@Service
@RequiredArgsConstructor
public class MpcService {
    final MpcClient mpcClient;

    final HuaweiCloudProperties huaweiCloudProperties;

    private ObsObjInfo obsObjInfo(String objectKey) {
        ObsObjInfo obsObjInfo = new ObsObjInfo();
        obsObjInfo.setBucket(huaweiCloudProperties.getBucket());
        obsObjInfo.setLocation(huaweiCloudProperties.getLocation());
        obsObjInfo.setObject(objectKey);
        return obsObjInfo;
    }

    private ThumbnailPara thumbnailPara(){
        List<Integer> dots = new ArrayList<>();
        dots.add(2);
        ThumbnailPara thumbnailPara = new ThumbnailPara();
        thumbnailPara.setFormat(1);
        thumbnailPara.setType(TypeEnum.DOTS);
        thumbnailPara.setMaxLength(480);
        thumbnailPara.setDots(dots);
        return thumbnailPara;
    }

    private CreateThumbReq createThumbReq(String objectKey, boolean isSync) {
        CreateThumbReq createThumbReq = new CreateThumbReq();
        createThumbReq.setInput(obsObjInfo(objectKey));
        createThumbReq.setOutput(obsObjInfo("/" + objectKey + "/"));
        createThumbReq.setSync(isSync ? 1 : 0);
        createThumbReq.setThumbnailPara(thumbnailPara());
        createThumbReq.setProjectId(huaweiCloudProperties.getProjectId());
        return createThumbReq;
    }

    /**
     * 视频截帧
     *
     * @param objectKey
     * @param isSync
     * @return java.lang.String isSync=true，直接返回帧对象，否则返回taskId
     * @author 敖癸 2020-12-04 - 22:24
     **/
    public String thumbnails(String objectKey,boolean isSync) {
        CreateThumbnailsTaskRequest createThumbnailsTaskRequest = new CreateThumbnailsTaskRequest();
        createThumbnailsTaskRequest.setBody(createThumbReq(objectKey,isSync));
        CreateThumbnailsTaskResponse thumbnailsTask = mpcClient.createThumbnailsTask(createThumbnailsTaskRequest);
        if (isSync){
            return thumbnailsTask.getOutput().getObject();
        } else {
            return thumbnailsTask.getTaskId();
        }
    }
}
