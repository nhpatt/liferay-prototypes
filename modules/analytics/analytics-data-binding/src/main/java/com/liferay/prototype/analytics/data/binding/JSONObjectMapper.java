package com.liferay.prototype.analytics.data.binding;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public interface JSONObjectMapper<T> {

	public T convert(InputStream inputStream) throws IOException;

	public T convert(String json) throws IOException;

	public String convert(T t) throws IOException;

}