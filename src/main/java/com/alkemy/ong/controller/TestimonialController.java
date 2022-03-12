package com.alkemy.ong.controller;


import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.TestimonialDtoFull;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    //Controller for save testimonial, role ADMIN
    @PostMapping
    public ResponseEntity<TestimonialDto> saveTestimonial(@RequestBody TestimonialDto testimonialDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialService.saveTestimonial(testimonialDto));
    }
    //Controller for updateTestimonial, role ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> updateTestimonial(@PathVariable String id, @RequestBody TestimonialDto testimonialDto) throws Exception {
        return ResponseEntity.ok(testimonialService.updateTestimonial(id, testimonialDto));
    }
    //Delete Testimonial for id, role ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<TestimonialDto> deleteTestimonial(@PathVariable String id){
        this.testimonialService.deleteTestimonial(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //Pagination of 10, role USER
    @GetMapping
    public ResponseEntity<?> getPageTestimonial(@RequestParam int page) {
        PagesDto<TestimonialDtoFull> response = testimonialService.searchPaginatedTestimonial(page);
        return ResponseEntity.ok().body(response);
    }
}
