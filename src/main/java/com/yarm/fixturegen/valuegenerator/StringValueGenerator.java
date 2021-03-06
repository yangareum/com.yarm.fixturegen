package com.yarm.fixturegen.valuegenerator;

import com.yarm.fixturegen.StringValueType;
import com.yarm.fixturegen.cache.CacheContext;
import com.yarm.fixturegen.cache.MetaCache;
import com.yarm.fixturegen.config.FixtureConfig;
import java.lang.reflect.Field;
import java.util.UUID;

public final class StringValueGenerator implements ValueGenerator<String>{

    private FixtureConfig config;
    private Field field;

    @Override
    public StringValueGenerator config(FixtureConfig config) {
        this.config = config;
        return this;
    }

    @Override
    public StringValueGenerator field(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public String create(){
        if(config.getValueType().equals(StringValueType.RANDOM)){
            return UUID.randomUUID().toString();
        }
        MetaCache metaCache = CacheContext.get(field);
        if(metaCache==null){
            return field.getName().toLowerCase();
        }
        return config.getTheme().getRedefinedValue(field, field.getName().toLowerCase() + (metaCache.getAssignCount()==0?"":metaCache.getAssignCount()));
    }
}
