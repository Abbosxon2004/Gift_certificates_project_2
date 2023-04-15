package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.response.ExceptionResponse.InvalidRequestException;
import com.epam.esm.response.Response;
import com.epam.esm.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public Response<List<TagDto>> getAll() {
        try {
            return new Response<>(tagService.findAll());
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public Response getById(@PathVariable long id) {
        try {
            return new Response<>(HttpStatus.OK, tagService.findById(id));
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping
    public Response<Object> create(@RequestBody @Valid TagDto tag,
                                   BindingResult bindingResult)
            throws ModificationException {
        if (bindingResult.hasErrors())
            return new Response<>(HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());

        try {
            tagService.create(tag);
            return new Response<>(HttpStatus.OK, "Tag was successfully created!");
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public Response<Object> deleteById(@PathVariable long id)
            throws NotFoundException, ModificationException {
        try {
            tagService.delete(id);
            return new Response<>(HttpStatus.OK, "tag was successfully deleted!");
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }
}
