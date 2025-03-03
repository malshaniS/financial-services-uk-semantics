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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Common Constant Class.
 */
public class CommonConstants {

        public static final String MAX_INSTRUCTED_AMOUNT = "1000.00";
        public static final String MAX_FUTURE_PAYMENT_DAYS = "900";
        public static final boolean CUTOFF_DATE_ENABLED = false;
        public static final String CUTOFF_DATE_POLICY = "ACCEPT";
        public static final String DAILY_CUTOFF = "12:30:30+00:01";
        public static final String EXPECTED_EXECUTION_TIME = "ConsentManagement.PaymentRestrictions.CutOffDateTime" +
                ".ExpectedExecutionTime";
        public static final String EXPECTED_SETTLEMENT_TIME = "ConsentManagement.PaymentRestrictions.CutOffDateTime" +
                ".ExpectedSettlementTime";
        public static final String CUSTOM_LOCAL_INSTRUMENT_VALUES = "ConsentManagement.CustomLocalInstrumentValues";
        public static final String REAUTH_ENABLE_ACC_UPDATE = "ConsentManagement.ConsentReAuthentication" +
                ".EnableAccountUpdateByPSU";
        public static final String VALIDATE_DEBTOR_ACC = "ValidateDebtorAccount";
        public static final String IDEMPOTENCY_ALLOWED_TIME = "ConsentManagement.Idempotency" +
                ".AllowedTimeDurationForIdempotency";
        public static final String MULTIPLE_AUTHORIZATION_EXPIRY = "ConsentManagement.MultiAuthorization" +
                ".DaysToExpireRequest";
        public static final String ENABLE_REQUEST_JTI_VALIDATION = "DCR.EnableRequestJTIValidation";
        public static final String ENABLE_SSA_JTI_VALIDATION = "DCR.EnableSSAJTIValidation";
        public static final String JTI_CACHE_ACCESS_EXPIRY = "DCR.JTICache.CacheAccessExpiry";
        public static final String JTI_CACHE_MODIFY_EXPIRY = "DCR.JTICache.CacheModifiedExpiry";
        public static final String ZONE_ID = "ZoneId";

        public static final String REVOKED_STATUS = "revoked";
        public static final String REJECTED_STATUS = "rejected";
        public static final String AUTHORIZED_STATUS = "authorised";
        public static final String RE_AUTHORIZED_STATUS = "re-authorised";
        public static final String CONSUMED_STATUS = "consumed";
        public static final String AWAITING_AUTH_STATUS = "awaitingAuthorisation";
        public static final String AWAITING_UPLOAD_STATUS = "awaitingUpload";
        public static final String UK_REVOKED_STATUS = "Revoked";
        public static final String UK_REJECTED_STATUS = "Rejected";
        public static final String UK_AUTHORIZED_STATUS = "Authorised";
        public static final String UK_AWAITING_AUTH_STATUS = "AwaitingAuthorisation";
        public static final String UK_CONSUMED_STATUS = "Consumed";
        public static final String UK_AWAITING_UPLOAD_STATUS = "AwaitingUpload";

        // Jws Signature Validation & Response  Signing Configuration
        public static final String JWS_SIG_VALIDATION_TAN_LIST = "JwsSignatureConfiguration.OBIE." +
                "TrustedAnchors.SignatureValidation";
        public static final String JWS_RESP_SIGNING_TAN_LIST = "JwsSignatureConfiguration.OBIE." +
                "TrustedAnchors.ResponseSigning";
        public static final String JWS_SIG_VALIDATION_MANDATED_APIS = "JwsSignatureConfiguration.SignatureValidation" +
                ".MandatedAPIs.APIContext";
        public static final String UK_MESSAGE_SIGNING_ORG_ID = "JwsSignatureConfiguration.OBIE.OrganizationId";
        public static final String UK_MESSAGE_SIGNING_REQ_APIS = "JwsSignatureConfiguration.ResponseSigning." +
                "ResponseSignatureRequiredAPIs.APIContext";

        public static final String IS_VALID = "isValid";
        public static final String HTTP_CODE = "httpCode";
        public static final String ERRORS = "errors";
        public static final String IS_VALID_PAYLOAD = "isValidPayload";
        public static final String UUID_REGEX =
                "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
        public static final String PAYMENT_TYPE = "PaymentType";
        public static final String HTTP_METHOD_POST = "POST";
        public static final String HTTP_METHOD_PUT = "PUT";
        public static final String APPLICATION_JSON = "application/json";
        public static final String IS_ERROR = "isError";

