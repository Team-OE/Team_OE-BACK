package project.backend.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 975537420L;

    public static final QMember member = new QMember("member1");

    public final project.backend.domain.common.entity.QBaseEntity _super = new project.backend.domain.common.entity.QBaseEntity(this);

    public final ListPath<project.backend.domain.comment.entity.Comment, project.backend.domain.comment.entity.QComment> comments = this.<project.backend.domain.comment.entity.Comment, project.backend.domain.comment.entity.QComment>createList("comments", project.backend.domain.comment.entity.Comment.class, project.backend.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<project.backend.domain.feedlike.entity.FeedLike, project.backend.domain.feedlike.entity.QFeedLike> feedLikes = this.<project.backend.domain.feedlike.entity.FeedLike, project.backend.domain.feedlike.entity.QFeedLike>createList("feedLikes", project.backend.domain.feedlike.entity.FeedLike.class, project.backend.domain.feedlike.entity.QFeedLike.class, PathInits.DIRECT2);

    public final ListPath<project.backend.domain.feed.entity.Feed, project.backend.domain.feed.entity.QFeed> feeds = this.<project.backend.domain.feed.entity.Feed, project.backend.domain.feed.entity.QFeed>createList("feeds", project.backend.domain.feed.entity.Feed.class, project.backend.domain.feed.entity.QFeed.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileUrl = createString("profileUrl");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<SocialType> socialType = createEnum("socialType", SocialType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

