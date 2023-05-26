package io.openjob.server.common.util;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.List;

/**
 * @author inhere in.798@qq.com
 */
public class BeanMapperUtil {

    /**
     * Mapper factory
     */
    private static final MapperFactory NOT_NULL_MAPPER_FACTORY = new DefaultMapperFactory.Builder().mapNulls(false).build();


    private BeanMapperUtil() {

    }

    /**
     * Map object
     *
     * @param source           source
     * @param destinationClass targetClass
     * @return object or null
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return NOT_NULL_MAPPER_FACTORY.getMapperFacade().map(source, destinationClass);
    }

    /**
     * Map list
     *
     * @param sourceList       sourceList
     * @param sourceClass      sourceClass
     * @param destinationClass destinationClass
     * @return List
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return NOT_NULL_MAPPER_FACTORY.getMapperFacade().mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }
}
