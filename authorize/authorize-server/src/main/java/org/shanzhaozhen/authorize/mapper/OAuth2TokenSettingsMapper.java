package org.shanzhaozhen.authorize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端的token配置项 Mapper 接口
 */
public interface OAuth2TokenSettingsMapper extends BaseMapper<OAuth2TokenSettingsDO> {

    OAuth2TokenSettingsDO getOAuth2TokenSettingsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

    void deleteOAuth2TokenSettingsByRegisteredClientId(@Param("registeredClientId") String registeredClientId);

}
