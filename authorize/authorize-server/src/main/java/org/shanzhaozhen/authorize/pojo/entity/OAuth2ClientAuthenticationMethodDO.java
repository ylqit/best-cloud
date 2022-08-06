package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_client_authentication_method")
@Schema(description = "oauth2客户端认证方式DO实体")
public class OAuth2ClientAuthenticationMethodDO extends BaseEntity {

    private static final long serialVersionUID = -1096564364823745973L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "客户端认证方式")
    private String clientAuthenticationMethod;

}
