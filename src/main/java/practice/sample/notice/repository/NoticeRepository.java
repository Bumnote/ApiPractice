package practice.sample.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import practice.sample.notice.domain.Notice;

@RestController
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
