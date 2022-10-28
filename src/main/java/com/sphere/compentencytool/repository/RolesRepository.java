package com.sphere.compentencytool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sphere.compentencytool.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

}
