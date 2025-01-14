package org.shanzhaozhen.authorize.authentication.account;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.feign.UserFeignClient;
import org.shanzhaozhen.uaa.pojo.dto.AuthUser;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsServiceImpl implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user;
        try {
            R<UserDTO> data = userFeignClient.loadUserByUsername(username);
            user = data.getData();
        } catch (Exception e) {
            throw new BadCredentialsException("用户获取过程出现异常!");
        }

        if (user == null) {
            throw new BadCredentialsException("用户不存在!");
        }
        return new AuthUser(user);
    }

}
