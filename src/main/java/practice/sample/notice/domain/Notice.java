package practice.sample.notice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {

    // ID, 제목, 내용, 등록일
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column
    private int hits; // 조회 수

    @Column
    private int likes; // 좋아요 수

    // 커스텀 생성자 구현
    public Notice(String title, String content, LocalDateTime now) {
        this.title = title;
        this.content = content;
        this.regDate = now;
    }
}
