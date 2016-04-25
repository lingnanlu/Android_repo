package lingnanlu.github.io.daggerdemo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by rico on 4/10/2016.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {
}
