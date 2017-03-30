
package com.liferay.prototype.analytics.storage.stubs;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "companyId",
    "deviceId",
    "deviceType",
    "languageId",
    "location",
    "signedIn",
    "userId"
})
public class Context {

    @JsonProperty("companyId")
    private Integer companyId;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deviceType")
    private String deviceType;
    @JsonProperty("languageId")
    private String languageId;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("signedIn")
    private Boolean signedIn;
    @JsonProperty("userId")
    private Integer userId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The companyId
     */
    @JsonProperty("companyId")
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId
     *     The companyId
     */
    @JsonProperty("companyId")
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 
     * @return
     *     The deviceId
     */
    @JsonProperty("deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 
     * @param deviceId
     *     The deviceId
     */
    @JsonProperty("deviceId")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 
     * @return
     *     The deviceType
     */
    @JsonProperty("deviceType")
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 
     * @param deviceType
     *     The deviceType
     */
    @JsonProperty("deviceType")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 
     * @return
     *     The languageId
     */
    @JsonProperty("languageId")
    public String getLanguageId() {
        return languageId;
    }

    /**
     * 
     * @param languageId
     *     The languageId
     */
    @JsonProperty("languageId")
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    /**
     * 
     * @return
     *     The location
     */
    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The signedIn
     */
    @JsonProperty("signedIn")
    public Boolean getSignedIn() {
        return signedIn;
    }

    /**
     * 
     * @param signedIn
     *     The signedIn
     */
    @JsonProperty("signedIn")
    public void setSignedIn(Boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * 
     * @return
     *     The userId
     */
    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The userId
     */
    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(companyId).append(deviceId).append(deviceType).append(languageId).append(location).append(signedIn).append(userId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Context) == false) {
            return false;
        }
        Context rhs = ((Context) other);
        return new EqualsBuilder().append(companyId, rhs.companyId).append(deviceId, rhs.deviceId).append(deviceType, rhs.deviceType).append(languageId, rhs.languageId).append(location, rhs.location).append(signedIn, rhs.signedIn).append(userId, rhs.userId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