        // VRP parameters
        public static final String DOMESTIC_VRP_CONSENT_PATH = "domestic-vrp-consents";
        public static final String DOMESTIC_VRP_PAYMENT_PATH = "domestic-vrps";
        public static final String VRP_RESPONSE_PROCESS_PATH = "vrp-response-process";

        public static final String INTERACTION_ID_HEADER = "x-fapi-interaction-id";
        public static final String ACCOUNT_CONSENT_PATH = "account-access-consents";
        public static final String COF_CONSENT_PATH = "funds-confirmation-consents";
        public static final String DOMESTIC_CONSENT_PATH = "domestic-payment-consents";
        public static final String DOMESTIC_SCHEDULED_CONSENT_PATH = "domestic-scheduled-payment-consents";
        public static final String DOMESTIC_STANDING_ORDER_CONSENT_PATH = "domestic-standing-order-consents";
        public static final String INTERNATIONAL_CONSENT_PATH = "international-payment-consents";
        public static final String INTERNATIONAL_SCHEDULED_CONSENT_PATH = "international-scheduled-payment-consents";
        public static final String INTERNATIONAL_STANDING_ORDER_CONSENT_PATH = "international-standing-order-consents";
        public static final String FILE_PAYMENT_CONSENT_PATH = "file-payment-consents";
        public static final String DOMESTIC_PAYMENTS = "Domestic Payments";
        public static final String DOMESTIC_VRP = "Domestic VRP";
        public static final String DOMESTIC_SCHEDULED_PAYMENTS = "Domestic Scheduled Payments";
        public static final String DOMESTIC_STANDING_ORDER_PAYMENTS = "Domestic Standing Orders";
        public static final String INTERNATIONAL_PAYMENTS = "International Payments";
        public static final String INTERNATIONAL_SCHEDULED_PAYMENTS = "International Scheduled Payments";
        public static final String INTERNATIONAL_STANDING_ORDER_PAYMENTS = "International Standing Orders";
        public static final String BULK_FILE_PAYMENT = "Bulk File Payments";
        public static final String BATCH_FILE_PAYMENT = "Batch File Payments";
        public static final String BULK_PAYMENTS = "Bulk Payments";
        public static final String FILE_PAYMENT_PATH = "file-payment";
        public static final String PAYMENT_COF_PATH = "funds-confirmation";
        public static final String DOMESTIC_TYPE = "domestic";
        public static final String DOMESTIC_SCHEDULED_TYPE = "domestic-scheduled";
        public static final String DOMESTIC_STANDING_ORDER_TYPE = "domestic-standing-order";
        public static final String INTERNATIONAL_TYPE = "international";
        public static final String INTERNATIONAL_SCHEDULED_TYPE = "international-scheduled";
        public static final String INTERNATIONAL_STANDING_ORDER_TYPE = "international-standing-order";
        public static final String FILE_PAYMENT_TYPE = "file-payment";
        public static final String FILE_PAYMENT_CONSENT_ID_FILE = "/file-payment-consents/{ConsentId}/file";
        public static final String ACCOUNTS = "accounts";
        public static final String PAYMENTS = "payments";
        public static final String VRP = "vrp";
        public static final String FUNDSCONFIRMATIONS = "fundsconfirmations";
        public static final String FILE_UPLOAD = "FileUpload";
        public static final String CREATED_STATUS = "created";
        public static final String AUTH_TYPE_AUTHORIZATION = "authorization";
        public static final String DATA = "Data";
        public static final String CONSENT_ID = "ConsentId";
        public static final String STATUS = "Status";
        public static final String STATUS_UPDATE_TIME = "StatusUpdateDateTime";
        public static final String CREATION_TIME = "CreationDateTime";
        public static final String LINKS = "Links";
        public static final String SELF = "Self";
        public static final String META = "Meta";
        public static final String RISK = "Risk";
        public static final String PERMISSIONS = "Permissions";
        public static final String EXPIRATION_DATE = "ExpirationDateTime";
        public static final String TRANSACTION_FROM_DATE = "TransactionFromDateTime";
        public static final String TRANSACTION_TO_DATE = "TransactionToDateTime";
        public static final String DATA_SIMPLE = "data";
        public static final String TITLE = "title";
        public static final String EXPIRATION_DATE_TITLE = "Expiration Date Time";
        public static final String TRANSACTION_FROM_DATE_TITLE = "Transaction From Date Time";
        public static final String TRANSACTION_TO_DATE_TITLE = "Transaction To Date Time";
        public static final String REAUTHORIZATION_TITLE = "Reauthorization";
        public static final String MULTI_AUTHORISATION_TITLE = "Multi Authorisation";
        public static final String ACCOUNT_ID = "AccountId";
        public static final String ACCOUNT_ID_LIST = "AccountIds";
        public static final String ACCOUNT_ID_TYPE = "AccountIdType";
        public static final String FILE = "File";
        public static final String CUT_OFF_TIME_ACCEPTABLE = "CutOffTimeAcceptable";
        public static final String ACCOUNT_IDS = "accountIds";
        public static final int NUMBER_OF_PARTS_IN_JWS = 3;
        public static final String CLAIMS = "claims";
        public static final List<String> CLAIM_FIELDS = Collections.unmodifiableList(Arrays.asList("userinfo", "id_token"));
        public static final String OPENBANKING_INTENT_ID = "openbanking_intent_id";
        public static final String VALUE = "value";
        public static final String USER_ID_KEY_NAME = "userID";
        public static final String CONSENT_ID_KEY_NAME = "consentID";
        public static final String TRANSACTIONS = "transactions";
        public static final String STATEMENTS = "statements";
        public static final String TO_STATEMENT_DATE_TIME = "toStatementDateTime";
        public static final String FROM_STATEMENT_DATE_TIME = "fromStatementDateTime";
        public static final String TO_BOOKING_DATE_TIME = "toBookingDateTime";
        public static final String FROM_BOOKING_DATE_TIME = "fromBookingDateTime";
        public static final String TRANSACTION_FROM_DATE_TIME = "transactionFromDateTime";
        public static final String TRANSACTION_TO_DATE_TIME = "transactionToDateTime";
        public static final String DEBTOR_ACC = "DebtorAccount";
        public static final String CREDITOR_ACC = "CreditorAccount";
        public static final String SCHEME_NAME = "SchemeName";
        public static final String IDENTIFICATION = "Identification";
        public static final String NAME = "Name";
        public static final String SECONDARY_IDENTIFICATION = "SecondaryIdentification";
        public static final String DEBTOR_ACC_TITLE = "Debtor Account";
        public static final String SCHEME_NAME_TITLE = "Scheme Name";
        public static final String IDENTIFICATION_TITLE = "Identification";
        public static final String NAME_TITLE = "Name";
        public static final String SECONDARY_IDENTIFICATION_TITLE = "Secondary Identification";
        public static final String OPEN_ENDED_AUTHORIZATION = "Open Ended Authorization Requested";
        public static final String ERROR_CODE = "errorCode";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String HTTPS_CODE = "httpsCode";
        public static final String VALIDATE_ACCOUNT_ID = "ValidateAccountIdOnRetrieval";
        public static final String SIMPLE_ACCOUNT_ID = "account_id";
        public static final String DISPLAY_NAME = "display_name";
        public static final String RE_AUTH_DISPLAY_NAMES = "reAuthSelectedDisplayNames";
        public static final String RE_AUTH_DISPLAY_LIST = "reauthAccountsDisplayList";
        public static final String RE_AUTH_ACCOUNT_LIST = "reauthAccountsList";
        public static final String DATA_REQUESTED = "data_requested";
        public static final String ACCOUNT_DATA = "account_data";
        public static final String DEBTOR_ACCOUNT_BACKEND = "debtor_account";
        public static final String SELECTED_ACCOUNT = "selectedAccount";
        public static final String CONSENT_TYPE = "consent_type";
        public static final String PAYMENT_ACCOUNT = "paymentAccount";
        public static final String COF_ACCOUNT = "cofAccount";
        public static final String ACCOUNT_ARRAY = "accounts[]";
        public static final String CONSENT_STATUS = "consentStatus";
        public static final String AUTH_STATUS = "authStatus";
        public static final String RE_AUTH_TYPE = "ReAuthorization";
        public static final String IDEMPOTENCY_KEY = "IdempotencyKey";
        public static final String FILE_UPLOAD_IDEMPOTENCY_KEY = "FileUploadIdempotencyKey";
        public static final String IS_NEW_RESOURCE = "IsNewResource";
        public static final String STORED_RESPONSE = "StoredResponse";
        public static final String CREATED_TIME = "CreatedTime";

