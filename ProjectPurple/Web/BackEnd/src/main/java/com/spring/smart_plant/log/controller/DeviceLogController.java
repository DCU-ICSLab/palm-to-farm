package com.spring.smart_plant.log.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.smart_plant.common.domain.ResultDTO;

@RestController
@RequestMapping("/log")
public class DeviceLogController {
	
	@GetMapping("/latest/{sfCode}")
	public ResultDTO getLatestLog(@PathVariable int sfCode) {
		return null;
	}
	
	@GetMapping("/all/{sfCode}")
	public ResultDTO getAllLog(@PathVariable int sfCode) {
		return null;
	} 
}