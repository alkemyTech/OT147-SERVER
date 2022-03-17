package com.alkemy.ong.controller;


import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.TestimonialDtoFull;
import com.alkemy.ong.service.TestimonialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.alkemy.ong.util.DocumentationMessages.*;
import static com.alkemy.ong.util.DocumentationResponse.*;

@Tag(name = "Testimonials", description = "Create, update show and delete Testimonials")
@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    //Controller for save testimonial, role ADMIN
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_CREATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_CREATE,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = CODE_BAD_REQUEST,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_400_DESCRIPTION)})
    @Tag(name = "Testimonials")
    public ResponseEntity<TestimonialDto> saveTestimonial(@RequestBody TestimonialDto testimonialDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialService.saveTestimonial(testimonialDto));
    }

    //Controller for updateTestimonial, role ADMIN
    @Tag(name = "Testimonials")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_OK,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = CODE_FORBIDDEN,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = CODE_BAD_REQUEST,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = CODE_NOT_FOUND,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    public ResponseEntity<TestimonialDto> updateTestimonial(@PathVariable String id, @RequestBody TestimonialDto testimonialDto) throws Exception {
        return ResponseEntity.ok(testimonialService.updateTestimonial(id, testimonialDto));
    }

    //Delete Testimonial for id, role ADMIN
    @DeleteMapping(value = "/{id}")
    @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_NO_CONTENT,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_204_DESCRIPTION),
            @ApiResponse(responseCode = CODE_FORBIDDEN,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = CODE_NOT_FOUND,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    @Tag(name = "Testimonials")
    public ResponseEntity<TestimonialDto> deleteTestimonial(@PathVariable String id){
        this.testimonialService.deleteTestimonial(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Pagination of 10, role USER/ADMIN
    @Tag(name = "Testimonials")
    @GetMapping(params = "page")
    @Operation(summary = DocumentationMessages.TESTIMONIAL_CONTROLLER_SUMMARY_LIST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_OK,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = CODE_BAD_REQUEST,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = CODE_FORBIDDEN,
                    description = DocumentationMessages.TESTIMONIAL_CONTROLLER_RESPONSE_403_DESCRIPTION)})
    public ResponseEntity<?> getPageTestimonial(@RequestParam(defaultValue = "0") int page) {
        PagesDto<TestimonialDtoFull> response = testimonialService.searchPaginatedTestimonial(page);
        return ResponseEntity.ok().body(response);
    }
}