        public static final String FILE_UPLOAD_CREATED_TIME = "FileUploadCreatedTime";
        public static final String IS_IDEMPOTENT = "isIdempotent";
        public static final String START_TIMESTAMP = "startTimeStamp";
        public static final String END_TIMESTAMP = "endTimeStamp";
        public static final String INTERACTION_START_TIMESTAMP = "interactionStartTimestamp";
        public static final String INTERACTION_END_TIMESTAMP = "interactionEndTimestamp";
        public static final String ACCOUNTS_ID_WITH_USERS = "accountIdWithUsers";

        public static final String MULTI_AUTH_TYPE = "MultiAuthType";
        public static final String MULTI_AUTH_EXPIRY = "MultiAuthExpiry";
        public static final String DEFAULT_MULTIPLE_AUTHORIZATION_TYPE = "Any";
        public static final String MULTIPLE = "multiple";
        public static final String AUTHORIZATION_METHOD = "authorizationMethod";
        public static final String MULTI_AUTHORIZATION = "multiAuthorization";
        public static final String IS_JOINT_ACCOUNT = "isJointAccount";
        public static final String ACCEPT_HEADER_NAME = "Accept";
        public static final String ACCEPT_HEADER_VALUE = "application/json";
        public static final String SERVICE_URL_SLASH = "/";
        public static final String IS_REAUTHORIZATION = "isReauthorization";
        public static final String IS_REAUTH_ACCOUNT_UPDATE_ENABLED = "isReauthAccountUpdateEnabled";
        public static final String REAUTH_SELECTED_ACCOUNT = "reauthSelectedAccount";
        public static final String CONSENT_DATA = "consentData";
        public static final String PAYABLE_ENDPOINT = "PayableAccountsRetrieveEndpoint";
        public static final String SHARABLE_ENDPOINT = "SharableAccountsRetrieveEndpoint";

