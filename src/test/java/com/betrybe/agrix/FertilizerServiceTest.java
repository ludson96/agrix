package com.betrybe.agrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.FertilizerRepository;
import com.betrybe.agrix.services.FertilizerService;
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
@DisplayName("Testes unitários para FertilizerService")
public class FertilizerServiceTest {

  @Mock
  private FertilizerRepository fertilizerRepository;

  @InjectMocks
  private FertilizerService fertilizerService;

  private Fertilizer fertilizer;

  @BeforeEach
  void setUp() {
    fertilizer = new Fertilizer();
    fertilizer.setId(1L);
    fertilizer.setName("Superfosfato Simples");
    fertilizer.setBrand("FertiMax");
    fertilizer.setComposition("Fósforo, Cálcio, Enxofre");
  }

  @Test
  @DisplayName("Deveria inserir fertilizante com sucesso")
  void insertFertilizerSuccess() {
    when(fertilizerRepository.save(any(Fertilizer.class))).thenReturn(fertilizer);

    Fertilizer created = fertilizerService.insertFertilizer(fertilizer);

    assertNotNull(created);
    assertEquals("Superfosfato Simples", created.getName());
    verify(fertilizerRepository).save(fertilizer);
  }

  @Test
  @DisplayName("Deveria retornar todos os fertilizantes")
  void getAllFertilizersSuccess() {
    when(fertilizerRepository.findAll()).thenReturn(List.of(fertilizer));

    List<Fertilizer> result = fertilizerService.getAllFertilizes();

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Deveria buscar fertilizante por ID existente")
  void getFertilizerByIdSuccess() throws CustomError {
    when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));

    Fertilizer found = fertilizerService.getFertilizeById(1L);

    assertNotNull(found);
    assertEquals(1L, found.getId());
    assertEquals("Superfosfato Simples", found.getName());
  }

  @Test
  @DisplayName("Deveria lançar CustomError quando fertilizante não for encontrado")
  void getFertilizerByIdNotFound() {
    when(fertilizerRepository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(CustomError.class, () -> fertilizerService.getFertilizeById(99L));
  }
}
