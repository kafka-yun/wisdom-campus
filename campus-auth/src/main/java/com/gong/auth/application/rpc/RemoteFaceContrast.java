package com.gong.auth.application.rpc;


import com.gong.auth.model.entity.dto.RpcFaceMessage;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程服务调用接口
 * */
@FeignClient(value = "spark-server")
@RequestMapping("/face")
public interface RemoteFaceContrast {

    /**
     * 人脸对比服务调用
     * */
    @PostMapping("/baseFace")
    String base64FaceContrast(@RequestBody RpcFaceMessage rpcFaceMessage);


}