        //Permissions

        public static final String READACCOUNTSBASIC = "ReadAccountsBasic";
        public static final String READACCOUNTSDETAIL = "ReadAccountsDetail";
        public static final String READTRANSACTIONSBASIC = "ReadTransactionsBasic";
        public static final String READTRANSACTIONSDETAIL = "ReadTransactionsDetail";
        public static final String READTRANSACTIONSCREDITS = "ReadTransactionsCredits";
        public static final String READTRANSACTIONSDEBITS = "ReadTransactionsDebits";
        public static final String READBALANCES = "ReadBalances";
        public static final String READBENEFICIARIESBASIC = "ReadBeneficiariesBasic";
        public static final String READBENEFICIARIESDETAIL = "ReadBeneficiariesDetail";
        public static final String READDIRECTDEBITS = "ReadDirectDebits";
        public static final String READPRODUCTS = "ReadProducts";
        public static final String READSTANDINGORDERSBASIC = "ReadStandingOrdersBasic";
        public static final String READSTANDINGORDERSDETAIL = "ReadStandingOrdersDetail";
        public static final String READSTATEMENTSBASIC = "ReadStatementsBasic";
        public static final String READSTATEMENTSDETAIL = "ReadStatementsDetail";
        public static final String READOFFERS = "ReadOffers";
        public static final String READPARTY = "ReadParty";
        public static final String READPARTYPSU = "ReadPartyPSU";
        public static final String READSCHEDULEDPAYMENTSBASIC = "ReadScheduledPaymentsBasic";
        public static final String READSCHEDULEDPAYMENTSDETAIL = "ReadScheduledPaymentsDetail";
        public static final String READPAN = "ReadPAN";

