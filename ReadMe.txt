OOPS Assignment
kuch bhi...
ha bhai kuch bhi...

package com.epay.merchant.service.impl;


import com.epay.merchant.dao.MerchantDAO;
import com.epay.merchant.model.request.UserValidationRequest;
import com.epay.merchant.model.response.ErrorDto;
import com.epay.merchant.model.response.ResponseDto;
import com.epay.merchant.util.AppConstants;
import com.epay.merchant.util.ErrorConstants;
import com.epay.merchant.validator.MerchantValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.epay.merchant.service.MerchantService;

import java.text.MessageFormat;
import java.util.List;

/**
 * Class Name: MerchantServiceImpl
 * *
 * Description:
 * *
 * Author: V1017903(bhushan wadekar)
 * <p>
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantDAO merchantDAO;

    @Override
    public ResponseDto<String> validateUser(UserValidationRequest userValidationRequest) {
        MerchantValidator.merchantValidator(userValidationRequest);
        if( merchantDAO.getCountByUserIdOrEmailOrMobilePhone(userValidationRequest.getUserId()) > 0) {
            return ResponseDto.<String>builder()
                    .status(AppConstants.RESPONSE_SUCCESS)
                    .data(List.of(AppConstants.VALID_USER_DETAILS_MESSAGE))
                    .build();
        }
        return ResponseDto.<String>builder()
                .status(AppConstants.RESPONSE_FAILURE)
                .errors(List.of(ErrorDto.builder()
                        .errorCode(ErrorConstants.INVALID_ERROR_CODE)
                                .errorMessage( MessageFormat.format(ErrorConstants.INVALID_ERROR_CODE_MESSAGE, "User"))
                        .build()))
                .build();
    }
}

package com.epay.merchant.validator;

import com.epay.merchant.exception.ValidationException;
import com.epay.merchant.model.request.UserValidationRequest;
import com.epay.merchant.model.response.ErrorDto;
import com.epay.merchant.util.enums.RequestTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.epay.merchant.util.ErrorConstants.*;

/**
 * Class Name: MerchantValidator
 * *
 * Description:
 * *
 * Author: V1017903(bhushan wadekar)
 * <p>
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Component
public class MerchantValidator {

    public static void merchantValidator(UserValidationRequest userValidationRequest) {
        List<ErrorDto> errorMessages = new ArrayList<>();
        if (StringUtils.isEmpty(userValidationRequest.getRequestType())) {
            errorMessages.add(new ErrorDto(MANDATORY_ERROR_CODE, MessageFormat.format(MANDATORY_ERROR_MESSAGE, "Request Type")));
        } else if (StringUtils.isEmpty(userValidationRequest.getUserId())) {
            errorMessages.add(new ErrorDto(MANDATORY_ERROR_CODE, MessageFormat.format(MANDATORY_ERROR_MESSAGE, "UserId")));
        } else if (!RequestTypeEnum.LOGIN.getName().equalsIgnoreCase(userValidationRequest.getRequestType())) {
            errorMessages.add(new ErrorDto(INVALID_ERROR_CODE, MessageFormat.format(INVALID_ERROR_CODE_MESSAGE, "Request Type")));
        }
        if (!errorMessages.isEmpty()) {
            throw new ValidationException(errorMessages);
        }
    }
}
_--_--------+++------------------------


package com.epay.merchant.service.impl;

import com.epay.merchant.dao.MerchantDAO;
import com.epay.merchant.model.request.UserValidationRequest;
import com.epay.merchant.model.response.ErrorDto;
import com.epay.merchant.model.response.ResponseDto;
import com.epay.merchant.util.AppConstants;
import com.epay.merchant.util.ErrorConstants;
import com.epay.merchant.validator.MerchantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MerchantServiceImplTest {

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Mock
    private MerchantDAO merchantDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateUser_ValidUser_ReturnsSuccessResponse() {
        // Arrange
        UserValidationRequest request = new UserValidationRequest();
        request.setUserId("testUser");
        request.setRequestType("LOGIN");

        doNothing().when(() -> MerchantValidator.merchantValidator(request));
        when(merchantDAO.getCountByUserIdOrEmailOrMobilePhone(request.getUserId())).thenReturn(1);

        // Act
        ResponseDto<String> response = merchantService.validateUser(request);

        // Assert
        assertEquals(AppConstants.RESPONSE_SUCCESS, response.getStatus());
        assertEquals(1, response.getData().size());
        assertEquals(AppConstants.VALID_USER_DETAILS_MESSAGE, response.getData().get(0));
        verify(merchantDAO, times(1)).getCountByUserIdOrEmailOrMobilePhone(request.getUserId());
    }

    @Test
    void validateUser_InvalidUser_ReturnsFailureResponse() {
        // Arrange
        UserValidationRequest request = new UserValidationRequest();
        request.setUserId("invalidUser");
        request.setRequestType("LOGIN");

        doNothing().when(() -> MerchantValidator.merchantValidator(request));
        when(merchantDAO.getCountByUserIdOrEmailOrMobilePhone(request.getUserId())).thenReturn(0);

        // Act
        ResponseDto<String> response = merchantService.validateUser(request);

        // Assert
        assertEquals(AppConstants.RESPONSE_FAILURE, response.getStatus());
        assertNull(response.getData());
        assertEquals(1, response.getErrors().size());
        ErrorDto error = response.getErrors().get(0);
        assertEquals(ErrorConstants.INVALID_ERROR_CODE, error.getErrorCode());
        assertEquals(MessageFormat.format(ErrorConstants.INVALID_ERROR_CODE_MESSAGE, "User"), error.getErrorMessage());
        verify(merchantDAO, times(1)).getCountByUserIdOrEmailOrMobilePhone(request.getUserId());
    }

    @Test
    void validateUser_ValidationException_ThrowsException() {
        // Arrange
        UserValidationRequest request = new UserValidationRequest();
        request.setUserId(null); // Missing userId
        request.setRequestType(null); // Missing requestType

        doThrow(new RuntimeException("Validation error"))
                .when(() -> MerchantValidator.merchantValidator(request));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> merchantService.validateUser(request));
        assertEquals("Validation error", exception.getMessage());
        verify(merchantDAO, never()).getCountByUserIdOrEmailOrMobilePhone(anyString());
    }
}
