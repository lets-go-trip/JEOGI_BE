package com.ssafy.tripchat.travel.domain;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QLocals is a Querydsl query type for Locals
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocals extends EntityPathBase<Locals> {

    private static final long serialVersionUID = -1966298200L;

    public static final QLocals locals = new QLocals("locals");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> metropolitanCode = createNumber("metropolitanCode", Integer.class);

    public final StringPath name = createString("name");

    public QLocals(String variable) {
        super(Locals.class, forVariable(variable));
    }

    public QLocals(Path<? extends Locals> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocals(PathMetadata metadata) {
        super(Locals.class, metadata);
    }

}

