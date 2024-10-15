package com.sparta.schedulemangerapp_develop.domain.member.repository;

import com.sparta.schedulemangerapp_develop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
