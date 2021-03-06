package com.yarm.fixturegen.valuegenerator;

import com.yarm.fixturegen.NumberValueType;
import com.yarm.fixturegen.cache.CacheContext;
import com.yarm.fixturegen.cache.MetaCache;
import com.yarm.fixturegen.config.FixtureConfig;

import java.lang.reflect.Field;
import java.util.Random;

public final class FloatValueGenerator implements ValueGenerator<Float> {

    private FixtureConfig config;
    private Field field;

    @Override
    public FloatValueGenerator config(FixtureConfig config) {
        this.config = config;
        return this;
    }

    @Override
    public FloatValueGenerator field(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public Float create() {
        float leftLimit =  pow(10, config.getFloatDigitSize()-1);
        float rightLimit = leftLimit * 10;

        float generatedFloat = 0f;

        if(config.getNumberValueType().equals(NumberValueType.SEQUENCE)){
            MetaCache metaCache = CacheContext.get(field);
            if(metaCache==null){
                generatedFloat = leftLimit;
            }else{
                generatedFloat = leftLimit + metaCache.getAssignCount();
            }
        } else {
            generatedFloat = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
        }

        return config.getTheme().getRedefinedValue(field, generatedFloat);
    }

    private Integer pow(int a, int b){
        Integer result = 1;
        for(int i=0;i<b;i++){
            result *= a;
        }
        return result;
    }
}
