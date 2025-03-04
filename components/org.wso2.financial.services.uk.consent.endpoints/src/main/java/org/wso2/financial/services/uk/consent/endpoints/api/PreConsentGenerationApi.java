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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.json.JSONObject;
import org.wso2.financial.services.uk.consent.endpoints.model.InlineResponse200;
import org.wso2.financial.services.uk.consent.endpoints.model.RequestBody;
import org.wso2.financial.services.uk.consent.endpoints.utils.AccountsConsentValidationUtil;
import org.wso2.financial.services.uk.consent.endpoints.utils.CommonConstants;
import org.wso2.financial.services.uk.consent.endpoints.utils.ErrorResponse;
import org.wso2.financial.services.uk.consent.endpoints.utils.PaymentsConsentValidationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/pre-consent-generation")
@Api(description = "the pre-consent-generation API")
public class PreConsentGenerationApi {

    @POST
    @Path("/{s:.*}")
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
    public Response preConsentGenerationPost(@Context HttpServletRequest request, @Valid @NotNull RequestBody requestBody) throws Exception {

        // Read the request body
        Object consentReceipt = requestBody.getEvent().getRequest().getConsentReceipt();

        String requestPath = request.getRequestURI();
        Object validationResponse = null;
        String eventId = requestBody.getEventId();

        if(requestPath != null){
            //Accounts Consent Validations
            if (requestPath.contains(CommonConstants.ACCOUNT_CONSENT_PATH)){
                validationResponse = AccountsConsentValidationUtil.validateAccountsConsentRequest(consentReceipt, eventId);
            }
            //Payments Consent Validations
            else if (requestPath.contains(CommonConstants.DOMESTIC_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.DOMESTIC_SCHEDULED_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.INTERNATIONAL_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.INTERNATIONAL_SCHEDULED_CONSENT_PATH) ||
                    requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)){

                validationResponse = PaymentsConsentValidationUtil.validatePaymentConsentRequest(requestPath, consentReceipt, eventId);
            }
        }

        // Check if validationResponse contains an error message
        if (validationResponse != null && validationResponse.toString().contains("errors")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationResponse).build();
        }

        return Response.ok().entity(validationResponse).build();
    }
}
