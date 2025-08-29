package com.example.demo.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ParentDto;
import com.example.demo.model.Parent;
import com.example.demo.service.ParentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/parents")
public class ParentController {
    @Autowired
    private ParentService parentService;

   /* @PostMapping("/signup")
    public ResponseEntity<String> signup(HttpServletRequest request) {
        System.out.println("Hit path: " + request.getRequestURI());
        return ResponseEntity.ok("OK");
    } */
    
    @PostMapping("/signup")
    public ResponseEntity<ParentDto> signup(@RequestBody ParentDto parentDto) {
        Parent savedParent = parentService.signup(parentDto);

        // Map the saved entity to DTO before sending response
        ParentDto responseDto = new ParentDto();
        responseDto.setName(savedParent.getName());
        responseDto.setEmail(savedParent.getEmail());
        // Do NOT send password back to frontend

        return ResponseEntity.ok(responseDto);
    }

}