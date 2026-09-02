package com.betrybe.agrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FertilizerRepository;
import com.betrybe.agrix.services.CropService;
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
@DisplayName("Testes unitários para CropService")
public class CropServiceTest {

  @Mock
  private CropRepository cropRepository;

  @Mock
  private FertilizerRepository fertilizerRepository;

  @InjectMocks
  private CropService cropService;

  private Crop crop;
  private Fertilizer fertilizer;

  @BeforeEach
  void setUp() {
    crop = new Crop();
    crop.setId(10L);
    crop.setName("Trigo");
    crop.setPlantedArea(120.0);
    crop.setPlantedDate(LocalDate.of(2025, 1, 15));
    crop.setHarvestDate(LocalDate.of(2025, 6, 20));
    crop.setFertilizers(new ArrayList<>());

    fertilizer = new Fertilizer();
    fertilizer.setId(5L);
    fertilizer.setName("NPK 10-10-10");
    fertilizer.setBrand("AgroTech");
    fertilizer.setComposition("Nitrogênio, Fósforo, Potássio");
    fertilizer.setCrops(new ArrayList<>());
  }

  @Test
  @DisplayName("Deveria retornar lista de plantações")
  void findAllCropsSuccess() {
    when(cropRepository.findAll()).thenReturn(List.of(crop));

    List<Crop> result = cropService.findAllCrops();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Trigo", result.get(0).getName());
  }

  @Test
  @DisplayName("Deveria retornar uma plantação por ID existente")
  void getCropByIdSuccess() throws CustomError {
    when(cropRepository.findById(10L)).thenReturn(Optional.of(crop));

    Crop foundCrop = cropService.getCropById(10L);

    assertNotNull(foundCrop);
    assertEquals(10L, foundCrop.getId());
    assertEquals("Trigo", foundCrop.getName());
  }

  @Test
  @DisplayName("Deveria lançar CustomError quando ID de plantação não for encontrado")
  void getCropByIdNotFound() {
    when(cropRepository.findById(999L)).thenReturn(Optional.empty());

    assertThrows(CustomError.class, () -> cropService.getCropById(999L));
  }

  @Test
  @DisplayName("Deveria buscar plantações por intervalo de datas de colheita")
  void searchCropsByHarvestDate() {
    LocalDate start = LocalDate.of(2025, 1, 1);
    LocalDate end = LocalDate.of(2025, 12, 31);

    when(cropRepository.findAllByharvestDateBetween(start, end)).thenReturn(List.of(crop));

    List<Crop> result = cropService.searchCrops(start, end);

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Deveria associar fertilizante a uma plantação com sucesso")
  void associateCropWithFertilizerSuccess() throws CustomError {
    when(cropRepository.findById(10L)).thenReturn(Optional.of(crop));
    when(fertilizerRepository.findById(5L)).thenReturn(Optional.of(fertilizer));

    cropService.associateCropWithFertilizer(10L, 5L);

    verify(cropRepository).save(crop);
    verify(fertilizerRepository).save(fertilizer);
    assertEquals(1, crop.getFertilizers().size());
  }

  @Test
  @DisplayName("Deveria retornar lista de fertilizantes de uma plantação")
  void getFertilizersByCropSuccess() throws CustomError {
    crop.getFertilizers().add(fertilizer);
    when(cropRepository.findById(10L)).thenReturn(Optional.of(crop));

    List<Fertilizer> fertilizers = cropService.getFertilizersByCrop(10L);

    assertNotNull(fertilizers);
    assertEquals(1, fertilizers.size());
    assertEquals("NPK 10-10-10", fertilizers.get(0).getName());
  }
}
