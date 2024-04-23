package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.CropDtoToEntity;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
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
@RestController
@RequestMapping("/farms")
public class FarmController {
  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  @PostMapping
  public ResponseEntity<Farm> insertFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.insertFarm(farmDto.dtoToEntity());
    return ResponseEntity.status(HttpStatus.CREATED).body(newFarm);
  }

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
  @GetMapping("/{id}")
  public ResponseEntity<Farm> getFarmById(@PathVariable(name = "id") Long id) throws CustomError {
    Farm optionalFarm = farmService.findFarmById(id);
    return ResponseEntity
        .ok()
        .body(optionalFarm);
  }

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
