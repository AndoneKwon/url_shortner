package com.example.demo.domain.url;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Table(name="URLS")
@Getter
@NoArgsConstructor
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String originalUrl;

    @Builder
    Url(String originalUrl){
        this.originalUrl=originalUrl;
    }

}
