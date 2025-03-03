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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class CommonConsentValidationUtil {

    private static final Log log = LogFactory.getLog(CommonConsentValidationUtil.class);

    /**
     * Method to construct the consent manage validation response.
     *
     * @param errorCode    Error Code
     * @param errorMessage Error Message
     * @param errorPath    Error Path
     * @return
     */
    public static JSONObject getValidationResponse(String errorCode, String errorMessage, String errorPath) {
        JSONObject validationResponse = new JSONObject();

        validationResponse.put(CommonConstants.IS_VALID, false);
        validationResponse.put(CommonConstants.HTTP_CODE, ErrorConstants.HTTP_BAD_REQUEST);
        validationResponse.put(CommonConstants.ERRORS, ErrorUtil
                .constructUKError(ErrorConstants.BAD_REQUEST_CODE, ErrorConstants.INVALID_REQ_PAYLOAD,
                        errorCode, errorMessage, errorPath));
        return validationResponse;
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
     * Util method to extract the payload from a HTTP request object. Can be JSONObject or JSONArray
     *
     * @param request The HTTP request object
     * @return Object payload can be either an instance of JSONObject or JSONArray only. Can be a ConsentException if
     * is and error scenario. Error is returned instead of throwing since the error response should be handled by the
     * toolkit is the manage scenario.
     */
    public static Object getPayload(HttpServletRequest request) {
        try {
            Object payload = new JSONParser(JSONParser.MODE_PERMISSIVE).parse(getStringPayload(request));
            if (payload == null) {
                log.debug("Payload is empty. Returning null");
                return null;
            }
            if (!(payload instanceof JSONObject || payload instanceof JSONArray)) {
                //Not throwing error since error should be formatted by manage toolkit
                log.error("Payload is not a JSON. Returning null");
                return null;
            }
            return payload;
        } catch (ParseException e) {
            //Not throwing error since error should be formatted by manage toolkit
            log.error(ErrorConstants.ERROR_PAYLOAD_PARSE + ". Returning null", e);
            return null;
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

}
