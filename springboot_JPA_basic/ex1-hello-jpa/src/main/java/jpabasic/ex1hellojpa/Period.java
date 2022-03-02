package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Period {

    private LocalDateTime start_date;

    private LocalDateTime end_date;
}
