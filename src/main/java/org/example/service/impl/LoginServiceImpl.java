package org.example.service.impl;

import org.example.entity.LoginInfo;
import org.example.mapper.LoginInfoMapper;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public LoginInfo login(int id, String password) {
        return loginInfoMapper.checkLogin(id, password);
    }

    @Override
    @Transactional
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        // 验证旧密码是否正确
        LoginInfo user = loginInfoMapper.checkLogin(id, oldPassword);
        if (user == null) {
            return false;
        }

        // 更新密码
        return loginInfoMapper.updatePassword(id, oldPassword, newPassword) > 0;
    }
}
