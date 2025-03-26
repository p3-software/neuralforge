package com.cenfotec.p3.neuralforge_api.service;

import com.cenfotec.p3.neuralforge_api.model.entity.DynamicContentEntity;
import com.cenfotec.p3.neuralforge_api.repository.DynamicContentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicContentServiceTest {

    @InjectMocks
    private DynamicContentService dynamicContentService;

    @Mock
    private DynamicContentRepository dynamicContentRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MultipartFile file;

    private DynamicContentEntity mockDynamicContentEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Given: Mock Dynamic Content Entity
        mockDynamicContentEntity = new DynamicContentEntity();
        mockDynamicContentEntity.setTitle("Test Title");
        mockDynamicContentEntity.setPath("src/main/resources/dynamicContent/Test_Title.pdf");
        mockDynamicContentEntity.setEmail("test@example.com");
        mockDynamicContentEntity.setType("Test Type");
        mockDynamicContentEntity.setCreationDate(LocalDateTime.now());
    }

    @Test
    void givenValidFile_whenExtractTextAndGeneratePdf_thenReturnSuccessMessage() throws IOException {
        // Given
        when(file.isEmpty()).thenReturn(false);
        when(file.getInputStream()).thenReturn(new FileInputStream("src/test/resources/sample.pdf"));

        // When
        String result = dynamicContentService.extractTextAndGeneratePdf(file, "Test Title", "test@example.com", "Test Type");

        // Then
        assertEquals("PDF generado y guardado correctamente", result);
        verify(dynamicContentRepository, times(1)).save(any(DynamicContentEntity.class));
    }

    @Test
    void givenEmptyFile_whenExtractTextAndGeneratePdf_thenThrowException() {
        // Given
        when(file.isEmpty()).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dynamicContentService.extractTextAndGeneratePdf(file, "Test Title", "test@example.com", "Test Type"));

        assertEquals("El archivo está vacío.", exception.getMessage());
        verify(dynamicContentRepository, never()).save(any(DynamicContentEntity.class));
    }
}