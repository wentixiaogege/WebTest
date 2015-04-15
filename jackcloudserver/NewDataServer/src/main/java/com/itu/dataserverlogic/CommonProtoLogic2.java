package com.itu.dataserverlogic;

import com.google.protobuf.Message;

public abstract class CommonProtoLogic2<T extends Message> extends ICommonLogic<T, String> {
	public abstract T executeActionBuffer(T t);
}
