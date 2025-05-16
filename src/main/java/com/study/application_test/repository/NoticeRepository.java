package com.study.application_test.repository;

import com.study.application_test.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository <Notice, Long> {
}
