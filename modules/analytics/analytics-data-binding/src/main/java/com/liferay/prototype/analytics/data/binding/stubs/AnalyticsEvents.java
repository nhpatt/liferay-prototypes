
package com.liferay.prototype.analytics.data.binding.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "applicationId",
    "channel",
    "context",
    "events",
    "messageFormat"
})
public class AnalyticsEvents {

    @JsonProperty("applicationId")
    private String applicationId;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("context")
    private Context context;
    @JsonProperty("events")
    private List<Event> events = new ArrayList<Event>();
    @JsonProperty("messageFormat")
    private String messageFormat;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The applicationId
     */
    @JsonProperty("applicationId")
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 
     * @param applicationId
     *     The applicationId
     */
    @JsonProperty("applicationId")
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * 
     * @return
     *     The channel
     */
    @JsonProperty("channel")
    public String getChannel() {
        return channel;
    }

    /**
     * 
     * @param channel
     *     The channel
     */
    @JsonProperty("channel")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 
     * @return
     *     The context
     */
    @JsonProperty("context")
    public Context getContext() {
        return context;
    }

    /**
     * 
     * @param context
     *     The context
     */
    @JsonProperty("context")
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 
     * @return
     *     The events
     */
    @JsonProperty("events")
    public List<Event> getEvents() {
        return events;
    }

    /**
     * 
     * @param events
     *     The events
     */
    @JsonProperty("events")
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * 
     * @return
     *     The messageFormat
     */
    @JsonProperty("messageFormat")
    public String getMessageFormat() {
        return messageFormat;
    }

    /**
     * 
     * @param messageFormat
     *     The messageFormat
     */
    @JsonProperty("messageFormat")
    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
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
        return new HashCodeBuilder().append(applicationId).append(channel).append(context).append(events).append(messageFormat).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AnalyticsEvents) == false) {
            return false;
        }
        AnalyticsEvents rhs = ((AnalyticsEvents) other);
        return new EqualsBuilder().append(applicationId, rhs.applicationId).append(channel, rhs.channel).append(context, rhs.context).append(events, rhs.events).append(messageFormat, rhs.messageFormat).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
