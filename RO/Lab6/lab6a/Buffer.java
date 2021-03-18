package lab6a;

import java.util.concurrent.atomic.AtomicBoolean;

public class Buffer {
    private Integer[][] primary;
    private Integer[][] secondary;
    private AtomicBoolean was_read;
    private AtomicBoolean was_written;

    Buffer() {
        primary = secondary = null;
        was_read = new AtomicBoolean(true);
        was_written = new AtomicBoolean(false);
    }

    public void putInSecondary(Integer[][] data) {
        while (!was_read.get()) {
            Thread.yield();
        }
        primary = secondary != null ? secondary.clone() : null;
        secondary = data;
        was_read.set(false);
        was_written.set(true);
    }

    public Integer[][] getFromPrimary() {
        while (!was_written.get()) {
            Thread.yield();
        }
        was_written.set(false);
        was_read.set(true);
        return primary;
    }

}
