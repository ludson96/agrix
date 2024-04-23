package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import java.util.List;

/**
 * Dto para filtragem de dados.
 *
 * @param name Um String como nome da fazenda.
 * @param size Um Double como tamanho da fazenda.
 * @param crop Um List de Crops como plantações.
 */
public record FarmDto(String name, Double size, List<Crop> crop) {

  public Farm dtoToEntity() {
    return new Farm(name, size, crop);
  }
}
