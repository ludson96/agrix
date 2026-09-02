package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.FertilizerDto;
import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.FertilizerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller da entidade Fertilizer.
 */
@Tag(name = "Fertilizantes", description = "Endpoints para cadastro e consulta de fertilizantes")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/fertilizers")
public class FertilizerController {

  private final FertilizerService fertilizerService;

  @Autowired
  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Método adiciona um Fertilizante no banco de dados.
   *
   * @param fertilizerDto Corpo da requisição a ser adicionado.
   * @return Retorna status 201 e o novo Fertilizer com id.
   */
  @Operation(
      summary = "Cadastrar fertilizante",
      description = "Cadastra um novo fertilizante (Requer Role ADMIN)"
  )
  @ApiResponse(responseCode = "201", description = "Fertilizante cadastrado com sucesso")
  @PostMapping
  public ResponseEntity<FertilizerDto> insertFertilizer(
      @RequestBody FertilizerDto fertilizerDto
  ) {
    Fertilizer newFertilizer = fertilizerService.insertFertilizer(fertilizerDto.dtoToEntity());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(FertilizerDto.fromEntityToDto(newFertilizer));
  }

  /**
   * Método retorna todos os fertilizantes no banco de dados.
   *
   * @return Retorna status 200 e um List com todos os Fertilizer cadastrados.
   */
  @Operation(
      summary = "Listar todos os fertilizantes",
      description = "Retorna todos os fertilizantes cadastrados (Requer Role ADMIN)"
  )
  @ApiResponse(responseCode = "200", description = "Lista de fertilizantes")
  @GetMapping
  public ResponseEntity<List<FertilizerDto>> getAllFertilizes() {
    List<Fertilizer> allFertilizes = fertilizerService.getAllFertilizes();
    return ResponseEntity.ok(
        allFertilizes.stream()
            .map(crop -> new FertilizerDto(
                crop.getId(),
                crop.getName(),
                crop.getBrand(),
                crop.getComposition()
            )).toList()
    );
  }

  /**
   * Método retorna um Fertilizer específico.
   *
   * @param id Id do Fertilizer desejado.
   * @return Retorna status 200 e Fertilizer com id.
   * @throws CustomError Exceção lançada caso não encontre nenhum Fertilizer informado.
   */
  @Operation(
      summary = "Buscar fertilizante por ID",
      description = "Retorna os detalhes de um fertilizante (Requer Role ADMIN)"
  )
  @ApiResponse(responseCode = "200", description = "Fertilizante encontrado")
  @ApiResponse(responseCode = "404", description = "Fertilizante não encontrado")
  @GetMapping("{id}")
  public ResponseEntity<FertilizerDto> getFertilizerById(@PathVariable(name = "id") Long id)
      throws CustomError {
    Fertilizer fertilizeById = fertilizerService.getFertilizeById(id);

    return ResponseEntity
        .ok()
        .body(FertilizerDto.fromEntityToDto(fertilizeById));
  }
}
