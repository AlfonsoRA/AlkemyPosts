package com.alfonso.alkemy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alfonso.alkemy.entity.Post;
import com.alfonso.alkemy.interfaces.IPostService;

@RestController
@RequestMapping("/posts/")
public class PostsRestController {
	
	@Autowired
	IPostService servicePost;
	
	@GetMapping("listPosts")
	public List<Post> listPost(){
		return servicePost.listPosts();
	}
	
	@GetMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getPosts(@PathVariable("id") Long id) {
		Post posts = null;
		Map<String, Object> response = new HashMap<>();
		posts = servicePost.getPosts(id);
		if(posts == null){
			response.put("mensaje", "El id del post: ".concat(id.toString().concat(" no existe en la base de datos!!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FORBIDDEN);
		};
		return new ResponseEntity<Post>(posts, HttpStatus.OK);
	}
	
	@PostMapping("savePost")
	public ResponseEntity<?> save(@Valid @RequestBody Post posts, BindingResult result) {
		
		Post postsNew = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err ->  "El campo '"+ err.getField()+ "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
					
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			postsNew = servicePost.guardarPosts(posts);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
		
		response.put("mensaje", "El posts ha sido creado con exito!!");
		response.put("posts", postsNew );
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> updatePost(@Valid @RequestBody Post posts, BindingResult result, @PathVariable Long id) {
		
		Post postsActual = servicePost.getPosts(id);
		
		
		Post postsUpdate= null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err ->  "El campo '"+ err.getField()+ "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
					
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(postsActual == null) {
			response.put("mensaje", "Error: No se pudo editar, el posts id: ".concat(id.toString().concat(" no existe en la base de datos!!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			postsActual.setTitulo(posts.getTitulo());
			postsActual.setImagen(posts.getImagen());
			postsActual.setContenido(posts.getContenido());
			postsActual.setCreate_at(posts.getCreate_at());
			postsActual.setCategoria(posts.getCategoria());
			
			postsUpdate = servicePost.updatePosts(postsActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el posts en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El posts ha sido actualizado con exito!!");
		response.put("posts", postsUpdate );
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteMateria(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
	
		try {
			servicePost.deletePosts(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error: No se pudo editar, el posts id: ".concat(id.toString().concat(" no existe en la base de datos!!")));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El posts ha sido eliminado con exito!!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
	
	
	

}
