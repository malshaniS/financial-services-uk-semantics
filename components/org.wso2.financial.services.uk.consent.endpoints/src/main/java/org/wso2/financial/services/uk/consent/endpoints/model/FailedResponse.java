package org.wso2.financial.services.uk.consent.endpoints.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("FailedResponse")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T12:33:30.949099+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class FailedResponse   {
  public enum ActionStatusEnum {

    FAILED(String.valueOf("FAILED"));


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
  private String failureReason;
  private String failureDescription;

  public FailedResponse() {
  }

  /**
   * Indicates the outcome of the request. For a failed operation, this should be set to FAILED.
   **/
  public FailedResponse actionStatus(ActionStatusEnum actionStatus) {
    this.actionStatus = actionStatus;
    return this;
  }

  
  @ApiModelProperty(value = "Indicates the outcome of the request. For a failed operation, this should be set to FAILED.")
  @JsonProperty("actionStatus")
  public ActionStatusEnum getActionStatus() {
    return actionStatus;
  }

  @JsonProperty("actionStatus")
  public void setActionStatus(ActionStatusEnum actionStatus) {
    this.actionStatus = actionStatus;
  }

  /**
   * Provides the reason for failing to validate consent.
   **/
  public FailedResponse failureReason(String failureReason) {
    this.failureReason = failureReason;
    return this;
  }

  
  @ApiModelProperty(value = "Provides the reason for failing to validate consent.")
  @JsonProperty("failureReason")
  public String getFailureReason() {
    return failureReason;
  }

  @JsonProperty("failureReason")
  public void setFailureReason(String failureReason) {
    this.failureReason = failureReason;
  }

  /**
   * Offers a detailed explanation of the failure.
   **/
  public FailedResponse failureDescription(String failureDescription) {
    this.failureDescription = failureDescription;
    return this;
  }

  
  @ApiModelProperty(value = "Offers a detailed explanation of the failure.")
  @JsonProperty("failureDescription")
  public String getFailureDescription() {
    return failureDescription;
  }

  @JsonProperty("failureDescription")
  public void setFailureDescription(String failureDescription) {
    this.failureDescription = failureDescription;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FailedResponse failedResponse = (FailedResponse) o;
    return Objects.equals(this.actionStatus, failedResponse.actionStatus) &&
        Objects.equals(this.failureReason, failedResponse.failureReason) &&
        Objects.equals(this.failureDescription, failedResponse.failureDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionStatus, failureReason, failureDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FailedResponse {\n");
    
    sb.append("    actionStatus: ").append(toIndentedString(actionStatus)).append("\n");
    sb.append("    failureReason: ").append(toIndentedString(failureReason)).append("\n");
    sb.append("    failureDescription: ").append(toIndentedString(failureDescription)).append("\n");
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

