package com.devh.project.htmlparser.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ParserResponseVO {
    private List<String> bunches;
    private String remainder;
}
