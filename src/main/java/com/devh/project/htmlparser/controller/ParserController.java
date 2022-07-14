package com.devh.project.htmlparser.controller;

import com.devh.project.htmlparser.constant.ApiStatus;
import com.devh.project.htmlparser.exception.JsoupException;
import com.devh.project.htmlparser.exception.ParserServiceException;
import com.devh.project.htmlparser.service.ParserService;
import com.devh.project.htmlparser.vo.ApiResponseVO;
import com.devh.project.htmlparser.vo.ParserRequestVO;
import com.devh.project.htmlparser.vo.ParserResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parser")
@RequiredArgsConstructor
@Slf4j
public class ParserController {
    private final ParserService parserService;

    @PostMapping
    public ApiResponseVO<ParserResponseVO> parse(@RequestBody ParserRequestVO parserRequestVO) throws JsoupException, ParserServiceException {
        log.info("[POST] parser "+parserRequestVO);
        return ApiResponseVO.success(ApiStatus.Success.OK, parserService.parse(parserRequestVO));
    }

}