        public static final String ACCOUNT_REGEX = "/accounts";
        public static final String BALANCES_REGEX = "/balances";
        public static final String TRANSACTIONS_REGEX = "/transactions";
        public static final String BENEFICIARY_REGEX = "/beneficiaries";
        public static final String DIRECT_DEBITS_REGEX = "/direct-debits";
        public static final String OFFERS_REGEX = "/offers";
        public static final String PARTY_REGEX = "/party";
        public static final String PRODUCT_REGEX = "/products";
        public static final String SCHEDULED_PAYMENTS_REGEX = "/scheduled-payments";
        public static final String STANDING_ORDER_REGEX = "/standing-orders";
        public static final String STATEMENTS_REGEX = "/statements";
        public static final String ACCOUNT_ID_REGEX = "/accounts/[^/?]*";
        public static final String BALANCES_ID_REGEX = "/accounts/[^/?]*/balances";
        public static final String TRANSACTIONS_ID_REGEX = "/accounts/[^/?]*/transactions";
        public static final String BENEFICIARY_ID_REGEX = "/accounts/[^/?]*/beneficiaries";
        public static final String DIRECT_DEBITS_ID_REGEX = "/accounts/[^/?]*/direct-debits";
        public static final String OFFERS_ID_REGEX = "/accounts/[^/?]*/offers";
        public static final String PARTIES_ID_REGEX = "/accounts/[^/?]*/parties";
        public static final String PARTY_ID_REGEX = "/accounts/[^/?]*/party";
        public static final String PRODUCT_ID_REGEX = "/accounts/[^/?]*/product";
        public static final String SCHEDULED_PAYMENTS_ID_REGEX = "/accounts/[^/?]*/scheduled-payments";
        public static final String STANDING_ORDER_ID_REGEX = "/accounts/[^/?]*/standing-orders";
        public static final String ACCOUNT_ID_STATEMENTS_REGEX = "/accounts/[^/?]*/statements";
        public static final String STATEMENTS_ID_REGEX = "/accounts/[^/?]*/statements/[^/?]*";
        public static final String STATEMENTS_FILE_REGEX = "/accounts/[^/?]*/statements/[^/?]*/file";
        public static final String STATEMENTS_TRANSACTIONS_REGEX = "/accounts/[^/?]*/statements/[^/?]*/transactions";

        public static final String COF_CONSENT_INITIATION_PATH = "/funds-confirmation-consents";
        public static final String COF_CONSENT_CONSENT_ID_PATH = "/funds-confirmation-consents/{ConsentId}";
        public static final String COF_SUBMISSION_PATH = "/funds-confirmations";
        public static final String REJECT = "REJECT";
        public static final String ACCEPT = "ACCEPT";
        public static final String BICFI = "BICFI";
        public static final int BICFI_IDENTIFICATION_LENGHTH_1 = 8;
        public static final int BICFI_IDENTIFICATION_LENGHTH_2 = 11;
        public static final String ACTUAL_RATE_TYPE = "Actual";
        public static final String INDICATIVE_RATE_TYPE = "Indicative";
        public static final String AGREED_RATE_TYPE = "Agreed";
        public static final String SORT_CODE_ACCOUNT_NUMBER = "SortCodeAccountNumber";
        public static final String UK_OBIE_SORT_CODE_ACCOUNT_NUMBER = "UK.OBIE.SortCodeAccountNumber";
        public static final int ACCOUNT_IDENTIFICATION_LENGTH = 14;
        public static final String SORT_CODE_PATTERN = "^[0-9]{6}[0-9]{8}$";
        public static final String AGENT_SCHEME_NAME = "UK.OBIE.BICFI";
        public static final String ECOMMERCE_GOODS = "EcommerceGoods";
        public static final String ECOMMERCE_SERVICES = "EcommerceServices";

