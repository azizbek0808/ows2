package uz.com.hibernate.base;

import java.util.stream.Stream;

public class PageStream<T extends _Entity> {
    private final Stream<T> stream;
    private final Long size;

    public PageStream(Stream<T> stream, Long size) {
        this.stream = stream;
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public Stream<T> stream() {
        return stream;
    }
}
