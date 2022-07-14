package com.devh.project.htmlparser.vo;

import com.devh.project.htmlparser.constant.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * Description :
 *     사용자의 파싱 요청 정보를 담는 객체
 * ===============================================
 * Member fields :
 *     String url
 *     Type type
 *     int bunch
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2022. 7. 14.
 * </pre>
 */
@Getter
@Setter
@ToString
public class ParserRequestVO {
    private String url;
    private Type type;
    private int bunch;
}
