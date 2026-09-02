package com.betrybe.agrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import com.betrybe.agrix.services.FarmService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para FarmService")
public class FarmServiceTest {

  @Mock
  private FarmRepository farmRepository;

  @Mock
  private CropRepository cropRepository;

  @InjectMocks
  private FarmService farmService;

  private Farm farm;

  @BeforeEach
  void setUp() {
    farm = new Farm();
    farm.setId(1L);
    farm.setName("Fazenda Sol Nascente");
    farm.setSize(150.5);
    farm.setCrops(new ArrayList<>());
  }

  @Test
  @DisplayName("Deveria inserir uma fazenda com sucesso")
  void insertFarmSuccess() {
    when(farmRepository.save(any(Farm.class))).thenReturn(farm);

    Farm createdFarm = farmService.insertFarm(farm);

    assertNotNull(createdFarm);
    assertEquals("Fazenda Sol Nascente", createdFarm.getName());
    assertEquals(150.5, createdFarm.getSize());
    verify(farmRepository).save(farm);
  }

  @Test
  @DisplayName("Deveria encontrar uma fazenda por ID existente")
  void findFarmByIdSuccess() throws CustomError {
    when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));

    Farm foundFarm = farmService.findFarmById(1L);

    assertNotNull(foundFarm);
    assertEquals(1L, foundFarm.getId());
    assertEquals("Fazenda Sol Nascente", foundFarm.getName());
  }

  @Test
  @DisplayName("Deveria lançar CustomError ao buscar fazenda por ID inexistente")
  void findFarmByIdNotFound() {
    when(farmRepository.findById(99L)).thenReturn(Optional.empty());

    CustomError error = assertThrows(CustomError.class, () -> farmService.findFarmById(99L));
    assertEquals("Fazenda não encontrada!", error.getMessage());
  }

  @Test
  @DisplayName("Deveria retornar todas as fazendas")
  void findAllFarmsSuccess() {
    when(farmRepository.findAll()).thenReturn(List.of(farm));

    List<Farm> farms = farmService.findAllFarm();

    assertNotNull(farms);
    assertEquals(1, farms.size());
  }

  @Test
  @DisplayName("Deveria inserir plantação vinculada a uma fazenda com sucesso")
  void insertCropToFarmSuccess() throws CustomError {
    Crop crop = new Crop();
    crop.setName("Milho");
    crop.setPlantedArea(45.0);
    crop.setPlantedDate(LocalDate.now());
    crop.setHarvestDate(LocalDate.now().plusMonths(4));

    when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
    when(cropRepository.save(any(Crop.class))).thenReturn(crop);
    when(farmRepository.save(any(Farm.class))).thenReturn(farm);

    Crop createdCrop = farmService.insertCrop(1L, crop);

    assertNotNull(createdCrop);
    assertEquals("Milho", createdCrop.getName());
    assertEquals(farm, createdCrop.getFarm());
  }

  @Test
  @DisplayName("Deveria lançar erro ao inserir plantação em fazenda inexistente")
  void insertCropToInvalidFarmThrows() {
    Crop crop = new Crop();
    crop.setName("Soja");

    when(farmRepository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(CustomError.class, () -> farmService.insertCrop(99L, crop));
  }
}
