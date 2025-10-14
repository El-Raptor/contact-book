package com.raptor.agendadecontatos.controller;

import com.raptor.agendadecontatos.model.Contact;
import com.raptor.agendadecontatos.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Get all contacts")
    @GetMapping("/contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found contact",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            )
    })
    public ResponseEntity<List<Contact>> getAllContacts() {
        var contacts = contactService.findAll();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @Operation(summary = "Get a contact by its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found contact by its Id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            )
    })
    @GetMapping("contacts/{id}")
    public ResponseEntity<Contact> getContactById(@Parameter(description = "id of contact to be searched")
                                                  @PathVariable int id) {
        var contact = contactService.findById(id);
        if (contact == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Operation(summary = "Add new contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New contact created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            )
    })
    @PostMapping("contacts")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        contactService.save(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            )
    })
    @PutMapping("contacts")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
        contactService.save(contact);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Operation(summary = "Delete a contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Contact not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)
                    )
            )
    })
    @DeleteMapping("contacts/{id}")
    public ResponseEntity<Contact> deleteContactById(@PathVariable int id) {
        var contact = contactService.findById(id);
        if (contact == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        contactService.deleteById(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

}
