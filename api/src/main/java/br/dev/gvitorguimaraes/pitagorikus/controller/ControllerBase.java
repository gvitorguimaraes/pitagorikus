package br.dev.gvitorguimaraes.pitagorikus.controller;

import java.util.Collections;
import java.util.List;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ErroResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;

public abstract class ControllerBase {
	
	protected Integer getApiVersion() {
		return 1;
	}
	
	protected <T> ResponseDTO<T> getSuccessResponse(T payload) {
	    return new ResponseDTO<>(getApiVersion(), 
	                             List.of(payload), 
	                             null);
	}

	protected <T> ResponseDTO<T> getSuccessResponse(List<T> payload) {
	    return new ResponseDTO<>(getApiVersion(), 
	                             payload, 
	                             null);
	}

	protected <T> ResponseDTO<T> getErrorResponse(int statusCode, String message) {
	    return new ResponseDTO<>(getApiVersion(), 
	                             Collections.emptyList(), 
	                             new ErroResponseDTO(statusCode, message));
	}

}
