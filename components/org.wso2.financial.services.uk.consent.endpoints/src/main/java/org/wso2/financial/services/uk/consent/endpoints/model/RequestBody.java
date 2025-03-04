package org.wso2.financial.services.uk.consent.endpoints.model;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RequestBody")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T12:33:30.949099+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class RequestBody   {
  private String eventId;
  private Event event;
  private AllowedOperation allowedOperation;

  public RequestBody() {
  }

  /**
   * A unique correlation identifier
   **/
  public RequestBody eventId(String eventId) {
    this.eventId = eventId;
    return this;
  }

  
  @ApiModelProperty(example = "Ec1wMjmiG8", value = "A unique correlation identifier")
  @JsonProperty("eventId")
  public String getEventId() {
    return eventId;
  }

  @JsonProperty("eventId")
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  /**
   **/
  public RequestBody event(Event event) {
    this.event = event;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("event")
  @Valid public Event getEvent() {
    return event;
  }

  @JsonProperty("event")
  public void setEvent(Event event) {
    this.event = event;
  }

  /**
   **/
  public RequestBody allowedOperation(AllowedOperation allowedOperation) {
    this.allowedOperation = allowedOperation;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("allowedOperation")
  @Valid public AllowedOperation getAllowedOperation() {
    return allowedOperation;
  }

  @JsonProperty("allowedOperation")
  public void setAllowedOperation(AllowedOperation allowedOperation) {
    this.allowedOperation = allowedOperation;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestBody requestBody = (RequestBody) o;
    return Objects.equals(this.eventId, requestBody.eventId) &&
        Objects.equals(this.event, requestBody.event) &&
        Objects.equals(this.allowedOperation, requestBody.allowedOperation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, event, allowedOperation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestBody {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    event: ").append(toIndentedString(event)).append("\n");
    sb.append("    allowedOperation: ").append(toIndentedString(allowedOperation)).append("\n");
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

