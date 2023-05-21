20230420:
Try to add Third-party authentication.

Finally:
Over this part ---"third-party authentication", 
because even author doesn't write the completed codes which about "third-party authentication" and a lot of thing go wrong.




20230422:
Finally Solve the problem of "a lot of thing go wrong", 
look like "Registering and logining successfully, but when trying to open orderForm.html, it went wrong".

Why this will happen==>Because Spring Security's CSRF blocks all of Post Request.

According to https://stackoverflow.com/questions/51026694/spring-security-blocks-post-requests-despite-securityconfig,
we can 
(1) disable CSRF(), by using "  .and().csrf().disable()"   
(2)using thymeleaf dialect like context relative ==> th:action="@{/login}"  ==>so that thymeleaf can render hidden field and CSRF token matched, Post will not go wrong.




20230422_2:
orderForm.html Post 后报错：
2023-04-22 18:19:33.626 ERROR 19828 --- [nio-8080-exec-9] o.a.c.c.C.[.[.[/].[dispatcherServlet]    :
 Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; 
nested exception is org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist: 
tacos.model.Taco; nested exception is org.hibernate.PersistentObjectException: detached entity passed to persist: tacos.model.Taco] with root cause

解决：
参考__https://stackoverflow.com/questions/13370221/persistentobjectexception-detached-entity-passed-to-persist-thrown-by-jpa-and-h
把TacoOrder.class 中原本的 @OneToMany 改为 @ManyToMany




Finally__20230422_3:
第五章大部分都实践完成了可以正常运行，除了
third-party 中facebook的验证、 
@PreAuthorize 、 @PostAuthorize ，
下次 "Producing" 的时候再亲自配置   @PreAuthorize  和  @PostAuthorize。