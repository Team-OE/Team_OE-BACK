package project.backend.domain.feed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = -478229420L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeed feed = new QFeed("feed");

    public final project.backend.domain.common.entity.QBaseEntity _super = new project.backend.domain.common.entity.QBaseEntity(this);

    public final project.backend.domain.category.entity.QCategory category;

    public final ListPath<project.backend.domain.comment.entity.Comment, project.backend.domain.comment.entity.QComment> comments = this.<project.backend.domain.comment.entity.Comment, project.backend.domain.comment.entity.QComment>createList("comments", project.backend.domain.comment.entity.Comment.class, project.backend.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<project.backend.domain.feedlike.entity.FeedLike, project.backend.domain.feedlike.entity.QFeedLike> feedLikes = this.<project.backend.domain.feedlike.entity.FeedLike, project.backend.domain.feedlike.entity.QFeedLike>createList("feedLikes", project.backend.domain.feedlike.entity.FeedLike.class, project.backend.domain.feedlike.entity.QFeedLike.class, PathInits.DIRECT2);

    public final StringPath hashtag = createString("hashtag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl1 = createString("imageUrl1");

    public final StringPath imageUrl2 = createString("imageUrl2");

    public final StringPath imageUrl3 = createString("imageUrl3");

    public final StringPath latitude = createString("latitude");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath longitude = createString("longitude");

    public final project.backend.domain.member.entity.QMember member;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QFeed(String variable) {
        this(Feed.class, forVariable(variable), INITS);
    }

    public QFeed(Path<? extends Feed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeed(PathMetadata metadata, PathInits inits) {
        this(Feed.class, metadata, inits);
    }

    public QFeed(Class<? extends Feed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new project.backend.domain.category.entity.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new project.backend.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

