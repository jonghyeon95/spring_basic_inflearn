package practice.mybatis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import practice.mybatis.dto.MemberDto;
import practice.mybatis.mapper.MemberMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper testMapper;

    @Override
    public List<MemberDto> getUserList() {
        return testMapper.getUserList();
    }

}
