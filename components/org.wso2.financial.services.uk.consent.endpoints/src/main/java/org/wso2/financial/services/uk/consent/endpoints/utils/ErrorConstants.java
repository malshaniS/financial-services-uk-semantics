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

/**
 * Error Constant Class.
 */
public class ErrorConstants {

    //Error Response Structure constants
    public static final String CODE = "Code";
    public static final String ID = "Id";
    public static final String MESSAGE = "Message";
    public static final String ERRORS = "Errors";
    public static final String ERROR_CODE = "ErrorCode";
    public static final String PATH = "Path";
    public static final String URL = "Url";
    public static final String ERROR_URL = "ErrorURL";
    public static final String ERROR = "error";
    public static final String ERROR_DESCRIPTION = "error_description";

    //HTTP Error Codes
    public static final String HTTP_BAD_REQUEST = "400";
    public static final String HTTP_UNAUTHORIZED = "401";
    public static final String HTTP_FORBIDDEN = "403";
    public static final String HTTP_NOT_ACCEPTABLE = "406";
    public static final String HTTP_UNSUPPORTED_MEDIA_TYPE = "415";

    // High level textual error code, to help categorize the errors.
    public static final String BAD_REQUEST_CODE = "400 BadRequest";
    public static final String UNAUTHORIZED_CODE = "401 Unauthorized";
    public static final String FORBIDDEN_CODE = "403 Forbidden";
    public static final String NOT_ACCEPTABLE_CODE = "406 Not Acceptable";
    public static final String SERVER_ERROR_CODE = "500 Internal Server Error";
    public static final String UNSUPPORTED_MEDIA_TYPE_CODE = "415 Unsupported Media Type";

