package practice.mybatis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

    private int seq;
    private String name;
    private int age;
    private String email;

    public MemberDto(int seq, String name, int age, String email) {
        this.seq = seq;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
