package ous.train.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ous.train.service.MemberService;

@RestController
public class testController {
	@Autowired
	private MemberService memberService;
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/count")
	public int count() {
		return memberService.count();
	}
}
