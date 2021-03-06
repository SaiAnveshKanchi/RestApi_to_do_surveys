package survey.springboot.restapi.surveyrestapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import survey.springboot.restapi.surveyrestapi.model.Question;
import survey.springboot.restapi.surveyrestapi.model.Survey;
import survey.springboot.restapi.surveyrestapi.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	private SurveyService surveyService;
	
	@GetMapping("/surveys")
	public List<Survey> retrieveSurveys(){
		return surveyService.retrieveAllSurveys();
	}
	
	@GetMapping("/surveys/{surveyId}")
	public Survey retrieveSurvey(@PathVariable String surveyId){
		return surveyService.retrieveSurvey(surveyId);
	}
	
	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestions(@PathVariable String surveyId){
		return surveyService.retrieveQuestions(surveyId);
	}
	
	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Void>  addQuestion(@PathVariable String surveyId,@RequestBody Question question){
		Question newQuestion =  surveyService.addQuestion(surveyId, question);
		if (newQuestion == null)
			return ResponseEntity.noContent().build();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(newQuestion.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveQuestion(@PathVariable String surveyId, @PathVariable String questionId){
		return surveyService.retrieveQuestion(surveyId, questionId);
	}
	
	
	
	
}