        public static final String INITIATION = "Initiation";
        public static final String INSTRUCTION = "Instruction";
        public static final String CONTROL_PARAMETERS = "ControlParameters";
        public static final String PSU_AUTHENTICATION_METHOD = "PSUAuthenticationMethods";
        public static final String VRP_TYPE = "VRPType";
        public static final String PERIOD_ALIGNMENT = "PeriodAlignment";
        public static final String CONSENT = "Consent";
        public static final String CALENDAR = "Calendar";
        public static final String PERIOD_TYPE = "PeriodType";
        public static final String DAY = "Day";
        public static final String WEEK = "Week";
        public static final String FORTNIGHT = "Fortnight";
        public static final String MONTH = "Month";
        public static final String HALF_YEAR = "Half-year";
        public static final String YEAR = "Year";
        public static final String AUTH_METHOD_SCA = "UK.OBIE.SCA";
        public static final String AUTH_METHOD_NOT_REQUIRED = "UK.OBIE.SCANotRequired";
        public static final String PERIOD_AMOUNT_LIMIT = "Amount";
        public static final String VALID_FROM_DATE_TIME = "ValidFromDateTime";
        public static final String VALID_TO_DATE_TIME = "ValidToDateTime";
        public static final String PAID_AMOUNT = "paidAmount";
        public static final String PREVIOUS_PAID_AMOUNT = "prevPaidAmount";
        public static final String LAST_PAYMENT_DATE = "lastPaymentDate";
        public static final String PREVIOUS_LAST_PAYMENT_DATE = "prevLastPaymentDate";
        public static final int DAYS_IN_WEEK = 7;
        public static final String PERMISSION = "Permission";
        public static final String READ_REFUND = "ReadRefundAccount";
        public static final String FREQUENCY = "Frequency";
        public static final String REFERENCE = "Reference";
        public static final String FIRST_PAYMENT_DATE = "FirstPaymentDateTime";
        public static final String FIRST_PAYMENT_AMOUNT = "FirstPaymentAmount";
        public static final String AMOUNT = "Amount";
        public static final String CURRENCY = "Currency";
        public static final String RECURRING_PAYMENT_DATE = "RecurringPaymentDateTime";
        public static final String RECURRING_AMOUNT = "RecurringPaymentAmount";
        public static final String FINAL_PAYMENT_DATE = "FinalPaymentDateTime";
        public static final String FINAL_PAYMENT_AMOUNT = "FinalPaymentAmount";
        public static final String CONTEXT_CODE = "PaymentContextCode";
        public static final String REQUEST_EXECUTION_DATE = "RequestedExecutionDateTime";
        public static final String AUTHORISATION = "Authorisation";
        public static final String AUTHORISATION_TYPE = "AuthorisationType";
        public static final String COMPLETION_DATE = "CompletionDateTime";
        public static final String NUMBER_OF_PAYMENTS = "NumberOfPayments";
        public static final String CREDITOR_AGENT = "CreditorAgent";
        public static final String EXCHANGE_RATE_INFO = "ExchangeRateInformation";
        public static final String RATE_TYPE = "RateType";
        public static final String UNIT_CURRENCY = "UnitCurrency";
        public static final String CONTRACT_IDENTIFICATION = "ContractIdentification";
        public static final String EXCHANGE_RATE = "ExchangeRate";
        public static final String POSTAL_ADDRESS = "PostalAddress";
        public static final String LOCAL_INSTRUMENT = "LocalInstrument";
        public static final String PURPOSE = "Purpose";
        public static final String MERCHANT_CATEGORY_CODE = "MerchantCategoryCode";
        public static final String MERCHANT_IDENTIFICATION = "MerchantCustomerIdentification";
        public static final String DELIVERY_ADDRESS = "DeliveryAddress";
        public static final String INSTRUCTED_AMOUNT = "InstructedAmount";
        public static final String CUT_OFF_DATE_TIME = "CutOffDateTime";
        public static final String FILE_HASH = "FileHash";
        public static final String FILE_TYPE = "FileType";
        public static final String INSTRUCTION_IDENTIFICATION = "InstructionIdentification";
        public static final String END_TO_END_IDENTIFICATION = "EndToEndIdentification";
        public static final String CURRENCY_OF_TRANSFER = "CurrencyOfTransfer";
        public static final String FILE_REFERENCE = "FileReference";
        public static final String NUM_OF_TRANSACTIONS = "NumberOfTransactions";
        public static final String CONTROL_SUM = "ControlSum";
        public static final String REMITTANCE_INFO = "RemittanceInformation";
        public static final String DEBTOR_AGENT = "DebtorAgent";
        public static final String UNSTRUCTURED = "Unstructured";
        public static final String DESTINATION_CODE = "DestinationCountryCode";
        public static final String INSTRUCTION_PRIORITY = "InstructionPriority";
        public static final String EXTENDED_PURPOSE = "ExtendedPurpose";
        public static final String CREDITOR = "Creditor";
        public static final String STREET_NAME = "StreetName";
        public static final String BUILDING_NUMBER = "BuildingNumber";
        public static final String POST_CODE = "PostCode";
        public static final String TOWN_NAME = "TownName";
        public static final String COUNTRY = "Country";
        public static final String ADDRESS_LINE = "AddressLine";
        public static final String COUNTRY_SUB_DIVISION = "CountrySubDivision";
        public static final String ADDRESS_TYPE = "AddressType";
        public static final String DEPARTMENT = "Department";
        public static final String SUB_DEPARTMENT = "SubDepartment";
        public static final String READ_REFUND_ACCOUNT = "ReadRefundAccount";
        public static final String FILE_TYPE_PAYMENT_INIT = "UK.OBIE.PaymentInitiation.3.1";
        public static final String FILE_TYPE_PAIN = "UK.OBIE.pain.001.001.08";

