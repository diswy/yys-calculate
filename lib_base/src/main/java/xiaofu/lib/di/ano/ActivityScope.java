package xiaofu.lib.di.ano;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by @author xiaofu on 2018/12/19.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface ActivityScope {
}
