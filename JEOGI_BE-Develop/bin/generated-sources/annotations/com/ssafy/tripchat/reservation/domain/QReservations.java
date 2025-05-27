package com.ssafy.tripchat.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.processing.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservations is a Querydsl query type for Reservations
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservations extends EntityPathBase<Reservations> {

    private static final long serialVersionUID = 442371909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservations reservations = new QReservations("reservations");

    public final com.ssafy.tripchat.global.domain.QBaseEntity _super = new com.ssafy.tripchat.global.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.ssafy.tripchat.member.domain.QMembers member;

    public final QParkingLots parkingLot;

    public final QReservationPeriod reservationPeriod;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReservations(String variable) {
        this(Reservations.class, forVariable(variable), INITS);
    }

    public QReservations(Path<? extends Reservations> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservations(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservations(PathMetadata metadata, PathInits inits) {
        this(Reservations.class, metadata, inits);
    }

    public QReservations(Class<? extends Reservations> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ssafy.tripchat.member.domain.QMembers(forProperty("member")) : null;
        this.parkingLot = inits.isInitialized("parkingLot") ? new QParkingLots(forProperty("parkingLot"), inits.get("parkingLot")) : null;
        this.reservationPeriod = inits.isInitialized("reservationPeriod") ? new QReservationPeriod(forProperty("reservationPeriod")) : null;
    }

}

