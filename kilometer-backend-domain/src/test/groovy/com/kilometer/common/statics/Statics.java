package com.kilometer.common.statics;

import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import java.util.List;

public class Statics {

    // Item 관련 상수
    public static final Long 전시회_ID = 1L;

    // Archive 관련 상수
    public static final String 아카이브_코멘트 = "comment";
    public static final int 아카이브_별점 = 1;
    public static final boolean 아카이브_공개_설정 = true;
    public static final List<String> 아카이브_이미지들 = List.of("image1", "image2");
    public static final List<UserVisitPlace> 사용자_방문장소들 = List.of();
}
