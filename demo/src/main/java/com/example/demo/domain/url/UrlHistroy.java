package com.example.demo.domain.url;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Table(name="URLHISTORY")
@Getter
@NoArgsConstructor
@Entity
public class UrlHistroy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private long uid;

    @NonNull
    private String originalUrl;

    @NonNull
    private String shortUrl;

    @Builder
    UrlHistroy(long uid, String originalUrl,String shortUrl){
        this.uid=uid;
        this.originalUrl=originalUrl;
        this.shortUrl=shortUrl;
    }
}

