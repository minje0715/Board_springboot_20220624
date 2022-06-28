package com.its.board.repository;

import com.its.board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <MemberEntity, Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
