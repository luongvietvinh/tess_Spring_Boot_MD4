package com.example.demoSpringBoot_MD4.repository;

import com.example.demoSpringBoot_MD4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IUserRepo extends PagingAndSortingRepository<User, Long> {
    @Query(value = "select u from User u where u.userName like concat('%' ,:name, '%')")
    ArrayList<User> findAllByName(@Param("name") String name);
}
