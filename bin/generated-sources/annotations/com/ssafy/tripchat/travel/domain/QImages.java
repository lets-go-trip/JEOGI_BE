package com.ssafy.tripchat.travel.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImages is a Querydsl query type for Images
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImages extends EntityPathBase<Images> {

    private static final long serialVersionUID = -2054086728L;

    public static final QImages images = new QImages("images");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath url = createString("url");

    public QImages(String variable) {
        super(Images.class, forVariable(variable));
    }

    public QImages(Path<? extends Images> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImages(PathMetadata metadata) {
        super(Images.class, metadata);
    }

}

