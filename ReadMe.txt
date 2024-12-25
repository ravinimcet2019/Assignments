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
_____________________________________



public MerchantResponse<OtpValidationResponse> otpValidation(OtpValidationRequest otpValidationRequest) {
    // Validate the incoming request
    otpValidator.validateOtpValidationRequest(otpValidationRequest);

    // Fetch OTP details and throw exception if not found
    OtpDetailsDto otpDetails = otpManagementDao.getOtpDetailsByRequestId(otpValidationRequest.getRequestId())
            .orElseThrow(() -> new MerchantException(
                    ErrorConstants.INVALID_ERROR_CODE,
                    MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Request Id")
            ));

    // Verify the OTP
    verifyOtp(otpValidationRequest, otpDetails);

    // Fetch token details and create response
    Optional<TokenManagement> tokenManagement = tokenManagementDao.getTokenByUserIdAndIsValidTrue(otpDetails.getUserId());
    OtpValidationResponse otpValidationResponse = createTokenAndResponse(tokenManagement, otpDetails);

    // Build and return the response
    return MerchantResponse.<OtpValidationResponse>builder()
            .status(MerchantConstant.RESPONSE_SUCCESS)
            .data(List.of(otpValidationResponse))
            .build();
}

private void verifyOtp(OtpValidationRequest otpValidationRequest, OtpDetailsDto otpDetails) {
    // Mark OTP as verified in the database
    otpManagementDao.updateIsVerifiedByRequestId(otpValidationRequest.getRequestId());

    // Validate OTP value
    if (!otpValidationRequest.getOtp().equals(otpDetails.getOtpCode())) {
        throw new MerchantException(
                ErrorConstants.INVALID_ERROR_CODE,
                MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "OTP")
        );
    }

    // Validate OTP expiry
    if (!DateTimeUtils.isPastDate(otpDetails.getExpiryTime())) {
        throw new MerchantException(
                ErrorConstants.EXPIRY_TIME_ERROR_CODE,
                MessageFormat.format(ErrorConstants.EXPIRY_TIME_ERROR_MESSAGE, "OTP")
        );
    }
}

private OtpValidationResponse createTokenAndResponse(Optional<TokenManagement> existingToken, OtpDetailsDto otpDetails) {
    // Build token management object
    TokenManagement tokenManagement = TokenManagement.builder()
            .isGenerated(TokenStatus.IN_PROGRESS.name)
            .userId(otpDetails.getUserId())
            .isValid(false)
            .build();

    // Save the initial token management state
    tokenManagementDao.saveToken(tokenManagement);

    // Generate user token
    String token = generateUserTokenSafely(tokenManagement, otpDetails);

    // Update token details in the database
    tokenManagement.setIsGenerated(TokenStatus.GENERATED.name);
    tokenManagement.setValid(true);

    if (existingToken.isPresent()) {
        // Update existing token details if a valid token exists
        tokenManagementDao.saveAndUpdateTokenDetails(tokenManagement);
    } else {
        // Save new token details
        tokenManagementDao.saveToken(tokenManagement);
    }

    // Build and return the OTP validation response
    return OtpValidationResponse.builder()
            .requestId(otpDetails.getRequestId())
            .token(token)
            .message(MessageFormat.format(MerchantConstant.SUCCESS_MESSAGE, "OTP validated"))
            .build();
}

private String generateUserTokenSafely(TokenManagement tokenManagement, OtpDetailsDto otpDetails) {
    try {
        // Attempt to generate the token
        return authenticationService.generateUserToken(createTokenRequestInstance(otpDetails));
    } catch (Exception e) {
        // Handle token generation failure
        tokenManagement.setIsGenerated(TokenStatus.NOT_GENERATED.name);
        tokenManagementDao.saveToken(tokenManagement);
        throw new MerchantException("501", "Exception while generating token");
    }
}

private UserTokenRequest createTokenRequestInstance(OtpDetailsDto otpDetails) {
    // Build and return a UserTokenRequest object
    return UserTokenRequest.builder()
            .tokenType(TokenType.USER)
            .username(otpDetails.getUserName())
            .password(otpDetails.getPassword())
            .roles(List.of(MerchantUserRoles.ADMIN.name()))
            .expirationTime(DateTimeUtils.addMinutes(merchantConfig.getTokenExpiryTime()).intValue())
            .build();
}

