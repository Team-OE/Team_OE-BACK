package project.backend.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLogoutToken is a Querydsl query type for LogoutToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLogoutToken extends EntityPathBase<LogoutToken> {

    private static final long serialVersionUID = 2003785565L;

    public static final QLogoutToken logoutToken = new QLogoutToken("logoutToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath token = createString("token");

    public QLogoutToken(String variable) {
        super(LogoutToken.class, forVariable(variable));
    }

    public QLogoutToken(Path<? extends LogoutToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLogoutToken(PathMetadata metadata) {
        super(LogoutToken.class, metadata);
    }

}

