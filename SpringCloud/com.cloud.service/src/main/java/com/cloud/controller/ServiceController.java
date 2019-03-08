package com.cloud.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ServiceController {

	@RequestMapping(value="/math.json")
	public String math(@RequestParam Integer number) {
		return String.valueOf(number * number);
	}
}
