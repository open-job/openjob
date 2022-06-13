package io.openjob.server.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/test")
    public String index() {
        return "openapi";
    }
}
