package com.devh.project.htmlparser.util;

import com.devh.project.htmlparser.exception.JsoupException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <pre>
 * Description :
 *     Jsoup 래퍼 유틸 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 16.
 * </pre>
 */
@Component
@Slf4j
public class JsoupUtils {
    /**
     * <pre>
     * Description
     *     url을 통해 Document 객체 반환
     * ===============================================
     * Parameters
     *     String url
     * Returns
     *     String url
     * Throws
     *     JsoupException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2022. 7. 16.
     * </pre>
     */
    public Document findDocumentByUrl(String url) throws JsoupException {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            log.error(ExceptionUtils.stackTraceToString(e));
            throw new JsoupException(e.getMessage());
        }
    }
}
