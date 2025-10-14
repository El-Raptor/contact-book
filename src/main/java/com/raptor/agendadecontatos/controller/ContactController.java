package com.raptor.agendadecontatos.controller;

import com.raptor.agendadecontatos.model.Contact;
import com.raptor.agendadecontatos.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Get all contacts")
    @GetMapping("/")
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
    @GetMapping("/{id}")
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
    @PostMapping("/")
    public ResponseEntity<Contact> addContact(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Contact to create",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class),
                    examples = @ExampleObject(value =
                            "{\"name\": \"new contact\", \"email\": \"new.contact@email.com\", \"phone\": \"5511999999999\"}"
                    )
            )
    ) @RequestBody Contact contact) {
        var newContact = contactService.save(contact);
        URI location = URI.create("/api/contacts/" + newContact.getId());
        return ResponseEntity.created(location).body(newContact);
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
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@Parameter(description = "id of contact to be searched")
                                                 @PathVariable int id,
                                                 @Parameter(description = "Contact to be updated")
                                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                         description = "Contact to create",
                                                         required = true,
                                                         content = @Content(
                                                                 mediaType = "application/json",
                                                                 schema = @Schema(implementation = Contact.class),
                                                                 examples = @ExampleObject(value =
                                                                         "{\"name\": \"new contact\", \"email\": \"new.contact@email.com\", \"phone\": \"5511999999999\"}"
                                                                 )
                                                         )
                                                 ) @RequestBody Contact contact) {
        var existingContact = contactService.findById(id);
        if (existingContact == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        contact.setId(id);
        var updatedContact = contactService.save(contact);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @Operation(summary = "Delete a contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contact deleted",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Contact> deleteContactById(@Parameter(description = "id of contact to be deleted")
                                                     @PathVariable int id) {
        var contact = contactService.findById(id);
        if (contact == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        contactService.deleteById(id);
        return new ResponseEntity<>(contact, HttpStatus.NO_CONTENT);
    }

}
