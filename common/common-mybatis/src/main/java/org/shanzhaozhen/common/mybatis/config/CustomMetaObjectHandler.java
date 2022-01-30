package org.shanzhaozhen.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = JwtUtils.getUserIdWithoutError();
        this.setFieldValByName("createdDate", new Date(), metaObject);
        this.setFieldValByName("createdBy", userId, metaObject);
        this.setFieldValByName("lastModifiedDate", new Date(), metaObject);
        this.setFieldValByName("lastModifiedBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = JwtUtils.getUserIdWithoutError();
        this.setFieldValByName("lastModifiedDate", new Date(), metaObject);
        this.setFieldValByName("lastModifiedBy", userId, metaObject);
    }
}