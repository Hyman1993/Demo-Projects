package org.client.b.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 获取页面的接口
 * @author hyman
 *
 */
@Controller
public class IndexAction {

    @GetMapping(value = "")
    public String index() {
        System.out.println("进入ClientB首页");
        return "index.html";
    }

    @GetMapping(value = "securedPage")
    public String home() {
        System.out.println("进入ClientB securedPage");
        return "securedPage.html";
    }
}
