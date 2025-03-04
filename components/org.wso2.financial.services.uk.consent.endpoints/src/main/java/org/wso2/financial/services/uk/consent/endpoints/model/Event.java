package org.wso2.financial.services.uk.consent.endpoints.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Defines the context data related to the pre consent validation event that needs to be shared with the custom service to process and execute.
 **/
@ApiModel(description = "Defines the context data related to the pre consent validation event that needs to be shared with the custom service to process and execute.")
@JsonTypeName("Event")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T12:33:30.949099+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class Event   {
  private Request request;

  public Event() {
  }

  /**
   **/
  public Event request(Request request) {
    this.request = request;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("request")
  @Valid public Request getRequest() {
    return request;
  }

  @JsonProperty("request")
  public void setRequest(Request request) {
    this.request = request;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(this.request, event.request);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Event {\n");
    
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
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

