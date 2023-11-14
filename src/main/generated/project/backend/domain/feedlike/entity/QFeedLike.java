package project.backend.domain.feedlike.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedLike is a Querydsl query type for FeedLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedLike extends EntityPathBase<FeedLike> {

    private static final long serialVersionUID = -842026206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedLike feedLike = new QFeedLike("feedLike");

    public final project.backend.domain.common.entity.QBaseEntity _super = new project.backend.domain.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final project.backend.domain.feed.entity.QFeed feed;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.backend.domain.member.entity.QMember member;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QFeedLike(String variable) {
        this(FeedLike.class, forVariable(variable), INITS);
    }

    public QFeedLike(Path<? extends FeedLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedLike(PathMetadata metadata, PathInits inits) {
        this(FeedLike.class, metadata, inits);
    }

    public QFeedLike(Class<? extends FeedLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feed = inits.isInitialized("feed") ? new project.backend.domain.feed.entity.QFeed(forProperty("feed"), inits.get("feed")) : null;
        this.member = inits.isInitialized("member") ? new project.backend.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

