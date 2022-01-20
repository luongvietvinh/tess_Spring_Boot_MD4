package com.example.demoSpringBoot_MD4.repository;

import com.example.demoSpringBoot_MD4.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IRolerepo extends PagingAndSortingRepository<Role , Long> {
}
