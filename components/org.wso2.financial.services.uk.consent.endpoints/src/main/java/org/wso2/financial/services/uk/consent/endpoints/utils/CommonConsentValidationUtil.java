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
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.wso2.financial.services.uk.consent.endpoints.model.RequestBody;
import org.wso2.financial.services.uk.consent.endpoints.model.SuccessResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class CommonConsentValidationUtil {

    private static final Log log = LogFactory.getLog(CommonConsentValidationUtil.class);

    /**
     * Method to construct the consent manage error response.
     *
     * @param errorMessage Error Message
     * @return
     */
    public static ErrorResponse getErrorResponse(String errorMessage) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.actionStatus(ErrorResponse.ActionStatusEnum.ERROR);
        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setErrorDescription(errorMessage);

        return errorResponse;
    }

    /**
     * Method to construct the consent manage success response.
     * @param requestBody
     * @return
     */
    public static SuccessResponse getSuccessResponse(String eventId) {

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setEventId(eventId);
        successResponse.actionStatus(SuccessResponse.ActionStatusEnum.SUCCESS);
        successResponse.setOperations(successResponse.getOperations());

        return successResponse;
    }

    /**
     * Method to construct the consent manage success response.
     * @param isSuccess
     * @param message
     * @return
     */
    public static Object getResponse(String evenId, boolean isSuccess, String message) {
        if (isSuccess) {
            return getSuccessResponse(evenId);
        } else {
            return getErrorResponse(message);
        }
    }

    /**
     * Validate whether the date is a valid ISO 8601 format.
     * @param dateValue
     * @return
     */
    public static boolean isValid8601(String dateValue) {
        try {
            OffsetDateTime.parse(dateValue);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Extract string payload from request object
     *
     * @param request The request object
     * @return String payload
     */
    public static String getStringPayload(HttpServletRequest request) {
        try {
            // Read the input stream and return the string payload
            return IOUtils.toString(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            // Log the error message and return a default message or null
            log.error("Error reading payload", e);
            return null;
        }
    }

    /**
     * Convert an object to a JSON object
     * @param object
     * @return
     * @throws Exception
     */
    public static JSONObject convertObjectToJson(Object object) throws Exception {
        // Convert Object to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(object);

        // Parse JSON string to JSONObject
        return new JSONObject(jsonString);
    }

}
