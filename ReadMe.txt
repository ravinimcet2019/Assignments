huOOPS Assignment
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

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- liquibase formatted sql
-- changeset Ravi:1
CREATE TABLE MERCHANT_USER (
    ID RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    MID VARCHAR2(255) NOT NULL,
    PARENT_USERID VARCHAR2(255) NOT NULL,
    USER_ID VARCHAR2(255) UNIQUE NOT NULL,
    FIRST_NAME VARCHAR2(255) NOT NULL,
    MIDDLE_NAME VARCHAR2(255),
    LAST_NAME VARCHAR2(255) NOT NULL,
    EMAIL VARCHAR2(255) UNIQUE,
    PRIMARY_PHONE VARCHAR2(255) UNIQUE,
    SECONDARY_PHONE VARCHAR2(255),
    MOBILE_PHONE VARCHAR2(255) UNIQUE NOT NULL,
    OFFICE_PHONE VARCHAR2(255) NOT NULL,
    COUNTRY_CODE VARCHAR2(255) NOT NULL,
    STATE_CODE VARCHAR2(255) NOT NULL,
    PIN_CODE VARCHAR2(255) NOT NULL,
    CITY VARCHAR2(255) NOT NULL,
    ROLE VARCHAR2(255) NOT NULL,
    STATUS VARCHAR2(255) NOT NULL,
    PASSWORD VARCHAR2(255) NOT NULL,
    LAST_PASSWORD_CHANGE NUMBER NOT NULL,
    PASSWORD_EXPIRY_TIME NUMBER NOT NULL,
    LOGIN_FAIL_ATTEMPT NUMBER NOT NULL,
    LAST_SUCCESS_LOGIN NUMBER NOT NULL,
    LAST_FAIL_LOGIN NUMBER NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL
);



package com.epay.merchant.entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;


/**
 * Class Name: Merchant
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

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "MERCHANT_USER")
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;


    private String mId;
    private String parentUserId;
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String primaryPhone;
    private String secondaryPhone;
    private String mobilePhone;
    private String officePhone;
    private String countryCode;
    private String stateCode;
    private String pinCode;
    private String city;
    private String role;
    private String status;
    private String password;
    private long lastPasswordChange;
    private long PasswordExpiryTime;
    private long loginFailAttempt;
    private long lastSuccessLogin;
    private long lastFailLogin;
    private Date createdDate;

}


Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'merchantService' defined in file [F:\SBI\microservices\epay_merchant_service\build\classes\java\main\com\epay\merchant\service\MerchantService.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'merchantDAO' defined in file [F:\SBI\microservices\epay_merchant_service\build\classes\java\main\com\epay\merchant\dao\MerchantDAO.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'merchantRepository' defined in com.epay.merchant.repository.MerchantRepository defined in @EnableJpaRepositories declared on EpayMerchantServiceApplication: Not a managed type: class com.epay.merchant.entity.Merchant

-----------------------------------------------------------------------------------------------------------------------

package com.epay.merchant.dao;

import com.epay.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Class Name: MerchantDAO
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

@Repository
@RequiredArgsConstructor
public class MerchantDAO {

    private final MerchantRepository merchantRepository;

    public boolean isExitsByUserIdOrEmailOrMobilePhone(String userId) {
        return merchantRepository.isExitsByUserIdOrEmailOrMobilePhone(userId);
    }
}

-----------------------------------------------------------------------------------------------------------------------------
package com.epay.merchant.repository;
import com.epay.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Class Name: MerchantRepository
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

public MerchantResponse<OtpValidationResponse> otpValidation(OtpValidationRequest otpValidationRequest) {
        otpValidator.validateOtpValidationRequest(otpValidationRequest);
        OtpDetailsDto otpDetails = otpManagementDao.getOtpDetailsByRequestId(otpValidationRequest.getRequestId())
                .orElseThrow(() -> new MerchantException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Request Id")));
        isOtpVerified(otpValidationRequest, otpDetails);
        Optional<TokenManagement> tokenManagement = tokenManagementDao.getTokenByUserIdAndIsValidTrue(otpDetails.getUserId());
        OtpValidationResponse otpValidationResponse = getTokenDetails(tokenManagement, otpDetails);
        return MerchantResponse.<OtpValidationResponse>builder()
                .status(MerchantConstant.RESPONSE_SUCCESS)
                .data(List.of(otpValidationResponse))
                .build();

    }

    private void isOtpVerified(OtpValidationRequest otpValidationRequest, OtpDetailsDto otpDetails) {
        otpManagementDao.updateIsVerifiedByRequestId(otpValidationRequest.getRequestId());
        if (!(otpValidationRequest.getOtp().equals(otpDetails.getOtpCode()))) {
            throw new MerchantException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "OTP"));
        }
        if(!(DateTimeUtils.isPastDate(otpDetails.getExpiryTime()))) {
            throw new MerchantException(ErrorConstants.EXPIRY_TIME_ERROR_CODE, MessageFormat.format(ErrorConstants.EXPIRY_TIME_ERROR_MESSAGE, "OTP"));
        }
    }

    private OtpValidationResponse getTokenDetails(Optional<TokenManagement> tokenManagement, OtpDetailsDto otpDetailsDto) {

        TokenManagement tokenManagementBuilder = TokenManagement.builder()
                .isGenerated(TokenStatus.IN_PROGRESS.name)
                .userId(otpDetailsDto.getUserId())
                .isValid(false)
                .build();
        TokenManagement tokenResponse = tokenManagementDao.saveToken(tokenManagementBuilder);
        String token = "";
        try {
            token = authenticationService.generateUserToken(createTokenRequestInstance(otpDetailsDto));
        } catch (Exception e) {
            tokenManagementBuilder.setIsGenerated(TokenStatus.NOT_GENERATED.name);
            tokenManagementDao.saveToken(tokenManagementBuilder);
            throw new MerchantException("501","Exception while generating token");
        }
        tokenManagementBuilder.setIsGenerated(TokenStatus.GENERATED.name);
        tokenManagementBuilder.setValid(true);
        if (tokenManagement.isPresent()) {
            // TODO set expiry time in db
            tokenManagementDao.saveAndUpdateTokenDetails(tokenManagementBuilder);
            return OtpValidationResponse.builder()
                    .requestId(otpDetailsDto.getRequestId())
                    .token(token)
                    .message(MessageFormat.format(MerchantConstant.SUCCESS_MESSAGE, "OTP validated"))
                    .build();
        }
        tokenManagementDao.saveToken(tokenManagementBuilder);
        return OtpValidationResponse.builder()
                .requestId(otpDetailsDto.getRequestId())
                .token(token)
                .message(MessageFormat.format(MerchantConstant.SUCCESS_MESSAGE, "OTP validated"))
                .build();
    }
    
    private UserTokenRequest createTokenRequestInstance(OtpDetailsDto otpDetailsDto) {
        UserTokenRequest tokenRequest = new UserTokenRequest();
        tokenRequest.setTokenType(TokenType.USER);
        tokenRequest.setUsername(otpDetailsDto.getUserName());
        tokenRequest.setPassword(otpDetailsDto.getPassword());
//        tokenRequest.setMid(merchantUser.getM);
        tokenRequest.setRoles(List.of(MerchantUserRoles.ADMIN.name()));
        tokenRequest.setExpirationTime(DateTimeUtils.addMinutes(merchantConfig.getTokenExpiryTime()).intValue());
        return tokenRequest;
    }
