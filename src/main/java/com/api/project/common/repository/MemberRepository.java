package com.api.project.common.repository;

import com.api.project.common.entity.Member;
import com.api.project.common.enums.IsYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberIdAndMemberPw(String memberId, String memberPw);

    Optional<Member> findByIdAndIsDelete(long id, IsYn isDelete);
}