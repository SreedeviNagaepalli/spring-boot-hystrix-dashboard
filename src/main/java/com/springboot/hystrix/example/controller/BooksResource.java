package com.springboot.hystrix.example.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.hystrix.example.request.BookDetail;

@RestController
@RequestMapping("/rest/books")
public class BooksResource {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${client.url}")
	private String url;
	
	@HystrixCommand(fallbackMethod="hystrixFallBack",
			commandKey="fallback",groupKey="fallback")
	@GetMapping(value="/hystrix")
	public List<BookDetail> hystrix()
	{
		LOGGER.info("Inside the hystrix method URL is :"+url);
		return restTemplate.getForObject(url, List.class);
	}
	
	@GetMapping(value="")
	public List<BookDetail> general()
	{
		LOGGER.info("Inside the general method URL is :"+url);
		return restTemplate.getForObject(url, List.class);
	}
	
	public List<BookDetail> hystrixFallBack()
	{
		return Collections.emptyList();
	}
}
