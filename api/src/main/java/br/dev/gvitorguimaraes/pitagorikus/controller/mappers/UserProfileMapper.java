package br.dev.gvitorguimaraes.pitagorikus.controller.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.UserProfileDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.UserProfile;
import br.dev.gvitorguimaraes.pitagorikus.model.enums.InterestsProfileEnum;

public class UserProfileMapper {
	
    public static UserProfileDTO toDTO(UserProfile entity) {
        if (entity == null) return null;

        UserProfileDTO dto = new UserProfileDTO(
        		entity.getUser().getName(),
        		entity.getUser().getUsername(),
        		entity.getProfileImageUrl(),
        		entity.getDateOfBirth(),
        		entity.getAbout(),
        		entity.getInterestsList().stream().map(InterestsProfileEnum::getCode).collect(Collectors.toList())
        );
        
        return dto;
    }
    
    public static UserProfile updateEntity(UserProfile entity, UserProfileDTO dto) {
    	if (entity == null || dto == null) return null;
    	
    	if(dto.name() != null && !dto.name().isBlank()) {
    		entity.getUser().setName(dto.name());
    	}
    	if(dto.dateOfBirth() != null) {
    		entity.setDateOfBirth(dto.dateOfBirth());
    	}
    	if(dto.about() != null && !dto.about().isBlank()) {
    		entity.setAbout(dto.about());
    	}
    	if(dto.interests() != null && !dto.interests().isEmpty()) {
    		List<InterestsProfileEnum> interestsList = new ArrayList<>();
    		for(String interestCode : dto.interests()) {
    			InterestsProfileEnum interest = InterestsProfileEnum.fromCode(interestCode);
    			if (interest != null) {
    				interestsList.add(interest);
    			}
    		}
    		entity.setInterestsList(interestsList);
    	}
    	
    	return entity;
    }
}
