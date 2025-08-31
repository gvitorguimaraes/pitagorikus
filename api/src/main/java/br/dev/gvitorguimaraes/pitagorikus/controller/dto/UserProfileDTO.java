package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record UserProfileDTO(String name, 
							 String username, 
							 String profileImageUrl, 
							 LocalDate dateOfBirth, 
							 String about, 
							 List<String> interests
							 ) {

}
