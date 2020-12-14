package com.example.demo.domain.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlHistoryRepository extends JpaRepository<UrlHistroy,Long> {
    List<UrlHistroy> findAllByUid(long uid);
}
