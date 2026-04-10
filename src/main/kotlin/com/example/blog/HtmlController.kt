package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

/* @Controller
* this tells spring that this class should handle web requests
* it registers this class in Spring's Application Context
* enables Spring MVC to scan it for requests @Get Mapping
* */
@Controller
class HtmlController {

    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        return "blog"
    }

}

/*
* spring follows the MVC Pattern
* Model -> data (your model)
* View -> HTML Template (blog.html)?
* Controller -> handles requests
* */