package com.liferay.prototype.analytics.data.binding;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public interface JSONObjectMapper<T> {

	public T convert(String json) throws IOException;

	public String convert(T t) throws IOException;

}