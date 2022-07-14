package com.devh.project.htmlparser.exception;

import com.devh.project.htmlparser.constant.ApiStatus;
import com.devh.project.htmlparser.vo.ApiResponseVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 * Description :
 *     API 수행 중 감지된 에러 핸들링
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({JsoupException.class})
    public <T>ApiResponseVO<T> handleJsoupException(Exception e) {
        return ApiResponseVO.customError(ApiStatus.CustomError.JSOUP_ERROR, e.getMessage());
    }
    @ExceptionHandler({ParserServiceException.class})
    public <T>ApiResponseVO<T> handleParserServiceException(Exception e) {
        return ApiResponseVO.customError(ApiStatus.CustomError.PARSER_SERVICE_ERROR, e.getMessage());
    }
}
