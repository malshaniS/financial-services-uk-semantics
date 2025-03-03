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
import net.minidev.json.JSONObject;
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

        String rateType = exchangeRateInformation.getAsString(CommonConstants.RATE_TYPE);

        Optional<String> contractIdentification = Optional.ofNullable(exchangeRateInformation
                .getAsString(CommonConstants.CONTRACT_IDENTIFICATION));

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

        if (Double.parseDouble(instructedAmount.getAsString(CommonConstants.AMOUNT)) < 1) {
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

        String creditor_Agent = creditorAgent.getAsString(CommonConstants.CREDITOR_AGENT);

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
    public static JSONObject validateCreditorAccount(JSONObject initiation) {

        JSONObject validationResponse = new JSONObject();

        //Validate Creditor Account
        JSONObject creditorAccount = (JSONObject) initiation.get(CommonConstants.CREDITOR_ACC);

        //Validate Sort Code number scheme
        String schemeName = creditorAccount.getAsString(CommonConstants.SCHEME_NAME);
        String identification = creditorAccount.getAsString(CommonConstants.IDENTIFICATION);

        if (!checkSortCodeSchemeNameAndIdentificationValidity(schemeName, identification)) {

            log.error(ErrorConstants.INVALID_IDENTIFICATION);
            validationResponse.put(CommonConstants.IS_VALID, false);

            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.INVALID_REQ_PAYLOAD,
                    ErrorConstants.INVALID_IDENTIFICATION, ErrorConstants.PATH_DEBTOR_ACCOUNT);
        }

        return (JSONObject) validationResponse.put(CommonConstants.IS_VALID, true);
    }

    /**
     * Validate debtor account.
     *
     * @param initiation Initiation object
     * @return
     */
    public static JSONObject validateDebtorAccount(JSONObject initiation) {

        JSONObject validationResponse = new JSONObject();

        //Validate Creditor Account
        JSONObject creditorAccount = (JSONObject) initiation.get(CommonConstants.DEBTOR_ACC);

        //Validate Sort Code number scheme
        String schemeName = creditorAccount.getAsString(CommonConstants.SCHEME_NAME);
        String identification = creditorAccount.getAsString(CommonConstants.IDENTIFICATION);

        if (!checkSortCodeSchemeNameAndIdentificationValidity(schemeName, identification)) {

            log.error(ErrorConstants.INVALID_IDENTIFICATION);
            validationResponse.put(CommonConstants.IS_VALID, false);

            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.INVALID_REQ_PAYLOAD,
                    ErrorConstants.INVALID_IDENTIFICATION, ErrorConstants.PATH_DEBTOR_ACCOUNT);
        }

        return (JSONObject) validationResponse.put(CommonConstants.IS_VALID, true);
    }

    /**
     * Validate payment consent request.
     *
     * @param requestPath Request Path
     * @param request     Request Object
     * @return
     */
    public static JSONObject validatePaymentConsentRequest(String requestPath, JSONObject request) {

        JSONObject validationResponse = new JSONObject();

        JSONObject data = (JSONObject) request.get(CommonConstants.DATA);
        JSONObject initiation = (JSONObject) data.get(CommonConstants.INITIATION);
        JSONObject instructedAmount = (JSONObject) initiation.get(CommonConstants.INSTRUCTED_AMOUNT);
        JSONObject creditorAgent = (JSONObject) initiation.get(CommonConstants.CREDITOR_AGENT);
        String firstPaymentDateTime = initiation.getAsString(CommonConstants.FIRST_PAYMENT_DATE);
        String finalPaymentDateTime = initiation.getAsString(CommonConstants.FINAL_PAYMENT_DATE);
        JSONObject requestExecutionDateTime = (JSONObject) initiation.get(CommonConstants.REQUEST_EXECUTION_DATE);
        String recurringPaymentDateTime = initiation.getAsString(CommonConstants.RECURRING_PAYMENT_DATE);
        String recurringPaymentAmount = initiation.getAsString(CommonConstants.RECURRING_AMOUNT);
        String firstPaymentAmount = initiation.getAsString(CommonConstants.FIRST_PAYMENT_AMOUNT);
        JSONObject exchangeRateInformation = (JSONObject) initiation.get(CommonConstants.EXCHANGE_RATE_INFO);
        JSONObject authorisation = (JSONObject) data.get(CommonConstants.AUTHORISATION);
        String completionDateTime = authorisation.getAsString(CommonConstants.COMPLETION_DATE);
        JSONObject risk = (JSONObject) data.get(CommonConstants.RISK);
        String paymentContextCode = risk.getAsString(CommonConstants.CONTEXT_CODE);
        JSONObject firstPaymentObj = (JSONObject) initiation.get(CommonConstants.FIRST_PAYMENT_AMOUNT);
        JSONObject recurringPaymentObj = (JSONObject) initiation.get(CommonConstants.RECURRING_AMOUNT);
        JSONObject finalPaymentObj = (JSONObject) initiation.get(CommonConstants.FINAL_PAYMENT_AMOUNT);

        //Validate InstructedAmount in Initiation section of the payment payload
        if (!requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH) ||
                !requestPath.contains(CommonConstants.FILE_PAYMENT_CONSENT_PATH)) {

            if (!validateInstructedAmountIsNotZero(instructedAmount)) {
                log.error(ErrorConstants.INVALID_INSTRUCTED_AMOUNT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                        ErrorConstants.INVALID_INSTRUCTED_AMOUNT, ErrorConstants.PATH_INSTRUCTED_AMOUNT);
            }
            if (!validateMaxInstructedAmount(instructedAmount.getAsString(CommonConstants.AMOUNT))) {
                log.error(ErrorConstants.MAX_INSTRUCTED_AMOUNT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                        ErrorConstants.MAX_INSTRUCTED_AMOUNT, ErrorConstants.PATH_INSTRUCTED_AMOUNT);
            }
        }

        //Validate cutoff datetime
        if (shouldInitiationRequestBeRejected()) {

            log.error(ErrorConstants.MSG_ELAPSED_CUT_OFF_DATE_TIME);
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.PAYMENT_INITIATION_HANDLE_ERROR,
                    ErrorConstants.MSG_ELAPSED_CUT_OFF_DATE_TIME, ErrorConstants.PATH_REQUEST_TIME);
        }

        //Validate Creditor Account
        if (!requestPath.contains(CommonConstants.FILE_PAYMENT_PATH)) {

            validateCreditorAccount(initiation);
        }

        //Validate Debtor Account
        if(initiation.get(CommonConstants.DEBTOR_ACC) != null) {
            validateDebtorAccount(initiation);
        }

        //check whether creditor agent is not available if creditor identification is sort code scheme
        if (requestPath.contains(CommonConstants.INTERNATIONAL_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_SCHEDULED_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)) {

            if (!validateCreditorAgent(CommonConstants.UK_OBIE_SORT_CODE_ACCOUNT_NUMBER, creditorAgent)) {

                log.error(ErrorConstants.CREDITOR_AGENT_UNEXPECTED);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_UNEXPECTED,
                        ErrorConstants.CREDITOR_AGENT_UNEXPECTED, ErrorConstants.PATH_CREDIT_AGENT);
            }
        }

        //Check whether execution date time is before the max date
        if (requestPath.contains(CommonConstants.DOMESTIC_SCHEDULED_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_SCHEDULED_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.FILE_PAYMENT_PATH)) {

            if ((initiation.get(CommonConstants.REQUEST_EXECUTION_DATE) instanceof String) &&
                    !validateMaxFutureDate(requestExecutionDateTime.getAsString(CommonConstants.REQUEST_EXECUTION_DATE))) {

                log.error(ErrorConstants.MAX_EXECUTION_DATE);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                        ErrorConstants.MAX_EXECUTION_DATE, ErrorConstants.PATH_EXECUTION_DATE);
            }
        }

        //Validate First Payment date and Final Payment Dates
        if (requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)) {

            //Verify FirstPaymentDateTime is valid
            if (!(initiation.get(CommonConstants.FIRST_PAYMENT_DATE) instanceof String) ||
                    !CommonConsentValidationUtil.isValid8601(firstPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_FIRST_PAYMENT_DATE_FORMAT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                        ErrorConstants.INVALID_FIRST_PAYMENT_DATE_FORMAT,
                        ErrorConstants.PATH_FIRST_PAYMENT_DATE_TIME);
            }

            //Verify FinalPaymentDateTime is valid
            if (!(initiation.get(CommonConstants.FINAL_PAYMENT_DATE) instanceof String) ||
                    !CommonConsentValidationUtil.isValid8601(finalPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_FINAL_PAYMENT_DATE_FORMAT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                        ErrorConstants.INVALID_FINAL_PAYMENT_DATE_FORMAT,
                        ErrorConstants.PATH_FINAL_PAYMENT_DATE_TIME);
            }

            //Ensure firstPaymentDateTime is before FinalPaymentDateTime
            if (StringUtils.isNotEmpty(firstPaymentDateTime) && StringUtils.isNotEmpty(finalPaymentDateTime) &&
                    !validRelativeOrder(firstPaymentDateTime, finalPaymentDateTime)) {

                log.error(ErrorConstants.FINAL_PAYMENT_BEFORE_FIRSTPAYEMNT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                        ErrorConstants.FINAL_PAYMENT_BEFORE_FIRSTPAYEMNT, ErrorConstants.PATH_FINAL_PAYMENT_DATE_TIME);
            }

            // if neither no. of payments nor final payment date time is present in the request,
            //      then standing order is open ended.
            // Otherwise, standing order is not open ended.
            // If standing order is not open ended,
            //      both no. of payments and final payment date time should not be specified at the same time.

            String numberOfPayments = (initiation.containsKey(CommonConstants.NUMBER_OF_PAYMENTS)) ?
                    initiation.getAsString(CommonConstants.NUMBER_OF_PAYMENTS) : null;

            if (StringUtils.isNotEmpty(numberOfPayments) && StringUtils.isNotEmpty(finalPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_STANDING_ORDER);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                        ErrorConstants.INVALID_STANDING_ORDER, ErrorConstants.PATH_FINAL_PAYMENT_DATE_TIME);
            }
        }

        //Validate RecurringPaymentAmount
        if (requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH)) {

            //Validate RecurringPaymentAmount is valid
            if (!(initiation.get(CommonConstants.RECURRING_PAYMENT_DATE) instanceof String) ||
                    !CommonConsentValidationUtil.isValid8601(recurringPaymentDateTime)) {

                log.error(ErrorConstants.INVALID_RECURRING_PAYMENT_DATE_FORMAT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                        ErrorConstants.INVALID_RECURRING_PAYMENT_DATE_FORMAT,
                        ErrorConstants.PATH_RECURRING_PAYMENT_DATE_TIME);
            }

            //Ensure RecurringPaymentDateTime is before FinalPaymentDateTime
            if (StringUtils.isNotEmpty(recurringPaymentDateTime) && StringUtils.isNotEmpty(finalPaymentDateTime) &&
                    !validRelativeOrder(recurringPaymentDateTime, finalPaymentDateTime)) {

                log.error(ErrorConstants.FINAL_PAYMENT_BEFORE_RECURRINGPAYMENT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                        ErrorConstants.FINAL_PAYMENT_BEFORE_RECURRINGPAYMENT,
                        ErrorConstants.PATH_FINAL_PAYMENT_DATE_TIME);
            }

            //Ensure firstPaymentDateTime is before RecurringPaymentDateTime
            if (StringUtils.isNotEmpty(firstPaymentDateTime) && StringUtils.isNotEmpty(recurringPaymentDateTime) &&
                    !validRelativeOrder(firstPaymentDateTime, recurringPaymentDateTime)) {

                log.error(ErrorConstants.RECURRINGPAYMENT_BEFORE_FIRSTPAYMENT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                        ErrorConstants.RECURRINGPAYMENT_BEFORE_FIRSTPAYMENT,
                        ErrorConstants.PATH_RECURRING_PAYMENT_DATE_TIME);
            }

            //validate recurring payment
            if (StringUtils.isNotEmpty(recurringPaymentAmount) && StringUtils.isNotEmpty(recurringPaymentDateTime)
                    && StringUtils.isNotEmpty(firstPaymentAmount) && StringUtils.isNotEmpty(firstPaymentDateTime) &&
                    !validateRecurringPayment(firstPaymentDateTime, recurringPaymentDateTime, firstPaymentAmount,
                            recurringPaymentAmount)) {

                log.error(ErrorConstants.INVALID_RECURRING_PAYMENT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                        ErrorConstants.INVALID_RECURRING_PAYMENT, ErrorConstants.PATH_RECURRING_PAYMENT_DATE_TIME);
            }
        }

        //Validate ExchangeRateInformation
        if(requestPath.contains(CommonConstants.INTERNATIONAL_CONSENT_PATH) ||
                requestPath.contains(CommonConstants.INTERNATIONAL_STANDING_ORDER_CONSENT_PATH)){

            if (!validateExchangeRateInformation(exchangeRateInformation)) {

                if (CommonConstants.AGREED_RATE_TYPE
                        .equals(exchangeRateInformation.getAsString(CommonConstants.RATE_TYPE))) {

                    log.error(ErrorConstants.INVALID_EXCHANGE_RATE_INFO_AGREED_RATE);
                    return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_EXPECTED,
                            ErrorConstants.INVALID_EXCHANGE_RATE_INFO_AGREED_RATE,
                            ErrorConstants.PATH_EXCHANGE_RATE_INFO);
                } else {

                    log.error(ErrorConstants.INVALID_EXCHANGE_RATE_INFO);
                    return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_UNEXPECTED,
                            ErrorConstants.INVALID_EXCHANGE_RATE_INFO, ErrorConstants.PATH_EXCHANGE_RATE_INFO);
                }
            }
        }

        //Validate authorisation completion date time
        if (!CommonConsentValidationUtil.isValid8601(completionDateTime)) {

            log.error(ErrorConstants.INVALID_COMPLETION_DATE_FORMAT);
            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID_DATE,
                    ErrorConstants.INVALID_COMPLETION_DATE_FORMAT, ErrorConstants.PATH_COMPLETION_DATE_TIME);
        }

        //Validate Risk Section
        if (StringUtils.isNotEmpty(paymentContextCode) &&
                (CommonConstants.ECOMMERCE_GOODS.equalsIgnoreCase(risk.getAsString(CommonConstants.CONTEXT_CODE)) ||
                        CommonConstants.ECOMMERCE_SERVICES.equalsIgnoreCase(risk.getAsString(CommonConstants.CONTEXT_CODE)))) {

            if (StringUtils.isEmpty(risk.getAsString(CommonConstants.MERCHANT_CATEGORY_CODE)) &&
                    StringUtils.isEmpty(risk.getAsString(CommonConstants.MERCHANT_IDENTIFICATION))) {

                log.error(ErrorConstants.INVALID_RISK_SECTION);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_MISSING,
                        ErrorConstants.INVALID_RISK_SECTION, ErrorConstants.PATH_RISK);
            }
            if (CommonConstants.ECOMMERCE_GOODS
                    .equalsIgnoreCase(risk.getAsString(CommonConstants.CONTEXT_CODE))) {

                if (risk.get(CommonConstants.DELIVERY_ADDRESS) == null) {
                    log.error(ErrorConstants.INVALID_DELIVERY_ADDRESS);
                    return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_MISSING,
                            ErrorConstants.INVALID_DELIVERY_ADDRESS, ErrorConstants.PATH_RISK_ADDRESS);
                }
            }
        }
