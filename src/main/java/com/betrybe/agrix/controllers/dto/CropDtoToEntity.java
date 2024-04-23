package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import java.time.LocalDate;

/**
 * Dto que transforma um Dto para uma entidade para ser salva no db.
 *
 * @param name nome de Crop.
 * @param plantedArea Area plantada de Crop.
 * @param farmId FarmId referente a fazenda relacionada.
 */
public record CropDtoToEntity(
    String name,
    Double plantedArea,
    LocalDate plantedDate,
    LocalDate harvestDate,
    Farm farmId
) {
  public Crop dtoToEntity() {
    return new Crop(name, plantedArea, farmId, plantedDate, harvestDate);
  }
}
