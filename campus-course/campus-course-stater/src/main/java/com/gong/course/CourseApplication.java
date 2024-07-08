package com.gong.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.gong.course.model.dao")
@ComponentScan("com.gong.**")
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class,args);
    }
}