@Query("SELECT new com.epay.merchant.dto.OtpDetailsDto(o.id, o.requestType, o.userId, " +
       "o.requestId, o.otpCode, o.expiryTime, o.isVerified, u.userName, u.password) " +
       "FROM OtpManagement o " +
       "JOIN MerchantUser u ON o.userId = u.id " +
       "WHERE o.requestId = :requestId")
Optional<OtpDetailsDto> getOtpDetailsByRequestId(@Param("requestId") UUID requestId);


package com.epay.merchant.dto;

import java.util.UUID;

public class OtpDetailsDto {
    private UUID id;
    private String requestType;
    private UUID userId;
    private UUID requestId;
    private String otpCode;
    private Long expiryTime;
    private Boolean isVerified;
    private String userName;
    private String password;

    public OtpDetailsDto(UUID id, String requestType, UUID userId, UUID requestId, 
                         String otpCode, Long expiryTime, Boolean isVerified, 
                         String userName, String password) {
        this.id = id;
        this.requestType = requestType;
        this.userId = userId;
        this.requestId = requestId;
        this.otpCode = otpCode;
        this.expiryTime = expiryTime;
        this.isVerified = isVerified;
        this.userName = userName;
        this.password = password;
    }

    // Getters and setters
}

Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'otpService' defined in file [F:\SBI\microservices\epay_merchant_service\build\classes\java\main\com\epay\merchant\service\OtpService.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'otpValidator' defined in file [F:\SBI\microservices\epay_merchant_service\build\classes\java\main\com\epay\merchant\validator\OtpValidator.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'otpManagementDao' defined in file [F:\SBI\microservices\epay_merchant_service\build\classes\java\main\com\epay\merchant\dao\OtpManagementDao.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'otpManagementRepository' defined in com.epay.merchant.repository.OtpManagementRepository defined in @EnableJpaRepositories declared on EpayMerchantServiceApplication: Could not create query for public abstract java.util.Optional com.epay.merchant.repository.OtpManagementRepository.getOtpDetailsByRequestId(java.util.UUID); Reason: Validation failed for query for method public abstract java.util.Optional com.epay.merchant.repository.OtpManagementRepository.getOtpDetailsByRequestId(java.util.UUID)

@Query("SELECT new com.epay.merchant.dto.OtpDetailsDto(o.id, o.requestType, o.userId, " +
       "o.requestId, o.otpCode, o.expiryTime, o.isVerified, u.userName, u.password) " +
       "FROM OtpManagement o " +
       "JOIN MerchantUser u ON o.userId = u.id " +
       "WHERE o.requestId = :requestId")
Optional<OtpDetailsDto> getOtpDetailsByRequestId(@Param("requestId") UUID requestId);

CREATE TABLE MERCHANT_INFO (
    MERCHANT_ID RAW(16)	DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    MID	VARCHAR2(50) UNIQUE,
    MERCHANT_NAME	VARCHAR2(100) NOT NULL,
    BUSINESS_NAME	VARCHAR2(100) NOT NULL,
    BRAND_NAME	VARCHAR2(100),
    BUSINESS_CATEGORY	VARCHAR2(50),
    CATEGORY_CODE	VARCHAR2(10),
    ADDRESS_LINE1	VARCHAR2(200) NOT NULL,
    ADDRESS_LINE2	VARCHAR2(200),
    STATE	VARCHAR2(50) NOT NULL,
    CITY	VARCHAR2(50) NOT NULL,
    COUNTRY	VARCHAR2(50) NOT NULL,
    PINCODE	VARCHAR2(10) NOT NULL,
    MOBILE_NUMBER	VARCHAR2(15) NOT NULL,
    PHONE_NUMBER	VARCHAR2(15),
    PRIMARY_EMAIL	VARCHAR2(100) NOT NULL,
    SECONDARY_EMAIL	VARCHAR2(100),
    MERCHANT_URL	VARCHAR2(255),
    STATUS	VARCHAR2(20) NOT NULL,
    VALIDITY_START_TIME	NUMBER NOT NULL,
    VALIDITY_END_TIME	NUMBER,
    ONBOARDING_TIME	NUMBER NOT NULL,
    ENCRYPTED_ALGO	VARCHAR2(50) NOT NULL,
    RM_NAME	VARCHAR2(100) NOT NULL,
    BANK_CODE	VARCHAR2(20),
    BRANCH_CODE	VARCHAR2(20),
    GST_NUMBER	VARCHAR2(20),
    IS_CHARGEBACK_ALLOWED	CHAR(1),
    AGGREGATOR_ID	VARCHAR2(50) NOT NULL,
    NOTIFICATION	VARCHAR2(255),
    CREATED_BY	VARCHAR2(50) NOT NULL,
    CREATED_AT	NUMBER NOT NULL,
    UPDATED_BY	VARCHAR2(50) NOT NULL,
    UPDATED_AT	NUMBER NOT NULL
);

CREATE TABLE MERCHANT_USER (
    ID                      RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    PARENT_USERID           VARCHAR2(50),
    USER_NAME               VARCHAR2(200) NOT NULL UNIQUE,
    FIRST_NAME              VARCHAR2(100) NOT NULL,
    MIDDLE_NAME             VARCHAR2(50),
    LAST_NAME               VARCHAR2(100) NOT NULL,
    EMAIL                   VARCHAR2(100) NOT NULL UNIQUE,
    PRIMARY_PHONE           VARCHAR2(20) NOT NULL,
    SECONDARY_PHONE         VARCHAR2(20),
    MOBILE_PHONE            VARCHAR2(20) NOT NULL UNIQUE,
    OFFICE_PHONE            VARCHAR2(20),
    COUNTRY_CODE            VARCHAR2(10),
    STATE_CODE              VARCHAR2(10),
    PIN_CODE                VARCHAR2(10),
    CITY                    VARCHAR2(50),
    ROLE                    VARCHAR2(50) NOT NULL,
    STATUS                  VARCHAR2(50) NOT NULL,
    PASSWORD                VARCHAR2(500) NOT NULL,
    LAST_PASSWORD_CHANGE    LONG,
    PASSWORD_EXPIRY_TIME    NUMBER NOT NULL,
    LOGIN_FAIL_ATTEMPT      NUMBER,
    LAST_SUCCESS_LOGIN      NUMBER,
    LAST_FAIL_LOGIN         NUMBER,
    CREATED_BY              VARCHAR2(50) NOT NULL,
    CREATED_AT              NUMBER NOT NULL,
    UPDATED_BY              VARCHAR2(50) NOT NULL,
    UPDATED_AT              NUMBER NOT NULL
);

CREATE TABLE OTP_MANAGEMENT (
ID              RAW(16) PRIMARY KEY NOT NULL, 	--Unique identifier for each OTP entry.
REQUEST_TYPE	VARCHAR2(255) NOT NULL,	  	--Type of request associated with the OTP, e.g., login, resetPassword.
REQUEST_ID	    RAW(16) NOT NULL UNIQUE,	--A unique identifier for the specific request (e.g., session ID or transaction ID).
USER_ID		    RAW(16) NOT NULL ,			--Reference to the user requesting the OTP.
OTP_CODE	    VARCHAR(200)	,				--Hash Value of generated OTP.
EXPIRY_TIME	    NUMBER NOT NULL,		    --Timestamp indicating when the OTP expires.
IS_VERIFIED	    NUMBER(1) NOT NULL,		    --Flag indicating whether the OTP has been successfully verified.
CREATED_AT	NUMBER NOT NULL,		    --Timestamp of when the OTP was generated.
UPDATED_AT	    NUMBER NOT NULL,            --Timestamp of the last update (e.g., verification status change).
CONSTRAINT FK_OTP_MANAGEMENT_USER FOREIGN KEY (USER_ID) REFERENCES MERCHANT_USER(ID) -- Foreign Key to `merchant_info` table
);

CREATE TABLE TOKEN_MANAGEMENT (
    ID RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    USER_ID RAW(16) NOT NULL,
    TOKEN VARCHAR2(50),
    TOKEN_EXPIRY_TIME NUMBER NOT NULL,
    IS_GENERATED VARCHAR2(2) NOT NULL,
    IS_VALID NUMBER(1) NOT NULL,		    --Flag indicating whether the TOKEN has been successfully generated.
    REMARKS VARCHAR2(50),
    CREATED_BY	VARCHAR2(50)NOT NULL,
    CREATED_AT	NUMBER NOT NULL,
    CONSTRAINT FK_MERCHANT_TOKEN_ENTITY_USER_ID  FOREIGN KEY (USER_ID) REFERENCES MERCHANT_USER(ID) -- Foreign Key to `MERCHANT USER` table
);

CREATE TABLE USER_ROLES (
    ID	RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    ROLE	VARCHAR2(50 BYTE) NOT NULL UNIQUE,
    DESCRIPTION	VARCHAR2(100 BYTE)
);

INSERT INTO MERCHANT_INFO (
    MERCHANT_ID, MID, MERCHANT_NAME, BUSINESS_NAME, BRAND_NAME, BUSINESS_CATEGORY, CATEGORY_CODE, 
    ADDRESS_LINE1, ADDRESS_LINE2, STATE, CITY, COUNTRY, PINCODE, MOBILE_NUMBER, PHONE_NUMBER, 
    PRIMARY_EMAIL, SECONDARY_EMAIL, MERCHANT_URL, STATUS, VALIDITY_START_TIME, VALIDITY_END_TIME, 
    ONBOARDING_TIME, ENCRYPTED_ALGO, RM_NAME, BANK_CODE, BRANCH_CODE, GST_NUMBER, 
    IS_CHARGEBACK_ALLOWED, AGGREGATOR_ID, NOTIFICATION, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT
) VALUES (
    SYS_GUID(), 'MID12345', 'Merchant One', 'Business One', 'Brand One', 'Retail', 'R01',
    '123 Business St', 'Suite 101', 'State A', 'City X', 'Country Y', '123456', '9876543210', '0123456789',
    'merchant1@example.com', 'secondary@example.com', 'http://merchant1.com', 'Active', 1672531200, 1704067200,
    1672531200, 'AES256', 'John Doe', 'BANK123', 'BRANCH123', 'GST1234567890',
    'Y', 'AGG001', 'Welcome Notification', 'Admin', 1672531200, 'Admin', 1672531200
);

INSERT INTO MERCHANT_USER (
    ID, PARENT_USERID, USER_NAME, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, PRIMARY_PHONE, SECONDARY_PHONE, 
    MOBILE_PHONE, OFFICE_PHONE, COUNTRY_CODE, STATE_CODE, PIN_CODE, CITY, ROLE, STATUS, PASSWORD, 
    LAST_PASSWORD_CHANGE, PASSWORD_EXPIRY_TIME, LOGIN_FAIL_ATTEMPT, LAST_SUCCESS_LOGIN, LAST_FAIL_LOGIN, 
    CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT
) VALUES (
    SYS_GUID(), NULL, 'user1', 'John', 'M.', 'Doe', 'user1@example.com', '1234567890', NULL,
    '9876543210', '0123456789', '+91', 'ST123', '123456', 'City X', 'Admin', 'Active', 'hashedPassword123',
    NULL, 1704067200, 0, NULL, NULL, 'Admin', 1672531200, 'Admin', 1672531200
);

INSERT INTO OTP_MANAGEMENT (
    ID, REQUEST_TYPE, REQUEST_ID, USER_ID, OTP_CODE, EXPIRY_TIME, IS_VERIFIED, CREATED_AT, UPDATED_AT
) VALUES (
    SYS_GUID(), 'Login', SYS_GUID(), 'USER_ID_FROM_MERCHANT_USER', '123456', 1704067200, 0, 1672531200, 1672531200
);

INSERT INTO TOKEN_MANAGEMENT (
    ID, USER_ID, TOKEN, TOKEN_EXPIRY_TIME, IS_GENERATED, IS_VALID, REMARKS, CREATED_BY, CREATED_AT
) VALUES (
    SYS_GUID(), 'USER_ID_FROM_MERCHANT_USER', 'sampleToken123', 1704067200, 'Y', 1, 'Token generated successfully', 
    'Admin', 1672531200
);


@Repository
public interface OtpManagementRepository extends JpaRepository<OtpManagement, UUID> {
    @Query("SELECT new com.example.dto.OtpUserDetailsDto( " +
           "o.id, o.requestType, o.requestId, o.otpCode, o.expiryTime, o.isVerified, " +
           "u.userName, u.password, r.role) " +
           "FROM OtpManagement o " +
           "JOIN MerchantUser u ON o.userId = u.id " +
           "JOIN UserRoles r ON u.role = r.id " +
           "WHERE o.requestId = :requestId")
    Optional<OtpUserDetailsDto> getOtpDetailsWithRoleName(@Param("requestId") UUID requestId);
}

CREATE TABLE MERCHANT_ENTITY_USER (
    ID RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    USER_ID RAW(16)  NOT NULL,
    ENTITY_ID VARCHAR2(50),
    MID VARCHAR2(50),
    CREATED_BY	VARCHAR2(50)NOT NULL,
    CREATED_AT	NUMBER NOT NULL,
    UPDATED_BY	VARCHAR2(50) NOT NULL,
    UPDATED_AT	NUMBER NOT NULL,
    CONSTRAINT FK_MERCHANT_ENTITY_USER_ID  FOREIGN KEY (USER_ID) REFERENCES MERCHANT_USER(ID), -- Foreign Key to `MERCHANT USER` table
    CONSTRAINT FK_MERCHANT_ENTITY_USER_MID  FOREIGN KEY (MID) REFERENCES MERCHANT_INFO(MID) -- Foreign Key to `MERCHANT Info` table
);

CREATE TABLE MERCHANT_ENTITY_GROUP (
    ID RAW(16)  DEFAULT SYS_GUID() PRIMARY KEY,
    MID VARCHAR2(50) NOT NULL,
    ENTITY_ID VARCHAR2(50) NOT NULL,
    CREATED_BY	VARCHAR2(50) NOT NULL,
    CREATED_AT	NUMBER NOT NULL,
    UPDATED_BY	VARCHAR2(50) NOT NULL,
    UPDATED_AT	NUMBER NOT NULL,
    CONSTRAINT UK_MERCHANT_ENTITY_GROUP UNIQUE (MID, ENTITY_ID), -- Unique KEY
    CONSTRAINT FK_MERCHANT_ENTITY_GROUP FOREIGN KEY (MID) REFERENCES MERCHANT_INFO(MID) -- Foreign Key to `merchant_info` table
);

@Repository
public interface MerchantEntityRepository extends JpaRepository<MerchantEntityUser, UUID> {

    @Query("SELECT CASE " +
           "WHEN me.entityId IS NULL THEN " +
           "    (SELECT DISTINCT mu.mid FROM MerchantEntityUser mu WHERE mu.userId = :userId) " +
           "ELSE " +
           "    (SELECT DISTINCT meg.mid FROM MerchantEntityGroup meg WHERE meg.entityId = me.entityId) " +
           "END " +
           "FROM MerchantEntityUser me WHERE me.userId = :userId")
    List<String> getMidsBasedOnEntityId(@Param("userId") UUID userId);
}

CREATE TABLE MERCHANT_INFO (
    MERCHANT_ID RAW(16)	DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    MID	VARCHAR2(50) UNIQUE,
    MERCHANT_NAME	VARCHAR2(100) NOT NULL,
    BUSINESS_NAME	VARCHAR2(100) NOT NULL,
    BRAND_NAME	VARCHAR2(100),
    BUSINESS_CATEGORY	VARCHAR2(50),
    CATEGORY_CODE	VARCHAR2(10),
    ADDRESS_LINE1	VARCHAR2(200) NOT NULL,
    ADDRESS_LINE2	VARCHAR2(200),
    STATE	VARCHAR2(50) NOT NULL,
    CITY	VARCHAR2(50) NOT NULL,
    COUNTRY	VARCHAR2(50) NOT NULL,
    PINCODE	VARCHAR2(10) NOT NULL,
    MOBILE_NUMBER	VARCHAR2(15) NOT NULL,
    PHONE_NUMBER	VARCHAR2(15),
    PRIMARY_EMAIL	VARCHAR2(100) NOT NULL,
    SECONDARY_EMAIL	VARCHAR2(100),
    MERCHANT_URL	VARCHAR2(255),
    STATUS	VARCHAR2(20) NOT NULL,
    VALIDITY_START_TIME	NUMBER NOT NULL,
    VALIDITY_END_TIME	NUMBER,
    ONBOARDING_TIME	NUMBER NOT NULL,
    ENCRYPTED_ALGO	VARCHAR2(50) NOT NULL,
    RM_NAME	VARCHAR2(100) NOT NULL,
    BANK_CODE	VARCHAR2(20),
    BRANCH_CODE	VARCHAR2(20),
    GST_NUMBER	VARCHAR2(20),
    IS_CHARGEBACK_ALLOWED	CHAR(1),
    AGGREGATOR_ID	VARCHAR2(50) NOT NULL,
    NOTIFICATION	VARCHAR2(255),
    CREATED_BY	VARCHAR2(50) NOT NULL,
    CREATED_AT	NUMBER NOT NULL,
    UPDATED_BY	VARCHAR2(50) NOT NULL,
    UPDATED_AT	NUMBER NOT NULL
);



@Repository
public interface MerchantEntityRepository extends JpaRepository<MerchantEntityUser, UUID> {

    @Query("SELECT CASE " +
           "WHEN me.entityId IS NULL THEN " +
           "    (SELECT DISTINCT mu.mid " +
           "     FROM MerchantEntityUser mu " +
           "     JOIN MerchantInfo mi ON mu.mid = mi.mid " +
           "     WHERE mu.userId = :userId AND mi.status = 'ACTIVE') " +
           "ELSE " +
           "    (SELECT DISTINCT meg.mid " +
           "     FROM MerchantEntityGroup meg " +
           "     JOIN MerchantInfo mi ON meg.mid = mi.mid " +
           "     WHERE meg.entityId = me.entityId AND mi.status = 'ACTIVE') " +
           "END " +
           "FROM MerchantEntityUser me " +
           "WHERE me.userId = :userId")
    List<String> getMidsBasedOnEntityId(@Param("userId") UUID userId);
}

import com.epay.merchant.dao.MerchantEntityUserDao;
import com.epay.merchant.entity.MerchantUser;
import com.epay.merchant.model.response.MerchantResponse;
import com.epay.merchant.model.response.MidMappingResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantEntityUserService {

    private final MerchantEntityUserDao merchantEntityUserDao;

    public MerchantResponse<MidMappingResponse> getMidMappingList(UUID userId, List<String> mids) {
        MerchantUser merchantUser = merchantEntityUserDao.findMerchantById(userId).get();

        if (ObjectUtils.isEmpty(merchantUser.getParentUserId())) {
            return buildResponse(mids, List.of());
        }
        List<String> allMids = merchantEntityUserDao.getMidsBasedOnUserIdAndEntityId(userId);
        List<String> unassignedMids = getUnassignedMids(mids, allMids);
        return buildResponse(mids, unassignedMids);

    }

    private List<String> getUnassignedMids(List<String> parentMids, List<String> assignedMids) {
        return assignedMids.stream()
                .filter(mid -> !parentMids.contains(mid))
                .collect(Collectors.toList());
    }

    private MerchantResponse<MidMappingResponse> buildResponse(List<String> assignedMids, List<String> unAssignedMids) {
        return unAssignedMids.isEmpty() ?
                (MerchantResponse.<MidMappingResponse>builder()
                        .data(List.of(MidMappingResponse.builder()
                                .assignedMids(assignedMids)
                                .build()))
                        .build()) :
                (MerchantResponse.<MidMappingResponse>builder()
                        .data(List.of(MidMappingResponse.builder()
                                .assignedMids(assignedMids)
                                .unAssignedMids(unAssignedMids)
                                .build()))
                        .build());
    }
}
