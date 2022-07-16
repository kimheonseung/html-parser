package com.devh.project.htmlparser.repository;

import com.devh.project.htmlparser.constant.Type;
import com.devh.project.htmlparser.exception.JsoupException;
import com.devh.project.htmlparser.util.JsoupUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

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
@RequiredArgsConstructor
@Slf4j
public class JsoupRepository {
    private final JsoupUtils jsoupUtils;

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
        Document document = jsoupUtils.findDocumentByUrl(url);
        /* DOCTYPE 제거 */
        document.childNodes()
	        .stream()
	        .filter(node -> node instanceof DocumentType)
	        .findFirst()
	        .ifPresent(Node::remove);
        
        /* html 파서. case sensitive를 고려하여 true 옵션 설정 */
        final Parser parser = Parser.htmlParser().settings(new ParseSettings(true, true));
        
        String rawString = null;
        String regex = "[^0-9a-zA-Z]";
        switch (type) {
            case HTML:
                rawString = document.parser(parser).html().replaceAll(regex, "");
                break;
            case TEXT:
                rawString = document.parser(parser).text().replaceAll(regex, "");
                break;
        }

        return rawString;
    }
}
