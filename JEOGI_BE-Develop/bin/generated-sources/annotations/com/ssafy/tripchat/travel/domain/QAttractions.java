package com.ssafy.tripchat.travel.domain;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QAttractions is a Querydsl query type for Attractions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttractions extends EntityPathBase<Attractions> {

    private static final long serialVersionUID = 59848876L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttractions attractions = new QAttractions("attractions");

    public final StringPath address = createString("address");

    public final QContentTypes contentTypes;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<Images, QImages> imgUrl = this.<Images, QImages>createList("imgUrl", Images.class, QImages.class, PathInits.DIRECT2);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final QLocals local;

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final QMetropolitans metropolitan;

    public final StringPath overview = createString("overview");

    public final com.ssafy.tripchat.reservation.domain.QParkingLots parkingLot;

    public final StringPath tel = createString("tel");

    public final StringPath title = createString("title");

    public QAttractions(String variable) {
        this(Attractions.class, forVariable(variable), INITS);
    }

    public QAttractions(Path<? extends Attractions> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttractions(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttractions(PathMetadata metadata, PathInits inits) {
        this(Attractions.class, metadata, inits);
    }

    public QAttractions(Class<? extends Attractions> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contentTypes = inits.isInitialized("contentTypes") ? new QContentTypes(forProperty("contentTypes")) : null;
        this.local = inits.isInitialized("local") ? new QLocals(forProperty("local")) : null;
        this.metropolitan = inits.isInitialized("metropolitan") ? new QMetropolitans(forProperty("metropolitan")) : null;
        this.parkingLot = inits.isInitialized("parkingLot") ? new com.ssafy.tripchat.reservation.domain.QParkingLots(forProperty("parkingLot"), inits.get("parkingLot")) : null;
    }

}

