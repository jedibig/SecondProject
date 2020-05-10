package com.java.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.java.dto.PropertyOwner;
import com.java.service.UserAuthenticationService;

@Controller
public class LoginController{
	
	@Autowired 
	UserAuthenticationService ownerlogin;
	
	@GetMapping("/login.do")
	public String getData(HttpServletRequest req) { //@ModelAttribute PropertyOwner owner) {
		
		PropertyOwner existingOwner = (PropertyOwner) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpSession session = req.getSession();
		session.setAttribute("owner", existingOwner);
		return "index";
	}
	
	@GetMapping("postprop.do")
	public String propPage(HttpSession session) {
		PropertyOwner owner = (PropertyOwner) session.getAttribute("owner");
		if(owner == null) {
			return "login";
		}
		return "welcome";
		
	}
	
	@GetMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("profile")
	public String profile(HttpSession session, HttpServletRequest req) {
		PropertyOwner owner = (PropertyOwner) session.getAttribute("owner");
		if(owner == null) {
			return "login";
		}
		PropertyOwner profile = ownerlogin.getProfile(owner.getId());
		req.setAttribute("profile", profile);
		return "profile";
	}
	
	@GetMapping("listprop")
	public String listProp(HttpSession session) {
		PropertyOwner owner = (PropertyOwner) session.getAttribute("owner");
		if(owner == null) {
			return "register";
		}
		return "welcome";
	}

}
