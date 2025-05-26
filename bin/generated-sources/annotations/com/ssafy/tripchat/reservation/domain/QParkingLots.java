package com.ssafy.tripchat.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParkingLots is a Querydsl query type for ParkingLots
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParkingLots extends EntityPathBase<ParkingLots> {

    private static final long serialVersionUID = -5054916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParkingLots parkingLots = new QParkingLots("parkingLots");

    public final com.ssafy.tripchat.travel.domain.QAttractions attraction;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> totalCount = createNumber("totalCount", Integer.class);

    public QParkingLots(String variable) {
        this(ParkingLots.class, forVariable(variable), INITS);
    }

    public QParkingLots(Path<? extends ParkingLots> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParkingLots(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParkingLots(PathMetadata metadata, PathInits inits) {
        this(ParkingLots.class, metadata, inits);
    }

    public QParkingLots(Class<? extends ParkingLots> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attraction = inits.isInitialized("attraction") ? new com.ssafy.tripchat.travel.domain.QAttractions(forProperty("attraction"), inits.get("attraction")) : null;
    }

}

