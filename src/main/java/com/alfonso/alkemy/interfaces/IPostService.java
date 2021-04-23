package com.alfonso.alkemy.interfaces;

import java.util.List;

import com.alfonso.alkemy.entity.Post;


public interface IPostService {

	public List<Post> listPosts();
	public Post getPosts(Long id);
	public Post guardarPosts(Post posts);
	public Post updatePosts(Post posts);
	public void deletePosts(Long id);
	
}
