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

/**
 * Controller class for handling requests related to certificates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    /**
     * Method for handling GET requests to retrieve all certificates.
     *
     * @return a Response object containing the list of all CertificateDtos or a NOT_FOUND status if no certificates are found.
     */
    @GetMapping
    public Response getAll() {
        try {
            return new Response<>(certificateService.findAll());
        } catch (NotFoundException exception) {
            return new Response(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    /**
     * Retrieves certificate by ID
     *
     * @param id ID of the certificate to be retrieved
     * @return Response object containing the certificate or an error message if none found
     */

    @GetMapping(value = "/{id}")
    public Response getById(@PathVariable long id) {
        try {
            return new Response<>(HttpStatus.OK, certificateService.findById(id));
        } catch (NotFoundException exception) {
            return new Response(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    /**
     * Retrieves certificates by tag
     *
     * @param tag           TagDto object containing the tag name to filter by
     * @param bindingResult BindingResult object containing any validation errors
     * @return Response object containing the list of certificates or an error message if none found
     */
    @PostMapping(value = "/byTag")
    public Response getByTag(@RequestBody @Valid TagDto tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());

        try {
            return new Response<>(HttpStatus.OK, certificateService.findByTag(tag));
        } catch (NotFoundException exception) {
            return new Response(HttpStatus.NOT_FOUND, exception.getMessage());
        }

    }

    /**
     * Retrieves certificates by search filter
     *
     * @param searchFilter  SearchFilterDto object containing the search filter parameters
     * @param bindingResult BindingResult object containing any validation errors
     * @return Response object containing the list of certificates or an error message if none found
     */
    @PostMapping(value = "/search")
    public Response getBySearchFilter(@RequestBody @Valid SearchFilterDto searchFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());
        try {
            return new Response<>(HttpStatus.OK, certificateService.findBySearchFilter(searchFilter));
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    /**
     * Retrieve certificates by sort filter.
     *
     * @param sortFilter    The sort and filter options.
     * @param bindingResult The result of validating the input.
     * @return A response containing the retrieved certificates.
     */
    @PostMapping(value = "/sort")
    public Response getBySortFilter(@RequestBody SortFilterDto sortFilter, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage());

        try {
            return new Response<>(HttpStatus.OK, certificateService.findBySortFilter(sortFilter));
        } catch (NotFoundException exception) {
            return new Response<>(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    /**
     * Create a new certificate.
     *
     * @param certificate   The certificate to create.
     * @param bindingResult The result of validating the input.
     * @return A response indicating the success of the operation.
     */
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

    /**
     * Update an existing certificate.
     *
     * @param certificate   The updated certificate.
     * @param bindingResult The result of validating the input.
     * @return A response indicating the success of the operation.
     */
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

    /**
     * Delete a certificate by ID.
     *
     * @param id The ID of the certificate to delete.
     * @return A response indicating the success of the operation.
     */
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
