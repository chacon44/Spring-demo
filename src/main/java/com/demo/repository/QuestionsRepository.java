package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//CrudRepository mainly provides CRUD functions.
//PagingAndSortingRepository provides methods to do pagination and sorting records.
//JpaRepository provides some JPA-related methods such as flushing the persistence context and deleting records in a batch.
//Because of the inheritance mentioned above, JpaRepository will have all the functions of CrudRepository and PagingAndSortingRepository.
// So if you don't need the repository to have the functions provided by JpaRepository and PagingAndSortingRepository , use CrudRepository.
@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}
