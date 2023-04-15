package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchFilterDto;
import com.epam.esm.dto.SortFilterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.response.ExceptionResponse.InvalidRequestException;
import com.epam.esm.response.Response;
import com.epam.esm.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping
    public Response getAll() {
        try {
            return new Response<>(certificateService.findAll());
        } catch (NotFoundException exception) {
            return new Response(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public Response getById(@PathVariable long id) {
        try {
            return new Response<>(HttpStatus.OK,certificateService.findById(id));
        } catch (NotFoundException exception) {
            return new Response(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping(value = "/byTag")
    public Response getByTag(@RequestBody @Valid TagDto tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());

        try {
            return new Response<>(HttpStatus.OK,certificateService.findByTag(tag));
        } catch (NotFoundException exception) {
            return new Response(HttpStatus.NOT_FOUND, exception.getMessage());
        }

    }

    @PostMapping(value = "/search")
    public Response getBySearchFilter(@RequestBody @Valid SearchFilterDto searchFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());
        try {
            return new Response<>(HttpStatus.OK,certificateService.findBySearchFilter(searchFilter));
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping(value = "/sort")
    public Response getBySortFilter(@RequestBody SortFilterDto sortFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());

        try {
            return new Response<>(HttpStatus.OK,certificateService.findBySortFilter(sortFilter));
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping
    public Response create(@RequestBody @Valid CertificateDto certificate, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());
        try {
            certificateService.create(certificate);
            return new Response<>(HttpStatus.OK, "certificate was successfully created!");
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }

    @PatchMapping
    public Response edit(@RequestBody @Valid CertificateDto certificate, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());
        try {
            certificateService.update(certificate);
            return new Response<>(HttpStatus.OK, "certificate was successfully updated!");
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public Response deleteById(@PathVariable long id) {
        try {
            certificateService.delete(id);
            return new Response<>(HttpStatus.NO_CONTENT, "certificate was successfully deleted!");
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (ModificationException exception) {
            return new Response<>(HttpStatus.NOT_MODIFIED, exception.getMessage());
        }
    }

}
