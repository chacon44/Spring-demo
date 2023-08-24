# Subtasks

Theory questions

* For subtask 1:
  * Why bother with servers (jetty/tomcat/etc), why not just run Spring app through main method as usual Java app?
  * Why bother with listeners? 
  * Why can't we just write own logic and call Spring methods, when we need them, like we do
  with most libraries? Actually: is Spring a normal library or something else?
  * Bonus HARD question: what are Spring lifecycle phases? Which one can we attach our own logic to via listeners? Can we
  use our Spring beans in those listeners?
* For subtask 2:
  * What is Spring Controller?
  * What is difference between @Controller and @RestController in Spring?
  * What is DTO and why would you need them? Do you need any other classes to store data in or can you just use DTOs
  everywhere?
  * Bonus HARD question: which part of Spring is aware of our controllers and decides, which HTTP call goes to which
  controller? In which Spring project does it live in? (Core, Security, MVC, Data, etc)
* For subtask 3:
  * Why split interfaces and implementation into different classes? Which of them we should use when we call those
  classes? (Hint: besides theoretical reason for humans, for Spring the practical reason also exists)
  * What is Spring Service?
  * What would happen if we have two Spring services implementing same interface? Can this situation be fixed and if yes,
  how?
  
  * What is actual class of an object, that Spring puts in our controller, when we autowire the service in? Answer first,
    than check in debugger, placing a breakpoint inside controller and looking what class debugger would show for the
    service. To debug jetty:run you would need to create Run/Debug configuration, that contains it and start it in Debug
    mode: https://www.jetbrains.com/help/idea/run-debug-configuration-maven.html
  * Bonus HARD question: after doing previous question, change code a bit (no need to push changes, just revert them
    after you are done). Add dependency to your pom.xml: 
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>${spring-version}</version>
  </dependency> 
    Add @EnableTransactionManagement on top of ApplicationConfiguration class Add @Transactional on top
    of your @Service class. Now run debugger again as you did in previous question and if you did everything correctly,
    than the class in debugger would be different Why? What did Spring did to the class?
* For subtask 4:
    * What is relationship between @RequestMapping annotation and @PostMapping, @GetMapping, etc? Why does simple
      @RequestMapping even exist?
      Req

        * What is difference between "produces" and "consumes" values in @RequestMapping and others?

        * How does Postman communicates type of request body (i. e. JSON, plain text, binary, etc) to server? Would just
          choosing the proper body type in Postman "Body" tab be enough?
        * How to override default header in Postman?
        * What does @RequestBody actually do? What would happen, if you send body with different structure, than it
          expects? (I. e. response DTO instead of expected request DTO)
        * Bonus HARD question: assume, that, now HTTP 400 response would also need to have response body in a form of
          separate ErrorResponseDTO, like this: json { "errorDescription": "Your question is missing or empty" } Java would
          show
          an error, if you would try to put an ErrorResponseDTO into ResponseEntity<ResponseDTO>. How can this be resolved?