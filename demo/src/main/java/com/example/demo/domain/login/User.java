package com.example.demo.domain.login;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "User")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String u_id;
    @NonNull
    private String password;

    //0 : 일반 사용자 1 : 관리자
    @NonNull
    @ColumnDefault("0")
    private int status;


}
