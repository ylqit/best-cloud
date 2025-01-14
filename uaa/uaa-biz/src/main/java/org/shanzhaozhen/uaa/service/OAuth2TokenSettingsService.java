package org.shanzhaozhen.uaa.service;

import org.shanzhaozhen.uaa.pojo.dto.OAuth2TokenSettingsDTO;

/**
 * <p>
 * oauth2客户端的token配置项 服务类
 * </p>
 *
 * @author shanzhaozhen
 * @since 2022-06-17
 */
public interface OAuth2TokenSettingsService {

    /**
     * 通过客户端 id 获得 oauth2 客户端配置
     * @param registeredClientId
     * @return
     */
    OAuth2TokenSettingsDTO getOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

    /**
     * 添加或更新客户端信息 oauth2 客户端配置
     * @param clientId
     * @param oAuth2TokenSettingsDTO
     */
    void addOrUpdateOAuth2TokenSettings(String clientId, OAuth2TokenSettingsDTO oAuth2TokenSettingsDTO);

    /**
     * 通过客户端 id 删除客户端信息 oauth2 客户端配置
     * @param registeredClientId
     */
    void deleteOAuth2TokenSettingsByRegisteredClientId(String registeredClientId);

}
