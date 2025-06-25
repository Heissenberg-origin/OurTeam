package org.example.service;

import org.example.entity.LoginInfo;

public interface LoginService {
    // 登录验证
    LoginInfo login(int id, String password);

    // 修改密码
    boolean changePassword(int id, String oldPassword, String newPassword);
}
