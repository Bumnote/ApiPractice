package practice.sample.notice.domain;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
public class NoticeDeleteInput {

    private List<Long> idList;
}
