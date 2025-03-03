package org.wso2.financial.services.uk.consent.endpoints.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@JsonTypeName("inline_response_200")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T12:33:30.949099+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class InlineResponse200   {
  private String eventId;
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
  private @Valid List<Object> operations = new ArrayList<>();
  private String failureReason;
  private String failureDescription;

  public InlineResponse200() {
  }

  /**
   **/
  public InlineResponse200 eventId(String eventId) {
    this.eventId = eventId;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("eventId")
  public String getEventId() {
    return eventId;
  }

  @JsonProperty("eventId")
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  /**
   * Indicates the outcome of the request. For a failed operation, this should be set to FAILED.
   **/
  public InlineResponse200 actionStatus(ActionStatusEnum actionStatus) {
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
   * Defines the success response.
   **/
  public InlineResponse200 operations(List<Object> operations) {
    this.operations = operations;
    return this;
  }

  
  @ApiModelProperty(value = "Defines the success response.")
  @JsonProperty("operations")
  public List<Object> getOperations() {
    return operations;
  }

  @JsonProperty("operations")
  public void setOperations(List<Object> operations) {
    this.operations = operations;
  }

  public InlineResponse200 addOperationsItem(Object operationsItem) {
    if (this.operations == null) {
      this.operations = new ArrayList<>();
    }

    this.operations.add(operationsItem);
    return this;
  }

  public InlineResponse200 removeOperationsItem(Object operationsItem) {
    if (operationsItem != null && this.operations != null) {
      this.operations.remove(operationsItem);
    }

    return this;
  }
  /**
   * Provides the reason for failing to validate consent.
   **/
  public InlineResponse200 failureReason(String failureReason) {
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
  public InlineResponse200 failureDescription(String failureDescription) {
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
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.eventId, inlineResponse200.eventId) &&
        Objects.equals(this.actionStatus, inlineResponse200.actionStatus) &&
        Objects.equals(this.operations, inlineResponse200.operations) &&
        Objects.equals(this.failureReason, inlineResponse200.failureReason) &&
        Objects.equals(this.failureDescription, inlineResponse200.failureDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, actionStatus, operations, failureReason, failureDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    actionStatus: ").append(toIndentedString(actionStatus)).append("\n");
    sb.append("    operations: ").append(toIndentedString(operations)).append("\n");
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

