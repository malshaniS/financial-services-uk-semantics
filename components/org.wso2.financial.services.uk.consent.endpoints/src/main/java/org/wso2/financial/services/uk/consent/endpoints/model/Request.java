package org.wso2.financial.services.uk.consent.endpoints.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("Request")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T12:33:30.949099+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class Request   {
  private Object payload;
  private @Valid List<Object> additionalHeaders = new ArrayList<>();
  private @Valid List<Object> additionalParams = new ArrayList<>();

  public Request() {
  }

  /**
   * The receipt used by Third parties which includes detailed information on data access request
   **/
  public Request consentReceipt(Object payload) {
    this.payload = payload;
    return this;
  }

  
  @ApiModelProperty(value = "The receipt used by Third parties which includes detailed information on data access request")
  @JsonProperty("payload")
  public Object getPayload() {
    return payload;
  }

  @JsonProperty("payload")
  public void setPayload(Object payload) {
    this.payload = payload;
  }

  /**
   * Any additional HTTP headers included in the consent validation request. These may contain custom information or metadata that the client has sent. All headers in request are not incorporated specially sensitive headers like ‘Authorization’, ‘Cookie’, etc.
   **/
  public Request additionalHeaders(List<Object> additionalHeaders) {
    this.additionalHeaders = additionalHeaders;
    return this;
  }

  
  @ApiModelProperty(value = "Any additional HTTP headers included in the consent validation request. These may contain custom information or metadata that the client has sent. All headers in request are not incorporated specially sensitive headers like ‘Authorization’, ‘Cookie’, etc.")
  @JsonProperty("additionalHeaders")
  public List<Object> getAdditionalHeaders() {
    return additionalHeaders;
  }

  @JsonProperty("additionalHeaders")
  public void setAdditionalHeaders(List<Object> additionalHeaders) {
    this.additionalHeaders = additionalHeaders;
  }

  public Request addAdditionalHeadersItem(Object additionalHeadersItem) {
    if (this.additionalHeaders == null) {
      this.additionalHeaders = new ArrayList<>();
    }

    this.additionalHeaders.add(additionalHeadersItem);
    return this;
  }

  public Request removeAdditionalHeadersItem(Object additionalHeadersItem) {
    if (additionalHeadersItem != null && this.additionalHeaders != null) {
      this.additionalHeaders.remove(additionalHeadersItem);
    }

    return this;
  }
  /**
   * Any additional parameters included in the consent validation request. These may be custom parameters defined by the client or necessary for specific flows.
   **/
  public Request additionalParams(List<Object> additionalParams) {
    this.additionalParams = additionalParams;
    return this;
  }

  
  @ApiModelProperty(value = "Any additional parameters included in the consent validation request. These may be custom parameters defined by the client or necessary for specific flows.")
  @JsonProperty("additionalParams")
  public List<Object> getAdditionalParams() {
    return additionalParams;
  }

  @JsonProperty("additionalParams")
  public void setAdditionalParams(List<Object> additionalParams) {
    this.additionalParams = additionalParams;
  }

  public Request addAdditionalParamsItem(Object additionalParamsItem) {
    if (this.additionalParams == null) {
      this.additionalParams = new ArrayList<>();
    }

    this.additionalParams.add(additionalParamsItem);
    return this;
  }

  public Request removeAdditionalParamsItem(Object additionalParamsItem) {
    if (additionalParamsItem != null && this.additionalParams != null) {
      this.additionalParams.remove(additionalParamsItem);
    }

    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Request request = (Request) o;
    return Objects.equals(this.payload, request.payload) &&
        Objects.equals(this.additionalHeaders, request.additionalHeaders) &&
        Objects.equals(this.additionalParams, request.additionalParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payload, additionalHeaders, additionalParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Request {\n");
    
    sb.append("    consentReceipt: ").append(toIndentedString(payload)).append("\n");
    sb.append("    additionalHeaders: ").append(toIndentedString(additionalHeaders)).append("\n");
    sb.append("    additionalParams: ").append(toIndentedString(additionalParams)).append("\n");
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

