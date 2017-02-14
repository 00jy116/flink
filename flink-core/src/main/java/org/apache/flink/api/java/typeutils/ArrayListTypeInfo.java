/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.api.java.typeutils;

import org.apache.flink.annotation.Public;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.ArrayListSerializer;

import java.util.ArrayList;

/**
 * A {@link TypeInformation} for the list types of the Java API.
 *
 * @param <T> The type of the elements in the list.
 */


@Public
public final class ArrayListTypeInfo<T> extends TypeInformation<ArrayList<T>> {

	private final TypeInformation<T> elementTypeInfo;

	public ArrayListTypeInfo(Class<T> elementTypeClass) {
		this.elementTypeInfo = TypeExtractor.createTypeInfo(elementTypeClass);
	}

	public ArrayListTypeInfo(TypeInformation<T> elementTypeInfo) {
		this.elementTypeInfo = elementTypeInfo;
	}

	public TypeInformation<T> getElementTypeInfo() {
		return elementTypeInfo;
	}

	@Override
	public boolean isBasicType() {
		return false;
	}

	@Override
	public boolean isTupleType() {
		return false;
	}

	@Override
	public int getArity() {
		return 0;
	}

	@Override
	public int getTotalFields() {
		return elementTypeInfo.getTotalFields();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<ArrayList<T>> getTypeClass() {
		return (Class<ArrayList<T>>)(new ArrayList<T>().getClass());
	}

	@Override
	public boolean isKeyType() {
		return false;
	}

	@Override
	public TypeSerializer<ArrayList<T>> createSerializer(ExecutionConfig config) {
		TypeSerializer<T> elementTypeSerializer = elementTypeInfo.createSerializer(config);
		return new ArrayListSerializer<>(elementTypeSerializer);
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArrayListTypeInfo) {
			@SuppressWarnings("unchecked")
			ArrayListTypeInfo<T> other = (ArrayListTypeInfo<T>) obj;

			return other.canEqual(this) && elementTypeInfo.equals(other.elementTypeInfo);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 31 * elementTypeInfo.hashCode() + 1;
	}

	@Override
	public boolean canEqual(Object obj) {
		return (obj instanceof ArrayListTypeInfo);
	}
}
