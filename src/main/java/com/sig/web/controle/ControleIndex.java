package com.sig.web.controle;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControleIndex {
	
	@GetMapping("*")
	public String index() {		
		return "index";
	}
	


}
