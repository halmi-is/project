package com.simplecoding.simpledms.vo.admin;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Admin {
        private int fno;            // FAQ 고유 ID (FNO)
        private String hashtag;       // 제목 (TITLE)
        private String question;    // 질문 내용 (QUESTION)
        private String answer;      // 답변 내용 (ANSWER)

    }
