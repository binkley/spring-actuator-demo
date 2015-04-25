package hello.health;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * {@code HelloWorldHealth} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:boxley@thoughtworks.com">Brian Oxley</a>
 * @todo Needs documentation
 */
@Component
public class RemoteHelloService
        extends AbstractHealthIndicator {
    private final DiscoveryClient discovery;

    @Inject
    public RemoteHelloService(final DiscoveryClient discovery) {
        this.discovery = discovery;
    }

    @Override
    protected void doHealthCheck(final Builder builder)
            throws Exception {
        final List<InstanceStatus> statii = discovery
                .getInstancesById("remote-hello").stream().
                        map(InstanceInfo::getStatus).
                        collect(toList());
        final long up = statii.stream().
                filter(s -> InstanceStatus.UP == s).
                count();

        if (!statii.isEmpty() && up == statii.size())
            builder.up();
        else
            builder.down();

        builder.withDetail("statuses", statii);
    }
}
