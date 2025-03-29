package com.svwh.phonereview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 19:45
 */
@RestController
@RequestMapping()
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
