package com.devh.project.htmlparser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping
    public String getTest() {
        log.info("[GET] test");
        return "test.html";
    }

}
