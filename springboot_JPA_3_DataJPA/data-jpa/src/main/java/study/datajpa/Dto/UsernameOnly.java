package study.datajpa.Dto;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

//    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
