package com.epam.esm.controller;

import com.epam.esm.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // Indicates that this class is a REST controller that handles incoming HTTP requests
@RequiredArgsConstructor // Generates a constructor with a parameter for each field that requires dependency injection
@RequestMapping("/") // Maps HTTP requests with the root URL to this controller
public class HomeController {

    /**
     * This method handles GET requests to the root URL and returns a simple message in a Response object.
     *
     * @return a Response object with a message.
     */
    @GetMapping
    public Response<Object> get() {
        return new Response<>("Hello everyone this is home page.You can get all information in README.md file");
    }
}
