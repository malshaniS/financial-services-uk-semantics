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
import org.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Payments Consent Validation Util class.
 */
public class PaymentsConsentValidationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Log log = LogFactory.getLog(PaymentsConsentValidationUtil.class);

    /**
     * Check whether the amount is higher that the max instructed amount allowed by the bank.
     *
     * @param instructedAmount Instructed Amount to validate
     */
    public static boolean validateMaxInstructedAmount(String instructedAmount) {

        String maxInstructedAmount = CommonConstants.MAX_INSTRUCTED_AMOUNT;
        return Double.parseDouble(instructedAmount) <= Double.parseDouble(maxInstructedAmount);
    }

    /**
     * Validates whether Cutoffdatetime is enabled, if the request is arriving past the cut off time and if it
     * should be rejected by policy.
     *
     * @return if the request should be rejected, or not.
     */
    public static boolean shouldInitiationRequestBeRejected() {

        if (CommonConstants.CUTOFF_DATE_ENABLED && hasCutOffTimeElapsed() && CommonConstants.REJECT
                .equals(CommonConstants.CUTOFF_DATE_POLICY)) {
            if (log.isDebugEnabled()) {
                log.debug("Request Rejected as CutOffDateTime has elapsed.");
            }
            return true;
        }
        return false;
    }

    /**
     * Validates whether the CutOffTime for the day has elapsed.
     *
     * @return has elapsed
     */
    public static boolean hasCutOffTimeElapsed() {

        OffsetTime dailyCutOffTime = OffsetTime.parse(CommonConstants.DAILY_CUTOFF);
        OffsetTime currentTime = LocalTime.now().atOffset(dailyCutOffTime.getOffset());
        if (log.isDebugEnabled()) {
            log.debug("Request received at" + currentTime + " daily cut off time set to " + dailyCutOffTime);
        }
        return currentTime.isAfter(dailyCutOffTime);
    }

    /**
     * Utility class to check whether the SortCode SchemeName and Identification is valid.
     *
     * @param schemeName     Scheme name
     * @param identification Identification
     * @return
     */
    private static boolean checkSortCodeSchemeNameAndIdentificationValidity(String schemeName, String identification) {

        boolean isValid = true;
        if ((CommonConstants.UK_OBIE_SORT_CODE_ACCOUNT_NUMBER.equals(schemeName)
                || CommonConstants.SORT_CODE_ACCOUNT_NUMBER.equals(schemeName)) &&
                (StringUtils.isNotEmpty(identification) &&
                        !(identification.length() == CommonConstants.ACCOUNT_IDENTIFICATION_LENGTH &&
                                identification.matches(CommonConstants.SORT_CODE_PATTERN)))) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * If ratetype is Agreed, a valid exchange rate and ContractIdentification must be specified.
     *
     * @param exchangeRateInformation Exchange Rate Information Object
     */
    public static boolean validateExchangeRateInformation(JSONObject exchangeRateInformation) {

        String rateType = exchangeRateInformation.getString(CommonConstants.RATE_TYPE);

        Optional<String> contractIdentification = Optional.ofNullable(exchangeRateInformation
                .getString(CommonConstants.CONTRACT_IDENTIFICATION));

        Optional<Double> exchangeRate = Optional.ofNullable((Double) exchangeRateInformation
                .get(CommonConstants.EXCHANGE_RATE));

        if (CommonConstants.ACTUAL_RATE_TYPE.equals(rateType) ||
                CommonConstants.INDICATIVE_RATE_TYPE.equals(rateType)) {
            return !contractIdentification.isPresent() && !exchangeRate.isPresent();
        }
        return !CommonConstants.AGREED_RATE_TYPE.equals(rateType) ||
                exchangeRate.isPresent() && contractIdentification.isPresent();
    }

    /**
     * Validate the instructed amount is not zero.
     * @param instructedAmount
     * @return
     */
    public static boolean validateInstructedAmountIsNotZero(JSONObject instructedAmount) {

        if (Double.parseDouble(instructedAmount.getString(CommonConstants.AMOUNT)) < 1) {
            return false;
        }
        return true;
    }

    /**
     * Validate creditor agent is not available if creditor identification is sort code scheme.
     * @param schemaName
     * @param creditorAgent
     * @return
     */
    public static boolean validateCreditorAgent(String schemaName, JSONObject creditorAgent) {

        String creditor_Agent = creditorAgent.getString(CommonConstants.CREDITOR_AGENT);

        if (CommonConstants.UK_OBIE_SORT_CODE_ACCOUNT_NUMBER.equals(schemaName) &&
                !(creditor_Agent == null)) {

            return false;
        }
        return true;
    }

    /**
     * Check whether the requested execution date is not passed the max number of future days that can be requested.
     *
     * @param futureDate Date to validate
     */
    public static boolean validateMaxFutureDate(String futureDate) {

        try {
            OffsetDateTime expDate = OffsetDateTime.parse(futureDate);
            OffsetDateTime currDate = OffsetDateTime.now(expDate.getOffset());

            String maxFutureDays = CommonConstants.MAX_FUTURE_PAYMENT_DAYS;
            OffsetDateTime maxFutureDate = currDate.plusDays(Integer.parseInt(maxFutureDays));

            return expDate.compareTo(maxFutureDate) <= 0;

        } catch (DateTimeParseException e) {

            log.error("Error while parsing the date for maximum future date validation", e);
            return false;
        }
    }

    /**
     * Check relative ordering of payment dates.
     *
     * @param firstDate First date to validate
     * @param lastDate  Second date to validate
     */
    static boolean validRelativeOrder(String firstDate, String lastDate) {

        try {
            OffsetDateTime earlyDate = OffsetDateTime.parse(firstDate);
            OffsetDateTime laterDate = OffsetDateTime.parse(lastDate);

            return laterDate.compareTo(earlyDate) > 0;
        } catch (DateTimeParseException e) {
            log.error("Error in the Date Time format");
            return false;
        }
    }

    /**
     * Validate whether recurring payment date time and amount is different from first payment date time.
     *
     * @param firstPaymentDateTime     First Payment Date Time
     * @param recurringPaymentDateTime Recurring Payment Date Time
     * @param firstPaymentAmount       First Payment Amount
     * @param recurringPaymentAmount   Recurring Payment Amount
     */
    public static boolean validateRecurringPayment(String firstPaymentDateTime, String recurringPaymentDateTime,
                                                   String firstPaymentAmount, String recurringPaymentAmount) {

        return !firstPaymentDateTime.equals(recurringPaymentDateTime) &&
                (!firstPaymentAmount.equals(recurringPaymentAmount));
    }

    /**
     * Validate creditor account.
     *
     * @param initiation Initiation object
     * @return
     */
    public static Object validateCreditorAccount(JSONObject initiation, String eventId) {

        JSONObject validationResponse = new JSONObject();

        //Validate Creditor Account
        JSONObject creditorAccount = (JSONObject) initiation.get(CommonConstants.CREDITOR_ACC);

        //Validate Sort Code number scheme
        String schemeName = creditorAccount.getString(CommonConstants.SCHEME_NAME);
        String identification = creditorAccount.getString(CommonConstants.IDENTIFICATION);

        if (!checkSortCodeSchemeNameAndIdentificationValidity(schemeName, identification)) {

            log.error(ErrorConstants.INVALID_IDENTIFICATION);

            return CommonConsentValidationUtil.getResponse(eventId, false, ErrorConstants.INVALID_IDENTIFICATION);
        }

        return CommonConsentValidationUtil.getResponse(eventId,true, CommonConstants.IS_VALID);
    }

    /**
     * Validate debtor account.
     *
     * @param initiation Initiation object
     * @return
     */
    public static Object validateDebtorAccount(JSONObject initiation, String eventId) {

        JSONObject validationResponse = new JSONObject();

        //Validate Creditor Account
        JSONObject creditorAccount = (JSONObject) initiation.get(CommonConstants.DEBTOR_ACC);

        //Validate Sort Code number scheme
        String schemeName = creditorAccount.getString(CommonConstants.SCHEME_NAME);
        String identification = creditorAccount.getString(CommonConstants.IDENTIFICATION);

        if (!checkSortCodeSchemeNameAndIdentificationValidity(schemeName, identification)) {

            log.error(ErrorConstants.INVALID_IDENTIFICATION);
            validationResponse.put(CommonConstants.IS_VALID, false);

            return CommonConsentValidationUtil.getResponse(eventId,false, ErrorConstants.INVALID_IDENTIFICATION);
        }

        return CommonConsentValidationUtil.getResponse(eventId, true, CommonConstants.IS_VALID);
    }

    /**
     * Validate payment consent request.
     *
     * @param requestPath Request Path
     * @param request     Request Object
     * @return
     */
    public static Object validatePaymentConsentRequest(String requestPath, Object request, String eventId) {

        JSONObject validationResponse = new JSONObject();

        JSONObject data = (JSONObject) ((JSONObject) request).get(CommonConstants.DATA);
        JSONObject initiation = (JSONObject) data.get(CommonConstants.INITIATION);
        JSONObject instructedAmount = (JSONObject) initiation.get(CommonConstants.INSTRUCTED_AMOUNT);
        JSONObject creditorAgent = (JSONObject) initiation.get(CommonConstants.CREDITOR_AGENT);
        String firstPaymentDateTime = initiation.getString(CommonConstants.FIRST_PAYMENT_DATE);
        String finalPaymentDateTime = initiation.getString(CommonConstants.FINAL_PAYMENT_DATE);
        JSONObject requestExecutionDateTime = (JSONObject) initiation.get(CommonConstants.REQUEST_EXECUTION_DATE);
        String recurringPaymentDateTime = initiation.getString(CommonConstants.RECURRING_PAYMENT_DATE);
        String recurringPaymentAmount = initiation.getString(CommonConstants.RECURRING_AMOUNT);
        String firstPaymentAmount = initiation.getString(CommonConstants.FIRST_PAYMENT_AMOUNT);
        JSONObject exchangeRateInformation = (JSONObject) initiation.get(CommonConstants.EXCHANGE_RATE_INFO);
        JSONObject authorisation = (JSONObject) data.get(CommonConstants.AUTHORISATION);
        String completionDateTime = authorisation.getString(CommonConstants.COMPLETION_DATE);
        JSONObject risk = (JSONObject) data.get(CommonConstants.RISK);
        String paymentContextCode = risk.getString(CommonConstants.CONTEXT_CODE);
        JSONObject firstPaymentObj = (JSONObject) initiation.get(CommonConstants.FIRST_PAYMENT_AMOUNT);
        JSONObject recurringPaymentObj = (JSONObject) initiation.get(CommonConstants.RECURRING_AMOUNT);
        JSONObject finalPaymentObj = (JSONObject) initiation.get(CommonConstants.FINAL_PAYMENT_AMOUNT);

        //Validate InstructedAmount in Initiation section of the payment payload
        if (!requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH) ||
                !requestPath.contains(CommonConstants.FILE_PAYMENT_CONSENT_PATH)) {

            if (!validateInstructedAmountIsNotZero(instructedAmount)) {
                log.error(ErrorConstants.INVALID_INSTRUCTED_AMOUNT);
                return CommonConsentValidationUtil.getResponse(eventId,false, ErrorConstants.INVALID_INSTRUCTED_AMOUNT);

            }
            if (!validateMaxInstructedAmount(instructedAmount.getString(CommonConstants.AMOUNT))) {
                log.error(ErrorConstants.MAX_INSTRUCTED_AMOUNT);
                return CommonConsentValidationUtil.getResponse(eventId,false, ErrorConstants.MAX_INSTRUCTED_AMOUNT);
            }
        }

        //Validate cutoff datetime
        if (shouldInitiationRequestBeRejected()) {

            log.error(ErrorConstants.MSG_ELAPSED_CUT_OFF_DATE_TIME);
            return CommonConsentValidationUtil.getResponse(eventId,false, ErrorConstants.MSG_ELAPSED_CUT_OFF_DATE_TIME);
        }

        //Validate Creditor Account
        if (!requestPath.contains(CommonConstants.FILE_PAYMENT_PATH)) {

            validateCreditorAccount(initiation, eventId);
        }

        //Validate Debtor Account
        if(initiation.get(CommonConstants.DEBTOR_ACC) != null) {
            validateDebtorAccount(initiation, eventId);
        }

        //check whether creditor agent is not available if creditor identification is sort code scheme
        if (requestPath.contains(CommonConstants.INTERNATIONAL_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_SCHEDULED_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)) {

            if (!validateCreditorAgent(CommonConstants.UK_OBIE_SORT_CODE_ACCOUNT_NUMBER, creditorAgent)) {

                log.error(ErrorConstants.CREDITOR_AGENT_UNEXPECTED);
                return CommonConsentValidationUtil.getResponse(eventId,false, ErrorConstants.CREDITOR_AGENT_UNEXPECTED);
            }
        }

        //Check whether execution date time is before the max date
        if (requestPath.contains(CommonConstants.DOMESTIC_SCHEDULED_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_SCHEDULED_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.FILE_PAYMENT_PATH)) {

            if ((initiation.get(CommonConstants.REQUEST_EXECUTION_DATE) instanceof String) &&
                    !validateMaxFutureDate(requestExecutionDateTime.getString(CommonConstants.REQUEST_EXECUTION_DATE))) {

                log.error(ErrorConstants.MAX_EXECUTION_DATE);
                return CommonConsentValidationUtil.getResponse(eventId,false, ErrorConstants.MAX_EXECUTION_DATE);
            }
        }

        //Validate First Payment date and Final Payment Dates
        if (requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)) {

            //Verify FirstPaymentDateTime is valid
            if (!(initiation.get(CommonConstants.FIRST_PAYMENT_DATE) instanceof String) ||
                    !CommonConsentValidationUtil.isValid8601(firstPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_FIRST_PAYMENT_DATE_FORMAT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.INVALID_FIRST_PAYMENT_DATE_FORMAT);
            }

            //Verify FinalPaymentDateTime is valid
            if (!(initiation.get(CommonConstants.FINAL_PAYMENT_DATE) instanceof String) ||
                    !CommonConsentValidationUtil.isValid8601(finalPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_FINAL_PAYMENT_DATE_FORMAT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.INVALID_FINAL_PAYMENT_DATE_FORMAT);
            }

            //Ensure firstPaymentDateTime is before FinalPaymentDateTime
            if (StringUtils.isNotEmpty(firstPaymentDateTime) && StringUtils.isNotEmpty(finalPaymentDateTime) &&
                    !validRelativeOrder(firstPaymentDateTime, finalPaymentDateTime)) {

                log.error(ErrorConstants.FINAL_PAYMENT_BEFORE_FIRSTPAYEMNT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.FINAL_PAYMENT_BEFORE_FIRSTPAYEMNT);
            }

            // if neither no. of payments nor final payment date time is present in the request,
            //      then standing order is open-ended.
            // Otherwise, standing order is not open-ended.
            // If standing order is not open-ended,
            //      both no. of payments and final payment date time should not be specified at the same time.

            String numberOfPayments = (initiation.has(CommonConstants.NUMBER_OF_PAYMENTS)) ?
                    initiation.getString(CommonConstants.NUMBER_OF_PAYMENTS) : null;

            if (StringUtils.isNotEmpty(numberOfPayments) && StringUtils.isNotEmpty(finalPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_STANDING_ORDER);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.INVALID_STANDING_ORDER);
            }
        }

        //Validate RecurringPaymentAmount
        if (requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH)) {

            //Validate RecurringPaymentAmount is valid
            if (!(initiation.get(CommonConstants.RECURRING_PAYMENT_DATE) instanceof String) ||
                    !CommonConsentValidationUtil.isValid8601(recurringPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_RECURRING_PAYMENT_DATE_FORMAT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.INVALID_RECURRING_PAYMENT_DATE_FORMAT);
            }

            //Ensure RecurringPaymentDateTime is before FinalPaymentDateTime
            if (StringUtils.isNotEmpty(recurringPaymentDateTime) && StringUtils.isNotEmpty(finalPaymentDateTime) &&
                    !validRelativeOrder(recurringPaymentDateTime, finalPaymentDateTime)) {

                log.error(ErrorConstants.FINAL_PAYMENT_BEFORE_RECURRINGPAYMENT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.FINAL_PAYMENT_BEFORE_RECURRINGPAYMENT);
            }

            //Ensure firstPaymentDateTime is before RecurringPaymentDateTime
            if (StringUtils.isNotEmpty(firstPaymentDateTime) && StringUtils.isNotEmpty(recurringPaymentDateTime) &&
                    !validRelativeOrder(firstPaymentDateTime, recurringPaymentDateTime)) {

                log.error(ErrorConstants.RECURRINGPAYMENT_BEFORE_FIRSTPAYMENT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.RECURRINGPAYMENT_BEFORE_FIRSTPAYMENT);
            }

            //validate recurring payment
            if (StringUtils.isNotEmpty(recurringPaymentAmount) && StringUtils.isNotEmpty(recurringPaymentDateTime)
                    && StringUtils.isNotEmpty(firstPaymentAmount) && StringUtils.isNotEmpty(firstPaymentDateTime) &&
                    !validateRecurringPayment(firstPaymentDateTime, recurringPaymentDateTime, firstPaymentAmount,
                            recurringPaymentAmount)) {

                log.error(ErrorConstants.INVALID_RECURRING_PAYMENT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.INVALID_RECURRING_PAYMENT);
            }
        }

        //Validate ExchangeRateInformation
        if(requestPath.contains(CommonConstants.INTERNATIONAL_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)){

            if (!validateExchangeRateInformation(exchangeRateInformation)) {

                if (CommonConstants.AGREED_RATE_TYPE
                        .equals(exchangeRateInformation.getString(CommonConstants.RATE_TYPE))) {

                    log.error(ErrorConstants.INVALID_EXCHANGE_RATE_INFO_AGREED_RATE);
                    return CommonConsentValidationUtil.getResponse(eventId,false,
                            ErrorConstants.INVALID_EXCHANGE_RATE_INFO_AGREED_RATE);
                } else {

                    log.error(ErrorConstants.INVALID_EXCHANGE_RATE_INFO);
                    return CommonConsentValidationUtil.getResponse(eventId,false,
                            ErrorConstants.INVALID_EXCHANGE_RATE_INFO);
                }
            }
        }

        //Validate authorisation completion date time
        if (!CommonConsentValidationUtil.isValid8601(completionDateTime)) {

            log.error(ErrorConstants.INVALID_COMPLETION_DATE_FORMAT);
            return CommonConsentValidationUtil.getResponse(eventId,false,
                    ErrorConstants.INVALID_COMPLETION_DATE_FORMAT);
        }

        //Validate Risk Section
        if (StringUtils.isNotEmpty(paymentContextCode) &&
                (CommonConstants.ECOMMERCE_GOODS.equalsIgnoreCase(risk.getString(CommonConstants.CONTEXT_CODE)) ||
                        CommonConstants.ECOMMERCE_SERVICES.equalsIgnoreCase(risk.getString(CommonConstants.CONTEXT_CODE)))) {

            if (StringUtils.isEmpty(risk.getString(CommonConstants.MERCHANT_CATEGORY_CODE)) &&
                    StringUtils.isEmpty(risk.getString(CommonConstants.MERCHANT_IDENTIFICATION))) {

                log.error(ErrorConstants.INVALID_RISK_SECTION);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.INVALID_RISK_SECTION);
            }
            if (CommonConstants.ECOMMERCE_GOODS
                    .equalsIgnoreCase(risk.getString(CommonConstants.CONTEXT_CODE))) {

                if (risk.get(CommonConstants.DELIVERY_ADDRESS) == null) {
                    log.error(ErrorConstants.INVALID_DELIVERY_ADDRESS);
                    return CommonConsentValidationUtil.getResponse(eventId,false,
                            ErrorConstants.INVALID_DELIVERY_ADDRESS);
                }
            }
        }
