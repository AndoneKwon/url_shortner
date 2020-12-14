package com.example.demo.domain.login;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "u_id")
    private String uid;
    @NonNull
    private String password;
    @NonNull
    private String salt;

    //0 : 일반 사용자 1 : 관리자
    @NonNull
    @ColumnDefault("0")
    private int status;

    @NonNull
    @ColumnDefault("0")
    private int verified;

    @Builder
    User(String uid,String password,String salt){
        this.uid=uid;
        this.password=password;
        this.salt=salt;
    }
}
