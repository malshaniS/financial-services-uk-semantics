/**
 * Copyright (c) 2025, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.financial.services.uk.consent.endpoints.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

/**
 * Accounts Consent Validation Util class.
 */
public class AccountsConsentValidationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Log log = LogFactory.getLog(AccountsConsentValidationUtil.class);

    /**
     * Check whether the permission combination is accepted.
     * @param permissions
     * @return
     */
    private static boolean isPermissionCombinationAccepted(JSONArray permissions) {

        if (permissions.contains(CommonConstants.READACCOUNTSBASIC) ||
                permissions.contains(CommonConstants.READACCOUNTSDETAIL)) {

            if ((permissions.contains(CommonConstants.READTRANSACTIONSBASIC))
                    && !(permissions.contains(CommonConstants.READTRANSACTIONSCREDITS)
                    || permissions.contains(CommonConstants.READTRANSACTIONSDEBITS))) {
                return false;
            } else if ((permissions.contains(CommonConstants.READTRANSACTIONSDETAIL))
                    && !(permissions.contains(CommonConstants.READTRANSACTIONSCREDITS)
                    || permissions.contains(CommonConstants.READTRANSACTIONSDEBITS))) {
                return false;
            } else if ((permissions.contains(CommonConstants.READTRANSACTIONSCREDITS))
                    && !(permissions.contains(CommonConstants.READTRANSACTIONSBASIC)
                    || permissions.contains(CommonConstants.READTRANSACTIONSDETAIL))) {
                return false;
            } else if ((permissions.contains(CommonConstants.READTRANSACTIONSDEBITS))
                    && !((permissions.contains(CommonConstants.READTRANSACTIONSBASIC))
                    || permissions.contains(CommonConstants.READTRANSACTIONSDETAIL))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate Transaction From Data Time is earlier than Transaction To Date Time.
     *
     * @param fromDateVal Transaction from date time
     * @param toDateVal   Transaction to date time
     * @return
     */
    public static boolean isTransactionFromToTimeValid(String fromDateVal, String toDateVal) {
        if (fromDateVal == null || toDateVal == null) {
            return true;
        }
        try {
            OffsetDateTime fromDate = OffsetDateTime.parse(fromDateVal);
            OffsetDateTime toDate = OffsetDateTime.parse(toDateVal);

            // From date is earlier than To date
            return (fromDate.compareTo(toDate) <= 0);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validate Expiration Date Time.
     *
     * @param expDateVal Expiration Date Time
     * @return
     */
    public static boolean isConsentExpirationTimeValid(String expDateVal) {
        if (expDateVal == null) {
            return true;
        }
        try {
            OffsetDateTime expDate = OffsetDateTime.parse(expDateVal);
            OffsetDateTime currDate = OffsetDateTime.now(expDate.getOffset());

            return expDate.compareTo(currDate) > 0;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validate Accounts Initiation Request Payload.
     * @param request
     * @return
     */
    public static JSONObject validateAccountsConsentRequest(JSONObject request) {

        JSONObject validationResponse = new JSONObject();

        JSONObject data = (JSONObject) request.get(CommonConstants.DATA);
        JSONArray permissions = (JSONArray) data.get(CommonConstants.PERMISSIONS);

        //Check whether the payload contains valid permissions
        if (!isPermissionCombinationAccepted(permissions)) {
            log.error("Permission array contains unacceptable combinations");

            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                    ErrorConstants.MSG_WRONG_PERMISSIONS, ErrorConstants.PATH_PERMISSIONS);
        }

        //Check whether the expiration date is valid
        if (data.containsKey(CommonConstants.EXPIRATION_DATE) &&
                (!(data.get(CommonConstants.EXPIRATION_DATE) instanceof String) ||
                        !CommonConsentValidationUtil.isValid8601(data.getAsString(CommonConstants.EXPIRATION_DATE)))) {
            log.error("Expiration Date Time validation failed");
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                    ErrorConstants.MSG_INVALID_DATE_FORMAT, ErrorConstants.PATH_EXPIRATION_DATE);
        }

        //Check whether the expiration date is expired
        if (data.containsKey(CommonConstants.EXPIRATION_DATE) &&
                !isConsentExpirationTimeValid(data.getAsString(CommonConstants.EXPIRATION_DATE))) {
            log.error("Expiration Date Time is expired");
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                    ErrorConstants.EXPIRED_DATE_ERROR, ErrorConstants.PATH_EXPIRATION_DATE);
        }

        //Check whether the transaction from date is valid
        if (data.containsKey(CommonConstants.TRANSACTION_FROM_DATE) &&
                (!(data.get(CommonConstants.TRANSACTION_FROM_DATE) instanceof String) ||
                        !CommonConsentValidationUtil.isValid8601(data
                                .getAsString(CommonConstants.TRANSACTION_FROM_DATE)))) {
            log.error("Transaction From Date Time validation failed");
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                    ErrorConstants.MSG_INVALID_DATE_FORMAT, ErrorConstants.PATH_TRANSACTION_DATE);
        }

        //Check whether the transaction to date is valid
        if (data.containsKey(CommonConstants.TRANSACTION_TO_DATE) &&
                (!(data.get(CommonConstants.TRANSACTION_TO_DATE) instanceof String) ||
                        !CommonConsentValidationUtil.isValid8601(data
                                .getAsString(CommonConstants.TRANSACTION_TO_DATE)))) {
            log.error("Transaction To Date Time validation failed");
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                    ErrorConstants.MSG_INVALID_DATE_FORMAT, ErrorConstants.PATH_TRANSACTION_TO_DATE);
        }

        //Check whether the transaction from data time is earlier than transaction to date time
        if (!isTransactionFromToTimeValid(data.getAsString(CommonConstants.TRANSACTION_FROM_DATE),
                        data.getAsString(CommonConstants.TRANSACTION_TO_DATE))) {
            log.error("Transaction From and To Date Time validation failed");
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                    ErrorConstants.TRANSACTION_TO_FROM_INVALID_ERROR, ErrorConstants.PATH_DATE);
        }

        validationResponse.put(CommonConstants.IS_VALID, true);
        return validationResponse;

    }
}
