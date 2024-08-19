package practice.sample.notice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    // ID, 제목, 내용, 등록일
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime regDate;

    // 커스텀 생성자 구현
    public Notice(String title, String content, LocalDateTime now) {
        this.title = title;
        this.content = content;
        this.regDate = now;
    }
}