///---
        if (requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH)) {

            //Validate FirstPaymentAmount
            String firstPayAmount = null;

            if (initiation.has(CommonConstants.FIRST_PAYMENT_AMOUNT)) {
                if ((initiation.get(CommonConstants.FIRST_PAYMENT_AMOUNT) instanceof JSONObject)) {

                    if (firstPaymentObj.has(CommonConstants.AMOUNT)) {

                        firstPayAmount = firstPaymentObj.getString(CommonConstants.AMOUNT);

                        //Validate Max Instructed Amount
                        if (!(firstPaymentObj.get(CommonConstants.AMOUNT) instanceof String) ||
                                !validateMaxInstructedAmount(firstPayAmount)) {

                            log.error(ErrorConstants.MAX_FIRST_INSTRUCTED_AMOUNT_ERROR);
                            return CommonConsentValidationUtil.getResponse(eventId,false,
                                    ErrorConstants.MAX_FIRST_INSTRUCTED_AMOUNT_ERROR);
                        }
                    } else {
                        log.error(ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT);
                        return CommonConsentValidationUtil.getResponse(eventId,false,
                                ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT);
                    }
                } else {
                    log.error(ErrorConstants.INVALID_FIRST_PAYMENT_AMOUNT_OBJECT);
                    return CommonConsentValidationUtil.getResponse(eventId,false,
                            ErrorConstants.INVALID_FIRST_PAYMENT_AMOUNT_OBJECT);
                }
            } else {
                log.error(ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT_OBJECT);
                return CommonConsentValidationUtil.getResponse(eventId,false,
                        ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT_OBJECT);
            }

            //Validate RecurringPaymentAmount
            String recurringPayAmount = null;

            if (initiation.has(CommonConstants.RECURRING_AMOUNT)) {
                if ((initiation.get(CommonConstants.RECURRING_AMOUNT) instanceof JSONObject)) {

                    if (recurringPaymentObj.has(CommonConstants.AMOUNT)) {
                        recurringPayAmount = recurringPaymentObj.getString(CommonConstants.AMOUNT);

                        //Validate Max Instructed Amount
                        if (!(recurringPaymentObj.get(CommonConstants.AMOUNT) instanceof String) ||
                                !validateMaxInstructedAmount(recurringPayAmount)) {

                            log.error(ErrorConstants.MAX_RECURRING_INSTRUCTED_AMOUNT);
                            return CommonConsentValidationUtil.getResponse(eventId,false,
                                    ErrorConstants.MAX_RECURRING_INSTRUCTED_AMOUNT);
                        }
                    } else {
                        log.error(ErrorConstants.MISSING_RECURRING_PAYMENT_AMOUNT);
                        return CommonConsentValidationUtil.getResponse(eventId,false,
                                ErrorConstants.MISSING_RECURRING_PAYMENT_AMOUNT);
                    }
                } else {
                    log.error(ErrorConstants.INVALID_RECURRING_PAYMENT_AMOUNT_OBJECT);
                    return CommonConsentValidationUtil.getResponse(eventId,false,
                            ErrorConstants.INVALID_RECURRING_PAYMENT_AMOUNT_OBJECT);
                }
            }

            //Validate FinalPaymentAmount
            String finalPayAmount = null;
            if (initiation.has(CommonConstants.FINAL_PAYMENT_AMOUNT)) {
                if ((initiation.get(CommonConstants.FINAL_PAYMENT_AMOUNT) instanceof JSONObject)) {

                    if (finalPaymentObj.has(CommonConstants.AMOUNT)) {
                        finalPayAmount = finalPaymentObj.getString(CommonConstants.AMOUNT);

                        //Validate Max Instructed Amount
                        if (!(finalPaymentObj.get(CommonConstants.AMOUNT) instanceof String) ||
                                !validateMaxInstructedAmount(finalPayAmount)) {

                            log.error(ErrorConstants.MAX_FINAL_INSTRUCTED_AMOUNT);
                            return CommonConsentValidationUtil.getResponse(eventId,false,
                                    ErrorConstants.MAX_FINAL_INSTRUCTED_AMOUNT);
                        }
                    } else {
                        log.error(ErrorConstants.MISSING_FINAL_PAYMENT_AMOUNT);
                        return CommonConsentValidationUtil.getResponse(eventId,false,
                                ErrorConstants.MISSING_FINAL_PAYMENT_AMOUNT);
                    }
                } else {
                    log.error(ErrorConstants.INVALID_FINAL_PAYMENT_AMOUNT_OBJECT);
                    return CommonConsentValidationUtil.getResponse(eventId,false,
                            ErrorConstants.INVALID_FINAL_PAYMENT_AMOUNT_OBJECT);
                }
            }
        }
        return CommonConsentValidationUtil.getResponse(eventId,true, CommonConstants.IS_VALID);
    }
}
