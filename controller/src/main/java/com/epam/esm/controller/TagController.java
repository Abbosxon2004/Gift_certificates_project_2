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


/**
 * Controller class for handling requests related to tags.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Method for handling GET requests to retrieve all tags.
     *
     * @return a Response object containing the list of all TagDtos or a NOT_FOUND status if no tags are found.
     */
    @GetMapping
    public Response findAll() {
        try {
            return new Response<>(tagService.findAll());
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    /**
     * Method for handling GET requests to retrieve a specific tag by ID.
     *
     * @param id the ID of the tag to retrieve.
     * @return a Response object containing the TagDto with the specified ID or a NOT_FOUND status if the tag is not found.
     */
    @GetMapping(value = "/{id}")
    public Response findById(@PathVariable long id) {
        try {
            return new Response<>(HttpStatus.OK, tagService.findById(id));
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    /**
     * Method for handling POST requests to create a new tag.
     *
     * @param tag           the TagDto object containing the information for the new tag.
     * @param bindingResult the BindingResult object to validate the request body.
     * @return a Response object containing an OK status and a success message if the tag is successfully created or a BAD_REQUEST status and an error message if the request body is invalid.
     * @throws ModificationException if there is an error creating the tag.
     */
    @PostMapping
    public Response<Object> create(@RequestBody @Valid TagDto tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());

        try {
            tagService.create(tag);
            return new Response<>(HttpStatus.OK, "Tag was successfully created!");
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }

    /**
     * Method for handling DELETE requests to delete a specific tag by ID.
     *
     * @param id the ID of the tag to delete.
     * @return a Response object containing an OK status and a success message if the tag is successfully deleted, a NOT_FOUND status if the tag is not found or a NOT_MODIFIED status if there is an error deleting the tag.
     * @throws NotFoundException     if the tag with the specified ID is not found.
     * @throws ModificationException if there is an error deleting the tag.
     */
    @DeleteMapping(value = "/{id}")
    public Response<Object> deleteById(@PathVariable long id) {
        try {
            tagService.delete(id);
            return new Response<>(HttpStatus.NO_CONTENT, "tag was successfully deleted!");
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }
}
