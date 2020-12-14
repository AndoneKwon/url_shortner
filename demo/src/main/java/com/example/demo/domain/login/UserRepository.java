package com.example.demo.domain.login;

import com.sun.istack.Nullable;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    @Nullable
    User findFirstByUid(String uid);
}
