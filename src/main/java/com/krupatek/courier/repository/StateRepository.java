package com.krupatek.courier.repository;


import com.krupatek.courier.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StateRepository extends JpaRepository<State,String> {
    @Query(value = "SELECT state_name FROM easynew.state ORDER BY state_name;", nativeQuery = true)
    Set<String> findAllOrderByStateName();




}
