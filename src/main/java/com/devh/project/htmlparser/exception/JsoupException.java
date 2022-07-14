package com.devh.project.htmlparser.exception;

/**
 * <pre>
 * Description :
 *     Jsoup 수행 도중 일어난 커스텀 예외객체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
public class JsoupException extends Exception {
    public JsoupException(String message) {
        super(message);
    }
}
