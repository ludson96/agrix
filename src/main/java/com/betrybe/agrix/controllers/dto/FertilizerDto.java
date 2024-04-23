package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Fertilizer;

/**
 * Classe que converte entidade em dto e vice-versa.
 *
 * @param id do fertilizer.
 * @param name nome do fertilizer.
 * @param brand marca do fertilizer.
 * @param composition composição do fertilizer.
 */
public record FertilizerDto(Long id, String name, String brand, String composition) {

  public Fertilizer dtoToEntity() {
    return new Fertilizer(name, brand, composition);
  }

  /**
   * Método estático para converter entidade em dto adicionando o id.
   *
   * @param fertilizer Entidade fertilizante a ser convertida.
   * @return retorna um fertilizante com o id adicionado.
   */
  public static FertilizerDto fromEntityToDto(Fertilizer fertilizer) {
    return new FertilizerDto(
        fertilizer.getId(),
        fertilizer.getName(),
        fertilizer.getBrand(),
        fertilizer.getComposition()
    );
  }
}
