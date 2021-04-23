package com.alfonso.alkemy.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alfonso.alkemy.entity.Post;
import com.alfonso.alkemy.interfaces.IPostService;

@Controller
@RequestMapping("/")
public class PostsController {

	@Autowired
	IPostService servicePost;

	@GetMapping("home")
	public String listPost(Model modelo) {
		List<Post> posts = servicePost.listPosts();
		modelo.addAttribute("listPosts", posts);
		return "home";
	}

	@GetMapping("create")
	public String create(Post posts) {
		return "formPosts";
	}

	@PostMapping("save")
	public String save(@Valid Post posts, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "formPosts";
		}

		try {
			servicePost.guardarPosts(posts);
		} catch (Exception e) {
			result.addError(new ObjectError("excepcion", e.getMessage()));
			return "formPosts";
		}
		redirect.addFlashAttribute("save", "Guardado con exito!");
		return "redirect:/home";
	}

	@GetMapping("update/{id}")
	public String update(@PathVariable Long id, RedirectAttributes redirect) {
		Post posts = servicePost.getPosts(id);
		redirect.addFlashAttribute("post", posts);
		return "redirect:/create";
	}

	@GetMapping("detalle/{id}")
	public String detalle(@PathVariable Long id, Model modelo, RedirectAttributes redirect) {
		Post posts = servicePost.getPosts(id);
		modelo.addAttribute("detallePost", posts);
		return "detalle";
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable Long id) {
		servicePost.deletePosts(id);
		return "redirect:/home";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
}