    //Low level textual error code
    public static final String FIELD_EXPECTED = "UK.OBIE.Field.Expected";
    public static final String FIELD_INVALID = "UK.OBIE.Field.Invalid";
    public static final String FIELD_INVALID_DATE = "UK.OBIE.Field.InvalidDate";
    public static final String FIELD_MISSING = "UK.OBIE.Field.Missing";
    public static final String FIELD_UNEXPECTED = "UK.OBIE.Field.Unexpected";
    public static final String PATH_REQUEST_TIME = "Payload.Time";
    public static final String PATH_PERMISSIONS = "Data.Permissions";
    public static final String PATH_DATE = "Payload.Date";
    public static final String PATH_EXPIRATION_DATE = "Data.Expiration-Date";
    public static final String PATH_TRANSACTION_DATE = "Data.TransactionFromDateTime";
    public static final String PATH_TRANSACTION_TO_DATE = "Data.TransactionToDateTime";
    public static final String PATH_INSTRUCTED_AMOUNT = "Data.Initiation.InstructedAmount";
    public static final String PATH_EXECUTION_DATE = "Data.Initiation.RequestedExecutionDate";
    public static final String PATH_FIRST_PAYMENT_DATE_TIME = "Data.Initiation.FirstPaymentDateTime";
    public static final String PATH_COMPLETION_DATE_TIME = "Data.Authorisation.CompletionDateTime";
    public static final String PATH_DEBTOR_ACCOUNT = "Data.Initiation.DebtorAccount";
    public static final String PATH_RISK = "Data.Risk";
    public static final String PATH_RISK_ADDRESS = "Data.Risk.Address";
    public static final String PATH_CREDIT_AGENT = "Data.Initiation.CreditorAgent";
    public static final String PATH_FINAL_PAYMENT_DATE_TIME = "Data.Initiation.FinalPaymentDateTime";
    public static final String PATH_RECURRING_PAYMENT_DATE_TIME = "Data.Initiation.RecurringPaymentDateTime";
    public static final String PATH_FIRST_PAYMENT_AMOUNT = "Data.Initiation.FirstPaymentAmount";
    public static final String PATH_FIRST_PAYMENT_AMOUNT_AMOUNT = "Data.Initiation.FirstPaymentAmount.Amount";
    public static final String PATH_FINAL_PAYMENT_AMOUNT_AMOUNT = "Data.Initiation.FinalPaymentAmount.Amount";
    public static final String PATH_RECURRING_PAYMENT_AMOUNT = "Data.Initiation.RecurringPaymentAmount";
    public static final String PATH_RECURRING_PAYMENT_AMOUNT_AMOUNT = "Data.Initiation.RecurringPaymentAmount.Amount";
    public static final String PATH_EXCHANGE_RATE_INFO = "Data.Initiation.ExchangeRateInformation";
    public static final String TRANSACTION_TO_FROM_INVALID_ERROR = "The Transaction From/To values are invalid.";
    public static final String EXPIRED_DATE_ERROR = "The ExpirationDateTime value has to be a future date.";
    public static final String INVALID_REQ_PAYLOAD = "Invalid request payload";
    public static final String MSG_WRONG_PERMISSIONS = "The payload contains wrong set of permissions.";
    public static final String MSG_INVALID_DATE_FORMAT = "The payload contains non ISO 8601 datetime formats.";
    public static final String PAYMENT_INITIATION_HANDLE_ERROR = "Error occurred while handling the payment " +
            "initiation request";
    public static final String MSG_ELAPSED_CUT_OFF_DATE_TIME = "{payment-order} consent / resource received after " +
            "CutOffDateTime.";
    public static final String MAX_FIRST_INSTRUCTED_AMOUNT_ERROR = "First Payment Amount in the request exceeds the " +
            "maximum allowed amount by the bank ";
    public static final String MAX_RECURRING_INSTRUCTED_AMOUNT = "Recurring Amount specified exceed the Maximum" +
            " Instructed Amount of the bank";
    public static final String MAX_FINAL_INSTRUCTED_AMOUNT = "Final Amount specified exceed the Maximum Instructed " +
            "Amount of the bank";
    public static final String MAX_INSTRUCTED_AMOUNT = "Instructed Amount specified exceed the Maximum Instructed " +
            "Amount of the bank";
    public static final String INVALID_INSTRUCTED_AMOUNT = "Instructed Amount specified should be grater than zero";
    public static final String MAX_EXECUTION_DATE = "Requested Execution Date specified cannot exceed the maximum " +
            "number of days supported by the ASPSP";
    public static final String INVALID_COMPLETION_DATE_FORMAT = "Completion Date is not a valid ISO 8601 date-time";
    public static final String INVALID_FIRST_PAYMENT_DATE_FORMAT = "FirstPaymentDateTime is not a valid ISO 8601" +
            " date-time";
    public static final String INVALID_FINAL_PAYMENT_DATE_FORMAT = "FinalPaymentDateTime is not a valid ISO 8601" +
            " date-time";
    public static final String INVALID_RECURRING_PAYMENT_DATE_FORMAT = "RecurringPaymentDateTime is not a valid ISO" +
            " 8601 date-time";
    public static final String FINAL_PAYMENT_BEFORE_FIRSTPAYEMNT =
            "Final Payment Date Time value cannot be before the first payment date time";
    public static final String RECURRINGPAYMENT_BEFORE_FIRSTPAYMENT =
            "Recurring Payment Date Time value cannot be before the first payment date time";
    public static final String FINAL_PAYMENT_BEFORE_RECURRINGPAYMENT =
            "Final Payment Date Time value cannot be before the recurring payment date time";
    public static final String MISSING_FIRST_PAYMENT_AMOUNT_OBJECT = "Mandatory Parameter FirstPaymentAmount is " +
            "missing";
    public static final String INVALID_FIRST_PAYMENT_AMOUNT_OBJECT = "Mandatory Parameter FirstPaymentAmount is " +
            "invalid";
    public static final String INVALID_RECURRING_PAYMENT_AMOUNT_OBJECT = "Mandatory Parameter FirstPaymentAmount is " +
            "invalid";
    public static final String INVALID_FINAL_PAYMENT_AMOUNT_OBJECT = "Mandatory Parameter FirstPaymentAmount is " +
            "invalid";
    public static final String MISSING_FIRST_PAYMENT_AMOUNT = "Mandatory Parameter Amount is missing in " +
            "FirstPaymentAmount";
    public static final String MISSING_FINAL_PAYMENT_AMOUNT = "Mandatory Parameter Amount is missing in " +
            "FinalPaymentAmount";
    public static final String MISSING_RECURRING_PAYMENT_AMOUNT = "Mandatory Parameter Amount is missing in " +
            "RecurringPaymentAmount";
    public static final String INVALID_RECURRING_PAYMENT = "Recurring Payment Date Time or recurring payment amount" +
            " should be different from the first payment date time and first payment amount";
    public static final String INVALID_STANDING_ORDER = "Close ended standing order should have only one of these: " +
            "NumberOfPayments or FinalPaymentDateTime";
    public static final String INVALID_EXCHANGE_RATE_INFO_AGREED_RATE = "Exchange rate and ContractIdentification " +
            "must be specified for agreed rate type";
    public static final String INVALID_EXCHANGE_RATE_INFO = "ExchangeRateInformation,must not specify ExchangeRate" +
            " and/or ContractIdentification when requesting an Actual or Indicative RateType";
    public static final String INVALID_IDENTIFICATION = "Identification validation for SortCodeNumber Scheme failed.";
    public static final String CREDITOR_AGENT_UNEXPECTED = "Creditor agent should not be given if scheme name is " +
            "sort code";
    public static final String INVALID_RISK_SECTION = "When PaymentContextCode is EcommerceGoods or " +
            "EcommerceServices, MerchantCategoryCode and MerchantCustomerIdentification should be populated";
    public static final String INVALID_DELIVERY_ADDRESS = "EcommerceGoods context is set, but delivery address is" +
            " not set";
    public static final String ERROR_PAYLOAD_PARSE = "Error while parsing payload";
}
