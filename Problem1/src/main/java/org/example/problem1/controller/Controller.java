package org.example.problem1.controller;

import java.util.List;

import org.example.problem1.service.MyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Controller {
	private final MyService myService;

	@GetMapping("/{category}")
	public List BoardList(@PathVariable("category") String category){
		return myService.listBoard(category);
	}
}
