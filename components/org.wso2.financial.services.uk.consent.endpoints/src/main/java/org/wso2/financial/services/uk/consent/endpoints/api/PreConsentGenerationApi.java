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

package org.wso2.financial.services.uk.consent.endpoints.api;

import io.swagger.annotations.*;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.wso2.financial.services.uk.consent.endpoints.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/pre-consent-generation")
@Api(description = "the pre-consent-generation API")
public class PreConsentGenerationApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "handle pre-consent generation validations", notes = "", response = InlineResponse200.class, authorizations = {
        @Authorization(value = "OAuth2", scopes = {
             }),
        
        @Authorization(value = "BasicAuth")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ok", response = InlineResponse200.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    public Response preConsentGenerationPost(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {

        // Read the request body
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Convert the request body to JSONObject
        JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(requestBody.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON format").build();
        }

        String requestPath = request.getRequestURI();
        JSONObject validationResponse = null;
        if(requestPath != null){
            if (requestPath.contains(CommonConstants.ACCOUNT_CONSENT_PATH)){
                validationResponse = AccountsConsentValidationUtil.validateAccountsConsentRequest(jsonObject);
            }
            else if (requestPath.contains(CommonConstants.DOMESTIC_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.DOMESTIC_SCHEDULED_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.INTERNATIONAL_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.INTERNATIONAL_SCHEDULED_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)){

                validationResponse = PaymentsConsentValidationUtil.validatePaymentConsentRequest(requestPath, jsonObject);
            }
        }

        // Check if validationResponse contains an error message
        if (validationResponse != null && validationResponse.containsKey("error")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationResponse).build();
        }

        return Response.ok().entity(validationResponse).build();
    }
}
