package com.royalfoods.tastytables.factory.abstrcts;

import java.util.*;

public abstract class AbstractModelBuilderFactory {
    protected abstract <T> Optional build();
}
