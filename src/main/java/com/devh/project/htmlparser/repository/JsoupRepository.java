package com.devh.project.htmlparser.repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import com.devh.project.htmlparser.util.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description :
 *     Jsoup을 이용하여 HTML 조회 관련 작업을 수행
 * ===============================================
 * Member fields :
 *     String regex
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
@Component
@Slf4j
public class JsoupRepository {
    private final String regex = "[^0-9a-zA-Z]";

    /**
     * <pre>
     * Description
     *     url을 Document 객체로 반환
     * ===============================================
     * Parameters
     *     String url
     * Returns
     *     Document
     * Throws
     *     JsoupException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2022. 7. 14.
     * </pre>
     */
    private Document findDocumentByUrl(String url) throws JsoupException {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            log.error(ExceptionUtils.stackTraceToString(e));
            throw new JsoupException(e.getMessage());
        }
    }

    /**
     * <pre>
     * Description
     *     url과 type에 따라 알맞는 raw string 반환
     * ===============================================
     * Parameters
     *     String url
     *     Type type
     * Returns
     *     String
     * Throws
     *     JsoupException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2022. 7. 15.
     * </pre>
     */
    public String findRawStringByUrlAndType(String url, Type type) throws JsoupException {
        Document document = this.findDocumentByUrl(url);
        document.childNodes()
	        .stream()
	        .filter(node -> node instanceof DocumentType)
	        .findFirst()
	        .ifPresent(Node::remove);
        String rawString = null;
        switch (type) {
            case HTML:
                rawString = document.parser(Parser.htmlParser().settings(new ParseSettings(true, true))).html().replaceAll(regex, "");
                break;
            case TEXT:
                rawString = document.parser(Parser.htmlParser().settings(new ParseSettings(true, true))).text().replaceAll(regex, "");
                break;
        }

        return rawString;
    }
}
