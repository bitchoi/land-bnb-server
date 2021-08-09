package com.bitchoi.landbnbserver.repository;

import com.bitchoi.landbnbserver.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("SELECT CASE WHEN count(m) > 0 THEN true ELSE false END FROM Member m WHERE m.email = :email")
    boolean existByEmail(@Param("email") String email);

    Optional<Member> findByEmail(String email);


}
