package practice.sample.notice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notice {

    // ID, 제목, 내용, 등록일
    private int id;
    private String title;
    private String content;
    private LocalDateTime regDate;

}
