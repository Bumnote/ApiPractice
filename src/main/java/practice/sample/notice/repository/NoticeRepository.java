package practice.sample.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.sample.notice.domain.Notice;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {


        Optional<List<Notice>> findByIdIn(List<Long> idList);

}
