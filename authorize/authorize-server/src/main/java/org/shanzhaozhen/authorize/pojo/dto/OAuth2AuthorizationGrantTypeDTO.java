package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端授权方式DTO实体")
public class OAuth2AuthorizationGrantTypeDTO extends BaseInfo {

    private static final long serialVersionUID = -8892019207322733934L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "客户端授权方式")
    private String grantTypeName;

}
