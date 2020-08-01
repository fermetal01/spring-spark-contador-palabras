package co.edu.ucentral.spark.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ucentral.spark.service.ContadorPalabrasService;

import static java.util.Collections.reverseOrder;
@RestController
public class ContadorPalabrasController {

	@Autowired
	ContadorPalabrasService service;

	@RequestMapping(method = RequestMethod.POST, path = "/wordcount")
	public Map<String, Long> count(@RequestParam(required = false) String words) {
		List<String> wordList = Arrays.asList(words.split(" "));
		Map<String, Long> wordMap = service.getCount(wordList);
		return getSortedMap(wordMap);
	}

	private LinkedHashMap<String, Long> getSortedMap(Map<String,Long> wordMap) {
		return wordMap.entrySet()
				.stream()
				.sorted(reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

}
