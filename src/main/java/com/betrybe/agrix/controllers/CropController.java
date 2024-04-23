package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller da entidade Crop.
 */
@RestController
@RequestMapping("/crops")
public class CropController {

  private final CropService cropService;

  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * Retorna todos os Crops do banco de dados.
   *
   * @return status http 200 e um List com todos os Crops.
   */
  @GetMapping
  public ResponseEntity<List<CropDto>> getAllCrops() {
    List<Crop> allCrops = cropService.findAllCrops();

    List<CropDto> allCropsDto = allCrops.stream().map(crop -> new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getPlantedDate(),
        crop.getHarvestDate(),
        crop.getFarm().getId()
    )).collect(Collectors.toList());

    return ResponseEntity.ok().body(allCropsDto);
  }

  /**
   * Retorna um Crop baseado no seu id.
   *
   * @param id do Crop a ser retornado.
   * @return status http 200 e um CropDto enviando apenas seu id.
   * @throws CustomError lança uma exceção caso não exista nenhum Farm referente ao Crop solicitado.
   */
  @GetMapping("{id}")
  public ResponseEntity<CropDto> getCropById(@PathVariable(name = "id") Long id)
      throws CustomError {
    Crop cropById = cropService.getCropById(id);

    return ResponseEntity
        .ok()
        .body(CropDto.fromEntityToDto(cropById));
  }

  /**
   * Método pesquisa pela data de colheira entre duas datas fornecidas.
   *
   * @param start Data inicial da colheita desejada.
   * @param end Data final da colheita desejada.
   * @return retorna uma List de CropDto com todas as colheitas entre as datas fornecidas.
   */
  @GetMapping("/search")
  public ResponseEntity<List<CropDto>> searchCrops(
      @RequestParam LocalDate start,
      @RequestParam LocalDate end
  ) {
    List<Crop> allSearchCrops = cropService.searchCrops(start, end);

    List<CropDto> allSearchCropsDto = allSearchCrops.stream().map(crop -> new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            crop.getPlantedDate(),
            crop.getHarvestDate(),
            crop.getFarm().getId()
        ))
        .toList();

    return ResponseEntity
        .ok()
        .body(allSearchCropsDto);
  }

  /**
   * Método que associa um Crop a um Fertilizer.
   *
   * @param cropId Id do Crop desejado a ser associado.
   * @param fertilizerId Id do Fertilizer desejado a ser associado.
   * @return Retorna uma string de sucesso.
   * @throws CustomError Exceção lançada caso não exista no bd cropId ou fertilizerId informado.
   */
  @PostMapping("{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> associateCropWithFertilizer(
      @PathVariable(name = "cropId") Long cropId,
      @PathVariable(name = "fertilizerId") Long fertilizerId
  ) throws CustomError {
    cropService.associateCropWithFertilizer(cropId, fertilizerId);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("Fertilizante e plantação associados com sucesso!");
  }

  /**
   * Método retorna todos os Fertilizer de um Crop específico.
   *
   * @param cropId Id do crop especifico.
   * @return Retorna uma List de Fertilizer associado ao Crop informado.
   * @throws CustomError Exceção lançada se o id do Crop informado não for encontrado no db.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<List<Fertilizer>> getFertilizersByCrop(
      @PathVariable(name = "cropId") Long cropId
  ) throws CustomError {
    List<Fertilizer> fertilizersByCrop = cropService.getFertilizersByCrop(cropId);

    return ResponseEntity.ok().body(fertilizersByCrop);
  }
}
