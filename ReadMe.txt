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

public ResponseDto<String> merchantLogin(MerchantLoginRequest merchantLoginRequest) {
        MerchantValidator.loginValidator(merchantLoginRequest);
        Merchant merchantDetails = merchantDAO.getMerchantByUserNameAndPassword(merchantLoginRequest.getUserName(), merchantLoginRequest.getPassword())
                .orElseThrow(() -> new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Merchant")));
        Captcha captchaDetails = captchaDAO.getCaptchaByRequestId(merchantLoginRequest.getRequestId())
                .orElseThrow(() -> new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Captcha")));
        if(!DateUtil.isExpired(captchaDetails.getExpiryTime())) {
            throw new MerchantException(ErrorConstants.EXPIRY_TIME_ERROR_CODE, MessageFormat.format(ErrorConstants.EXPIRY_TIME_ERROR_MESSAGE, "Captcha"));
        }
        checkCaptcha(merchantLoginRequest.getCaptchaText(), captchaDetails.getCaptchaImage());
        return ResponseDto.<String>builder()
                .status(AppConstants.RESPONSE_SUCCESS)
                .data(List.of("Valid user"))
                .build();
    }

    private void checkCaptcha(String loginCaptchaImage, String captchaFromDB) {
        if(loginCaptchaImage.equals(captchaFromDB)) {
            throw new MerchantException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Captcha"));
        }
    }

