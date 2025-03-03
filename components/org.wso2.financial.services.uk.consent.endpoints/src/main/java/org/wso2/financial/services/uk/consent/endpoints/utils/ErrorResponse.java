package org.wso2.financial.services.uk.consent.endpoints.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * When the external service responds with an ERROR state, it can return an HTTP status code of 400, 401, or 500, indicating either a validation failure or an issue processing the request.  
 **/
@ApiModel(description = "When the external service responds with an ERROR state, it can return an HTTP status code of 400, 401, or 500, indicating either a validation failure or an issue processing the request.  ")
@JsonTypeName("ErrorResponse")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T12:33:30.949099+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class ErrorResponse   {
  public enum ActionStatusEnum {

    ERROR(String.valueOf("ERROR"));


    private String value;

    ActionStatusEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Convert a String into String, as specified in the
     * <a href="https://download.oracle.com/otndocs/jcp/jaxrs-2_0-fr-eval-spec/index.html">See JAX RS 2.0 Specification, section 3.2, p. 12</a>
     */
    public static ActionStatusEnum fromString(String s) {
        for (ActionStatusEnum b : ActionStatusEnum.values()) {
            // using Objects.toString() to be safe if value type non-object type
            // because types like 'int' etc. will be auto-boxed
            if (Objects.toString(b.value).equals(s)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected string value '" + s + "'");
    }

    @JsonCreator
    public static ActionStatusEnum fromValue(String value) {
        for (ActionStatusEnum b : ActionStatusEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

  private ActionStatusEnum actionStatus;
  private String errorMessage;
  private String errorDescription;

  public ErrorResponse() {
  }

  /**
   * Indicates the outcome of the request. For an error operation, this should be set to ERROR.
   **/
  public ErrorResponse actionStatus(ActionStatusEnum actionStatus) {
    this.actionStatus = actionStatus;
    return this;
  }

  
  @ApiModelProperty(value = "Indicates the outcome of the request. For an error operation, this should be set to ERROR.")
  @JsonProperty("actionStatus")
  public ActionStatusEnum getActionStatus() {
    return actionStatus;
  }

  @JsonProperty("actionStatus")
  public void setActionStatus(ActionStatusEnum actionStatus) {
    this.actionStatus = actionStatus;
  }

  /**
   * The cause of the error.
   **/
  public ErrorResponse errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  
  @ApiModelProperty(value = "The cause of the error.")
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  @JsonProperty("errorMessage")
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * A detailed description of the error.
   **/
  public ErrorResponse errorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
    return this;
  }

  
  @ApiModelProperty(value = "A detailed description of the error.")
  @JsonProperty("errorDescription")
  public String getErrorDescription() {
    return errorDescription;
  }

  @JsonProperty("errorDescription")
  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.actionStatus, errorResponse.actionStatus) &&
        Objects.equals(this.errorMessage, errorResponse.errorMessage) &&
        Objects.equals(this.errorDescription, errorResponse.errorDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionStatus, errorMessage, errorDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    
    sb.append("    actionStatus: ").append(toIndentedString(actionStatus)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    errorDescription: ").append(toIndentedString(errorDescription)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

