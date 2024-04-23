package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import java.time.LocalDate;

/**
 * DTO usado como response, retirando toda informação do farm e enviando apenas seu id.
 *
 * @param id do crop usado como response.
 * @param name do crop usado como response.
 * @param plantedArea do crop usado como response.
 * @param farmId Long do Farm usado como id.
 */
public record CropDto(
    Long id,
    String name,
    Double plantedArea,
    LocalDate plantedDate,
    LocalDate harvestDate,
    Long farmId
) {

  /**
   * Método estático que transforma uma entidade em um DTO mantendo apenas o if de Farm.
   *
   * @param crop Entidade Crop a ser retirado os dados de Farm.
   * @return retorna um Crop e apenas o id de Farm como referência a farm.
   */
  public static CropDto fromEntityToDto(Crop crop) {
    return new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getPlantedDate(),
        crop.getHarvestDate(),
        crop.getFarm().getId()
    );
  }
}