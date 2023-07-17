package shay.finalproject.webcraeler.annotations;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile("BFS")
public @interface Bfs {
}


// @Retention(RetentionPolicy.RUNTIME)
// @Inherited
// @Documented
// @Qualifier