        public static final String ACCOUNT_REFRESH_TOKEN_LAST_AUTHORIZED_DATE_LIMIT =
                "AccountRefreshTokenLastAuthorizedDateLimit";
        public static final String PRIMARY = "primary";

        public static final String INSTRUCTION_IDENTIFICATION_TITLE = "Instruction Identification";
        public static final String END_TO_END_IDENTIFICATION_TITLE = "End to End Identification";
        public static final String REQUESTED_EXECUTION_DATE_TIME_TITLE = "Requested Execution Date Time";
        public static final String FIRST_PAYMENT_DATE_TIME_TITLE = "First Payment Date Time";
        public static final String FIRST_PAYMENT_AMOUNT_TITLE = "First Payment Amount";
        public static final String RECURRING_PAYMENT_AMOUNT_TITLE = "Recurring Payment Amount";
        public static final String FINAL_PAYMENT_AMOUNT_TITLE = "Final Payment Amount";
        public static final String CURRENCY_OF_TRANSFER_TITLE = "Currency of Transfer";
        public static final String INSTRUCTED_AMOUNT_TITLE = "Instructed Amount";
        public static final String AMOUNT_TITLE = "Amount";
        public static final String PERMISSION_TITLE = "Permission";
        public static final String CREDITOR_ACC_TITLE = "Creditor Account";
        public static final String FREQUENCY_TITLE = "Frequency";
        public static final String CURRENCY_TITLE = "Currency";
        public static final String LOCAL_INSTRUMENT_TITLE = "Local Instrument";
        public static final String FILE_TYPE_TITLE = "File Type";
        public static final String FILE_HASH_TITLE = "File Hash";
        public static final String FILE_REFERENCE_TITLE = "File Reference";
        public static final String NUM_OF_TRANSACTIONS_TITLE = "Number Of Transactions";
        public static final String CONTROL_SUM_TITLE = "Control Sum";
        public static final String PAYMENT_TYPE_TITLE = "Payment Type";
        public static final String X_IDEMPOTENCY_KEY = "x-idempotency-key";
        public static final String MAXIMUM_INDIVIDUAL_AMOUNT = "MaximumIndividualAmount";
        public static final String PERIODIC_LIMITS = "PeriodicLimits";
        public static final String CONTROL_PARAMETER_PERIOD_ALIGNMENT_TITLE = "Period Alignment";
        public static final String CONTROL_PARAMETER_MAX_INDIVIDUAL_AMOUNT_TITLE = "Maximum amount per payment";
        public static final String CONTROL_PARAMETER_AMOUNT_TITLE = "Maximum payment amount per ";
        public static final String ACTIVE_MAPPING_STATUS = "active";
        public static final String VRP_HISTORY_APP = "VRPHistorySubmissionApp";
        public static final String CONSENT_TYPE_CC = "consentType";
        public static final String RECORDS = "records";
        public static final String TIMESTAMP = "Timestamp";
        public static final String VRP_HISTORY = "VRPHistory";
        public static final String CONSENT_ID_CC = "consentId";
        public static final String COF_HISTORY_STREAM_APP = "COFAccessHistorySubmissionApp";
        public static final String REFERENCE_CC = "reference";
        public static final String AMOUNT_CC = "amount";
        public static final String CURRENCY_CC = "currency";
        public static final String INSTRUCTED_AMOUNT_CC = "instructedAmount";
        public static final String FUNDS_AVAILABLE_CC = "fundsAvailable";
        public static final String COF_HISTORY = "CoFHistory";
        public static final String TIMESTAMP_CC = "timestamp";

        /**
         * CheckIdempotency enumeration.
         */
        public enum CheckIdempotency {
                RETRY, FRAUDULENT, NEW
        }
}
