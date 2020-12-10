package com.example.demo.domain.url;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url,Long> {
    @Nullable
    Url findFirstByOriginalUrl(String url);
    @Nullable
    Url findFirstById(long idx);

}
