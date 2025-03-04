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

import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Util class for Error Handling.
 */
public class ErrorUtil {

    /**
     * Util Method to construct error in UK format.
     * @param httpCode
     * @param errorCode
     * @param errorMsg
     * @param path
     * @return
     */
    public static JSONObject constructUKError(String httpCode, String message, String errorCode, String errorMsg,
                                              String path) {

        String errorId = UUID.randomUUID().toString();
        JSONObject errorObject = new JSONObject();
        JSONArray errorArray = new JSONArray();

        errorArray.put(constructUKErrorObject(errorCode, errorMsg, path));

        errorObject.put(ErrorConstants.CODE, httpCode);
        errorObject.put(ErrorConstants.ID, errorId);
        errorObject.put(ErrorConstants.MESSAGE, message);
        errorObject.put(ErrorConstants.ERRORS, errorArray);

        return errorObject;
    }

    /**
     * Util Method to construct error object in Errors array in UK format.
     * @param errorCode
     * @param errorMsg
     * @param path
     * @return
     */
    public static JSONObject constructUKErrorObject(String errorCode, String errorMsg, String path) {

        String errorURL = "";
        JSONObject error = new JSONObject();

        error.put(ErrorConstants.ERROR_CODE, errorCode);
        if (StringUtils.isNotEmpty(errorMsg) && errorMsg.length() > 500) {
            if (errorMsg.contains("is too long")) {
                String[] errorMsgParts = errorMsg.split("is too long");
                errorMsg = StringUtils.left(errorMsgParts[0], 200) + "..." + errorMsgParts[1];
            } else {
                errorMsg = StringUtils.left(errorMsg, 497) + "...";
            }
        }
        error.put(ErrorConstants.MESSAGE, errorMsg);
        if (StringUtils.isNotEmpty(path)) {
            error.put(ErrorConstants.PATH, path);
        }
        if (!errorURL.isEmpty()) {
            error.put(ErrorConstants.URL, errorURL);
        }
        return error;
    }
}
