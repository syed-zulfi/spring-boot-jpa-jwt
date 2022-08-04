package com.royalfoods.tastytables.factory.abstrcts;

import java.time.*;

public abstract class AbstractResponseBuilderFactory<T> extends AbstractModelBuilderFactory{
    protected LocalDateTime timeStamp;
    protected String message;

}
