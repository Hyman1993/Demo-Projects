package org.auth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 该接口类中的唯一接口，用于ClientA和ClientB在登录成功后获取用户信息用
 * 该接口地址可以任意修改，只要与ClientA/B中配置的用户信息地址一致即可
 * @author hyman
 *
 */
@RestController
@RequestMapping(value = "user")
public class UserAction {

    @GetMapping(value = "me")
    public Principal me(Principal principal) {
        System.out.println("调用me接口获取用户信息：" + principal);
        return principal;
    }
}
