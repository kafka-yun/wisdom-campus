package com.gong.auth.model.entity.dto;

import com.gong.commons.interfaces.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FaceDto {

    @NotEmpty(message = "头像信息不能为空",groups = {ValidationGroups.Insert.class})
    @NotEmpty(message = "头像信息不能为空",groups = {ValidationGroups.Update.class})
    @Size(message = "头像参数不正确", min = 100)
    String image;
    @NotEmpty(message = "邮箱信息不能为空",groups = {ValidationGroups.Insert.class})
    @Size(message = "邮箱参数不正确", min = 6)
    String email;

}
