package org.shanzhaozhen.authorize.authentication.account;

import org.shanzhaozhen.authorize.authentication.AbstractLoginFilterConfigurer;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AccountLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractLoginFilterConfigurer<H, AccountLoginConfigurer<H>, AccountAuthenticationFilter> {

    public AccountLoginConfigurer() {
        super(new AccountAuthenticationFilter(), AccountAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI);
    }

    public AccountLoginConfigurer(HttpSecurity http, UserDetailsService userDetailsService) {
        this();
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(authenticationProvider);
    }

    /**
     * 初始化账号登陆校验方式的配置
     * @param http
     * @throws Exception
     */
    @Override
    public void init(H http) throws Exception {
        super.init(http);
        initDefaultLoginFilter(http);
    }

    /**
     * 设置手机登陆的验证地址
     * @param loginProcessingUrl creates the {@link RequestMatcher} based upon the
     * loginProcessingUrl
     * @return
     */
    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    /**
     * 设置默认配置
     * @param http
     */
    private void initDefaultLoginFilter(H http) {
        DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = http
                .getSharedObject(DefaultLoginPageGeneratingFilter.class);
        if (loginPageGeneratingFilter != null) {
            loginPageGeneratingFilter.setFormLoginEnabled(false);
            loginPageGeneratingFilter.setAuthenticationUrl(getLoginProcessingUrl());
        }
    }

}