package com.ssafy.tripchat.travel.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContentTypes is a Querydsl query type for ContentTypes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentTypes extends EntityPathBase<ContentTypes> {

    private static final long serialVersionUID = -392706880L;

    public static final QContentTypes contentTypes = new QContentTypes("contentTypes");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath typeName = createString("typeName");

    public QContentTypes(String variable) {
        super(ContentTypes.class, forVariable(variable));
    }

    public QContentTypes(Path<? extends ContentTypes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentTypes(PathMetadata metadata) {
        super(ContentTypes.class, metadata);
    }

}

