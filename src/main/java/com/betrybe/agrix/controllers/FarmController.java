package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.CropDtoToEntity;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
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
 * Controller da entidade Farm representando uma fazenda.
 */
@Tag(name = "Fazendas", description = "Endpoints para gerenciamento de fazendas e suas plantações")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/farms")
public class FarmController {
  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  @Operation(
      summary = "Criar fazenda",
      description = "Cadastra uma nova fazenda (Requer Role USER, MANAGER ou ADMIN)"
  )
  @ApiResponse(responseCode = "201", description = "Fazenda criada com sucesso")
  @PostMapping
  public ResponseEntity<Farm> insertFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.insertFarm(farmDto.dtoToEntity());
    return ResponseEntity.status(HttpStatus.CREATED).body(newFarm);
  }

  @Operation(
      summary = "Listar todas as fazendas",
      description = "Retorna todas as fazendas cadastradas"
  )
  @ApiResponse(responseCode = "200", description = "Lista de fazendas")
  @GetMapping
  public ResponseEntity<List<Farm>> getAllFarms() {
    List<Farm> allFarms = farmService.findAllFarm();
    return ResponseEntity.ok().body(allFarms);
  }

  /**
   * Retorna um Farm baseado no id especificado.
   *
   * @param id a ser retornado.
   * @return status http 200 e o Farm desejado.
   * @throws CustomError lança uma exceção caso o Farm especificado pelo id não exista.
   */
  @Operation(
      summary = "Buscar fazenda por ID",
      description = "Retorna os detalhes de uma fazenda específica"
  )
  @ApiResponse(responseCode = "200", description = "Fazenda encontrada")
  @ApiResponse(responseCode = "404", description = "Fazenda não encontrada")
  @GetMapping("/{id}")
  public ResponseEntity<Farm> getFarmById(@PathVariable(name = "id") Long id) throws CustomError {
    Farm optionalFarm = farmService.findFarmById(id);
    return ResponseEntity
        .ok()
        .body(optionalFarm);
  }

  @Operation(
      summary = "Adicionar plantação à fazenda",
      description = "Cadastra uma nova plantação vinculada a uma fazenda existente"
  )
  @ApiResponse(responseCode = "201", description = "Plantação criada com sucesso")
  @ApiResponse(responseCode = "404", description = "Fazenda não encontrada")
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<CropDto> insertCrop(
      @PathVariable(name = "farmId") Long farmId,
      @RequestBody CropDtoToEntity crop
  ) throws CustomError {
    Crop newCrop = farmService.insertCrop(farmId, crop.dtoToEntity());
    return ResponseEntity.status(HttpStatus.CREATED).body(CropDto.fromEntityToDto(newCrop));
  }

  /**
   * Retorna todos os Crops baseado pelo id do Farm.
   *
   * @param farmId id do farm desejado.
   * @return status http 200 e um List com todos os CropsDTO, retornando apenas o id de Farm.
   * @throws CustomError lança uma exceção caso o Farm especificado pelo id não exista.
   */
  @Operation(
      summary = "Listar plantações de uma fazenda",
      description = "Retorna todas as plantações vinculadas a uma fazenda específica"
  )
  @ApiResponse(responseCode = "200", description = "Lista de plantações da fazenda")
  @ApiResponse(responseCode = "404", description = "Fazenda não encontrada")
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<List<CropDto>> getAllCrops(@PathVariable(name = "farmId") Long farmId)
      throws CustomError {
    List<Crop> allCrops = farmService.findAllCropByFarm(farmId);
    return ResponseEntity.ok(
        allCrops.stream()
            .map(
                crop -> new CropDto(
                    crop.getId(),
                    crop.getName(),
                    crop.getPlantedArea(),
                    crop.getPlantedDate(),
                    crop.getHarvestDate(),
                    crop.getFarm().getId()))
            .collect(Collectors.toList()));
  }
}
