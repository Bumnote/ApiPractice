package practice.sample.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.sample.notice.domain.Notice;
import practice.sample.notice.domain.NoticeDeleteInput;
import practice.sample.notice.domain.NoticeInput;
import practice.sample.notice.exception.AlreadyDeletedException;
import practice.sample.notice.exception.NoticeNotFoundException;
import practice.sample.notice.repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class NoticeController {

    private final NoticeRepository noticeRepository;

    public NoticeController(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 15. 공지사항에 글을 등록하기 위한 글작성에 대한 REST API 구현
    @PostMapping("/api/notice/create")
    public Notice noticeCreate(@RequestBody NoticeInput noticeInput) {
        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .content(noticeInput.getContent())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .deleted(false)
                .deleteDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);
        return notice;
    }

    // 16. 공지사항에 글을 수정하기 위한 상세 정보 요청에 대한 API 구현
    @GetMapping("/api/notice/{id}")
    public Notice noticeUpdate(@PathVariable Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.orElse(null);
    }

    // 17. 공지사항에 글을 수정하기 위한 글 수정에 대한 API 구현
    @PutMapping("/api/notice/{id}")
    public Notice noticeUpdate(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {
        Optional<Notice> notice = noticeRepository.findById(id);
        // 해당 id 의 게시글이 존재한다면 -> 입력으로 들어온 값으로 값 변경
        if (notice.isPresent()) {
            Notice newNotice = notice.get();
            newNotice.setTitle(noticeInput.getTitle());
            newNotice.setContent(noticeInput.getContent());
            newNotice.setUpdateDate(LocalDateTime.now());
            noticeRepository.save(newNotice);
            return newNotice;
        } else {
            return null;
        }
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 18. 19. 공지사항에 글을 수정하기 위한 글 수정에 대한 API 구현 -> 예외처리 Handler 구현
    @PutMapping("/api/notice/p/{id}")
    public void noticeUpdate1(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {

        Optional<Notice> notice = noticeRepository.findById(id);
        // 공지사항 글이 존재하지 않는 경우
        if (!notice.isPresent()) {
            // 예외 발생
            throw new NoticeNotFoundException("공지사항에 글이 존재하지 않습니다.");
        }
        // 공지사항 글이 존재하는 경우
        notice.get().setTitle(noticeInput.getTitle());
        notice.get().setContent(noticeInput.getContent());
        notice.get().setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice.get());
    }

    // 20. 공지사항 글의 조회수를 증가시키는 API 구현 / 부분적 변경 -> PATCH 사용
    @PatchMapping("/api/notice/hits/{id}")
    public void noticeHits(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        notice.setHits(notice.getHits() + 1);
        noticeRepository.save(notice);
    }


    // 21. 공지사항의 글을 삭제하기 위한 API 구현
    @DeleteMapping("/api/notice/{id}")
    public void noticeDelete(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }

    // 22. 공지사항의 글을 삭제하기 위한 API 구현 + 400에러 및 "내용이 존재하지 않습니다." 메시지 반환
    @DeleteMapping("/api/notice/p/{id}")
    public void noticeDelete1(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new NoticeNotFoundException("내용이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }


    // Error Handler
    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    // 23. 공지사항의 글을 삭제하기 위한 API 구현 + 글이 이미 삭제된 경우 -> 200 "이미 삭제된 글입니다." 메시지 반환
    @DeleteMapping("/api/notice/pp/{id}")
    public void deleteNotice(@PathVariable Long id) {
        Notice notice = noticeRepository
                .findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        if (notice.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 공지사항의 글입니다.");
        }

        notice.setDeleted(true);
        notice.setDeleteDate(LocalDateTime.now());

        noticeRepository.save(notice);
    }

    // 24. 공지사항의 글을 삭제하기 위한 API 구현 -> 여러 개의 글을 동시에 삭제
    @DeleteMapping("/api/delete")
    public void deleteNoticeList(@RequestBody NoticeDeleteInput noticeDeleteInput) {

        List<Notice> noticeList = noticeRepository
                .findByIdIn(noticeDeleteInput.getIdList())
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeList.stream().forEach(e -> {
            e.setDeleted(true);
            e.setDeleteDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(noticeList);
    }

    // 25. 공지사항의 모든 글을 삭제하는 API 구현
    @DeleteMapping("/api/delete/all")
    public void deleteAll() {
        noticeRepository.deleteAll(); // 너무 간단하다.
    }

}
