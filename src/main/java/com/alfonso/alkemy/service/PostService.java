package com.alfonso.alkemy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alfonso.alkemy.entity.Post;
import com.alfonso.alkemy.interfaces.IPostService;
import com.alfonso.alkemy.repository.PostRepository;

@Service
public class PostService implements IPostService{

	@Autowired
	PostRepository repositoryPost;
	
	@Override
	public List<Post> listPosts() {
		List<Post> lista = repositoryPost.findByOrderByCreate_atDesc();
		for (Post post : lista) {
			post.setContenido(null);
		}
		return lista;
	}

	@Override
	public Post getPosts(Long id) {
		return repositoryPost.findById(id).orElse(null);
	}

	@Override
	public Post guardarPosts(Post posts) {
		return repositoryPost.save(posts);
	}

	@Override
	public Post updatePosts(Post posts) {
		return repositoryPost.save(posts);
	}

	@Override
	public void deletePosts(Long id) {
		repositoryPost.deleteById(id);
	}


}
