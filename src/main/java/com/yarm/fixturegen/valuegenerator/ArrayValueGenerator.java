package com.yarm.fixturegen.valuegenerator;

import com.yarm.fixturegen.cache.CacheContext;
import com.yarm.fixturegen.utils.ClassUtils;
import com.yarm.fixturegen.Fixture;
import com.yarm.fixturegen.config.FixtureConfig;

import java.lang.reflect.*;

public final class ArrayValueGenerator implements ValueGenerator{

    private Type type;
    private FixtureConfig config;
    private Field field;

    public ArrayValueGenerator(Type type){
        this.type = type;
    }

    @Override
    public ArrayValueGenerator config(FixtureConfig config) {
        this.config = config;
        return this;
    }

    @Override
    public ArrayValueGenerator field(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public Object create() {
        try{
            int loopCount = config.getMaxCollectionSize();
            Class<?> aClass = ClassUtils.castToClass(type);

            if(loopCount==0){
                return Array.newInstance(aClass, 0);
            }
            Object objs = Array.newInstance(aClass, loopCount);
            for(int i=0;i<loopCount;i++){
                Array.set(objs, i, new Fixture().field(field).config(config).create(aClass));
            }
            return objs;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