///---
        if (requestPath.contains(CommonConstants.DOMESTIC_STANDING_ORDER_CONSENT_PATH)) {

            //Validate FirstPaymentAmount
            String firstPayAmount = null;

            if (initiation.containsKey(CommonConstants.FIRST_PAYMENT_AMOUNT)) {
                if ((initiation.get(CommonConstants.FIRST_PAYMENT_AMOUNT) instanceof JSONObject)) {

                    if (firstPaymentObj.containsKey(CommonConstants.AMOUNT)) {

                        firstPayAmount = firstPaymentObj.getAsString(CommonConstants.AMOUNT);

                        //Validate Max Instructed Amount
                        if (!(firstPaymentObj.get(CommonConstants.AMOUNT) instanceof String) ||
                                !validateMaxInstructedAmount(firstPayAmount)) {

                            log.error(ErrorConstants.MAX_FIRST_INSTRUCTED_AMOUNT_ERROR);
                            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                                    ErrorConstants.MAX_FIRST_INSTRUCTED_AMOUNT_ERROR,
                                    ErrorConstants.PATH_FIRST_PAYMENT_AMOUNT_AMOUNT);
                        }
                    } else {
                        log.error(ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT);
                        return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_MISSING,
                                ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT,
                                ErrorConstants.PATH_FIRST_PAYMENT_AMOUNT_AMOUNT);
                    }
                } else {
                    log.error(ErrorConstants.INVALID_FIRST_PAYMENT_AMOUNT_OBJECT);
                    return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                            ErrorConstants.INVALID_FIRST_PAYMENT_AMOUNT_OBJECT,
                            ErrorConstants.PATH_FIRST_PAYMENT_AMOUNT);
                }
            } else {
                log.error(ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT_OBJECT);
                return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_MISSING,
                        ErrorConstants.MISSING_FIRST_PAYMENT_AMOUNT_OBJECT,
                        ErrorConstants.PATH_FIRST_PAYMENT_AMOUNT_AMOUNT);
            }

            //Validate RecurringPaymentAmount
            String recurringPayAmount = null;

            if (initiation.containsKey(CommonConstants.RECURRING_AMOUNT)) {
                if ((initiation.get(CommonConstants.RECURRING_AMOUNT) instanceof JSONObject)) {

                    if (recurringPaymentObj.containsKey(CommonConstants.AMOUNT)) {
                        recurringPayAmount = recurringPaymentObj.getAsString(CommonConstants.AMOUNT);

                        //Validate Max Instructed Amount
                        if (!(recurringPaymentObj.get(CommonConstants.AMOUNT) instanceof String) ||
                                !validateMaxInstructedAmount(recurringPayAmount)) {

                            log.error(ErrorConstants.MAX_RECURRING_INSTRUCTED_AMOUNT);
                            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                                    ErrorConstants.MAX_RECURRING_INSTRUCTED_AMOUNT,
                                    ErrorConstants.PATH_RECURRING_PAYMENT_AMOUNT_AMOUNT);
                        }
                    } else {
                        log.error(ErrorConstants.MISSING_RECURRING_PAYMENT_AMOUNT);
                        return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_MISSING,
                                ErrorConstants.MISSING_RECURRING_PAYMENT_AMOUNT,
                                ErrorConstants.PATH_RECURRING_PAYMENT_AMOUNT_AMOUNT);
                    }
                } else {
                    log.error(ErrorConstants.INVALID_RECURRING_PAYMENT_AMOUNT_OBJECT);
                    return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                            ErrorConstants.INVALID_RECURRING_PAYMENT_AMOUNT_OBJECT,
                            ErrorConstants.PATH_RECURRING_PAYMENT_AMOUNT);
                }
            }

            //Validate FinalPaymentAmount
            String finalPayAmount = null;
            if (initiation.containsKey(CommonConstants.FINAL_PAYMENT_AMOUNT)) {
                if ((initiation.get(CommonConstants.FINAL_PAYMENT_AMOUNT) instanceof JSONObject)) {

                    if (finalPaymentObj.containsKey(CommonConstants.AMOUNT)) {
                        finalPayAmount = finalPaymentObj.getAsString(CommonConstants.AMOUNT);

                        //Validate Max Instructed Amount
                        if (!(finalPaymentObj.get(CommonConstants.AMOUNT) instanceof String) ||
                                !validateMaxInstructedAmount(finalPayAmount)) {

                            log.error(ErrorConstants.MAX_FINAL_INSTRUCTED_AMOUNT);
                            return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                                    ErrorConstants.MAX_FINAL_INSTRUCTED_AMOUNT,
                                    ErrorConstants.PATH_RECURRING_PAYMENT_AMOUNT_AMOUNT);
                        }
                    } else {
                        log.error(ErrorConstants.MISSING_FINAL_PAYMENT_AMOUNT);
                        return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_MISSING,
                                ErrorConstants.MISSING_FINAL_PAYMENT_AMOUNT,
                                ErrorConstants.PATH_FINAL_PAYMENT_AMOUNT_AMOUNT);
                    }
                } else {
                    log.error(ErrorConstants.INVALID_FINAL_PAYMENT_AMOUNT_OBJECT);
                    return CommonConsentValidationUtil.getValidationResponse(ErrorConstants.FIELD_INVALID,
                            ErrorConstants.INVALID_FINAL_PAYMENT_AMOUNT_OBJECT,
                            ErrorConstants.PATH_RECURRING_PAYMENT_AMOUNT);
                }
            }
        }

        validationResponse.put(CommonConstants.IS_VALID, true);
        return validationResponse;
    }
}
