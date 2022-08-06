package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2ClientSettingsConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2ClientSettingsMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientSettingsDO;
import org.shanzhaozhen.authorize.service.OAuth2ClientSettingsService;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端配置 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2ClientSettingsServiceImpl implements OAuth2ClientSettingsService {

    private final OAuth2ClientSettingsMapper oAuth2ClientSettingsMapper;

    @Override
    public OAuth2ClientSettingsDTO getOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        return oAuth2ClientSettingsMapper.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

    @Override
    @Transactional
    public void addOrUpdateOAuth2ClientSettings(String registeredClientId, OAuth2ClientSettingsDTO oAuth2ClientSettingsDTO) {
        OAuth2ClientSettingsDTO oAuth2ClientSettingsByClientIdInDB = this.getOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
        if (oAuth2ClientSettingsByClientIdInDB == null) {
            OAuth2ClientSettingsDO oAuth2ClientSettingsDO =  OAuth2ClientSettingsConverter.toDO(oAuth2ClientSettingsDTO);
            this.oAuth2ClientSettingsMapper.insert(oAuth2ClientSettingsDO);
        } else {
            OAuth2ClientSettingsDO oAuth2ClientSettingsDO = this.oAuth2ClientSettingsMapper.selectById(oAuth2ClientSettingsByClientIdInDB.getId());
            CustomBeanUtils.copyPropertiesExcludeMeta(oAuth2ClientSettingsDTO, oAuth2ClientSettingsDO);
            this.oAuth2ClientSettingsMapper.updateById(oAuth2ClientSettingsDO);
        }
    }

    @Override
    @Transactional
    public void deleteOAuth2ClientSettingsByRegisteredClientId(String registeredClientId) {
        this.oAuth2ClientSettingsMapper.deleteOAuth2ClientSettingsByRegisteredClientId(registeredClientId);
    }

}
