package com.demo.model;

//Create AnsweredQuestions (String question, boolean answer)
//Make it return a Hashmap with <question, answer>
//Create hashMap with <id, answeredquestions>
//return key (id) and value (answeredquestions) and get each field of the value
public record AnsweredQuestions (String question, boolean answer){
}
