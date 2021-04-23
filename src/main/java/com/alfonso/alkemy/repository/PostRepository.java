package com.alfonso.alkemy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfonso.alkemy.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("select p from Post p order by p.create_at desc")
	public List<Post> findByOrderByCreate_atDesc();
	
}
