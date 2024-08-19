package practice.sample.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.sample.notice.domain.Notice;
import practice.sample.notice.domain.NoticeInput;
import practice.sample.notice.repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NoticeController {

    private final NoticeRepository noticeRepository;

    public NoticeController(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }


    // 공지사항 게시판의 목록에 대한 요청을 처리하는 API
    @GetMapping("/api/notice")
    public List<Notice> noticeSearch() {
        Notice notice1 = new Notice(1, "공지사항입니다.", "공지사항 내용입니다.", LocalDateTime.of(2021, 1, 30, 0, 0));
        Notice notice2 = new Notice(2, "두번째 공지사항입니다.", "두번째 공지사항 내용입니다.", LocalDateTime.of(2021, 1, 31, 0, 0));
        List<Notice> noticeList = new ArrayList<>();
        noticeList.add(notice1);
        noticeList.add(notice2);

        return noticeList;
    }

    // 공지사항 게시판의 목록 중 전체 개수 정보에 대한 요청을 처리하는 API
    @GetMapping("/api/notice/count")
    public int noticeCount() {

        Notice notice1 = new Notice(1, "공지사항입니다.", "공지사항 내용입니다.", LocalDateTime.of(2021, 1, 30, 0, 0));
        Notice notice2 = new Notice(2, "두번째 공지사항입니다.", "두번째 공지사항 내용입니다.", LocalDateTime.of(2021, 1, 31, 0, 0));
        List<Notice> noticeList = new ArrayList<>();
        noticeList.add(notice1);
        noticeList.add(notice2);

        return noticeList.size();
    }

    // 공지사항에 글을 등록하기 위해서 글작성에 대한 API
    @PostMapping("/api/notice")
    public Notice noticePost(Notice notice) {

        notice.setId(2);
        notice.setRegDate(LocalDateTime.now());

        return notice;
    }

    @PostMapping("/api/notice/add")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {
        Notice notice = new Notice(noticeInput.getTitle(), noticeInput.getContent(), LocalDateTime.now());

        noticeRepository.save(notice);
        return notice;
    }


}
