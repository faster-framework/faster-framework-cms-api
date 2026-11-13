package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.SysUser;
import cn.org.faster.cms.mapper.SysUserMapper;
import cn.org.faster.cms.security.TokenDenylist;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * 后台用户服务。
 */
@Service
@Transactional
public class UserService {

    private static final String PASSWORD_ALPHABET =
            "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final SysUserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenDenylist tokenDenylist;

    public UserService(SysUserMapper userMapper,
                       UserRoleService userRoleService,
                       PasswordEncoder passwordEncoder,
                       TokenDenylist tokenDenylist) {
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenDenylist = tokenDenylist;
    }

    public record Query(String account, String name) {
    }

    @Transactional(readOnly = true)
    public PageResult<SysUser> page(Query query, long current, long size) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasText(query.account())) {
            wrapper.like(SysUser::getAccount, query.account());
        }
        if (StringUtils.hasText(query.name())) {
            wrapper.like(SysUser::getName, query.name());
        }
        wrapper.orderByDesc(SysUser::getCreateDate);
        return PageResult.of(userMapper.selectPage(
                Page.of(current < 1 ? 1 : current, size < 1 ? 20 : size), wrapper));
    }

    @Transactional(readOnly = true)
    public SysUser getRequired(Long id) {
        SysUser user = id == null ? null : userMapper.selectById(id);
        if (user == null) {
            throw BizException.of(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<SysUser> findByAccount(String account) {
        if (!StringUtils.hasText(account)) {
            return Optional.empty();
        }
        return Optional.ofNullable(userMapper.selectOne(
                Wrappers.<SysUser>lambdaQuery().eq(SysUser::getAccount, account).last("limit 1")));
    }

    @Transactional(readOnly = true)
    public List<SysUser> listAll() {
        return userMapper.selectList(Wrappers.<SysUser>lambdaQuery().orderByDesc(SysUser::getCreateDate));
    }

    public SysUser create(SysUser user, String rawPassword) {
        if (!StringUtils.hasText(rawPassword)) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "密码不能为空");
        }
        if (findByAccount(user.getAccount()).isPresent()) {
            throw BizException.of(ErrorCode.USER_EXISTS);
        }
        user.setPassword(passwordEncoder.encode(rawPassword));
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException ex) {
            throw BizException.of(ErrorCode.USER_EXISTS);
        }
        return user;
    }

    public SysUser update(Long id, SysUser patch) {
        requireMutable(id);
        patch.setId(id);
        patch.setPassword(null);
        patch.setAccount(null);
        userMapper.updateById(patch);
        return userMapper.selectById(id);
    }

    public void delete(Long id) {
        requireMutable(id);
        userMapper.deleteById(id);
        userRoleService.deleteByUser(id);
        tokenDenylist.invalidateUser(id);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        SysUser user = getRequired(id);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw BizException.of(ErrorCode.OLD_PASSWORD_ERROR);
        }
        updatePassword(id, newPassword);
    }

    /**
     * 重置为随机密码并返回明文，由管理员转交用户。
     */
    public String resetPassword(Long id) {
        getRequired(id);
        String password = randomPassword(12);
        updatePassword(id, password);
        return password;
    }

    private void updatePassword(Long id, String rawPassword) {
        if (!StringUtils.hasText(rawPassword) || rawPassword.length() < 8) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "密码长度至少 8 位");
        }
        SysUser patch = new SysUser();
        patch.setId(id);
        patch.setPassword(passwordEncoder.encode(rawPassword));
        userMapper.updateById(patch);
        tokenDenylist.invalidateUser(id);
    }

    private void requireMutable(Long id) {
        getRequired(id);
        if (SysUser.ADMIN_ID == id) {
            throw BizException.of(ErrorCode.USER_PROTECTED);
        }
    }

    private String randomPassword(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(PASSWORD_ALPHABET.charAt(RANDOM.nextInt(PASSWORD_ALPHABET.length())));
        }
        return builder.toString();
    }
}
