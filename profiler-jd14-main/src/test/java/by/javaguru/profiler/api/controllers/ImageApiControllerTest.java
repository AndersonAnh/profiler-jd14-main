package by.javaguru.profiler.api.controllers;

import by.javaguru.profiler.api.exception.EmptyFileException;
import by.javaguru.profiler.api.exception.ImageNotFoundException;
import by.javaguru.profiler.api.exception.ImageStorageException;
import by.javaguru.profiler.api.exception.WrongMediaTypeException;
import by.javaguru.profiler.usecasses.ImageService;
import by.javaguru.profiler.usecasses.dto.ImageDto;
import by.javaguru.profiler.usecasses.util.AuthService;
import by.javaguru.profiler.usecasses.util.ValidateImage;
import by.javaguru.profiler.util.ImageTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class ImageApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    @MockBean
    private AuthService authService;

    private static final String USERNAME = "username";

    @Test
    void shouldReturn201WhenUploadImageSuccessfully() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = ImageTestData.createImageDto();
            when(imageService.storageImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, ImageTestData.IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenUploadImageSuccessfully() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = ImageTestData.createImageDto();
            when(imageService.storageImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, ImageTestData.IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(objectMapper.writeValueAsString(imageDto)));
        }
    }

    @Test
    void shouldInvokeBusinessLogicWhenUploadImageSuccesfully() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = ImageTestData.createImageDto();
            when(imageService.storageImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, ImageTestData.IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isCreated());

            verify(imageService, times(1)).storageImage(any(InputStream.class), anyString());
            verify(authService, times(1)).getUsername();
        }
    }

    @Test
    void shouldReturn400WhenUploadImageWithNoContent() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createMultipartImageFileWithNoContent();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).thenThrow(EmptyFileException.class);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, ImageTestData.IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(imageService, times(0)).storageImage(any(InputStream.class), anyString());
            verify(authService, times(0)).getUsername();
        }
    }

    @Test
    void shouldReturn415WhenUploadImageWithUnsupportedType() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createMultipartImageFileWithUnsupportedType();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile))
                    .thenThrow(WrongMediaTypeException.class);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, ImageTestData.IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isUnsupportedMediaType());

            verify(imageService, times(0)).storageImage(any(InputStream.class), anyString());
            verify(authService, times(0)).getUsername();
        }
    }

    @Test
    void shouldReturn400WhenUploadImageAndImageStorageExceptionOccurs() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            when(imageService.storageImage(any(InputStream.class), anyString())).thenThrow(ImageStorageException.class);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, ImageTestData.IMAGES_URL_TEMPLATE).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    void shouldReturn200WhenGetImageSuccessfully() throws Exception {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(imageService.getImage(anyString())).thenReturn(ImageTestData.BYTE_SOURCE);

        mockMvc.perform(MockMvcRequestBuilders.get(ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    void shouldReturnExpectedByteArrayWhenGetImageSuccessfully() throws Exception {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(imageService.getImage(anyString())).thenReturn(ImageTestData.BYTE_SOURCE);

        mockMvc.perform(MockMvcRequestBuilders.get(ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(ImageTestData.BYTE_SOURCE));
    }

    @Test
    void shouldInvokeBusinessLogicWhenGetImageSuccessfully() throws Exception {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(imageService.getImage(anyString())).thenReturn(ImageTestData.BYTE_SOURCE);

        mockMvc.perform(MockMvcRequestBuilders.get(ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(authService, times(1)).getUsername();
        verify(imageService, times(1)).getImage(anyString());
    }

    @Test
    void shouldReturn400WhenGetImageWithImageServiceThrowsIOException() throws Exception {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(imageService.getImage(anyString())).thenThrow(IOException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn404WhenGetImageWithNonExistingImage() throws Exception {
        when(authService.getUsername()).thenReturn(USERNAME);
        when(imageService.getImage(anyString())).thenThrow(ImageNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn200WhenReplaceImageSuccessfully() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = ImageTestData.createImageDto();
            when(imageService.replaceImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    void shouldReturnExpectedResponseJsonWhenReplaceImageSuccessfully() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = ImageTestData.createImageDto();
            when(imageService.replaceImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(imageDto)));
        }
    }

    @Test
    void shouldInvokeBusinessLogicWhenReplaceImageSuccesfully() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            ImageDto imageDto = ImageTestData.createImageDto();
            when(imageService.replaceImage(any(InputStream.class), anyString())).thenReturn(imageDto);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isOk());

            verify(imageService, times(1)).replaceImage(any(InputStream.class), anyString());
            verify(authService, times(1)).getUsername();
        }
    }

    @Test
    void shouldReturn400WhenReplaceImageWithNoContentImage() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createMultipartImageFileWithNoContent();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).thenThrow(EmptyFileException.class);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

            verify(imageService, times(0)).replaceImage(any(InputStream.class), anyString());
            verify(authService, times(0)).getUsername();
        }
    }

    @Test
    void shouldReturn415WhenReplaceImageWithUnsupportedTypeImage() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createMultipartImageFileWithUnsupportedType();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile))
                    .thenThrow(WrongMediaTypeException.class);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isUnsupportedMediaType());

            verify(imageService, times(0)).replaceImage(any(InputStream.class), anyString());
            verify(authService, times(0)).getUsername();
        }
    }

    @Test
    void shouldReturn400WhenReplaceImageAndImageStorageExceptionOccurs() throws Exception {
        MockMultipartFile uploadingFile = ImageTestData.createValidMultipartImageFile();

        try (MockedStatic<ValidateImage> validateImageUtil = mockStatic(ValidateImage.class)) {
            validateImageUtil.when(() -> ValidateImage.validate(uploadingFile)).then(invocationOnMock -> null);

            when(authService.getUsername()).thenReturn(USERNAME);

            when(imageService.replaceImage(any(InputStream.class), anyString())).thenThrow(ImageStorageException.class);

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, ImageTestData.SPECIFIED_IMAGE_URL_TEMPLATE, ImageTestData.IMAGE_UUID).file(uploadingFile))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}
