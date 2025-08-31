package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import java.util.List;

public record ResponseDTO<T>(Integer apiVersion, List<T> obj, ErroResponseDTO error) {

}
