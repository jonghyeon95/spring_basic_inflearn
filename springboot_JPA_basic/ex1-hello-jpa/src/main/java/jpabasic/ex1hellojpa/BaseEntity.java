package jpabasic.ex1hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder @NoArgsConstructor @AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    private String create_by;
    private LocalDateTime create_date;
    private String last_modified_by;
    private LocalDateTime last_modified_date;

}
