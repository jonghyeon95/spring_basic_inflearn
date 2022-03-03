package jpabasic.ex1hellojpql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {

    private String username;
    private int age;
}
