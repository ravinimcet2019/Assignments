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

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Merchant m WHERE m.userId OR m.email OR m.mobilePhone = :userId")
    boolean isExitsByUserIdOrEmailOrMobilePhone(@Param("userId") String userId);
}

--------------------------------------------------

package com.epay.merchant.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Class Name: Merchant
 * Description: Entity class for MERCHANT_USER table.
 * Author: V1017903 (Bhushan Wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * Version: 1.0
 */

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MERCHANT_USER")
public class Merchant {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", columnDefinition = "RAW(16)")
    private UUID id;

    @Column(name = "MID", nullable = false)
    private String mId;

    @Column(name = "PARENT_USERID", nullable = false)
    private String parentUserId;

    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PRIMARY_PHONE", unique = true)
    private String primaryPhone;

    @Column(name = "SECONDARY_PHONE")
    private String secondaryPhone;

    @Column(name = "MOBILE_PHONE", nullable = false, unique = true)
    private String mobilePhone;

    @Column(name = "OFFICE_PHONE", nullable = false)
    private String officePhone;

    @Column(name = "COUNTRY_CODE", nullable = false)
    private String countryCode;

    @Column(name = "STATE_CODE", nullable = false)
    private String stateCode;

    @Column(name = "PIN_CODE", nullable = false)
    private String pinCode;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "LAST_PASSWORD_CHANGE", nullable = false)
    private long lastPasswordChange;

    @Column(name = "PASSWORD_EXPIRY_TIME", nullable = false)
    private long passwordExpiryTime;

    @Column(name = "LOGIN_FAIL_ATTEMPT", nullable = false)
    private long loginFailAttempt;

    @Column(name = "LAST_SUCCESS_LOGIN", nullable = false)
    private long lastSuccessLogin;

    @Column(name = "LAST_FAIL_LOGIN", nullable = false)
    private long lastFailLogin;

    @Column(name = "CREATED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}


