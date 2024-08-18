package practice.sample.notice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.sample.notice.model.Notice;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class NoticeController {

    // 공지사항 게시판의 목록에 대한 요청을 처리하는 API
    @GetMapping("/api/notice")
    public Notice noticeSearch() {
        Notice notice = new Notice();

        notice.setId(1);
        notice.setTitle("공지사항입니다.");
        notice.setContent("공지사항 내용입니다.");
        notice.setRegDate(LocalDateTime.of(2021, 2, 8, 0, 0));

        return notice;
    }

}
