package com.mosa.hogwartsartifactsonline.repo;

import com.mosa.hogwartsartifactsonline.entity.HogwartsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<HogwartsUser, Integer> {

}
