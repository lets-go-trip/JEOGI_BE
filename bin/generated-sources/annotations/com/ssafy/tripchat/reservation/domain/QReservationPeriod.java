package com.ssafy.tripchat.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationPeriod is a Querydsl query type for ReservationPeriod
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReservationPeriod extends BeanPath<ReservationPeriod> {

    private static final long serialVersionUID = -1998797233L;

    public static final QReservationPeriod reservationPeriod = new QReservationPeriod("reservationPeriod");

    public final DateTimePath<java.time.LocalDateTime> endDateTime = createDateTime("endDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startDateTime = createDateTime("startDateTime", java.time.LocalDateTime.class);

    public QReservationPeriod(String variable) {
        super(ReservationPeriod.class, forVariable(variable));
    }

    public QReservationPeriod(Path<? extends ReservationPeriod> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationPeriod(PathMetadata metadata) {
        super(ReservationPeriod.class, metadata);
    }

}

