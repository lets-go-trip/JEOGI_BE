package com.ssafy.tripchat.travel.domain;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QMetropolitans is a Querydsl query type for Metropolitans
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMetropolitans extends EntityPathBase<Metropolitans> {

    private static final long serialVersionUID = 2103184775L;

    public static final QMetropolitans metropolitans = new QMetropolitans("metropolitans");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public QMetropolitans(String variable) {
        super(Metropolitans.class, forVariable(variable));
    }

    public QMetropolitans(Path<? extends Metropolitans> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMetropolitans(PathMetadata metadata) {
        super(Metropolitans.class, metadata);
    }

}

