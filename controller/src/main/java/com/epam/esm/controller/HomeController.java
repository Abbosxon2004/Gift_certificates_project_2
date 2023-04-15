package com.epam.esm.controller;

import com.epam.esm.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public Response<Object> get() {
        return new Response<>("Hello everyone this is home page.You can get all information in README.md file");
    }
}
