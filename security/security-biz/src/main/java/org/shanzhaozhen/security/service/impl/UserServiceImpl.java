package org.shanzhaozhen.security.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.common.utils.CustomBeanUtils;
import org.shanzhaozhen.common.utils.PasswordUtils;
import org.shanzhaozhen.security.dto.CustomGrantedAuthority;
import org.shanzhaozhen.security.dto.RoleDTO;
import org.shanzhaozhen.security.service.RoleService;
import org.shanzhaozhen.security.utils.UserDetailsUtils;
import org.shanzhaozhen.security.domain.UserDO;
import org.shanzhaozhen.security.dto.JWTUser;
import org.shanzhaozhen.security.dto.UserDTO;
import org.shanzhaozhen.security.form.UserDepartmentForm;
import org.shanzhaozhen.security.service.UserRoleService;
import org.shanzhaozhen.security.service.UserService;
import org.shanzhaozhen.security.mapper.UserMapper;
import org.shanzhaozhen.security.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.security.vo.CurrentUser;
import org.shanzhaozhen.security.vo.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    private final UserMapper userMapper;

//    private final MenuService menuService;

    private final UserRoleMapper userRoleMapper;


    @Override
    public UserDTO getAuthUserByUsername(String username) {
        UserDTO userDTO = this.getUserByUsername(username);

        if (userDTO == null) {
            /**
             * 在这里会继续捕获到UsernameNotFoundException异常。
             * 由于hideUserNotFoundExceptions的值为true，所以这里会new一个新的BadCredentialsException异常抛出来，那么最后捕获到并放入session中的就是这个BadCredentialsException异常。
             * 所以我们在页面始终无法捕获我们自定义的异常信息。
             */
            throw new BadCredentialsException("用户不存在!");
        } else {
            //将数据库保存的权限存至登陆的账号里面
            List<RoleDTO> roleDTOList = roleService.getRolesByUserId(userDTO.getId());

            if (!CollectionUtils.isEmpty(roleDTOList)) {
                Set<CustomGrantedAuthority> grantedAuthorities = new HashSet<>();
                roleDTOList.forEach(role -> grantedAuthorities.add(new CustomGrantedAuthority(role.getCode())));
                userDTO.setAuthorities(grantedAuthorities);
            }
        }
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userMapper.getUserAndRolesByUserId(userId);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    @Cacheable(key = "#root.methodName + ':' + #userId")
    public JWTUser getJWTUser(Long userId) {
        return userMapper.getJWTUserByUserId(userId);
    }

    @Override
    public UserDTO getCurrentUser() {
        UserDTO userDTO = userMapper.getUserAndRolesByUserId(UserDetailsUtils.getUserId());
        Assert.notNull(userDTO, "没有找到当前用户信息");
        return userDTO;
    }

    @Override
    @Transactional
    public Long register(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO newUser = new UserDO();
        BeanUtils.copyProperties(userDTO, newUser, "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled");
        newUser.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public Boolean isExistUser(String username) {
        UserDO userDO = userMapper.selectUserByUsername(username);
        return userDO == null;
    }

    @Override
    public CurrentUser getUserInfo() {
        UserDTO userDTO = this.getCurrentUser();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userDTO, userInfo);
        return CurrentUser.builder()
                .userInfo(userInfo)
//                .roles(RoleConverter.toBase(userDTO.getRoles()))
//                .menus(MenuConverter.toMenuVO(menuService.getMenusByCurrentUser()))
                .build();
    }

    @Override
    @Transactional
    public Page<UserDTO> getUserPage(Page<UserDTO> page, String keyword) {
        return userMapper.getUserPage(page, keyword);
    }

    @Override
    @Transactional
    public Long addUser(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO userDO = new UserDO();
        CustomBeanUtils.copyPropertiesExcludeMeta(userDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(userDO);
        if (!CollectionUtils.isEmpty(userDTO.getRoleIds())) {
            userRoleService.bathAddUserRole(userDO.getId(), userDTO.getRoleIds());
        }
        return userDO.getId();
    }

    @Override
    @Transactional
    public Long updateUser(UserDTO userDTO) {
        Assert.notNull(userDTO.getId(), "用户id不能为空");
        UserDO userDO = userMapper.selectById(userDTO.getId());
        Assert.notNull(userDO, "更新失败：没有找到该用户或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(userDTO, userDO, "password");
        if (StringUtils.hasText(userDTO.getPassword())) {
            userDO.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        }
        userMapper.updateById(userDO);
        userRoleMapper.deleteByUserId(userDO.getId());
        userRoleService.bathAddUserRole(userDO.getId(), userDTO.getRoleIds());
        return userDO.getId();
    }

    @Override
    @Transactional
    public Long deleteUser(Long userId) {
        userRoleMapper.deleteByUserId(userId);
        userMapper.deleteById(userId);
        return userId;
    }

    @Override
    @Transactional
    public List<Long> batchDeleteUser(List<Long> userIds) {
        Assert.notEmpty(userIds, "没有需要删除的用户");
        for (Long userId : userIds) {
            this.deleteUser(userId);
        }
        return userIds;
    }

    @Override
    public Page<UserDTO> getUserPageByRoleId(Page<UserDTO> page, Long roleId, String keyword) {
        Assert.notNull(roleId, "没有有效的角色ID！");
        return userMapper.getUserPageByRoleId(page, roleId, keyword);
    }

    @Override
    public Page<UserDTO> getUserPageByDepartmentId(Page<UserDTO> page, Long departmentId, String keyword) {
        Assert.notNull(departmentId, "没有有效的部门ID！");
        return userMapper.getUserPageByDepartmentId(page, departmentId, keyword);
    }

    @Override
    @Transactional
    public Long updateUserDepartment(Long userId, Long departmentId) {
        UserDO userDO = userMapper.selectById(userId);
        Assert.notNull(userDO, "没有找到对应的用户");
        userDO.setDepId(departmentId);
        userMapper.updateById(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public List<Long> batchUpdateUserDepartment(UserDepartmentForm userDepartmentForm) {
        List<Long> userIds = userDepartmentForm.getUserIds();
        Long departmentId = userDepartmentForm.getDepartmentId();
        for (Long userId : userIds) {
            this.updateUserDepartment(userId, departmentId);
        }
        return userIds;
    }

    @Override
    public Boolean logout() {
        Long userId = UserDetailsUtils.getUserId();
        return true;
    }

}