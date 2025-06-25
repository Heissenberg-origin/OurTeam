package org.example.controller;

import org.example.entity.LoginInfo;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam int id,
            @RequestParam String password) {

        LoginInfo user = loginService.login(id, password);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("用户名或密码错误");
        }

        return ResponseEntity.ok(user);
    }

    // 修改密码接口
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @RequestParam int id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        if (newPassword == null || newPassword.length() < 4) {
            return ResponseEntity.badRequest()
                    .body("新密码长度不能少于4位");
        }

        boolean success = loginService.changePassword(id, oldPassword, newPassword);

        if (success) {
            return ResponseEntity.ok("密码修改成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("密码修改失败，请检查当前密码是否正确");
        }
    }
}
