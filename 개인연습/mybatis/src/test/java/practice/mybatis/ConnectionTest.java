package practice.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

@Slf4j
@SpringBootTest
public class ConnectionTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void contextLoads() {

        try (Connection con = sqlSessionFactory.openSession().getConnection()) {
            log.info("커넥션 성공");
        } catch (Exception e) {
            log.info("커넥션 실패");
            e.printStackTrace();
        }
    }
}
