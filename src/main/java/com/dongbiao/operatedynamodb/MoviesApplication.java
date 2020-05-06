package com.dongbiao.operatedynamodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesApplication {

    public static void main(String[] args) {
        //MovieCreateTable.createTable();
        //new MovieGetData().get();
        SpringApplication.run(MoviesApplication.class, args);
    }

}
