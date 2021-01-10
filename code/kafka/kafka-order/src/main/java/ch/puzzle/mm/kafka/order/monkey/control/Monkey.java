package ch.puzzle.mm.kafka.order.monkey.control;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import java.util.Random;

@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class Monkey {

    enum RateLimitType {
        BLOCK, FAIL
    }

    private static final Logger LOG = LoggerFactory.getLogger(ChaosMonkeyInterceptor.class);

    private boolean enabled = false;
    private double errorRate = 0.0D;
    private long latencyMs = 0L;
    private double permitsPerSec = Long.MAX_VALUE;
    private RateLimitType rateLimiterType = RateLimitType.BLOCK;

    @JsonbTransient
    private final Random random = new Random();

    @JsonbTransient
    private final RateLimiter rateLimiter = RateLimiter.create(permitsPerSec);

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    public long getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(long latencyMs) {
        this.latencyMs = latencyMs;
    }

    public double getPermitsPerSec() {
        return permitsPerSec;
    }

    public void setPermitsPerSec(double permitsPerSec) {
        this.permitsPerSec = permitsPerSec;
        this.rateLimiter.setRate(permitsPerSec);
    }

    public RateLimiter getRateLimiter() {
        return this.rateLimiter;
    }

    public RateLimitType getRateLimiterType() {
        return rateLimiterType;
    }

    public void setRateLimiterType(RateLimitType rateLimiterType) {
        this.rateLimiterType = rateLimiterType;
    }

    public void runErrorMonkey() {
        if (this.errorRate > 0.0D) {
            double strike = 1.0D - random.nextDouble();
            if (strike <= this.errorRate) {
                String msg = "ChaosMonkey strikes. ErrorRate=" + (this.errorRate * 100) + "%, CurrentStrike=" + String.format("%.2f", strike * 100);
                LOG.warn(msg);
                throw new InternalServerErrorException(msg);
            }
        }
    }

    public void runLatencyMonkey() {
        if (this.latencyMs > 0L) {
            LOG.warn("ChaosMonkey strikes. LatencyMs=" + (this.latencyMs));
            try {
                Thread.sleep(this.latencyMs);
            } catch (InterruptedException e) {
                /* ignore */
            }
        }
    }

    public void runRateLimiterMonkey() {
        if (this.permitsPerSec < Long.MAX_VALUE) {

            if (rateLimiterType == RateLimitType.BLOCK) {
                double waitedMs = this.rateLimiter.acquire(1);
                if (waitedMs >= 1) {
                    LOG.warn("ChaosMonkey strikes. Blocking RateLimiter. RateLimitPerSec: " + this.rateLimiter.getRate() + ", Throttled=" + (waitedMs));
                }
            } else {
                if (!this.rateLimiter.tryAcquire(1)) {
                    LOG.warn("ChaosMonkey strikes. Failing RateLimiter. RateLimitPerSec: " + this.rateLimiter.getRate());
                    throw new ClientErrorException("Too Many Requests. RateLimitPerSec: " + this.rateLimiter.getRate(), 429);
                }
            }
        }
    }
}
