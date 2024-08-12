package com.max.quizspring.controller;

import com.max.quizspring.auth.PropertyDto;
import com.max.quizspring.model.Agent;
import com.max.quizspring.model.Property;
import com.max.quizspring.service.AgentService;
import com.max.quizspring.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;
    private final AgentService agentService;

    public PropertyController(PropertyService propertyService, AgentService agentService) {
        this.propertyService = propertyService;
        this.agentService = agentService;
    }

    @PostMapping
    public ResponseEntity<Property> addProperty(@RequestBody PropertyDto propertyRequest) {
        try {
            if (propertyRequest.getAgentId() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Agent> optionalAgent = agentService.getAgentById(propertyRequest.getAgentId());
            if (optionalAgent.isPresent()) {
                Property property = new Property();
                property.setBhk(propertyRequest.getBhk());
                property.setContactName(propertyRequest.getContactName());
                property.setLocation(propertyRequest.getLocation());
                property.setPrice(Double.parseDouble(propertyRequest.getPrice())); // Convert to Double
                property.setType(propertyRequest.getType());
                property.setImg(propertyRequest.getImage()); // Use 'img' to store Base64 image
                property.setAgent(optionalAgent.get());
                Property newProperty = propertyService.addProperty(property);
                return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Agent not found
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        try {
            List<Property> properties = propertyService.getAllProperties();
            return new ResponseEntity<>(properties, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{location}")
    public ResponseEntity<List<PropertyDto>> getPropertiesByLocation(@PathVariable String location) {
        try {
            List<Property> properties = propertyService.getPropertiesByLocation(location);
            if (properties.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Convert to PropertyDto and fetch agent details
            List<PropertyDto> propertyDtos = properties.stream().map(property -> {
                PropertyDto dto = new PropertyDto();
                dto.setId(property.getId());
                dto.setBhk(property.getBhk());
                dto.setContactName(property.getContactName());
                dto.setLocation(property.getLocation());
                dto.setPrice(property.getPrice().toString());
                dto.setType(property.getType());
                dto.setAgentId(property.getAgent().getId());
                dto.setImage(property.getImg());
                return dto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
