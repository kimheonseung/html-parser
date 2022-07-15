package com.devh.project.htmlparser.exception;

/**
 * <pre>
 * Description :
 *     Parser 수행 도중 일어난 커스텀 예외객체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
public class ParserServiceException extends Exception {
	private static final long serialVersionUID = 8755953035801853440L;

	public ParserServiceException(String message) {
        super(message);
    }
}
