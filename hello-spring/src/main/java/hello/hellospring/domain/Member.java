package hello.hellospring.domain;

import javax.persistence.*;

@Entity //jpa관리 위해
public class Member {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto증가
    private Long id;

    @Column(name="name")    //db랑 이름다를때 매칭
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
