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
            Optional<Agent> optionalAgent = agentService.getAgentById(propertyRequest.getAgentId());
            if (optionalAgent.isPresent()) {
                Property property = new Property();
                property.setAgentName(propertyRequest.getAgentName());
                property.setBhk(propertyRequest.getBhk());
                property.setContactName(propertyRequest.getContactName());
                property.setLocation(propertyRequest.getLocation());
                property.setPrice(propertyRequest.getPrice());
                property.setType(propertyRequest.getType());
                property.setAgent(optionalAgent.get());
                Property newProperty = propertyService.addProperty(property);
                return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Agent not found
            }
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
        public ResponseEntity<List<Property>> getPropertiesByLocation(@PathVariable String location) {
            try {
                List<Property> properties = propertyService.getPropertiesByLocation(location);
                return new ResponseEntity<>(properties, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        try {
            Property updatedProperty = propertyService.updateProperty(id, property);
            if (updatedProperty != null) {
                return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
    try {
        boolean deleted = propertyService.deleteProperty(id);
        if (deleted) {
            return new ResponseEntity<>("Property with ID " + id + " is deleted successfully.", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Property with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("An error occurred while deleting the property.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
