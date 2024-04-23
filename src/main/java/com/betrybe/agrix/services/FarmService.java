package com.betrybe.agrix.services;

import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service da entidade Farm.
 */
@Service
public class FarmService {
  private final FarmRepository farmRepository;

  private final CropRepository cropRepository;

  @Autowired
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  /**
   * Método que inseri um novo Farm no banco de dados.
   *
   * @param farm dados do novo Farm a ser inserido no banco de dados.
   * @return o novo farm.
   */
  public Farm insertFarm(Farm farm) {
    return farmRepository.save(farm);
  }

  /**
   * Método encontrar um Farm pelo seu id.
   *
   * @param id a ser encontrado.
   * @return  o Farm com o id inserido
   * @throws CustomError retorna uma mensagem de erro caso não exista uma Farm com o id inserido.
   */
  public Farm findFarmById(Long id) throws CustomError {
    Optional<Farm> optionalFarm = farmRepository.findById(id);

    if (optionalFarm.isEmpty()) {
      throw new CustomError(
          "Fazenda não encontrada!",
          HttpStatus.NOT_FOUND.value()
      );
    }
    return optionalFarm.get();
  }

  public List<Farm> findAllFarm() {
    return farmRepository.findAll();
  }

  /**
   * Inseri um novo Crop no banco de dados.
   *
   * @param farmId da fazenda que terá um relacionamento com crops.
   * @param crop dados do novo Crop a ser inserido no Banco de dados.
   * @return o novo Crop.
   * @throws CustomError retorna uma mensagem de erro caso não exista a Farm especificada.
   */
  public Crop insertCrop(Long farmId, Crop crop) throws CustomError {
    Optional<Farm> optionalFarm = farmRepository.findById(farmId);

    if (optionalFarm.isEmpty()) {
      throw new CustomError(
          "Fazenda não encontrada!",
          HttpStatus.NOT_FOUND.value()
      );
    }

    Farm farm = optionalFarm.get();
    Crop newCrop = cropRepository.save(crop);
    newCrop.setFarm(farm);
    farm.getCrops().add(newCrop);
    farmRepository.save(farm);
    return newCrop;
  }

  /**
   * Retorna todas as plantações de uma fazenda em específico.
   *
   * @param farmId Um Long como id da fazenda desejada.
   * @return retorna uma List de Crop como todas as plantações daquela fazenda.
   * @throws CustomError Lança um exception caso a fazenda não exista.
   */
  public List<Crop> findAllCropByFarm(Long farmId) throws CustomError {
    Optional<Farm> optionalFarm = farmRepository.findById(farmId);

    if (optionalFarm.isEmpty()) {
      throw new CustomError(
          "Fazenda não encontrada!",
          HttpStatus.NOT_FOUND.value()
      );
    }

    return optionalFarm.get().getCrops();
  }
}
